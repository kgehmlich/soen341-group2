package com.PocketMoodle.Services;

/**
 * Created by Winterhart on 2017-03-04.
 */
import android.os.Bundle;
import android.util.Log;

import com.amazonaws.mobile.content.ContentItem;
import com.amazonaws.mobile.content.ContentProgressListener;
import com.amazonaws.mobile.content.UserFileManager;
import com.amazonaws.regions.Regions;

import  java.io.*;
/**
 * This class will be used to store services on document manipulations with AWS S3 bucket
 */
public class DocumentServices {
    private UserFileManager UFileMana;
    private boolean status = false;
    protected String error = "";
    /** The s3 bucket. */
    private String bucket;

    /** The S3 bucket region. */
    private Regions region;

    /** The s3 Prefix at which the UserFileManager is rooted. */
    private String prefix;
    private final static String TAG = "FROM/SERVICE_DOC-Upload";
    public static final String S3_PREFIX_PUBLIC = "public/";
    public static final String S3_PREFIX_PRIVATE = "private/";
    public static final String S3_PREFIX_PROTECTED = "protected/";
    public static final String S3_PREFIX_UPLOADS = "uploads/";

    /** Bundle key for retrieving the name of the S3 Bucket. */
    public static final String BUNDLE_ARGS_S3_BUCKET = "bucket";

    /** Bundle key for retrieving the s3 prefix at which to root the content manager. */
    public static final String BUNDLE_ARGS_S3_PREFIX = "prefix";

    /** Bundle key for retrieving the name of the S3 Bucket region. */
    public static final String BUNDLE_ARGS_S3_REGION = "region";

    /** Permission Request Code (Must be < 256). */
    private static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 93;

    public DocumentServices(){

    }
    /**
     * Can upload document using this method...to continue
     * @param PathToFile : pass the path to the file in String format
     * @return
     */
    public boolean UploadAFile(String PathToFile){
            final File file = new File(PathToFile);
            UFileMana.uploadContent(file, PathToFile, new ContentProgressListener() {

            @Override
            public void onSuccess(ContentItem contentItem) {
                status = true;
            }

            @Override
            public void onProgressUpdate(String filePath, boolean isWaiting, long bytesCurrent, long bytesTotal) {

            }

            @Override
            public void onError(String filePath, Exception ex) {
                status = false;
                error = ex.getMessage();

            }
        });
        Log.e(TAG, error);
        return status;
    }
}
