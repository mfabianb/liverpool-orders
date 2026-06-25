package com.liverpool.orders.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private String userId;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private List<Order> orders;
    private String shippingAddress;

    @Override
    public String toString(){
        return ReflectionToStringBuilder.toString(this);
    }
}
