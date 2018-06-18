package com.erickrim.contacts.data.repository;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.annimon.stream.Optional;
import com.erickrim.contacts.data.entity.ContactEntity;
import com.erickrim.contacts.data.entity.ContactEntityData;
import com.erickrim.contacts.data.entity.mapper.ContactEntityMapper;
import com.erickrim.contacts.exceptions.ContactNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeviceContactEntityRepositoryTest {

    public static final Uri URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

    public static final Long CONTACT_ID = 1234L;
    public static final String DISPLAY_NAME = "display_name";
    public static final String GIVEN_NAME = "given_name";
    public static final String NICKNAME = "nickname";
    public static final String FAMILY_NAME = "family_name";
    public static final String PHONE_NUMBER = "07869555555";
    public static final int PHONE_NUMBER_TYPE = 2;
    public static final String EMAIL_ADDRESS = "contact@email.com";
    public static final int EMAIL_ADDRESS_TYPE = 1;
    public static final String PHOTO_URL = "http://photo/2";

    @Mock
    private ContentResolver mContentResolver;

    @Mock
    private ContactEntityMapper mContactEntityMapper;

    @Mock
    private Cursor mCursor;

    @Mock
    private ContactEntityData mPhoneContactEntityData;

    @Mock
    private ContactEntityData mEmailContactEntityData;

    @Mock
    private ContactEntity mContactEntity;

    @Mock
    private List<ContactEntity> result = new ArrayList<>();

    @Mock
    Optional<ContactEntity> contactEntityOptional;

    DeviceContactEntityRepository mSut;

    @Before
    public void setup() {
        mSut = new DeviceContactEntityRepository(mContentResolver, mContactEntityMapper);
    }

    @Test
    public void returnsContactIdAndDisplayName() {
        result = Arrays.asList(mContactEntity);
        given(mContentResolver.query(URI, null, null, null, null)).willReturn(mCursor);
        given(mCursor.getCount()).willReturn(1);
        given(mCursor.moveToNext()).willReturn(Boolean.TRUE);

        mSut.getContactEntities().test();

        verify(mCursor).getLong(mCursor.getColumnIndex(ContactsContract.Data.CONTACT_ID));
        verify(mCursor).getLong(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
    }

    @Test
    public void returnsException() {
        given(mContentResolver.query(URI, null, null, null, null)).willReturn(mCursor);
        given(mCursor.getCount()).willReturn(0);

        TestObserver<List<ContactEntity>> testObserver = mSut.getContactEntities().test();

        testObserver.assertError(ContactNotFoundException.class);
        testObserver.assertNotComplete();
    }

}
