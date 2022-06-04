package com.raccoon.prefsimnotary.repository;

import com.raccoon.prefsimnotary.model.document.Term;
import com.raccoon.prefsimnotary.model.enums.Status;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TermRepository extends MongoRepository<Term, String> {

    Optional<Term> findByStatus(Status status);

    void deleteByTermCode(Integer termCode);

}
