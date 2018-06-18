package com.erickrim.contacts.presentation.presenter;

import com.erickrim.contacts.domain.interactor.GetContactsInteractor;
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

import java.util.List;

import io.reactivex.Single;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ContactListFragmentPresenterTest {

    @Mock
    private GetContactsInteractor mGetContactsInteractor;

    @Mock
    private UiContactMapper mUiContactMapper;

    @Mock
    private List<UiContact> mUiContacts;

    @Mock
    private List<Contact> mContacts;

    @Mock
    private ContactListFragmentPresenter.View mView;

    private ContactListFragmentPresenter mSut;

    @Before
    public void setup() {
        InteractorSchedulers schedulers = new TestInteractorSchedulers();
        mSut = new ContactListFragmentPresenter(mGetContactsInteractor, mUiContactMapper, schedulers);
    }

    @Test
    public void showsContacts() throws Exception{
        given(mGetContactsInteractor.getContacts()).willReturn(Single.just(mContacts));
        given(mUiContactMapper.toUiContacts(mContacts)).willReturn(mUiContacts);

        mSut.startPresenting(mView);

        verify(mView).showContacts(mUiContacts);
    }

    @Test
    public void showsContactsNotFound() throws Exception{
        given(mGetContactsInteractor.getContacts()).willReturn(Single.just(mContacts));
        given(mUiContactMapper.toUiContacts(mContacts)).willThrow(new ContactNotFoundException());

        mSut.startPresenting(mView);

        verify(mView).showNoContactFound();
    }

    @Test
    public void showsErrorMessage() {
        given(mGetContactsInteractor.getContacts()).willReturn(Single.error(new IllegalArgumentException()));

        mSut.startPresenting(mView);

        verify(mView).showError();
    }
}
