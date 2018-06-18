package com.erickrim.contacts.presentation.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.erickrim.contacts.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ContactsNotFoundFragment extends Fragment {

    public static final String TAG = ContactsNotFoundFragment.class.getSimpleName();

    @BindView(R.id.add_contacts_action)
    Button mAddContactsButton;

    private Unbinder mUnbinder;

    public static ContactsNotFoundFragment newInstance() {
        return new ContactsNotFoundFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contacts_not_found_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mAddContactsButton.setOnClickListener(v -> showAddContactsFragmnent());
        return view;
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    private void showAddContactsFragmnent() {
        //TODO: create AddContactFragment functionality
        Toast.makeText(getActivity(), "Add Contacts Functionality not yet available", Toast.LENGTH_SHORT).show();

    }
}
