package com.erickrim.contacts.presentation.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.erickrim.contacts.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PermissionDeniedFragment extends Fragment {

    public static final String TAG = PermissionDeniedFragment.class.getSimpleName();

    @BindView(R.id.add_contacts_action)
    Button mAddContactsButton;

    private Unbinder mUnbinder;

    public static PermissionDeniedFragment newInstance() {
        return new PermissionDeniedFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.permission_denied_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mAddContactsButton.setOnClickListener(v -> getActivity().finish());
        return view;
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }
}