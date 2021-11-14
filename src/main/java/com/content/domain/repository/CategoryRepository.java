package com.content.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.content.domain.entity.Category;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {

}
