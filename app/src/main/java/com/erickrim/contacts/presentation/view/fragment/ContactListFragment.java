package com.erickrim.contacts.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.erickrim.contacts.ContactsApplication;
import com.erickrim.contacts.R;
import com.erickrim.contacts.di.ContactsModule;
import com.erickrim.contacts.di.DaggerContactsFragmentComponent;
import com.erickrim.contacts.presentation.model.UiContact;
import com.erickrim.contacts.presentation.presenter.ContactListFragmentPresenter;
import com.erickrim.contacts.presentation.view.adapter.ContactListAdapter;
import com.erickrim.contacts.utils.MarginDecoration;
import com.erickrim.contacts.utils.SnackbarManager;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ContactListFragment extends Fragment implements ContactListFragmentPresenter.View, ContactListAdapter.ContactClickListener {

    public static final String TAG = ContactListFragment.class.getSimpleName();

    @BindView(R.id.recycler_view)
    RecyclerView mRecycleView;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.snackbar_view)
    View mSnackbarView;

    @Inject
    ContactListFragmentPresenter mPresenter;

    @Inject
    ContactListAdapter mAdapter;

    private Listener mListener;
    private Unbinder mUnbinder;


    public static ContactListFragment newInstance() {
        return new ContactListFragment();
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
        View view = inflater.inflate(R.layout.contacts_list_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        final GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        mRecycleView.addItemDecoration(new MarginDecoration(getActivity()));
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(manager);
        mRecycleView.setAdapter(mAdapter);
        mAdapter.setListener(this);
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
    public void showContacts(List<UiContact> uiContacts) {
        mAdapter.setContactList(uiContacts);
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
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
    public void showNoContactFound() {
        if (mListener != null) {
            mListener.showContactNotFound();
        }
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        if (mAdapter != null) {
            mAdapter.setListener(null);
        }
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        mListener = null; // avoid leak
        super.onDetach();
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

    @Override
    public void contactSelected(UiContact uiContact) {
        if (mListener != null) {
            mListener.showContactDetails(uiContact);
        }
    }

    public interface Listener {
        void showContactNotFound();

        void showContactDetails(UiContact uiContact);
    }
}
