package com.erickrim.contacts.data.entity.mapper;

import com.annimon.stream.Optional;
import com.erickrim.contacts.data.entity.ContactEntity;
import com.erickrim.contacts.data.entity.ContactEntityData;

public class ContactEntityMapper {

    public ContactEntityMapper() {}

    public Optional<ContactEntity> toContactEntity(long contactId, String displayName, String givenName, String familyName, String nickName,
                                                   ContactEntityData phoneNumber, ContactEntityData email, String photo) {
        ContactEntity contactEntity = new ContactEntity.Builder()
                .contactId(contactId)
                .displayName(displayName)
                .givenName(givenName)
                .familyName(familyName)
                .nickName(nickName)
                .phoneNumber(phoneNumber)
                .emailAddress(email)
                .photo(photo)
                .build();

        return validate(contactEntity);
    }

    public Optional<ContactEntity> toContactEntity(long contactId, String displayName, String givenName, String familyName, String nickName,
                                                   String company, String department, String title, String jobDescription, String officeLocation,
                                                   ContactEntityData phoneNumber, ContactEntityData email, ContactEntityData address,
                                                   ContactEntityData website, String country, String city, String postCode, String street,
                                                   String region, long postType, String identity, String namespace, String photo, String photoFieldId) {

        ContactEntity contactEntity = new ContactEntity.Builder()
                .contactId(contactId)
                .displayName(displayName)
                .givenName(givenName)
                .familyName(familyName)
                .nickName(nickName)
                .company(company)
                .department(department)
                .title(title)
                .jobDescription(jobDescription)
                .officeLocation(officeLocation)
                .phoneNumber(phoneNumber)
                .emailAddress(email)
                .postalAddress(address)
                .website(website)
                .country(country)
                .city(city)
                .postCode(postCode)
                .street(street)
                .region(region)
                .postType(postType)
                .identity(identity)
                .namespace(namespace)
                .photo(photo)
                .photoFieldId(photoFieldId)
                .build();

        return validate(contactEntity);
    }

    private Optional<ContactEntity> validate(ContactEntity contactEntity) {
        if (isValidContact(contactEntity)) {
            return Optional.of(contactEntity);
        } else {
            return Optional.empty();
        }
    }

    private boolean isValidContact(ContactEntity contactEntity) {
        return contactEntity.getDisplayName() != null
                || contactEntity.getFamilyName() != null
                || contactEntity.getGivenName() != null;
    }
}
