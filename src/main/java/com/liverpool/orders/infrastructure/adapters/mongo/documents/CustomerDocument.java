package com.liverpool.orders.infrastructure.adapters.mongo.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "customers")
public class CustomerDocument {

    @Id
    private String id;

    private String userId;

    private String firstName;

    private String lastName;

    private String middleName;

    private String email;

    private String shippingAddress;

    //private List<String> orders;
}