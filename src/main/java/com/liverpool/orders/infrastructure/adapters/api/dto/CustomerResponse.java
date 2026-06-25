package com.liverpool.orders.infrastructure.adapters.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse{

    private String userId;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String shippingAddress;
    private List<OrderResponse> orders;
}
