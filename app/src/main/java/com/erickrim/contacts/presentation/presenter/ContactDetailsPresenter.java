package com.erickrim.contacts.presentation.presenter;

import com.erickrim.contacts.domain.interactor.GetContactInteractor;
import com.erickrim.contacts.domain.interactor.GetContactsInteractor;
import com.erickrim.contacts.exceptions.ContactNotFoundException;
import com.erickrim.contacts.presentation.model.UiContact;
import com.erickrim.contacts.presentation.model.mapper.UiContactMapper;
import com.erickrim.contacts.rx.InteractorSchedulers;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ContactDetailsPresenter {

    private final GetContactInteractor mGetContactInteractor;
    private final UiContactMapper mUiContactMapper;
    private final InteractorSchedulers mInteractorSchedulers;
    private CompositeDisposable mCompositeDisposable;

    private View mView;

    @Inject
    public ContactDetailsPresenter(GetContactInteractor getContactInteractor,
                                        UiContactMapper uiContactMapper,
                                        InteractorSchedulers interactorSchedulers) {
        mGetContactInteractor = getContactInteractor;
        mUiContactMapper = uiContactMapper;
        mInteractorSchedulers = interactorSchedulers;
        mCompositeDisposable = new CompositeDisposable();
    }

    public void startPresenting(View view) {
        mView = view;
        mView.showProgress();
        long contactId = mView.getContactId();
        if (contactId > 0) {
            getContact(Long.toString(contactId));
        } else {
            mView.showError();
        }
    }

    public void stopPresenting() {
        mCompositeDisposable.clear();
    }

    private void getContact(String contactId) {
        Disposable disposable = mGetContactInteractor.getContact(contactId)
                .map(mUiContactMapper::toUiContact)
                .subscribeOn(mInteractorSchedulers.getExecutionScheduler())
                .observeOn(mInteractorSchedulers.getPostExecutionScheduler())
                .subscribe(this::handleSuccess, this::handleError);
        mCompositeDisposable.add(disposable);
    }

    private void handleSuccess(UiContact uiContact) {
        mView.hideProgress();
        mView.showContact(uiContact);
    }

    private void handleError(Throwable throwable) {
        mView.hideProgress();
        if (throwable instanceof ContactNotFoundException) {
            mView.showContactNotFound();
        } else {
            mView.showError();
        }
    }


    public interface View {

        long getContactId();

        void showContact(UiContact uiContact);

        void showError();

        void showContactNotFound();

        void showProgress();

        void hideProgress();
    }
}
