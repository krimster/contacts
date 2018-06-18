package com.erickrim.contacts.presentation.model;

public class UiContact {

    private final long mId;
    private final String mDisplayName;
    private final UiEmail mUiEmail;
    private final UiPhoneNumber mUiPhoneNumber;
    private final String mPhotoUrl;

    private UiContact(Builder builder) {
        this.mId = builder.mId;
        this.mDisplayName = builder.mDisplayName;
        this.mUiEmail = builder.mUiEmail;
        this.mUiPhoneNumber = builder.mUiPhoneNumber;
        this.mPhotoUrl = builder.mPhotoUrl;
    }

    public long getId() {
        return mId;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public UiEmail getUiEmail() {
        return mUiEmail;
    }

    public UiPhoneNumber getUiPhoneNumber() {
        return mUiPhoneNumber;
    }

    public String getUiPhoto() {
        return mPhotoUrl;
    }

    public static class Builder {
        private long mId;
        private String mDisplayName;
        private UiEmail mUiEmail;
        private UiPhoneNumber mUiPhoneNumber;
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

        public Builder uiEmail(final UiEmail email) {
            this.mUiEmail = email;
            return this;
        }

        public Builder uiPhoneNumber(final UiPhoneNumber phoneNumber) {
            this.mUiPhoneNumber = phoneNumber;
            return this;
        }

        public Builder uiPhoto(final String photo) {
            this.mPhotoUrl = photo;
            return this;
        }

        public UiContact build() {
            return new UiContact(this);
        }
    }
}
