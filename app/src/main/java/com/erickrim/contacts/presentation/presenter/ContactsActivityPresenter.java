package com.erickrim.contacts.presentation.presenter;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import javax.inject.Inject;

public class ContactsActivityPresenter {

    private View mView;

    @Inject
    public ContactsActivityPresenter() {
    }

    public void startPresenting(View view) {
        mView = view;
        boolean hasReadPermission = mView.hasReadContactsPermission();
        processReadPermission(hasReadPermission);
    }

    public void permissionRequestedResult(@NonNull int[] grantResults) {
        int length = grantResults.length;
        if(length > 0) {
            int grantResult = grantResults[0];
            if(grantResult == PackageManager.PERMISSION_GRANTED) {
                mView.showContactListFragment();
            } else {
                mView.permissionDeniedFragment();
            }
        } else {
            mView.permissionDeniedFragment();
        }
    }

    private void processReadPermission(boolean isGranted) {
        if(isGranted) {
            mView.showContactListFragment();
        } else {
            mView.requestReadPermission();
        }
    }

    public interface View {

        boolean hasReadContactsPermission();

        void requestReadPermission();

        void showContactListFragment();

        void permissionDeniedFragment();
    }
}
