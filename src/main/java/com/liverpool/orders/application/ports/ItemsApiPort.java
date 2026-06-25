package com.liverpool.orders.application.ports;

import com.liverpool.orders.infrastructure.adapters.api.dto.ItemResponse;

import java.util.List;

public interface ItemsApiPort {

    List<ItemResponse> getItems();

}
