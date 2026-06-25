package com.liverpool.orders.domain.exceptions;

import com.liverpool.orders.infrastructure.adapters.api.dto.CustomerResponse;
import com.liverpool.orders.infrastructure.adapters.api.dto.OrderResponse;

public class BusinessException extends RuntimeException {

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
