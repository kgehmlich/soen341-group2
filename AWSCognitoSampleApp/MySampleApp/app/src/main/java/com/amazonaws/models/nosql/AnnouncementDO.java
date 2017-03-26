package com.amazonaws.models.nosql;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "soengroup-mobilehub-1153046571-Announcement")

public class AnnouncementDO {
    private String _iD;
    private String _className;
    private String _date;
    private String _message;
    private String _userId;
    private String _username;

    @DynamoDBHashKey(attributeName = "ID")
    @DynamoDBAttribute(attributeName = "ID")
    public String getID() {
        return _iD;
    }

    public void setID(final String _iD) {
        this._iD = _iD;
    }
    @DynamoDBRangeKey(attributeName = "ClassName")
    @DynamoDBAttribute(attributeName = "ClassName")
    public String getClassName() {
        return _className;
    }

    public void setClassName(final String _className) {
        this._className = _className;
    }
    @DynamoDBAttribute(attributeName = "Date")
    public String getDate() {
        return _date;
    }

    public void setDate(final String _date) {
        this._date = _date;
    }
    @DynamoDBAttribute(attributeName = "Message")
    public String getMessage() {
        return _message;
    }

    public void setMessage(final String _message) {
        this._message = _message;
    }
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }
    @DynamoDBAttribute(attributeName = "username")
    public String getUsername() {
        return _username;
    }

    public void setUsername(final String _username) {
        this._username = _username;
    }

}
