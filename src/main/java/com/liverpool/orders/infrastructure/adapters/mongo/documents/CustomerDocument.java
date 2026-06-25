package com.liverpool.orders.infrastructure.adapters.mongo.documents;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "customers")
@Data
public class CustomerDocument {

    @Id
    private String userId;
    private String firstName;
    private String lastName;
    private String middleName;

    @Indexed(unique=true)
    private String email;

    private String shippingAddress;
    private List<OrderDocument> orders;

    @Override
    public String toString(){
        return ReflectionToStringBuilder.toString(this);
    }
}