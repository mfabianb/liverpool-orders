package com.liverpool.orders.infrastructure.adapters.api.dto;

import java.time.Instant;

public record ApiError(

        String code,

        String message,

        Instant timestamp
) {}
