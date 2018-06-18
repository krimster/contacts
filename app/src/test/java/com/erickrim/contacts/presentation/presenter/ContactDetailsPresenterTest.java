package com.erickrim.contacts.presentation.presenter;

import com.erickrim.contacts.domain.interactor.GetContactInteractor;
import com.erickrim.contacts.domain.model.Contact;
import com.erickrim.contacts.exceptions.ContactNotFoundException;
import com.erickrim.contacts.presentation.model.UiContact;
import com.erickrim.contacts.presentation.model.mapper.UiContactMapper;
import com.erickrim.contacts.rx.InteractorSchedulers;
import com.erickrim.contacts.rx.TestInteractorSchedulers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ContactDetailsPresenterTest {

    @Mock
    private GetContactInteractor mGetContactInteractor;

    @Mock
    private UiContactMapper mUiContactMapper;

    @Mock
    private UiContact mUiContact;

    @Mock
    private Contact mContact;

    @Mock
    private ContactDetailsPresenter.View mView;

    private ContactDetailsPresenter mSut;

    @Before
    public void setup() {
        InteractorSchedulers schedulers = new TestInteractorSchedulers();
        mSut = new ContactDetailsPresenter(mGetContactInteractor, mUiContactMapper, schedulers);
    }

    @Test
    public void showsContact() throws Exception{
        given(mView.getContactId()).willReturn(1234L);
        given(mGetContactInteractor.getContact("1234")).willReturn(Single.just(mContact));
        given(mUiContactMapper.toUiContact(mContact)).willReturn(mUiContact);

        mSut.startPresenting(mView);

        verify(mView).showContact(mUiContact);
    }

    @Test
    public void showsContactsNotFound() throws Exception{
        given(mView.getContactId()).willReturn(1234L);
        given(mGetContactInteractor.getContact("1234")).willReturn(Single.just(mContact));
        given(mUiContactMapper.toUiContact(mContact)).willThrow(new ContactNotFoundException());

        mSut.startPresenting(mView);

        verify(mView).showContactNotFound();
    }

    @Test
    public void showsErrorMessage() {
        given(mView.getContactId()).willReturn(0L);

        mSut.startPresenting(mView);

        verify(mView).showError();
    }
}
