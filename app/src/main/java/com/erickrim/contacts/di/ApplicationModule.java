package com.erickrim.contacts.di;

import android.app.Application;
import android.content.Context;

import com.erickrim.contacts.ContactsApplication;
import com.erickrim.contacts.rx.AndroidInteractorSchedulers;
import com.erickrim.contacts.rx.InteractorSchedulers;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private ContactsApplication app;

    public ApplicationModule(ContactsApplication app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return app;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return app;
    }

    @Provides
    @Singleton
    InteractorSchedulers provideInteractorSchedulers() {
        return new AndroidInteractorSchedulers();
    }

}
