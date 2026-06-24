package com.liverpool.orders.application.mappers;

import com.liverpool.orders.domain.model.Customer;
import com.liverpool.orders.infrastructure.adapters.api.dto.CreateCustomerRequest;
import com.liverpool.orders.infrastructure.adapters.api.dto.CustomerResponse;
import com.liverpool.orders.infrastructure.adapters.mongo.documents.CustomerDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toDomain(CreateCustomerRequest request);

    CustomerDocument toDocument(Customer customer);

    Customer toDomain(CustomerDocument document);

    CustomerResponse toResponse(Customer customer);
}
