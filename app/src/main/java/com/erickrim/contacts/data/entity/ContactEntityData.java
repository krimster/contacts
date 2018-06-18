package com.erickrim.contacts.data.entity;

public class ContactEntityData {

    private int dataType;
    private String dataValue;

    public ContactEntityData(int dataType, String dataValue) {
        this.dataType = dataType;
        this.dataValue = dataValue;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

}
