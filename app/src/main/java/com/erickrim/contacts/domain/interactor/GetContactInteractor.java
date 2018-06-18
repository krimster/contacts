package com.erickrim.contacts.domain.interactor;

import com.erickrim.contacts.data.repository.ContactEntityRepository;
import com.erickrim.contacts.domain.model.Contact;
import com.erickrim.contacts.domain.model.mapper.ContactMapper;

import javax.inject.Inject;

import io.reactivex.Single;

public class GetContactInteractor {

    private final ContactEntityRepository mRepository;
    private final ContactMapper mContactMapper;

    @Inject
    public GetContactInteractor(ContactEntityRepository repository,
                                 ContactMapper contactMapper) {
        mRepository = repository;
        mContactMapper = contactMapper;
    }

    public Single<Contact> getContact(String contactId) {
        return mRepository.getContactEntity(contactId)
                .map(mContactMapper::toContact);
    }
}
