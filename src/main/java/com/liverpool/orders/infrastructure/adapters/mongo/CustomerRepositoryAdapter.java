package com.liverpool.orders.infrastructure.adapters.mongo;

import com.liverpool.orders.application.mappers.CustomMapper;
import com.liverpool.orders.application.ports.CustomerRepositoryPort;
import com.liverpool.orders.domain.exceptions.CustomerNotFoundException;
import com.liverpool.orders.domain.model.Customer;
import com.liverpool.orders.infrastructure.adapters.mongo.documents.CustomerDocument;
import com.liverpool.orders.infrastructure.adapters.mongo.repositories.MongoCustomerRepository;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CustomerRepositoryAdapter implements CustomerRepositoryPort {

    @Autowired
    private MongoCustomerRepository mongoCustomerRepository;

    @Autowired
    CustomMapper customerMapper;

    private static final Log log = LogFactory.getLog(CustomerRepositoryAdapter.class);

    @Override
    public Optional<Customer> save(Customer customer) {
        CustomerDocument customerDocument = customerMapper.toDocument(customer);
        CustomerDocument customerDocument1 = mongoCustomerRepository.save(customerDocument);
        return Optional.of(customerMapper.toDomain(customerDocument1));
    }

    @Override
    public Optional<Customer> findByUserId(String userId) {
        Optional<CustomerDocument> optionalCustomerDocument = mongoCustomerRepository.findByUserId(userId);

        return optionalCustomerDocument.map(
                customerDocument -> customerMapper.toDomain(customerDocument));

    }

    @Override
    public List<Customer> findAll() {
        return mongoCustomerRepository.findAll().stream()
                .map(customerMapper::toDomain)
                .collect(Collectors.toList());
    }
}
