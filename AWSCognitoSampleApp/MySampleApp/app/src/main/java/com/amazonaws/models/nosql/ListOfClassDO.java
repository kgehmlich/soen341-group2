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

@DynamoDBTable(tableName = "soengroup-mobilehub-1153046571-ListOfClass")

public class ListOfClassDO {
    private String _className;
    private String _classSection;

    @DynamoDBHashKey(attributeName = "ClassName")
    @DynamoDBAttribute(attributeName = "ClassName")
    public String getClassName() {
        return _className;
    }

    public void setClassName(final String _className) {
        this._className = _className;
    }
    @DynamoDBRangeKey(attributeName = "ClassSection")
    @DynamoDBAttribute(attributeName = "ClassSection")
    public String getClassSection() {
        return _classSection;
    }

    public void setClassSection(final String _classSection) {
        this._classSection = _classSection;
    }

}
