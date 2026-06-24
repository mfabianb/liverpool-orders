package com.liverpool.orders.infrastructure.adapters.api;

import com.liverpool.orders.application.ports.CustomerApiPort;
import com.liverpool.orders.domain.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerApiAdapter implements CustomerApiPort {
    @Override
    public Customer create(Customer execute) {
        return null;
    }

    @Override
    public Customer get(String userId) {
        return null;
    }
}
