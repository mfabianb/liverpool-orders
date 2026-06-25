package com.liverpool.orders.domain.exceptions;

public class CustomerNotFoundException
        extends RuntimeException {
    public CustomerNotFoundException(String userId) {
        super("Customer not found. " + userId);
    }

    public CustomerNotFoundException() {
        super("Customer not found. ");
    }
}
