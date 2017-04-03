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
    private String _gradeID;
    private String _className;
    private Double _grade;
    private String _title;
    private String _userID;
    private String _username;

    @DynamoDBHashKey(attributeName = "gradeID")
    @DynamoDBAttribute(attributeName = "gradeID")
    public String getGradeID() {
        return _gradeID;
    }

    public void setGradeID(final String _gradeID) {
        this._gradeID = _gradeID;
    }
    @DynamoDBRangeKey(attributeName = "ClassName")
    @DynamoDBIndexRangeKey(attributeName = "ClassName", globalSecondaryIndexName = "IndexUserID")
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
    @DynamoDBAttribute(attributeName = "Title")
    public String getTitle() {
        return _title;
    }

    public void setTitle(final String _title) {
        this._title = _title;
    }
    @DynamoDBIndexHashKey(attributeName = "UserID", globalSecondaryIndexName = "IndexUserID")
    public String getUserID() {
        return _userID;
    }

    public void setUserID(final String _userID) {
        this._userID = _userID;
    }
    @DynamoDBAttribute(attributeName = "Username")
    public String getUsername() {
        return _username;
    }

    public void setUsername(final String _username) {
        this._username = _username;
    }

}
