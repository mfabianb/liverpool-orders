package com.liverpool.orders.infrastructure.adapters.api.controllers;

import com.liverpool.orders.application.ports.OrderSearchPort;
import com.liverpool.orders.application.ports.OrdersApiPort;
import com.liverpool.orders.infrastructure.adapters.api.dto.OrderResponse;
import com.liverpool.orders.infrastructure.adapters.elasticsearch.dto.OrderSearchResponse;
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

    @Autowired
    private OrderSearchPort orderSearchPort;

    @GetMapping("/search")
    public List<OrderSearchResponse> search(
            @RequestParam(required = false) String orderRef,
            @RequestParam(required = false) String orderStatus,
            @RequestParam(required = false) String storeName,
            @RequestParam(required = false) String displayName) {

        StringBuilder stringBuilder = new StringBuilder();

        if(Objects.nonNull(orderRef) && !orderRef.isEmpty()){
            stringBuilder.append("orderRef=").append(orderRef);
        }

        if(Objects.nonNull(orderStatus) && !orderStatus.isEmpty()){
            stringBuilder.append("orderStatus=").append(orderStatus);
        }

        if(Objects.nonNull(storeName) && !storeName.isEmpty()){
            stringBuilder.append("storeName=").append(storeName);
        }

        if(Objects.nonNull(displayName) && !displayName.isEmpty()){
            stringBuilder.append("displayName=").append(displayName);
        }

        return orderSearchPort.search(orderRef, orderStatus, storeName, displayName);
    }
}
