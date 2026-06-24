package com.liverpool.orders.application.ports;

import com.liverpool.orders.domain.model.Customer;

public interface CustomerApiPort {
    Customer create(Customer execute);
    Customer get(String userId);
}
