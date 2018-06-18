package com.erickrim.contacts.presentation.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ContactsActivityPresenterTest {

    @Mock
    ContactsActivityPresenter.View mView;

    private ContactsActivityPresenter mSut;

    @Before
    public void setup() {
        mSut = new ContactsActivityPresenter();
    }

    @Test
    public void showsContactListFragment() {
        given(mView.hasReadContactsPermission()).willReturn(Boolean.TRUE);

        mSut.startPresenting(mView);

        verify(mView).showContactListFragment();
    }

    @Test
    public void requestsReadPermission() {
        given(mView.hasReadContactsPermission()).willReturn(Boolean.FALSE);

        mSut.startPresenting(mView);

        verify(mView).requestReadPermission();
    }

    @Test
    public void showsContactListFragmentAfterPermissionGranted() {
        given(mView.hasReadContactsPermission()).willReturn(Boolean.FALSE);

        mSut.startPresenting(mView);
        mSut.permissionRequestedResult(new int[] {0});

        verify(mView).requestReadPermission();
        verify(mView).showContactListFragment();
    }

    @Test
    public void showsPermissionDeniedFragmentAfterPermissionDenied() {
        given(mView.hasReadContactsPermission()).willReturn(Boolean.FALSE);

        mSut.startPresenting(mView);
        mSut.permissionRequestedResult(new int[] {});

        verify(mView).requestReadPermission();
        verify(mView).permissionDeniedFragment();
    }

}
