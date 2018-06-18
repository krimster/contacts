package com.erickrim.contacts.presentation.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.erickrim.contacts.presentation.model.UiContact;
import com.erickrim.contacts.presentation.view.ContactListView;

import java.util.ArrayList;
import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private ContactClickListener mListener;
    private List<UiContact> mUiContacts;

    public ContactListAdapter() {
        mUiContacts = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(new ContactListView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UiContact uiContact = mUiContacts.get(position);
        holder.bindInstallDevice(uiContact);
    }

    @Override
    public int getItemCount() {
        return mUiContacts.size();
    }

    public void setContactList(List<UiContact> uiContacts) {
        mUiContacts = uiContacts;
        notifyDataSetChanged();
    }

    public void setListener(ContactClickListener listener) {
        mListener = listener;
    }

    public interface ContactClickListener {
        void contactSelected(UiContact uiContact);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ContactListView mView;

        public ViewHolder(ContactListView view) {
            super(view);
            mView = view;
            setOnClickListener();
        }

        private void setOnClickListener() {
            mView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position == RecyclerView.NO_POSITION) {
                    return;
                }

                UiContact uiContact = mUiContacts.get(position);
                mListener.contactSelected(uiContact);
            });
        }

        void bindInstallDevice(UiContact uiContact) {
            setIcon(uiContact);
            setTitle(uiContact);
        }

        private void setIcon(UiContact uiContact) {
            if (uiContact.getUiPhoto() != null) {
                String url = uiContact.getUiPhoto();
                if (url != null) {
                    mView.setIcon(url);
                }
            }

        }

        private void setTitle(UiContact uiContact) {
            mView.setTitle(uiContact.getDisplayName());
        }
    }
}