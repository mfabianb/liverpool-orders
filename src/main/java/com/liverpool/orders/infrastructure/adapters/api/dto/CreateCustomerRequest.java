package com.liverpool.orders.infrastructure.adapters.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateCustomerRequest(

        @NotBlank
        String userId,

        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        String middleName,

        @Email
        String email,

        String shippingAddress
) {}
