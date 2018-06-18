package com.erickrim.contacts.presentation.model.mapper;

import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.erickrim.contacts.R;
import com.erickrim.contacts.domain.model.Contact;
import com.erickrim.contacts.domain.model.ContactDataType;
import com.erickrim.contacts.domain.model.Email;
import com.erickrim.contacts.domain.model.PhoneNumber;
import com.erickrim.contacts.exceptions.ContactNotFoundException;
import com.erickrim.contacts.presentation.model.UiContact;
import com.erickrim.contacts.presentation.model.UiEmail;
import com.erickrim.contacts.presentation.model.UiPhoneNumber;

import java.util.List;

public class UiContactMapper {

    public UiContactMapper() {
    }

    public List<UiContact> toUiContacts(List<Contact> contacts) throws Exception {
        if (contacts != null && contacts.size() > 0) {
            return Stream.of(contacts)
                    .map(this::createUiContact)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
        } else {
            throw new ContactNotFoundException();
        }
    }

    public UiContact toUiContact(Contact contact) throws ContactNotFoundException {
        Optional<UiContact> uiContactOptional = createUiContact(contact);
        if (uiContactOptional.isPresent()) {
            return uiContactOptional.get();
        } else {
            throw new ContactNotFoundException();
        }
    }

    private Optional<UiContact> createUiContact(Contact contact) {
        if (contact != null) {
            long id = contact.getId();
            String displayName = contact.getDisplayName();
            UiEmail uiEmail = getUiEmailAddress(contact.getEmail());
            UiPhoneNumber uiPhoneNumber = getUiPhoneNumber(contact.getPhoneNumber());
            String uiPhotoUrl = getUiPhoto(contact.getPhoto());

            UiContact uiContact = new UiContact.Builder()
                    .withId(id)
                    .displayName(displayName)
                    .uiEmail(uiEmail)
                    .uiPhoneNumber(uiPhoneNumber)
                    .uiPhoto(uiPhotoUrl)
                    .build();

            return isValidUiContact(uiContact) ? Optional.of(uiContact) : Optional.empty();

        } else {
            return Optional.empty();
        }
    }

    private UiEmail getUiEmailAddress(Email contactEmail) {
        if (contactEmail != null && contactEmail.getEmail() != null) {
            String emailDisplay = contactEmail.getEmail();
            int typeResourceId = getTypeResourceId(contactEmail.getType());
            return new UiEmail(emailDisplay, typeResourceId);
        } else {
            return null;
        }
    }

    private UiPhoneNumber getUiPhoneNumber(PhoneNumber phoneNumber) {
        if (phoneNumber != null && phoneNumber.getNumber() != null) {
            String numberDisplay = phoneNumber.getNumber();
            int typeResourceId = getTypeResourceId(phoneNumber.getType());
            int countryCodeResourceId = getCountryResourceId(numberDisplay);
            return new UiPhoneNumber(numberDisplay, typeResourceId, countryCodeResourceId);
        } else {
            return null;
        }
    }

    private int getTypeResourceId(ContactDataType type) {
        switch (type) {
            case HOME:
                return R.string.contact_data_type_home;

            case MOBILE:
                return R.string.contact_data_type_mobile;

            case WORK:
                return R.string.contact_data_type_work;

            default:
                return R.string.contact_data_type_other;
        }
    }

    private String getUiPhoto(String photo) {
        return photo != null && !photo.isEmpty() ? photo : null;
    }

    private int getCountryResourceId(String phoneNumber) {
        //TODO: write logic to return correct flag icon base on country code
        return 0;
    }

    private boolean isValidUiContact(UiContact uiContact) {
        return uiContact.getId() > 0 && uiContact.getDisplayName() != null;
    }

}
