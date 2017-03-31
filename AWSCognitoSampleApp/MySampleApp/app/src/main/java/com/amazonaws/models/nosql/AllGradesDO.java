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

@DynamoDBTable(tableName = "soengroup-mobilehub-1153046571-AllGrades")

public class AllGradesDO {
    private String _userId;
    private String _className;
    private Double _grade;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _USER_ID) {
        this._userId = _USER_ID;
    }
    @DynamoDBRangeKey(attributeName = "ClassName")
    @DynamoDBAttribute(attributeName = "ClassName")
    public String getClassName() {
        return _className;
    }

    public void setClassName(final String _CLASS_NAME) {
        this._className = _CLASS_NAME;
    }
    @DynamoDBAttribute(attributeName = "Grade")
    public Double getGrade() {
        return _grade;
    }

    public void setGrade(final Double _GRADE) {
        this._grade = _GRADE;
    }

}
