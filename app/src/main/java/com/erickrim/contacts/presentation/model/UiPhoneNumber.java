package com.erickrim.contacts.presentation.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

public class UiPhoneNumber {

    private String mNumber;
    @StringRes
    private int mType;
    @DrawableRes
    private int mCountryFlag;

    public UiPhoneNumber(String number, int type, int flag) {
        mNumber = number;
        mType = type;
        mCountryFlag = flag;
    }

    public String getNumber() {
        return mNumber;
    }

    public int getType() {
        return mType;
    }

    public int getCountryFlag() {
        return mCountryFlag;
    }
}
