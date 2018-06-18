package com.erickrim.contacts.domain.mapper;

import com.erickrim.contacts.data.entity.ContactEntity;
import com.erickrim.contacts.domain.model.Contact;
import com.erickrim.contacts.domain.model.mapper.ContactMapper;
import com.erickrim.contacts.exceptions.ContactNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ContactMapperTest {

    @Mock
    private List<ContactEntity> mContactEntities;

    @Mock
    private ContactEntity mContactEntity;

    private ContactMapper mSut;

    @Before
    public void setup() {
        mSut = new ContactMapper();
    }

    @Test
    public void returnsContacts() throws Exception {
        mContactEntities = Arrays.asList(mContactEntity);
        given(mContactEntity.getDisplayName()).willReturn("tootoot");
        given((mContactEntity.getContactId())).willReturn(1234L);

        List<Contact> result = mSut.toContacts(mContactEntities);

        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getDisplayName(), "tootoot");
        assertEquals(result.get(0).getId(), 1234L);
    }

    @Test(expected = ContactNotFoundException.class)
    public void returnsException() throws Exception {
        List<Contact> result = mSut.toContacts(mContactEntities);
    }
}
