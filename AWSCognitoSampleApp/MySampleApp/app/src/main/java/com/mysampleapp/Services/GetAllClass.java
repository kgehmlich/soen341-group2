package com.mysampleapp.Services;

/**
 * Created by Winterhart on 2017-02-14.
 */

import android.support.annotation.NonNull;

import com.amazonaws.AmazonClientException;
import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.models.nosql.ListOfClassDO;
import com.amazonaws.models.nosql.UserDetailsDO;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeAction;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;


import java.util.*;

public class GetAllClass {


    public List<String> GetListOfClass() {
        // Fetch the default configured DynamoDB ObjectMapper

        AmazonDynamoDBClient client = AWSMobileClient.defaultMobileClient().getDynamoDBClient();

        List<String> AllClass = new List<String>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<String> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(T[] ts) {
                return null;
            }

            @Override
            public boolean add(String s) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> collection) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends String> collection) {
                return false;
            }

            @Override
            public boolean addAll(int i, Collection<? extends String> collection) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> collection) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public String get(int i) {
                return null;
            }

            @Override
            public String set(int i, String s) {
                return null;
            }

            @Override
            public void add(int i, String s) {

            }

            @Override
            public String remove(int i) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<String> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<String> listIterator(int i) {
                return null;
            }

            @NonNull
            @Override
            public List<String> subList(int i, int i1) {
                return null;
            }
        };
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

                    AllClass.add(classN);

                }
                catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }





        }while (result.getLastEvaluatedKey() != null);

        return AllClass;
    }
}
