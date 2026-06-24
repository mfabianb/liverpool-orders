package com.liverpool.orders.infrastructure.adapters.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderResponse {

    private String orderRef;
    private String userId;
    private String canal;
    private String orderStatus;
    private boolean marketPlace;
    private boolean giftRegistry;
    private List<String> items;
    private String storeName;
    private String id;

}
