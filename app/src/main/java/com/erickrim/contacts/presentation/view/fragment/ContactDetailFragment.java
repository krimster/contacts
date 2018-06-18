package com.erickrim.contacts.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.erickrim.contacts.ContactsApplication;
import com.erickrim.contacts.R;
import com.erickrim.contacts.di.ContactsModule;
import com.erickrim.contacts.di.DaggerContactsFragmentComponent;
import com.erickrim.contacts.presentation.model.UiContact;
import com.erickrim.contacts.presentation.presenter.ContactDetailsPresenter;
import com.erickrim.contacts.utils.SnackbarManager;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ContactDetailFragment extends Fragment implements ContactDetailsPresenter.View {

    public static final String TAG = ContactDetailFragment.class.getSimpleName();

    public static final String CONTACT_ID_ARGS = "contactId_args";

    @BindView(R.id.contact_photo)
    ImageView mPhotoView;
    @BindView(R.id.name_value)
    TextView mNameView;
    @BindView(R.id.email_value)
    TextView mEmailView;
    @BindView(R.id.number_value)
    TextView mPhoneNumberView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.snackbar_view)
    View mSnackbarView;

    private Unbinder mUnbinder;
    private Listener mListener;

    @Inject
    ContactDetailsPresenter mPresenter;

    public static ContactDetailFragment newInstance(long contactId) {
        Bundle args = new Bundle();
        args.putLong(CONTACT_ID_ARGS, contactId);
        ContactDetailFragment fragment = new ContactDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        DaggerContactsFragmentComponent.builder()
                .applicationComponent(ContactsApplication.getApp(getActivity()).getComponent())
                .contactsModule(new ContactsModule())
                .build()
                .inject(this);
        setListener(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contacts_detail_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.startPresenting(this);
    }

    @Override
    public void onStop() {
        mPresenter.stopPresenting();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public long getContactId() {
        return getArguments() != null
                ? getArguments().getLong(CONTACT_ID_ARGS, 0)
                : 0;
    }

    @Override
    public void showContact(UiContact uiContact) {
        if (uiContact.getUiPhoto() != null) {
            Picasso.get().load(uiContact.getUiPhoto()).into(mPhotoView);
        }
        mNameView.setText(uiContact.getDisplayName());

        if (uiContact.getUiEmail() != null && uiContact.getUiEmail().getEmail() != null) {
            mEmailView.setText(uiContact.getUiEmail().getEmail());
        }

        if (uiContact.getUiPhoneNumber() != null && uiContact.getUiPhoneNumber().getNumber() != null) {
            mPhoneNumberView.setText(uiContact.getUiPhoneNumber().getNumber());
        }
    }

    @Override
    public void showError() {
        if (getActivity() != null) {
            SnackbarManager.showErrorMessage(
                    getActivity().getTitle().toString(),
                    getActivity().getString(R.string.error_occurred),
                    mSnackbarView);
        }
    }

    @Override
    public void showContactNotFound() {
        if (mListener != null) {
            mListener.showContactNotFound();
        }
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void setListener(Context context) {
        Activity activity;
        if (context instanceof Activity){
            activity = (Activity) context;
            try{
                mListener = (Listener) activity;
            } catch (ClassCastException e){
                throw new ClassCastException(activity.toString()
                        + " must implement Listener interface");        }
        }
    }
    public interface Listener {

        void showContactNotFound();

    }

}
