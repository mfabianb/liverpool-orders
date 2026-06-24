package com.liverpool.orders.application.service;

import com.liverpool.orders.application.usecase.GetCustomerUseCase;
import com.liverpool.orders.domain.model.Customer;
import org.springframework.stereotype.Service;

@Service
public class OrderSearchService implements GetCustomerUseCase {
    @Override
    public Customer execute(String userId) {
        return null;
    }
}
