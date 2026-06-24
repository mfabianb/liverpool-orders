package com.liverpool.orders.application.usecase;

import com.liverpool.orders.domain.model.Customer;

public interface GetCustomerUseCase {
    Customer execute(String userId);
}
