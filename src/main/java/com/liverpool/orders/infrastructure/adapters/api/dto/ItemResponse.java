package com.liverpool.orders.infrastructure.adapters.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {

    private String itemId;
    private String skuId;
    private String quantity;
    private String displayName;
    private String deliveryStatus;
    private String id;

}
