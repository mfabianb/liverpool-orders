package com.liverpool.orders.infrastructure.adapters.api;

import com.liverpool.orders.application.mappers.CustomMapper;
import com.liverpool.orders.application.ports.CustomerApiPort;
import com.liverpool.orders.application.usecase.CreateCustomerUseCase;
import com.liverpool.orders.application.usecase.GetCustomerUseCase;
import com.liverpool.orders.application.usecase.UpdateCustomerUseCase;
import com.liverpool.orders.domain.exceptions.BusinessException;
import com.liverpool.orders.infrastructure.adapters.api.dto.CustomerRequest;
import com.liverpool.orders.infrastructure.adapters.api.dto.CustomerResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CustomerApiAdapter implements CustomerApiPort {

    @Autowired
    private GetCustomerUseCase getCustomerUseCase;

    @Autowired
    private CreateCustomerUseCase createCustomerUseCase;

    @Autowired
    private UpdateCustomerUseCase updateCustomerUseCase;

    @Autowired
    private CustomMapper customerMapper;

    private static final Log log = LogFactory.getLog(CustomerApiAdapter.class);

    @Override
    public CustomerResponse create(CustomerRequest customer) {
        log.info(customer);
        return customerMapper.toResponse(createCustomerUseCase.execute(customerMapper.toDomain(customer)));
    }

    @Override
    public CustomerResponse get(String userId) {
        return customerMapper.toResponse(getCustomerUseCase.getCustomer(userId));
    }

    @Override
    public List<CustomerResponse> getList() {
        return getCustomerUseCase.getCustomerList().stream().map(customerMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public CustomerResponse patch(String userId, CustomerRequest customerRequest) {

        if(Objects.isNull(customerRequest.getUserId()) || customerRequest.getUserId().isEmpty()
                || !customerRequest.getUserId().equals(userId)){
            throw new BusinessException("CustomerRequest userId and param userId should be equals");
        }

        return customerMapper.toResponse(updateCustomerUseCase.updateCustomer(customerMapper.toDomain(customerRequest)));
    }
}
