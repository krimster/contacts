package com.erickrim.contacts.domain.model;

public class Contact {

    private final long mId;
    private final String mDisplayName;
    private final Email mEmail;
    private final PhoneNumber mPhoneNumber;
    private final String mPhotoUrl;

    private Contact(Builder builder) {
        this.mId = builder.mId;
        this.mDisplayName = builder.mDisplayName;
        this.mEmail = builder.mEmail;
        this.mPhoneNumber = builder.mPhoneNumber;
        this.mPhotoUrl = builder.mPhotoUrl;
    }

    public long getId() {
        return mId;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public Email getEmail() {
        return mEmail;
    }

    public PhoneNumber getPhoneNumber() {
        return mPhoneNumber;
    }

    public String getPhoto() {
        return mPhotoUrl;
    }

    public static class Builder {
        private long mId;
        private String mDisplayName;
        private Email mEmail;
        private PhoneNumber mPhoneNumber;
        private String mPhotoUrl;

        public Builder() {}

        public Builder withId(final long id) {
            this.mId = id;
            return this;
        }

        public Builder displayName(final String displayName) {
            this.mDisplayName = displayName;
            return this;
        }

        public Builder email(final Email email) {
            this.mEmail = email;
            return this;
        }

        public Builder phoneNumber(final PhoneNumber phoneNumber) {
            this.mPhoneNumber = phoneNumber;
            return this;
        }

        public Builder photo(final String photo) {
            this.mPhotoUrl = photo;
            return this;
        }

        public Contact build() {
            return new Contact(this);
        }
    }

}
