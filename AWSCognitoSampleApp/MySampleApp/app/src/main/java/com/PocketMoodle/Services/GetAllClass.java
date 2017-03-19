package com.PocketMoodle.Services;

/**
 * Created by Winterhart on 2017-02-14.
 */

import android.util.Log;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.auth.AWSCognitoIdentityProvider;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobile.api.idjk9qys1cb9.UserAndClassMobileHubClient;
import com.amazonaws.mobileconnectors.apigateway.ApiRequest;
import com.amazonaws.mobileconnectors.apigateway.ApiResponse;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.models.nosql.ListOfClassDO;
import com.amazonaws.models.nosql.UserDetailsDO;
import com.amazonaws.regions.Region;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentity;
import com.amazonaws.services.cognitoidentity.model.GetCredentialsForIdentityRequest;
import com.amazonaws.services.cognitoidentity.model.GetCredentialsForIdentityResult;
import com.amazonaws.services.cognitoidentity.model.GetIdRequest;
import com.amazonaws.services.cognitoidentity.model.GetIdResult;
import com.amazonaws.services.cognitoidentity.model.GetOpenIdTokenRequest;
import com.amazonaws.services.cognitoidentity.model.GetOpenIdTokenResult;
import com.amazonaws.services.cognitoidentityprovider.model.GetUserRequest;
import com.amazonaws.services.cognitoidentityprovider.model.GetUserResult;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.s3.AmazonS3Client;

import java.util.*;

public class GetAllClass {
    static List AllClass;
    static List AllClassRegisteredIn;
    private final static String TAG = "From GetAllClass: ";
    static AmazonDynamoDB client = AWSMobileClient.defaultMobileClient().getDynamoDBClient();
    final String ActualUserID = AWSMobileClient.defaultMobileClient().getIdentityManager().getCachedUserID().toString();
    List<User> AllUserInThisClass;

    class User{
        private String ID;
        private Integer TaOrStu;
        User(String id, Integer taorstu){
            this.ID = id;
            this.TaOrStu = taorstu;
        }
    }
    /**
     * This function get all classes on table ListOfClass and return it
     * @return List of all classes on pocketMoodle...
     */
    public List<String> GetListOfClass() {
        AllClass = null;
        AllClass = new ArrayList<String>();

        Map<String, String> attributeName = new HashMap<String, String>();
        attributeName.put("#ClassName", "ClassName");

        ScanResult result = null;

        do{
            ScanRequest re = new ScanRequest();
            re.setTableName("soengroup-mobilehub-1153046571-ListOfClass");


            if(result != null){
                re.setExclusiveStartKey(result.getLastEvaluatedKey());
            }

            result = client.scan(re);

            List<Map<String, AttributeValue>> rows = result.getItems();
            for(Map<String, AttributeValue> map: rows){
                try{
                    AttributeValue v = map.get("ClassName");
                    String classN = v.getS();
                     Log.d(TAG, classN);
                    AllClass.add(classN);

                }
                catch(Exception e){
                    Log.e(TAG, e.getMessage());
                }
            }
        }while (result.getLastEvaluatedKey() != null);
        if(AllClass.size() > 0){
            Log.d(TAG, "MORe than 0");
        }
        else{Log.d(TAG, "LOWER 0 in ALLCLASS");}
        return AllClass;
    }

    /**
     * This class will get all class linked to a user
     * @return All Class Linked to a user
     */
    public List<String> GetAllClassRegisteredIn(){
        AllClassRegisteredIn = null;
        AllClassRegisteredIn = new ArrayList<String>();
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        Log.i(TAG, ActualUserID);
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":ud", new AttributeValue().withS(ActualUserID));


        DynamoDBScanExpression ScanExpression = new DynamoDBScanExpression()
                .withFilterExpression("userId = :ud")
                .withExpressionAttributeValues(eav);

        try{
            List<UserDetailsDO> ClassesOfTheUser = mapper.scan(UserDetailsDO.class, ScanExpression);
            for(UserDetailsDO Classes: ClassesOfTheUser ){

                if(Classes.getClassName() != null){
                    AllClassRegisteredIn.add(Classes.getClassName());
                    Log.d(TAG, Classes.getClassName());
                }

            }
        }
        catch (Exception ex2){
            Log.e(TAG, ex2.getMessage());
        }

        GetAllUsersInAClass("COEN 341");
        return AllClassRegisteredIn;
    }

    /**
     * This class is returning a List of user details in the same class...
     * @param TargetClass
     * @return
     */
    public List<User> GetAllUsersInAClass(String TargetClass){
        AllUserInThisClass = null;
        AllUserInThisClass = new ArrayList<User>();
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        //Log.i(TAG, ActualUserID);
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":cn", new AttributeValue().withS(TargetClass));


        DynamoDBScanExpression ScanExpression = new DynamoDBScanExpression()
                .withFilterExpression("ClassName = :cn")
                .withExpressionAttributeValues(eav);


        try{
            List<UserDetailsDO> UsersD = mapper.scan(UserDetailsDO.class, ScanExpression);
            for(UserDetailsDO users: UsersD ){

                if(users != null){
                    Log.d(TAG, users.getUsername());
                    Log.d(TAG, users.getTA().toString());

                }

            }
        }
        catch (Exception ex2){
            Log.e(TAG, ex2.getMessage());
        }



        return AllUserInThisClass;
    }

}
