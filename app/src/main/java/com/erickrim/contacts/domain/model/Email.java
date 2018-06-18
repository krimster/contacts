package com.erickrim.contacts.domain.model;

public class Email {

    private final String mEmail;
    private final ContactDataType mType;

    public Email(String email, ContactDataType type) {
        mEmail = email;
        mType = type;
    }

    public String getEmail() {
        return mEmail;
    }

    public ContactDataType getType() {
        return mType;
    }
}
