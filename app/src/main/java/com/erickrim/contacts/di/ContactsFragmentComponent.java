package com.erickrim.contacts.di;

import com.erickrim.contacts.di.scopes.PerFragment;
import com.erickrim.contacts.presentation.view.fragment.ContactDetailFragment;
import com.erickrim.contacts.presentation.view.fragment.ContactListFragment;

import dagger.Component;

@PerFragment
@Component(
        dependencies = ApplicationComponent.class,
        modules = ContactsModule.class
)
public interface ContactsFragmentComponent {

    void inject(ContactListFragment contactListFragment);

    void inject(ContactDetailFragment contactDetailFragment);
}
