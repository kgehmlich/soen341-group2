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

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

public class GradesServices {

    private final static String TAG = "From GradeService ";
    static AmazonDynamoDB client = AWSMobileClient.defaultMobileClient().getDynamoDBClient();
    final String ACTUAL_USER_ID = AWSMobileClient.defaultMobileClient().getIdentityManager().getCachedUserID().toString();
    private final static String TABLE_NAME = "soengroup-mobilehub-1153046571-AllGrades";
    List<Grade> allGradeInOneClassForOneUser;

    /**
     * Grade abs data type for grade...
     */
    public class Grade {
        private String gradeID;
        private String className;
        private String titleForGrade;
        private String userId;
        private String userName;
        private double gradeForUser;

        public Grade(String gradeid, String classname, String title, String userid, String username, double gradeforuser) {
            this.gradeID = gradeid;
            this.className = classname;
            this.titleForGrade = title;
            this.userId = userid;
            this.userName = username;
            this.gradeForUser = gradeforuser;
        }

        public String getGradeID() {
            return this.gradeID;
        }

        public String getClassName() {
            return this.className;
        }

        public String getTitleForGrade() {
            return this.titleForGrade;
        }

        public String getUserId() {
            return this.userId;
        }

        public String getUserName() {
            return this.userName;
        }

        public double getGradeForUser() {
            return this.gradeForUser;
        }
    }

    /**
     * Create an ID for each Grade
     */
    public final class GradesIdentifierGenerator {
        private SecureRandom random = new SecureRandom();

        /**
         * Creating a  random Grade ID
         *
         * @return Random Grade ID
         */
        public String nextGradeId() {
            return new BigInteger(130, random).toString(32);
        }
    }

    /***
     * Insert a Grade for a user in a class...
     * @param username
     * @param userid
     * @param className
     * @param titleforgrade
     * @param gradeVal
     */
    public void InsertGrade(String username, String userid, String className, String titleforgrade, double gradeVal) {
        // Fetch the default configured DynamoDB ObjectMapper
        final DynamoDBMapper DYNAMO_DB_MAPPER = AWSMobileClient.defaultMobileClient().getDynamoDBMapper();
        final AllGradesDO grade = new AllGradesDO();
        GradesIdentifierGenerator gradesIdentifierGenerator = new GradesIdentifierGenerator();
        final String gradeID = gradesIdentifierGenerator.nextGradeId();

        grade.setUserID(userid);
        grade.setGradeID(gradeID);
        grade.setUsername(username);
        grade.setClassName(className);
        grade.setTitle(titleforgrade);
        grade.setGrade(gradeVal);

        AmazonClientException lastException = null;

        try {
            DYNAMO_DB_MAPPER.save(grade);

        } catch (final AmazonClientException EX) {
            Log.e(TAG, EX.getMessage());
        }
    }

    /**
     * Get all Grades for one user in one Class...
     *
     * @param className
     * @param userID
     * @return (double) Grade of the userID
     */
    public List<Grade> GetAllGradesInClassForOneUser(String className, String userID) {
        allGradeInOneClassForOneUser = new ArrayList<>();
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":us", new AttributeValue().withS(userID));
        eav.put(":cl", new AttributeValue().withS(className));

        DynamoDBScanExpression scan = new DynamoDBScanExpression()
                .withFilterExpression("UserID = :us AND ClassName = :cl")
                .withExpressionAttributeValues(eav);


        try {
            List<AllGradesDO> allGradeForOneUser = mapper.scan(AllGradesDO.class, scan);
            if (!allGradeForOneUser.isEmpty() && allGradeForOneUser.size() != 0) {
                for (AllGradesDO grade : allGradeForOneUser) {
                    Grade aGrade = new Grade(grade.getGradeID(), grade.getClassName(), grade.getTitle(), grade.getUserID(), grade.getUsername(), grade.getGrade());
                    allGradeInOneClassForOneUser.add(aGrade);


                }
            }
        } catch (Exception exTA) {
            Log.e(TAG, exTA.getMessage());
        }

        return allGradeInOneClassForOneUser;
    }

    /***
     * Update grade for one user with gradeID, className and the new grade...
     * @param gradeid
     * @param className
     * @param newGrade
     * @return
     */
    public String UpdateAGradeForUser(String gradeid, String className, double newGrade) {
        String status = "";
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        Map<String, AttributeValue> keySelect = new HashMap<String, AttributeValue>();
        keySelect.put("gradeID", new AttributeValue().withS(gradeid));
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
        } catch (Exception ee) {
            Log.e(TAG, ee.getMessage());
        }
        return status;
    }

    /***
     * Delete a grade for one user, with GradeID and classname
     * @param gradeid
     * @param classname
     */
    public void DeleteOneGradeForAUser(String gradeid, String classname) {
        final DynamoDBMapper DYNAMO_DB_MAPPER = AWSMobileClient.defaultMobileClient().getDynamoDBMapper();
        final AllGradesDO gradeDo = new AllGradesDO();
        final String gradeID = gradeid;
        gradeDo.setGradeID(gradeID);
        gradeDo.setClassName(classname);
        try {
            DYNAMO_DB_MAPPER.delete(gradeDo);

        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }

    }

}
