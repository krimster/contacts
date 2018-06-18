package com.erickrim.contacts;

import android.app.Application;
import android.content.Context;

import com.erickrim.contacts.di.ApplicationComponent;
import com.erickrim.contacts.di.ApplicationModule;
import com.erickrim.contacts.di.DaggerApplicationComponent;

public class ContactsApplication extends Application {

    private ApplicationComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        setupGraph();
    }

    private void setupGraph() {
        mComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public static ContactsApplication getApp(Context context) {
        return (ContactsApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return mComponent;
    }

}
