package com.liverpool.orders.infrastructure.adapters.mongo.documents;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "orders")
@Data
public class OrderDocument {

    @Id
    private String orderRef;
    private List<String> itemsId;
    private Integer quantity;
    private String canal;
    private LocalDate estimatedDeliveryDate;

    @Override
    public String toString(){
        return ReflectionToStringBuilder.toString(this);
    }
}
