package com.PocketMoodle.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.PocketMoodle.R;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Kyle on 2017-03-19.
 */

public class DocumentServices {

    private AmazonS3 _s3;
    private final static String BUCKET_NAME = "soengroup-userfiles-mobilehub-1153046571";
    private final static String DELIMITER = "/";

    private Context _context;

    private File downloadCompleteFile;

    public DocumentServices(Context context) {
        _context = context;
        _s3 = new AmazonS3Client(AWSMobileClient.defaultMobileClient().getIdentityManager().getCredentialsProvider());
    }


    public void upload(File file, String documentName, String className) {
        // Create transfer utility (performs actual upload asynchronously)
        final TransferUtility transferUtility = new TransferUtility(_s3, _context);

        // Upload selected file to Amazon S3 bucket
        TransferObserver transferObserver = transferUtility.upload(
                BUCKET_NAME,
                className + DELIMITER + documentName,
                file
        );

        transferObserver.setTransferListener(new UploadListener());
    }


    public ArrayList<String> listDocumentsForClass(String className) {
        ListObjectsRequest lor = new ListObjectsRequest().withBucketName(BUCKET_NAME).withPrefix(className + DELIMITER).withDelimiter(DELIMITER);
        ObjectListing objList = _s3.listObjects(lor);

        ArrayList<String> documents = new ArrayList<>();

        for (S3ObjectSummary obj : objList.getObjectSummaries()) {
            String key = obj.getKey();
            key = key.substring(key.indexOf(DELIMITER) + 1);
            documents.add(key);
        }

        return documents;
    }


    /**
     * Listener for file uploads
     * Logs progress and state changes. Shows a Toast when upload is complete.
     */
    private class UploadListener implements TransferListener {

        private final static String TAG = "S3 Upload";

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


    /**
     * Downloads the specified document from AWS.
     *
     * @param documentName   The document's filename
     * @param className      The class under which the document is stored
     * @param downloadedFile The location where the downloaded file will be stored
     */
    public void download(String documentName, String className, File downloadedFile) {

        downloadCompleteFile = downloadedFile;

        // Create transfer utility (performs actual upload asynchronously)
        final TransferUtility transferUtility = new TransferUtility(_s3, _context);

        // Upload selected file to Amazon S3 bucket
        TransferObserver transferObserver = transferUtility.download(
                BUCKET_NAME,
                className + DELIMITER + documentName,
                downloadedFile
        );

        transferObserver.setTransferListener(new DownloadListener());
    }

    /**
     * Listener for file uploads
     * Logs progress and state changes. Shows a Toast when upload is complete.
     */
    private class DownloadListener implements TransferListener {

        private final static String TAG = "S3 Download";

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
                Toast.makeText(_context, "Download complete", Toast.LENGTH_LONG).show();

                // Show notification to allow file to be opened (heavily borrowed from stack overflow)
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.fromFile(downloadCompleteFile));

                PendingIntent pendingIntent = PendingIntent.getActivity(_context, 0, intent, 0);

                Notification notification = new NotificationCompat.Builder(_context).setContentTitle("Download Complete").setContentText(downloadCompleteFile.getName()).setSmallIcon(R.drawable.concordia).setContentIntent(pendingIntent).build();
                notification.flags |= Notification.FLAG_AUTO_CANCEL;

                NotificationManager notificationManager = (NotificationManager) _context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0, notification);
            }
        }
    }
}
