package com.PocketMoodle.Services;

/**
 * Created by Winterhart on 2017-03-25.
 */
import android.util.Log;
import com.amazonaws.AmazonClientException;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.models.nosql.AllGradesDO;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateItemResult;

import java.util.*;

public class GradesServices {

    private final static String TAG = "From GetAllClass: ";
    static AmazonDynamoDB client = AWSMobileClient.defaultMobileClient().getDynamoDBClient();
    final String ACTUAL_USER_ID = AWSMobileClient.defaultMobileClient().getIdentityManager().getCachedUserID().toString();
    private final static String TABLE_NAME = "soengroup-mobilehub-1153046571-AllGrades";

    /**
     * Insert a new Grade in AllGrade Table
     * @param userID
     * @param className
     * @param gradeVal must be a double
     */
    public void InsertGrade(String userID, String className, double gradeVal){
        // Fetch the default configured DynamoDB ObjectMapper
        final DynamoDBMapper DYNAMO_DB_MAPPER = AWSMobileClient.defaultMobileClient().getDynamoDBMapper();
        final AllGradesDO grade = new AllGradesDO(); // Initialize the Notes Object
        final String USER_ID = AWSMobileClient.defaultMobileClient().getIdentityManager().getCachedUserID().toString();
        final String USERNAME = AWSMobileClient.defaultMobileClient().getIdentityManager().getUserName().toString();

        grade.setUserId(userID);
        grade.setClassName(className);
        grade.setGrade(gradeVal);

        AmazonClientException lastException = null;

        try {
            DYNAMO_DB_MAPPER.save(grade);

        } catch (final AmazonClientException EX) {
            Log.e(TAG, EX.getMessage());
        }
    }

    /**
     * Get the Grade for one user in one Class...
     * @param className
     * @param userID
     * @return (double) Grade of the userID
     */
    public double GetGradeInClass(String className, String userID){
        double gradeOfUser = 0;
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":us", new AttributeValue().withS(userID));
        eav.put(":cl", new AttributeValue().withS(className));

        DynamoDBScanExpression scan = new DynamoDBScanExpression()
                .withFilterExpression("userId = :us AND ClassName = :cl")
                .withExpressionAttributeValues(eav);


        try{
            List<AllGradesDO> uniqueGrade = mapper.scan(AllGradesDO.class, scan);
            if(!uniqueGrade.isEmpty() && uniqueGrade.size() == 1){
                gradeOfUser = uniqueGrade.get(0).getGrade();
            }
        }
        catch (Exception exTA){
            Log.e(TAG, exTA.getMessage());
        }

        return gradeOfUser;
    }

    /**
     *
     * @param userID
     * @param className
     * @param newGrade
     * @return status if the update was a sucess or not...
     */
    public String UpdateAGrade(String userID, String className, double newGrade){
        String status = "";
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        Map<String, AttributeValue> keySelect = new HashMap<String, AttributeValue>();
        keySelect.put("userId", new AttributeValue().withS(userID));
        keySelect.put("ClassName", new AttributeValue().withS(className));

        Map<String, AttributeValue> eav2 = new HashMap<String, AttributeValue>();
        eav2.put(":gr", new AttributeValue().withN(String.valueOf(newGrade)));

        UpdateItemRequest updateRequest = new UpdateItemRequest()
                .withTableName(TABLE_NAME)
                .withKey(keySelect)
                .withUpdateExpression("set Grade = :gr")
                .withExpressionAttributeValues(eav2);

        try {
            UpdateItemResult resultOfUpdate = client.updateItem(updateRequest);
            status = "OK";
        }
        catch (Exception ee){
            Log.e(TAG, ee.getMessage());
        }
        return status;
    }


}
