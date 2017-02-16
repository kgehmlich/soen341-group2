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

    @DynamoDBHashKey(attributeName = "ClassName")
    @DynamoDBAttribute(attributeName = "ClassName")
    public String getClassName() {
        return _className;
    }

    public void setClassName(final String _className) {
        this._className = _className;
    }
    @DynamoDBRangeKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }
    @DynamoDBAttribute(attributeName = "TA")
    public Double getTA() {
        return _tA;
    }

    public void setTA(final Double _tA) {
        this._tA = _tA;
    }

}
