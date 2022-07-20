package com.raccoon.prefsimnotary.repository;

import com.raccoon.prefsimnotary.model.entity.NotaryOffice;
import com.raccoon.prefsimnotary.model.enums.Status;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface NotaryOfficeRepository extends MongoRepository<NotaryOffice, String> {

    Optional<NotaryOffice> findByNotaryOfficeCode(String notaryOfficeCode);

    List<NotaryOffice> findAllByOrderByName();

    List<NotaryOffice> findAllByPreferenceStatusOrderByName(Status preferenceStatus);

    List<NotaryOffice> findAllByNotaryOfficeCodeInAndPreferenceStatus(Set<String> notaryOfficeCode, Status preferenceStatus);

}
