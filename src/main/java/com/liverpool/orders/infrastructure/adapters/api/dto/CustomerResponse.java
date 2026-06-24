package com.liverpool.orders.infrastructure.adapters.api.dto;

import java.util.List;

public record CustomerResponse(

        String userId,

        String firstName,

        String lastName,

        String middleName,

        String email,

        String shippingAddress,

        List<String> orders
) {}
