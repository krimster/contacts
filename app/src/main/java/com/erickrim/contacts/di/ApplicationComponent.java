package com.erickrim.contacts.di;

import android.content.Context;

import com.erickrim.contacts.rx.InteractorSchedulers;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                ApplicationModule.class
        }
)
public interface ApplicationComponent {

    Context getContext();
    InteractorSchedulers getInteractorSchedulers();

}
