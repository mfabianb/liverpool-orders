package com.liverpool.orders.application.service;

import com.liverpool.orders.application.usecase.CreateCustomerUseCase;
import com.liverpool.orders.domain.model.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements CreateCustomerUseCase {
    @Override
    public Customer execute(Customer customer) {
        return null;
    }
}
