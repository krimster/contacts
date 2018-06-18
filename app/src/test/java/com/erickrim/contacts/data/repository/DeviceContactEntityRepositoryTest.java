package com.erickrim.contacts.data.repository;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.annimon.stream.Optional;
import com.erickrim.contacts.data.entity.ContactEntity;
import com.erickrim.contacts.data.entity.ContactEntityData;
import com.erickrim.contacts.data.entity.mapper.ContactEntityMapper;

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
    public void returnsContactEntity() {
//        result = Arrays.asList(mContactEntity);
//        given(mContentResolver.query(URI, null, null, null, null)).willReturn(mCursor);
//        given(mCursor.moveToNext()).willReturn(Boolean.TRUE);
//        given(mCursor.getCount()).willReturn(1);
//        given(mCursor.getLong(mCursor.getColumnIndex(ContactsContract.Data.CONTACT_ID))).willReturn(CONTACT_ID);
//        given(mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))).willReturn(DISPLAY_NAME);
//        given(mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME))).willReturn(FAMILY_NAME);
//        given(mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Nickname.NAME))).willReturn(NICKNAME);
//        given(mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME))).willReturn(GIVEN_NAME);
//        given(mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))).willReturn(PHONE_NUMBER);
//        given(mCursor.getInt(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE))).willReturn(PHONE_NUMBER_TYPE);
//        given(mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS))).willReturn(EMAIL_ADDRESS);
//        given(mCursor.getInt(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE))).willReturn(EMAIL_ADDRESS_TYPE);
//        given(mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))).willReturn(PHOTO_URL);
//        given(mPhoneContactEntityData.getDataType()).willReturn(PHONE_NUMBER_TYPE);
//        given(mPhoneContactEntityData.getDataValue()).willReturn(PHONE_NUMBER);
//        given(mContactEntityMapper.toContactEntity(CONTACT_ID, DISPLAY_NAME, DISPLAY_NAME, DISPLAY_NAME, DISPLAY_NAME, mPhoneContactEntityData, mEmailContactEntityData, DISPLAY_NAME))
//                .willReturn(contactEntityOptional);
//        given(contactEntityOptional.isPresent()).willReturn(true);
//        given(contactEntityOptional.get()).willReturn(mContactEntity);
//
//        TestObserver<List<ContactEntity>> testObserver = mSut.getContactEntities().test();
//
//
//        testObserver.awaitTerminalEvent();
//        testObserver
//                .assertNoErrors()
//                .assertValueCount(1);
//
//        verify(mContactEntityMapper);
    }


}
