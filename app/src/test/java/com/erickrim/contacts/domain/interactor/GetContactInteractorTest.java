package com.erickrim.contacts.domain.interactor;

import com.erickrim.contacts.data.entity.ContactEntity;
import com.erickrim.contacts.data.repository.ContactEntityRepository;
import com.erickrim.contacts.domain.model.Contact;
import com.erickrim.contacts.domain.model.mapper.ContactMapper;
import com.erickrim.contacts.exceptions.ContactNotFoundException;
import com.erickrim.contacts.presentation.view.fragment.ContactsNotFoundFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GetContactInteractorTest {

    @Mock
    private ContactEntityRepository mRepository;

    @Mock
    private ContactMapper mContactMapper;

    @Mock
    private Contact mContact;

    @Mock
    private ContactEntity mContactEntity;

    private GetContactInteractor mSut;

    @Before
    public void setup() {
        mSut = new GetContactInteractor(mRepository, mContactMapper);
    }

    @Test
    public void returnsContact() throws Exception {
        given(mRepository.getContactEntity("1234")).willReturn(Single.just(mContactEntity));
        given(mContactMapper.toContact(mContactEntity)).willReturn(mContact);

        TestObserver<Contact> testObserver = mSut.getContact("1234").test();

        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValueCount(1);
        testObserver.assertValue(mContact);
    }

    @Test
    public void returnsException() throws Exception {
        given(mRepository.getContactEntity("1234")).willReturn(Single.just(mContactEntity));
        given(mContactMapper.toContact(mContactEntity)).willThrow(new ContactNotFoundException());

        TestObserver<Contact> testObserver = mSut.getContact("1234").test();

        testObserver.assertNotComplete();
        testObserver.assertError(ContactNotFoundException.class);
    }
}
