package com.erickrim.contacts.domain.interactor;

import com.erickrim.contacts.data.repository.ContactEntityRepository;
import com.erickrim.contacts.domain.model.Contact;
import com.erickrim.contacts.domain.model.mapper.ContactMapper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class GetContactsInteractor {

    private final ContactEntityRepository mRepository;
    private final ContactMapper mContactMapper;

    @Inject
    public GetContactsInteractor(ContactEntityRepository repository,
                                 ContactMapper contactMapper) {
        mRepository = repository;
        mContactMapper = contactMapper;
    }

    public Single<List<Contact>> getContacts() {
        return mRepository.getContactEntities()
                .map(mContactMapper::toContacts);
    }
 }
