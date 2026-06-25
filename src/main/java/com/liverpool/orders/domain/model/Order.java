package com.liverpool.orders.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private String orderRef;//
    private List<Item> items;
    private Integer quantity;
    private String canal;
    private LocalDate estimatedDeliveryDate;
}
