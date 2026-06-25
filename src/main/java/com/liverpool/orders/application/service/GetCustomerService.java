package com.liverpool.orders.application.service;

import com.liverpool.orders.application.ports.CustomerRepositoryPort;
import com.liverpool.orders.application.ports.OrdersApiPort;
import com.liverpool.orders.application.usecase.GetCustomerUseCase;
import com.liverpool.orders.domain.exceptions.CustomerNotFoundException;
import com.liverpool.orders.domain.model.Customer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class GetCustomerService implements GetCustomerUseCase {

    @Autowired
    private CustomerRepositoryPort customerRepositoryPort;

    @Autowired
    private OrdersApiPort ordersApiPort;

    private static final Log log = LogFactory.getLog(GetCustomerService.class);

    @Override
    public Customer getCustomer(String userId) {
        Customer customer = customerRepositoryPort.findByUserId(userId).orElse(null);

        if(Objects.isNull(customer)){
            log.error("CustomerNotFoundException: userId=" + userId);
            throw new CustomerNotFoundException("userId=" + userId);
        }

        return customer;
    }

    @Override
    public List<Customer> getCustomerList() {
        return customerRepositoryPort.findAll();
    }
}
