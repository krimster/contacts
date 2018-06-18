package com.erickrim.contacts.di;

import android.app.Activity;
import android.content.Context;

import com.erickrim.contacts.presentation.view.activity.ContactsActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactsActivityModule {

    private Activity mContext;

    public ContactsActivityModule(Context context) {
        mContext = (Activity) context;
    }

    @Provides
    ContactsActivity contactsActivity() {
        return (ContactsActivity) mContext;
    }

}
