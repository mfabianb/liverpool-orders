package com.liverpool.orders.infrastructure.adapters.elasticsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearchResponse {

    private String orderRef;
    private String userId;
    private String canal;
    private String orderStatus;
    private boolean marketPlace;
    private boolean giftRegistry;
    private String storeName;
    private String id;
    private List<ItemSearchResponse> items;

}
