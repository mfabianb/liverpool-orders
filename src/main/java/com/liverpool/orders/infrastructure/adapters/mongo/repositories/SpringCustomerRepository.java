package com.liverpool.orders.infrastructure.adapters.mongo.repositories;

import com.liverpool.orders.infrastructure.adapters.mongo.documents.CustomerDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringCustomerRepository
        extends MongoRepository<CustomerDocument, String> {

    Optional<CustomerDocument> findByUserId(String userId);

}
