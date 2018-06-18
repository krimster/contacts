package com.erickrim.contacts.domain.model;

public class PhoneNumber {

    private final String mNumber;
    private final ContactDataType mType;

    public PhoneNumber(String number, ContactDataType type) {
        mNumber = number;
        mType= type;
    }

    public String getNumber() {
        return mNumber;
    }

    public ContactDataType getType() {
        return mType;
    }
}
