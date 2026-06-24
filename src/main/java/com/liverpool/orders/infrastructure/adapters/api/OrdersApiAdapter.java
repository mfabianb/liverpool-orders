package com.liverpool.orders.infrastructure.adapters.api;

import com.liverpool.orders.application.ports.OrdersApiPort;
import com.liverpool.orders.infrastructure.adapters.api.dto.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class OrdersApiAdapter implements OrdersApiPort {

    @Value("${external.orders.url}")
    private String ordersUrl;

    @Autowired
    private WebClient webClient;

    @Override
    public List<OrderResponse> getOrders() {
        return webClient
                .get()
                .uri(ordersUrl)
                .retrieve()
                .bodyToFlux(OrderResponse.class)
                .collectList()
                .block();
    }
}
