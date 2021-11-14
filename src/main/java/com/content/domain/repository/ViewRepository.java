package com.content.domain.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.content.domain.entity.View;

@Repository
public interface ViewRepository extends MongoRepository<View, String> {

    boolean existsByName(String name);

    Optional<View> findByName(String name);

}
