package com.liverpool.orders.application.ports;

import com.liverpool.orders.infrastructure.adapters.api.dto.OrderResponse;

import java.util.List;

public interface OrdersApiPort {

    List<OrderResponse> getOrders();

}
