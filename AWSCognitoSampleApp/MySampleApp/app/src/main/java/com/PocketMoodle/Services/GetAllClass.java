package com.PocketMoodle.Services;

/**
 * Created by Winterhart on 2017-02-14.
 */

import android.util.Log;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.models.nosql.ListOfClassDO;
import com.amazonaws.models.nosql.UserDetailsDO;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import java.util.*;

public class GetAllClass {
    static List AllClass;
    static List AllClassRegisteredIn;
    private final static String TAG = "From GetAllClass: ";
    static AmazonDynamoDB client = AWSMobileClient.defaultMobileClient().getDynamoDBClient();
    final String ActualUserID = AWSMobileClient.defaultMobileClient().getIdentityManager().getCachedUserID().toString();

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


        return AllClassRegisteredIn;
    }
}
