package com.erickrim.contacts.presentation.view.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.erickrim.contacts.R;
import com.erickrim.contacts.di.ApplicationComponent;
import com.erickrim.contacts.di.DaggerContactsActivityComponent;
import com.erickrim.contacts.presentation.model.UiContact;
import com.erickrim.contacts.presentation.presenter.ContactsActivityPresenter;
import com.erickrim.contacts.presentation.view.fragment.ContactDetailFragment;
import com.erickrim.contacts.presentation.view.fragment.ContactListFragment;
import com.erickrim.contacts.presentation.view.fragment.ContactsNotFoundFragment;
import com.erickrim.contacts.presentation.view.fragment.PermissionDeniedFragment;

import javax.inject.Inject;

public class ContactsActivity extends BaseActivity implements
        ContactListFragment.Listener,
        ContactDetailFragment.Listener,
        ContactsActivityPresenter.View {

    @Inject
    ContactsActivityPresenter mPresenter;

    @Override
    public void setUpComponent(ApplicationComponent appComponent) {
        DaggerContactsActivityComponent.builder()
                .applicationComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public int getLayout() {
        return R.layout.contacts_activity_layout;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.startPresenting(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPresenter.permissionRequestedResult(grantResults);
    }

    @Override
    public void requestReadPermission() {
        String requestPermissionArray[] = {Manifest.permission.READ_CONTACTS};
        ActivityCompat.requestPermissions(this, requestPermissionArray, 1);
    }

    @Override
    public void showContactListFragment() {
        showFragment(ContactListFragment.newInstance(), ContactListFragment.TAG);
    }

    @Override
    public void permissionDeniedFragment() {
        showFragment(PermissionDeniedFragment.newInstance(), PermissionDeniedFragment.TAG);
    }

    @Override
    public void showContactNotFound() {
        showFragment(ContactsNotFoundFragment.newInstance(), ContactsNotFoundFragment.TAG);
    }

    @Override
    public void showContactDetails(UiContact uiContact) {
        showFragment(ContactDetailFragment.newInstance(uiContact.getId()), ContactDetailFragment.TAG);
    }

    @Override
    public boolean hasReadContactsPermission() {
       return hasReadPermission() && !shouldRequestReadPermission();
    }

    private boolean hasReadPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED;
    }

    private boolean shouldRequestReadPermission() {
        return ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_CONTACTS);
    }
}
