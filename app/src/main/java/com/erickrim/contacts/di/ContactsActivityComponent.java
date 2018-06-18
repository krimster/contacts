package com.erickrim.contacts.di;

import com.erickrim.contacts.di.scopes.PerActivity;
import com.erickrim.contacts.presentation.view.activity.ContactsActivity;

import dagger.Component;

@PerActivity
@Component(
        dependencies = ApplicationComponent.class,
        modules = ContactsModule.class
)
public interface ContactsActivityComponent {

    void inject(ContactsActivity contactsActivity);
}
