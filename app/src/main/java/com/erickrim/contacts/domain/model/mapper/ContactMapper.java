package com.erickrim.contacts.domain.model.mapper;

import android.provider.ContactsContract;

import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.erickrim.contacts.data.entity.ContactEntity;
import com.erickrim.contacts.data.entity.ContactEntityData;
import com.erickrim.contacts.domain.model.Contact;
import com.erickrim.contacts.domain.model.ContactDataType;
import com.erickrim.contacts.domain.model.Email;
import com.erickrim.contacts.domain.model.PhoneNumber;
import com.erickrim.contacts.exceptions.ContactNotFoundException;

import java.util.List;

import javax.inject.Inject;

public class ContactMapper {

    @Inject
    public ContactMapper() {
    }

    public List<Contact> toContacts(List<ContactEntity> contactEntities) throws Exception {
        if (contactEntities != null && contactEntities.size() > 0) {
            return Stream.of(contactEntities)
                    .map(this::createContact)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
        } else {
            throw new ContactNotFoundException();
        }

    }

    public Contact toContact(ContactEntity contactEntity) throws ContactNotFoundException {
        Optional<Contact> contactOptional = createContact(contactEntity);
        if (contactOptional.isPresent()) {
            return contactOptional.get();
        } else {
            throw new ContactNotFoundException();
        }
    }


    private Optional<Contact> createContact(ContactEntity contactEntity) {
        if (contactEntity != null) {
            long id = contactEntity.getContactId();
            String displayName = getDisplayName(contactEntity).trim();
            Email email = getEmailAddress(contactEntity.getEmailAddress());
            PhoneNumber phoneNumber = getPhoneNumber(contactEntity.getPhoneNumber());
            String photo = getPhoto(contactEntity);

            Contact contact = new Contact.Builder()
                    .withId(id)
                    .displayName(displayName)
                    .email(email)
                    .phoneNumber(phoneNumber)
                    .photo(photo)
                    .build();

            return isValidContact(contact) ? Optional.of(contact) : Optional.empty();

        } else {
            return Optional.empty();
        }
    }

    private String getDisplayName(ContactEntity contactEntity) {
        if (contactEntity.getDisplayName() != null) {
            return contactEntity.getDisplayName();
        } else {
            return contactEntity.getGivenName() + " " + contactEntity.getFamilyName();
        }
    }

    private Email getEmailAddress(ContactEntityData contactEntityData) {
        if (contactEntityData != null && isValidValue(contactEntityData.getDataValue())) {
            String email = contactEntityData.getDataValue();
            ContactDataType type = getEmailType(contactEntityData.getDataType());
            return new Email(email, type);
        } else {
            return null;
        }
    }

    private PhoneNumber getPhoneNumber(ContactEntityData contactEntityData) {
        if (contactEntityData != null && isValidValue(contactEntityData.getDataValue())) {
            String number = contactEntityData.getDataValue();
            ContactDataType type = getPhoneNumberType(contactEntityData.getDataType());
            return new PhoneNumber(number, type);
        }
        return null;
    }

    private ContactDataType getEmailType(int dataType) {
       switch (dataType) {
           case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
               return ContactDataType.HOME;
           case ContactsContract.CommonDataKinds.Email.TYPE_WORK:
               return ContactDataType.WORK;
           default:
               return ContactDataType.OTHER;
       }
    }

    private ContactDataType getPhoneNumberType(int dataType) {
        switch (dataType) {
            case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                return ContactDataType.HOME;
            case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                return ContactDataType.WORK;
            case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                return ContactDataType.MOBILE;
            default:
                return ContactDataType.OTHER;
        }
    }

    private String getPhoto(ContactEntity contactEntity) {
        if (isValidValue(contactEntity.getPhoto())) {
            return contactEntity.getPhoto();
        }
        return null;
    }

    private boolean isValidValue(String value) {
        return value != null && !value.isEmpty();
    }

    private boolean isValidContact(Contact contact) {
        return contact.getId() > 0
                && isValidValue(contact.getDisplayName());
    }
}
