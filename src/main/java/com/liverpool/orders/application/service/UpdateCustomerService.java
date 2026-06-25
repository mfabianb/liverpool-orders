package com.liverpool.orders.application.service;

import com.liverpool.orders.application.ports.CustomerRepositoryPort;
import com.liverpool.orders.application.ports.OrdersApiPort;
import com.liverpool.orders.application.usecase.UpdateCustomerUseCase;
import com.liverpool.orders.domain.exceptions.BusinessException;
import com.liverpool.orders.domain.exceptions.CustomerNotFoundException;
import com.liverpool.orders.domain.model.Customer;
import com.liverpool.orders.domain.model.Order;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UpdateCustomerService implements UpdateCustomerUseCase {

    @Autowired
    private CustomerRepositoryPort customerRepositoryPort;

    @Autowired
    private OrdersApiPort ordersApiPort;

    private static final Log log = LogFactory.getLog(UpdateCustomerService.class);

    @Override
    public Customer updateCustomer(Customer customerRequest) {
        Customer customer = customerRepositoryPort.findByUserId(
                customerRequest.getUserId()).orElse(null);

        if(Objects.isNull(customer)){
            log.error("CustomerNotFoundException: userId=" + customerRequest.getUserId());
            throw new CustomerNotFoundException("userId=" + customerRequest.getUserId());
        }

        StringBuilder requestParams = new StringBuilder();

        if(Objects.nonNull(customerRequest.getUserId()) && !customerRequest.getUserId().isEmpty()){
            requestParams.append("userId=").append(customerRequest.getUserId());
        }

        List<Order> orderList = new ArrayList<>();

        try{
            orderList.addAll(ordersApiPort.getOrders(requestParams.toString()));
        }catch (CustomerNotFoundException e){
            log.info("ordersApiPort.getOrders empty");
        }

        orderList.forEach(log::info);

        customer.setEmail(customerRequest.getEmail());
        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setMiddleName(customerRequest.getMiddleName());
        customer.setShippingAddress(customerRequest.getShippingAddress());

        customer.setOrders(orderList);

        //return customer;
        try{
            return customerRepositoryPort.save(customer).orElseThrow(BusinessException::new);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }
}
