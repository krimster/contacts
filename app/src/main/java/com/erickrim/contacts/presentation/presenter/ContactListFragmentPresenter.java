package com.erickrim.contacts.presentation.presenter;

import com.erickrim.contacts.domain.interactor.GetContactsInteractor;
import com.erickrim.contacts.exceptions.ContactNotFoundException;
import com.erickrim.contacts.presentation.model.UiContact;
import com.erickrim.contacts.presentation.model.mapper.UiContactMapper;
import com.erickrim.contacts.rx.InteractorSchedulers;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ContactListFragmentPresenter {

    private final GetContactsInteractor mGetContactsInteractor;
    private final UiContactMapper mUiContactMapper;
    private final InteractorSchedulers mInteractorSchedulers;
    private CompositeDisposable mCompositeDisposable;

    private View mView;

    @Inject
    public ContactListFragmentPresenter(GetContactsInteractor getContactsInteractor,
                                        UiContactMapper uiContactMapper,
                                        InteractorSchedulers interactorSchedulers) {
        mGetContactsInteractor = getContactsInteractor;
        mUiContactMapper = uiContactMapper;
        mInteractorSchedulers = interactorSchedulers;
        mCompositeDisposable = new CompositeDisposable();
    }

    public void startPresenting(View view) {
        mView = view;
        mView.showProgress();
        getContacts();
    }

    public void stopPresenting() {
        mCompositeDisposable.clear();
    }

    private void getContacts() {
        Disposable disposable = mGetContactsInteractor.getContacts()
                .map(mUiContactMapper::toUiContacts)
                .subscribeOn(mInteractorSchedulers.getExecutionScheduler())
                .observeOn(mInteractorSchedulers.getPostExecutionScheduler())
                .subscribe(this::handleSuccess, this::handleError);
        mCompositeDisposable.add(disposable);
    }

    private void handleSuccess(List<UiContact> uiContacts) {
        mView.hideProgress();
        mView.showContacts(uiContacts);
    }

    private void handleError(Throwable throwable) {
        mView.hideProgress();
        if (throwable instanceof ContactNotFoundException) {
            mView.showNoContactFound();
        } else {
            mView.showError();
        }
    }

    public interface View {

        void showContacts(List<UiContact> uiContacts);

        void showError();

        void showNoContactFound();

        void showProgress();

        void hideProgress();
    }
}
