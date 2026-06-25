package com.liverpool.orders.application.service;

import com.liverpool.orders.application.mappers.CustomMapper;
import com.liverpool.orders.application.ports.ItemsApiPort;
import com.liverpool.orders.application.ports.OrdersApiPort;
import com.liverpool.orders.application.usecase.IndexOrdersUseCase;
import com.liverpool.orders.infrastructure.adapters.api.dto.ItemResponse;
import com.liverpool.orders.infrastructure.adapters.api.dto.OrderResponse;
import com.liverpool.orders.infrastructure.adapters.elasticsearch.document.OrderSearchDocument;
import com.liverpool.orders.infrastructure.adapters.elasticsearch.repository.OrderSearchRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IndexOrdersService implements IndexOrdersUseCase {

    private static final Log log = LogFactory.getLog(IndexOrdersService.class);

    private final OrdersApiPort ordersApiPort;

    private final ItemsApiPort itemsApiPort;

    private final CustomMapper mapper;

    @Autowired
    private OrderSearchRepository orderSearchRepository;

    @Override
    public void index() {
        //orderSearchRepository.save(document);

        List<OrderResponse> orders =
                ordersApiPort.getOrdersResponse("");

        log.info("orders.size(): " + orders.size());

        List<ItemResponse> items =
                itemsApiPort.getItems();

        log.info("items.size(): " + items.size());

        List<OrderSearchDocument> documents = orders.stream()
                .map(orderResponse -> mapper.toOrderSearchDocument(orderResponse, items))
                .collect(Collectors.toList());

        log.info("documents.size(): " + documents.size());

        orderSearchRepository.saveAll(documents);
    }

}
