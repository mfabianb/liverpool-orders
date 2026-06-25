package com.liverpool.orders.application.ports;

import com.liverpool.orders.domain.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepositoryPort {

    Optional<Customer>  save(Customer customer);
    Optional<Customer> findByUserId(String userId);
    List<Customer> findAll();


}
