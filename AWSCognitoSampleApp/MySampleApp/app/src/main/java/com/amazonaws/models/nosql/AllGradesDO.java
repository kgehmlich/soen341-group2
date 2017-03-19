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

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }
    @DynamoDBRangeKey(attributeName = "ClassName")
    @DynamoDBAttribute(attributeName = "ClassName")
    public String getClassName() {
        return _className;
    }

    public void setClassName(final String _className) {
        this._className = _className;
    }
    @DynamoDBAttribute(attributeName = "Grade")
    public Double getGrade() {
        return _grade;
    }

    public void setGrade(final Double _grade) {
        this._grade = _grade;
    }

}
