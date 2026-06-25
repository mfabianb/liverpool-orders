package com.liverpool.orders.application.ports;


import com.liverpool.orders.infrastructure.adapters.elasticsearch.dto.OrderSearchResponse;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface OrderSearchPort {

    List<OrderSearchResponse> search(String orderRef,
                                     String orderStatus,
                                     String storeName,
                                     String displayName);
}