package com.erickrim.contacts.domain.interactor;

import com.erickrim.contacts.data.entity.ContactEntity;
import com.erickrim.contacts.data.repository.ContactEntityRepository;
import com.erickrim.contacts.domain.model.Contact;
import com.erickrim.contacts.domain.model.mapper.ContactMapper;
import com.erickrim.contacts.exceptions.ContactNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GetContactsInteractorTest {

    @Mock
    private ContactEntityRepository mRepository;

    @Mock
    private ContactMapper mContactMapper;

    @Mock
    private List<ContactEntity> mContactEntities;


    @Mock
    private List<Contact> mContacts;

    private GetContactsInteractor mSut;

    @Before
    public void setup() {
        mSut = new GetContactsInteractor(mRepository, mContactMapper);
    }

    @Test
    public void returnsContacts() throws Exception {
        given(mRepository.getContactEntities()).willReturn(Single.just(mContactEntities));
        given(mContactMapper.toContacts(mContactEntities)).willReturn(mContacts);

        TestObserver<List<Contact>> testObserver = mSut.getContacts().test();

        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValueCount(1);
        testObserver.assertValue(mContacts);
    }

    @Test
    public void returnsException() throws Exception {
        given(mRepository.getContactEntities()).willReturn(Single.error(new ContactNotFoundException()));

        TestObserver<List<Contact>> testObserver = mSut.getContacts().test();

        testObserver.assertNotComplete();
        testObserver.assertError(ContactNotFoundException.class);
    }

}
