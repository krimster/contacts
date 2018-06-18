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

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class DeviceContactEntityRepository implements ContactEntityRepository {

    private final ContentResolver mContentResolver;
    private final ContactEntityMapper mContactEntityMapper;
    private final Uri mContentUri;

    public DeviceContactEntityRepository(ContentResolver contentResolver,
                                         ContactEntityMapper contactEntityMapper) {
        mContentResolver = contentResolver;
        mContactEntityMapper = contactEntityMapper;
        mContentUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    }

    @Override
    public Single<List<ContactEntity>> getContactEntities() {
        return Single.create(emitter -> {
            Cursor cursor = mContentResolver.query(mContentUri,
                    null,null, null, null);
            if (isValidCursor(cursor)) {
                List<ContactEntity> contactEntities = getContactEntities(cursor);
                if (contactEntities.size() > 0) {
                    emitter.onSuccess(contactEntities);
                } else {
                    emitter.onError(new ContactNotFoundException("No Contacts available"));
                }
            } else {
                emitter.onError(new ContactNotFoundException("No Contacts available"));
            }
        });
    }

    @Override
    public Single<ContactEntity> getContactEntity(String entityId) {
        return Single.create(emitter -> {
            String whereClauseBuf = ContactsContract.Data.RAW_CONTACT_ID + "=" + entityId;
            Cursor cursor = mContentResolver.query(mContentUri,
                    null , whereClauseBuf, null, null);
            if (isValidCursor(cursor)) {
                List<ContactEntity> contactEntities = getContactEntities(cursor);
                if (contactEntities.size() > 0) {
                    emitter.onSuccess(contactEntities.get(0));
                } else {
                    emitter.onError(new ContactNotFoundException("No Contacts available"));
                }
            } else {
                emitter.onError(new ContactNotFoundException("No Contacts available"));
            }
        });
    }

    private List<ContactEntity> getContactEntities(Cursor cursor) {
        List<ContactEntity> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            long contactId = cursor.getLong(cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID));
            String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String givenName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
            String familyName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
            String nickName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Nickname.NAME));

            String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            int phoneTypeInt = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
            ContactEntityData phoneContactEntityData = new ContactEntityData(phoneTypeInt, phoneNumber);

            String emailAddress = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
            int emailType = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
            ContactEntityData emailContactEntityData = new ContactEntityData(emailType, emailAddress);

            String company = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY));
            String department = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DEPARTMENT));
            String title = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
            String jobDescription = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.JOB_DESCRIPTION));
            String officeLocation = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.OFFICE_LOCATION));

            String postalAddress = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.SipAddress.SIP_ADDRESS));
            int addressTypeInt = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.SipAddress.TYPE));
            ContactEntityData postalAddressContactEntityData = new ContactEntityData(addressTypeInt, postalAddress);

            String country = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
            String city = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
            String region = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
            String street = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
            String postcode = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
            int postType = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));

            String identity = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Identity.IDENTITY));
            String namespace = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Identity.NAMESPACE));

            String photo = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
            String photoFileId = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_FILE_ID));

            String websiteUrl = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Website.URL));
            int websiteTypeInt = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Website.TYPE));
            ContactEntityData websiteContactEntityData = new ContactEntityData(websiteTypeInt, websiteUrl);

            String note = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));

            Optional<ContactEntity> optionalContactEntity = mContactEntityMapper.toContactEntity(
                    contactId, displayName, givenName, familyName, nickName,
                    phoneContactEntityData, emailContactEntityData, photo);

            if (optionalContactEntity.isPresent()) {
                result.add(optionalContactEntity.get());
            }
        }
        cursor.close();
        return result;
    }

    private boolean isValidCursor(Cursor cursor) {
        return cursor != null
                && cursor.getCount() > 0;
    }
}
