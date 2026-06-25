package com.liverpool.orders.application.usecase;

import com.liverpool.orders.domain.model.Customer;

import java.util.List;

public interface GetCustomerUseCase {
    Customer getCustomer(String userId);
    List<Customer> getCustomerList();
}
