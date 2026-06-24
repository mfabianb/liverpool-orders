package com.liverpool.orders.infrastructure.adapters.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemResponse {

    private String itemId;
    private String skuId;
    private String quantity;
    private String displayName;
    private String deliveryStatus;
    private String id;

}
