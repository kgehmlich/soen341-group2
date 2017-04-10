package com.PocketMoodle.Services;


import android.util.Log;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCognitoIdentityProvider;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobile.user.IdentityManager;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.models.nosql.UserDetailsDO;

/**
 * Created by Winterhart on 2017-02-14.
 */

public class InsertUserDetails  {

    // Post a class in DB
    private final static String TAG = "FromInsertUserDetails:";
    /**
     * Function to post details about user, add a class to a userId
     * Updated to included the user ID...
     * @param ClassName
     * @param TA
     */
    public void insertData(String ClassName, double TA) {
        // Fetch the default configured DynamoDB ObjectMapper
        final DynamoDBMapper DYNAMO_DB_MAPPER = AWSMobileClient.defaultMobileClient().getDynamoDBMapper();
        final UserDetailsDO NOTE = new UserDetailsDO(); // Initialize the Notes Object
        final String USER_ID = AWSMobileClient.defaultMobileClient().getIdentityManager().getCachedUserID().toString();
        final String USERNAME = AWSMobileClient.defaultMobileClient().getIdentityManager().getUserName().toString();


        NOTE.setUserId(USER_ID);
        NOTE.setClassName(ClassName);
        NOTE.setUsername(USERNAME);
        NOTE.setTA(TA);

        try {
            DYNAMO_DB_MAPPER.save(NOTE);
        } catch (final AmazonClientException EX) {
            Log.e(TAG, EX.getMessage());
        }
    }
}
