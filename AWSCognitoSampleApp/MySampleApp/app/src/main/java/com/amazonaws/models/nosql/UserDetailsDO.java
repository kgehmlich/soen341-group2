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

@DynamoDBTable(tableName = "soengroup-mobilehub-1153046571-UserDetails")

public class UserDetailsDO {
    private String _className;
    private String _userId;
    private Double _tA;
    private String _userName;

    @DynamoDBHashKey(attributeName = "ClassName")
    @DynamoDBAttribute(attributeName = "ClassName")
    public String getClassName() {
        return _className;
    }

    public void setClassName(final String _CLASS_NAME) {
        this._className = _CLASS_NAME;
    }
    @DynamoDBRangeKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _USER_ID) {
        this._userId = _USER_ID;
    }
    @DynamoDBAttribute(attributeName = "TA")
    public Double getTA() {
        return _tA;
    }

    public void setTA(final Double _TA) {
        this._tA = _TA;
    }
    @DynamoDBAttribute(attributeName = "Username")
    public String getUsername() {
        return _userName;
    }

    public void setUsername(final String _USER_NAME) {
        this._userName = _USER_NAME;
    }

}
