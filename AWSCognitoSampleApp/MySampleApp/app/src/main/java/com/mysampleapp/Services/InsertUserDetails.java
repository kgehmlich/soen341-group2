package com.mysampleapp.Services;


import com.amazonaws.AmazonClientException;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.models.nosql.UserDetailsDO;

/**
 * Created by Winterhart on 2017-02-14.
 */

public class InsertUserDetails  {

    // Post a class in DB

    /**
     * Function to post details about user, add a class to a userId
     * @param ClassName
     * @param TA
     */
    public void insertData(String ClassName, double TA) {
        // Fetch the default configured DynamoDB ObjectMapper
        final DynamoDBMapper dynamoDBMapper = AWSMobileClient.defaultMobileClient().getDynamoDBMapper();
        final UserDetailsDO note = new UserDetailsDO(); // Initialize the Notes Object

        note.setUserId(AWSMobileClient.defaultMobileClient().getIdentityManager().getCachedUserID().toString());
        note.setClassName(ClassName);
        note.setTA(TA);



        AmazonClientException lastException = null;

        try {
            dynamoDBMapper.save(note);
        } catch (final AmazonClientException ex) {

            //Log.e(LOG_TAG, "Failed saving item : " + ex.getMessage(), ex);
            //lastException = ex;
            System.out.println("Failed saving item: " + ex.getMessage());
        }
    }
}
