package com.PocketMoodle.Services;

import android.util.Log;

import com.amazonaws.AmazonClientException;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.models.nosql.UserDetailsDO;

/**
 * Created by Kyle on 2017-03-04.
 */

public class RemoveClass {

    private final static String TAG = "FromRemoveClass";


    public void removeClass(String ClassName) {

        final DynamoDBMapper dynamoDBMapper = AWSMobileClient.defaultMobileClient().getDynamoDBMapper();
        final UserDetailsDO note = new UserDetailsDO(); // Initialize the Notes Object
        final String UserID = AWSMobileClient.defaultMobileClient().getIdentityManager().getCachedUserID().toString();
        note.setUserId(UserID);
        note.setClassName(ClassName);


        AmazonClientException lastException = null;

        try {
            dynamoDBMapper.delete(note);
        } catch (final AmazonClientException ex) {
            Log.e(TAG, ex.getMessage());
        }

    }
}
