package com.liverpool.orders.infrastructure.adapters.elasticsearch.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "itemsearch")
public class ItemSearchDocument {

    private String itemId;
    private String skuId;
    private String quantity;
    private String displayName;
    private String deliveryStatus;
    private String id;

}
