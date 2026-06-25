package com.liverpool.orders.infrastructure.adapters.elasticsearch.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "ordersearch")
public class OrderSearchDocument {

    @Id
    private String id;
    private String orderRef;
    private String orderStatus;
    private String storeName;
    private List<ItemSearchDocument> items;
    private String canal;
}