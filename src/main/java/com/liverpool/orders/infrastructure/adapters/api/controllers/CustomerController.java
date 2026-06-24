package com.liverpool.orders.infrastructure.adapters.api.controllers;

import com.liverpool.orders.domain.model.Customer;
import com.liverpool.orders.infrastructure.adapters.api.CustomerApiAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/customers", produces = "application/json")
public class CustomerController {

    @Autowired
    private CustomerApiAdapter customerApiAdapter;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Customer> create(
            @RequestBody Customer customer) {

        return ResponseEntity.ok(
                customerApiAdapter.create(customer));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Customer> get(
            @PathVariable String userId) {

        return ResponseEntity.ok(
                customerApiAdapter.get(userId));
    }
}
