package com.liverpool.orders.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private String itemId;
    //private String skuId;
    private Integer quantity;
    //private String displayName;
    //private String deliveryStatus;
    //private String id;

}
