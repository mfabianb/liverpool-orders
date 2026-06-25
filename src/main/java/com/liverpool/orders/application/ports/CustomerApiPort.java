package com.liverpool.orders.application.ports;

import com.liverpool.orders.infrastructure.adapters.api.dto.CustomerRequest;
import com.liverpool.orders.infrastructure.adapters.api.dto.CustomerResponse;

import java.util.List;

public interface CustomerApiPort {

    CustomerResponse create(CustomerRequest customerRequest);
    CustomerResponse get(String userId);
    List<CustomerResponse> getList();
    CustomerResponse patch(String userId, CustomerRequest customerRequest);

}
