package com.erickrim.contacts.presentation.model;

import android.support.annotation.StringRes;

public class UiEmail {

    private String mEmail;
    @StringRes
    private int mType;

    public UiEmail(String email, int type) {
        mEmail = email;
        mType = type;
    }

    public String getEmail() {
        return mEmail;
    }

    public int getType() {
        return mType;
    }
}
