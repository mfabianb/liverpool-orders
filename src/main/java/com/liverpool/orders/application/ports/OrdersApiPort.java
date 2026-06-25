package com.liverpool.orders.application.ports;

import com.liverpool.orders.domain.model.Order;
import com.liverpool.orders.infrastructure.adapters.api.dto.OrderResponse;
import com.liverpool.orders.infrastructure.adapters.elasticsearch.dto.OrderSearchResponse;

import java.util.List;

public interface OrdersApiPort {
    List<OrderResponse> getOrdersResponse(String requestParams);
    List<Order> getOrders(String requestParams);
    List<OrderSearchResponse> getOrdersSearch();
}
