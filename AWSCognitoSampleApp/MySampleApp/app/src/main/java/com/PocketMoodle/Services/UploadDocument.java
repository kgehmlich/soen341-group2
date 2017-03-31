package com.PocketMoodle.Services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kyle on 2017-03-19.
 */

public class UploadDocument {

    private AmazonS3 _s3;
    private final static String BUCKET_NAME = "soengroup-userfiles-mobilehub-1153046571";

    private Context _context;

    public UploadDocument(Context context){
        _context = context;
        _s3 = new AmazonS3Client(AWSMobileClient.defaultMobileClient().getIdentityManager().getCredentialsProvider());
    }


    public void upload(File file, String documentTitle, String className) {
        // Build metadata
        ObjectMetadata objectMetadata = new ObjectMetadata();
        Map<String, String> fileMetadata = new HashMap<>();
        fileMetadata.put("title", documentTitle);

        // Create transfer utility (performs actual upload asynchronously)
        final TransferUtility transferUtility = new TransferUtility(_s3, _context);

        // Upload selected file to Amazon S3 bucket
        TransferObserver transferObserver = transferUtility.upload(
                BUCKET_NAME,
                className+"/"+file.getName(),
                file,
                objectMetadata
        );

        transferObserver.setTransferListener(new UploadListener());
    }





    /**
     * Listener for file uploads
     * Logs progress and state changes. Shows a Toast when upload is complete.
     */
    private class UploadListener implements TransferListener {

        private final static String TAG = "S3";

        // Simply updates the UI list when notified.
        @Override
        public void onError(int id, Exception e) {
            Log.e(TAG, "Error during upload: " + id, e);
        }

        @Override
        public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
            Log.d(TAG, String.format("onProgressChanged: %d, total: %d, current: %d",
                    id, bytesTotal, bytesCurrent));
        }

        @Override
        public void onStateChanged(int id, TransferState newState) {
            Log.d(TAG, "onStateChanged: " + id + ", " + newState);

            if (newState == TransferState.COMPLETED) {
                Toast.makeText(_context, "Upload complete", Toast.LENGTH_LONG).show();
            }
        }
    }
}
