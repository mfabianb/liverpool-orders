package com.liverpool.orders.infrastructure.adapters.api.controllers;

import com.liverpool.orders.infrastructure.adapters.api.CustomerApiAdapter;
import com.liverpool.orders.infrastructure.adapters.api.dto.CustomerRequest;
import com.liverpool.orders.infrastructure.adapters.api.dto.CustomerResponse;
import jakarta.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/customers", produces = "application/json")
public class CustomerController {

    @Autowired
    private CustomerApiAdapter customerApiAdapter;

    private static final Log log = LogFactory.getLog(CustomerController.class);

    @PostMapping(consumes = "application/json")
    public ResponseEntity<CustomerResponse> create(
            @Valid @RequestBody CustomerRequest customer) {

        return ResponseEntity.ok(
                customerApiAdapter.create(customer));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CustomerResponse> get(
            @PathVariable String userId) {

        return ResponseEntity.ok(
                customerApiAdapter.get(userId));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<CustomerResponse> patch(
            @PathVariable String userId, @Valid @RequestBody CustomerRequest customer) {

        return ResponseEntity.ok(
                customerApiAdapter.patch(userId, customer));
    }
}
