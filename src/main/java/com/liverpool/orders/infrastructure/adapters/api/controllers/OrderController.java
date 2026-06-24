package com.liverpool.orders.infrastructure.adapters.api.controllers;

import com.liverpool.orders.application.ports.OrdersApiPort;
import com.liverpool.orders.domain.model.Customer;
import com.liverpool.orders.infrastructure.adapters.api.dto.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/orders", produces = "application/json")
public class OrderController {

    @Autowired
    private OrdersApiPort ordersApiPort;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Customer> create(
            @RequestBody Customer customer) {

        return ResponseEntity.ok(null);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderResponse>> get(
            @PathVariable String userId) {

        return ResponseEntity.ok(
                ordersApiPort.getOrders());
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getList() {
        return ResponseEntity.ok(
                ordersApiPort.getOrders());
    }
}
