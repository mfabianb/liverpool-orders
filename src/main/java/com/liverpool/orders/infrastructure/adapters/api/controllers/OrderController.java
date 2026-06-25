package com.liverpool.orders.infrastructure.adapters.api.controllers;

import com.liverpool.orders.application.ports.OrdersApiPort;
import com.liverpool.orders.infrastructure.adapters.api.dto.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = "/api/v1/orders", produces = "application/json")
public class OrderController {

    @Autowired
    private OrdersApiPort ordersApiPort;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getList(
            @RequestParam(value = "userId", required = false) String userId) {

        StringBuilder requestParams = new StringBuilder();

        if (Objects.nonNull(userId) && !userId.isEmpty()) {
            requestParams.append("userId=").append(userId);
        }

        return ResponseEntity.ok(ordersApiPort.getOrdersResponse(requestParams.toString()));
    }
}
