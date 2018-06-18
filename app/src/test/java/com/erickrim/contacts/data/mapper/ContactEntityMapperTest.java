package com.erickrim.contacts.data.mapper;

import com.annimon.stream.Optional;
import com.erickrim.contacts.data.entity.ContactEntity;
import com.erickrim.contacts.data.entity.ContactEntityData;
import com.erickrim.contacts.data.entity.mapper.ContactEntityMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ContactEntityMapperTest {

    private static final String DISPLAY_NAME = "displayName";

    @Mock
    private ContactEntityData mPhoneNumber;

    @Mock
    private ContactEntityData mEmail;

    private ContactEntityMapper mSut;

    @Before
    public void setup() {
        mSut = new ContactEntityMapper();
    }

    @Test
    public void returnsContactEmptyWhenValidInput() {
        Optional<ContactEntity> contactEntityOptional = mSut.toContactEntity(
                123,
                DISPLAY_NAME,
                "givenName",
                null,
                null,
                mPhoneNumber,
                mEmail,
                "content/photo/1");

        assertTrue(contactEntityOptional.isPresent());
        assertEquals(contactEntityOptional.get().getDisplayName(), DISPLAY_NAME);
    }

    @Test
    public void returnsEmptyWhenNameIsMissing() {
        Optional<ContactEntity> contactEntityOptional = mSut.toContactEntity(
                123,
                null,
                null,
                null,
                null,
                mPhoneNumber,
                mEmail,
                "content/photo/1");

        assertFalse(contactEntityOptional.isPresent());
    }
}
