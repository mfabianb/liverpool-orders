package com.liverpool.orders.application.usecase;

import com.liverpool.orders.domain.model.Customer;

public interface CreateCustomerUseCase {
    Customer execute(Customer customer);
}
