package com.liverpool.orders.infrastructure.adapters.mongo.repositories;

import com.liverpool.orders.infrastructure.adapters.mongo.documents.CustomerDocument;
import org.jspecify.annotations.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MongoCustomerRepository
        extends MongoRepository<CustomerDocument, String> {

    CustomerDocument save(@NonNull CustomerDocument customerDocument);
    Optional<CustomerDocument> findByUserId(String userId);

}
