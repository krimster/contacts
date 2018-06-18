package com.erickrim.contacts.di;

import android.content.Context;

import com.erickrim.contacts.data.entity.mapper.ContactEntityMapper;
import com.erickrim.contacts.data.repository.ContactEntityRepository;
import com.erickrim.contacts.data.repository.DeviceContactEntityRepository;
import com.erickrim.contacts.presentation.model.mapper.UiContactMapper;
import com.erickrim.contacts.presentation.view.adapter.ContactListAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactsModule {

    @Provides
    ContactListAdapter contactListAdapter() {
        return new ContactListAdapter();
    }

    @Provides
    ContactEntityMapper contactEntityMapper() {
        return new ContactEntityMapper();
    }

    @Provides
    ContactEntityRepository contactEntityRepository(Context context, ContactEntityMapper contactEntityMapper) {
        return new DeviceContactEntityRepository(context.getContentResolver(), contactEntityMapper);
    }

    @Provides
    UiContactMapper uiContactMapper() {
        return new UiContactMapper();
    }

}
