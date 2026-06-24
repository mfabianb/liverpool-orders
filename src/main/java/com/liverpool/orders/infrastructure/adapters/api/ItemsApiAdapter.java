package com.liverpool.orders.infrastructure.adapters.api;

import com.liverpool.orders.application.ports.ItemsApiPort;
import com.liverpool.orders.infrastructure.adapters.api.dto.ItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class ItemsApiAdapter implements ItemsApiPort {

    @Autowired
    private WebClient webClient;

    @Override
    public List<ItemResponse> getItems() {
        return webClient
                .get()
                .uri("https://6994a4eab081bc23e9c0f61e.mockapi.io/api/v1/items")
                .retrieve()
                .bodyToFlux(ItemResponse.class)
                .collectList()
                .block();
    }
}
