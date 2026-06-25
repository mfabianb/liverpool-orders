package com.liverpool.orders.application.service;

import com.liverpool.orders.application.ports.CustomerRepositoryPort;
import com.liverpool.orders.application.usecase.CreateCustomerUseCase;
import com.liverpool.orders.domain.exceptions.BusinessException;
import com.liverpool.orders.domain.model.Customer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCustomerService implements CreateCustomerUseCase {

    @Autowired
    private CustomerRepositoryPort customerRepositoryPort;

    private static final Log log = LogFactory.getLog(CreateCustomerService.class);

    @Override
    public Customer execute(Customer customer) {
        log.info(customer);

        if(customerRepositoryPort.findByUserId(customer.getUserId()).isPresent()){
            throw new BusinessException("User already exists");
        }

        try{
            return customerRepositoryPort.save(customer).orElseThrow(BusinessException::new);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }
}
