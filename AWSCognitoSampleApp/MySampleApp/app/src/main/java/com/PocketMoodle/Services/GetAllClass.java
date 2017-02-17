package com.PocketMoodle.Services;

/**
 * Created by Winterhart on 2017-02-14.
 */

import android.util.Log;

import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;


import java.util.*;

public class GetAllClass {
    static List AllClass;
    private final static String TAG = "Activity";
    public List<String> GetListOfClass() {
        // Fetch the default configured DynamoDB ObjectMapper

        AmazonDynamoDBClient client = AWSMobileClient.defaultMobileClient().getDynamoDBClient();
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
}
