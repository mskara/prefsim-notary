package com.raccoon.prefsimnotary.repository;

import com.raccoon.prefsimnotary.model.entity.Notary;
import com.raccoon.prefsimnotary.model.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface NotaryRepository extends MongoRepository<Notary, String> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
