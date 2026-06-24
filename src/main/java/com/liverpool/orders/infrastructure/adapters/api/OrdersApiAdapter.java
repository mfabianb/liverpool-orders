package com.liverpool.orders.infrastructure.adapters.api;

import com.liverpool.orders.application.ports.OrdersApiPort;
import com.liverpool.orders.infrastructure.adapters.api.dto.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class OrdersApiAdapter implements OrdersApiPort {

    @Autowired
    private WebClient webClient;

    @Override
    public List<OrderResponse> getOrders() {
        return webClient
                .get()
                .uri("https://6994a4eab081bc23e9c0f61e.mockapi.io/api/v1/pedidos")
                .retrieve()
                .bodyToFlux(OrderResponse.class)
                .collectList()
                .block();
    }
}
