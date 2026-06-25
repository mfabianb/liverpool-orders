package com.liverpool.orders.infrastructure.adapters.api;

import com.liverpool.orders.application.mappers.CustomMapper;
import com.liverpool.orders.application.ports.OrdersApiPort;
import com.liverpool.orders.domain.exceptions.CustomerNotFoundException;
import com.liverpool.orders.domain.model.Order;
import com.liverpool.orders.infrastructure.adapters.api.dto.OrderResponse;
import com.liverpool.orders.infrastructure.adapters.elasticsearch.dto.OrderSearchResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrdersApiAdapter implements OrdersApiPort {

    @Value("${external.orders.url}")
    private String ordersUrl;

    @Autowired
    private WebClient webClient;

    @Autowired
    private CustomMapper orderMapper;

    private static final Log log = LogFactory.getLog(OrdersApiAdapter.class);

    @Override
    public List<OrderResponse> getOrdersResponse(String requestParams) {
        log.info(ordersUrl + "?" + requestParams);

        return webClient
                .get()
                .uri(ordersUrl + "?" + requestParams)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, clientResponse -> {
                    throw new CustomerNotFoundException(requestParams);
                })
                .bodyToFlux(OrderResponse.class)
                .collectList()
                .block();

    }

    @Override
    public List<Order> getOrders(String requestParams) {
        getOrdersResponse(requestParams).stream().forEach(log::info);
        return getOrdersResponse(requestParams).stream().map(orderMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<OrderSearchResponse> getOrdersSearch() {
        return webClient
                .get()
                .uri(ordersUrl + "?" )
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, clientResponse -> {
                    throw new CustomerNotFoundException("");
                })
                .bodyToFlux(OrderSearchResponse.class)
                .collectList()
                .block();
    }

}
