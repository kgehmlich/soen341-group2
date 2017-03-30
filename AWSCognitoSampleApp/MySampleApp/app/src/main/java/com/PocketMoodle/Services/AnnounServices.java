package com.PocketMoodle.Services;

/**
 * Created by Winterhart on 2017-03-26.
 */
import android.nfc.Tag;
import android.util.Log;
import com.amazonaws.AmazonClientException;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.models.nosql.AnnouncementDO;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.security.SecureRandom;
import java.math.BigInteger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
public class AnnounServices {
    private final static String TAG = "From AnnounService: ";
    static AmazonDynamoDB client = AWSMobileClient.defaultMobileClient().getDynamoDBClient();
    final String ACTUAL_USER_ID = AWSMobileClient.defaultMobileClient().getIdentityManager().getCachedUserID().toString();
    private final static String TABLE_NAME = "soengroup-mobilehub-1153046571-Announcement";
    List<Announcement> allAnnouncementForOneClases;

    /**
     * Create an ID for each Announcement
     */
    public final class AnnouncementIdentifierGenerator {
        private SecureRandom random = new SecureRandom();

        /**
         * Creating a  random Announcement ID
         * @return
         */
        public String nextAnnouncementId() {
            return new BigInteger(130, random).toString(32);
        }
    }
    public class Announcement {
        private String annouID;
        private String annouMainObj;
        private String annouDate;
        private String annouAuthor;
        private String annouTitle;
         Announcement(String id, String mainobj, String annoudate, String annouauthor, String title){
            this.annouID = id;
             this.annouMainObj = mainobj;
            this.annouDate = annoudate;
            this.annouAuthor = annouauthor;
             this.annouTitle = title;
        }
        public String getAnnouMainObj(){
            return this.annouMainObj;
        }
        public String getAnnouDate(){
            return this.annouDate;
        }
        public String getAnnouAuthor(){
            return  this.annouAuthor;
        }
        public String getAnnouTitle(){return  this.annouTitle;}
        public String getAnnouID(){return this.annouID;}

    }
    /**
     * This function will insert an announcement
     * @param classname
     * @param announcement
     * The parameter username, userId and Date are added automatically
     */
    public void InsertAnnouncement(String classname, String title, String announcement){
        // Fetch the default configured DynamoDB ObjectMapper
        final DynamoDBMapper DYNAMO_DB_MAPPER = AWSMobileClient.defaultMobileClient().getDynamoDBMapper();
        final AnnouncementDO anAnnoucement = new AnnouncementDO();
        final String USER_ID = AWSMobileClient.defaultMobileClient().getIdentityManager().getCachedUserID().toString();
        final String USERNAME = AWSMobileClient.defaultMobileClient().getIdentityManager().getUserName().toString();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:SS");
        Date date = new Date();
        final String CURRENT_DATE = dateFormat.format(date).toString();
        AnnouncementIdentifierGenerator aIG = new AnnouncementIdentifierGenerator();
        final String ANNOUNCEMENT_ID = aIG.nextAnnouncementId();

        anAnnoucement.setClassName(classname);
        anAnnoucement.setUserId(USER_ID);
        anAnnoucement.setMessage(announcement);
        anAnnoucement.setUsername(USERNAME);
        anAnnoucement.setDate(CURRENT_DATE);
        anAnnoucement.setID(ANNOUNCEMENT_ID);
        anAnnoucement.setTitle(title);




        AmazonClientException lastException = null;

        try {
            DYNAMO_DB_MAPPER.save(anAnnoucement);

        } catch (final AmazonClientException EX) {
            Log.e(TAG, EX.getMessage());
        }
    }

    /**
     * This function return a List of all announcement for one class
     * The List is made of obj. Announcement which have an Author, date and
     * main message
     * @param classname
     * @return List of type Announcement
     */
    public List<Announcement> GetAllAnnouncementsForClass(String classname){
        allAnnouncementForOneClases = null;
        allAnnouncementForOneClases = new ArrayList<Announcement>();

        DynamoDBMapper mapper = new DynamoDBMapper(client);
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":cn", new AttributeValue().withS(classname));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("ClassName = :cn")
                .withExpressionAttributeValues(eav);

        try{
            List<AnnouncementDO> announForOneClass = mapper.scan(AnnouncementDO.class, scanExpression);
            if(!announForOneClass.isEmpty()){
                for(AnnouncementDO announcementDO: announForOneClass){
                    Log.d(TAG, announcementDO.getMessage());
                    Announcement announcement =  new Announcement(announcementDO.getID(), announcementDO.getMessage(), announcementDO.getDate(), announcementDO.getUsername(), announcementDO.getTitle());
                    allAnnouncementForOneClases.add(announcement);

                }
            }

        }
        catch (Exception ee){
            Log.e(TAG, ee.getMessage());
        }
        return allAnnouncementForOneClases;
    }

    /**
     * Function to remove an announcement, the ID is inside the object Announcement
     * @param announcementid unique string linked to an announcement
     */
    public void RemoveAnAnnouncement(String announcementid, String classname){
        final DynamoDBMapper DYNAMO_DB_MAPPER = AWSMobileClient.defaultMobileClient().getDynamoDBMapper();
        final AnnouncementDO anAnnoucement = new AnnouncementDO();
        final String announcementID = announcementid;
        final String className = classname;
        anAnnoucement.setID(announcementID);
        anAnnoucement.setClassName(className);
        AmazonClientException lastException = null;
        try {
            DYNAMO_DB_MAPPER.delete(anAnnoucement);
        } catch (final AmazonClientException ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

}
