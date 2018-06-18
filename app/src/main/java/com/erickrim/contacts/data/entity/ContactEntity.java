package com.erickrim.contacts.data.entity;

public class ContactEntity {

    private final long contactId;
    private final String displayName;
    private final String givenName;
    private final String familyName;
    private final String nickName;
    private final String company;
    private final String department;
    private final String title;
    private final String jobDescription;
    private final String officeLocation;
    private final ContactEntityData phoneNumber;
    private final ContactEntityData emailAddress;
    private final ContactEntityData postalAddress;
    private final ContactEntityData website;
    private final String note;
    private final String country;
    private final String city;
    private final String postCode;
    private final String street;
    private final String region;
    private final long postType;
    private final String identity;
    private final String namespace;
    private final String photo;
    private final String photoFieldId;

    private ContactEntity(Builder builder) {
        this.contactId = builder.contactId;
        this.displayName = builder.displayName;
        this.givenName = builder.givenName;
        this.familyName = builder.familyName;
        this.nickName = builder.nickName;
        this.company = builder.company;
        this.department = builder.department;
        this.title = builder.title;
        this.jobDescription = builder.jobDescription;
        this.officeLocation = builder.officeLocation;
        this.phoneNumber = builder.phoneNumber;
        this.emailAddress = builder.emailAddress;
        this.postalAddress = builder.postalAddress;
        this.website = builder.website;
        this.note = builder.note;
        this.country = builder.country;
        this.city = builder.city;
        this.postCode = builder.postCode;
        this.street = builder.street;
        this.region = builder.region;
        this.postType = builder.postType;
        this.identity = builder.identity;
        this.namespace = builder.namespace;
        this.photo = builder.photo;
        this.photoFieldId = builder.photoFieldId;
    }

    public long getContactId() {
        return contactId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getNickName() {
        return nickName;
    }

    public String getCompany() {
        return company;
    }

    public String getDepartment() {
        return department;
    }

    public String getTitle() {
        return title;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public ContactEntityData getPhoneNumber() {
        return phoneNumber;
    }

    public ContactEntityData getEmailAddress() {
        return emailAddress;
    }

    public ContactEntityData getPostalAddress() {
        return postalAddress;
    }

    public ContactEntityData getWebsite() {
        return website;
    }

    public String getNote() {
        return note;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getStreet() {
        return street;
    }

    public String getRegion() {
        return region;
    }

    public long getPostType() {
        return postType;
    }

    public String getIdentity() {
        return identity;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getPhoto() {
        return photo;
    }

    public String getPhotoFieldId() {
        return photoFieldId;
    }

    public static class Builder {

        private long contactId;
        private String displayName;
        private String givenName;
        private String familyName;
        private String nickName;
        private String company;
        private String department;
        private String title;
        private String jobDescription;
        private String officeLocation;
        private ContactEntityData phoneNumber;
        private ContactEntityData emailAddress;
        private ContactEntityData postalAddress;
        private ContactEntityData website;
        private String note;
        private String country;
        private String city;
        private String postCode;
        private String street;
        private String region;
        private long postType;
        private String identity;
        private String namespace;
        private String photo;
        private String photoFieldId;

        public Builder() {}

        public Builder contactId(long contactId) {
            this.contactId = contactId;
            return this;
        }

        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder givenName(String givenName) {
            this.givenName = givenName;
            return this;
        }

        public Builder familyName(String familyName) {
            this.familyName = familyName;
            return this;
        }

        public Builder nickName(String nickName) {
            this.nickName = nickName;
            return this;
        }

        public Builder company(String company) {
            this.company = company;
            return this;
        }

        public Builder department(String department) {
            this.department = department;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder jobDescription(String jobDescription) {
            this.jobDescription = jobDescription;
            return this;
        }

        public Builder officeLocation(String officeLocation) {
            this.officeLocation = officeLocation;
            return this;
        }

        public Builder phoneNumber(ContactEntityData phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder emailAddress(ContactEntityData emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public Builder postalAddress(ContactEntityData postalAddress) {
            this.postalAddress = postalAddress;
            return this;
        }

        public Builder website(ContactEntityData website) {
            this.website = website;
            return this;
        }

        public Builder note(String note) {
            this.note = note;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder postCode(String postCode) {
            this.postCode = postCode;
            return this;
        }

        public Builder street(String street) {
            this.street = street;
            return this;
        }

        public Builder region(String region) {
            this.region = region;
            return this;
        }

        public Builder postType(long postType) {
            this.postType = postType;
            return this;
        }

        public Builder identity(String identity) {
            this.identity = identity;
            return this;
        }

        public Builder namespace(String namespace) {
            this.namespace = namespace;
            return this;
        }

        public Builder photo(String photo) {
            this.photo = photo;
            return this;
        }

        public Builder photoFieldId(String photoFieldId) {
            this.photoFieldId = photoFieldId;
            return this;
        }

        public ContactEntity build() {
            return new ContactEntity(this);
        }
    }
}
