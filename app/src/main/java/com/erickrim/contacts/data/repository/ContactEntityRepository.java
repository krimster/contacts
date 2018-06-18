package com.erickrim.contacts.data.repository;

import com.erickrim.contacts.data.entity.ContactEntity;

import java.util.List;

import io.reactivex.Single;

public interface ContactEntityRepository {

    Single<List<ContactEntity>> getContactEntities();

    Single<ContactEntity> getContactEntity(String entityId);
}
