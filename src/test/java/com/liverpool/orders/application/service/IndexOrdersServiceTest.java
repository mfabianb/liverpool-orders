package com.liverpool.orders.application.service;

import com.liverpool.orders.application.mappers.CustomMapper;
import com.liverpool.orders.application.ports.ItemsApiPort;
import com.liverpool.orders.application.ports.OrdersApiPort;
import com.liverpool.orders.infrastructure.adapters.api.dto.ItemResponse;
import com.liverpool.orders.infrastructure.adapters.api.dto.OrderResponse;
import com.liverpool.orders.infrastructure.adapters.elasticsearch.document.OrderSearchDocument;
import com.liverpool.orders.infrastructure.adapters.elasticsearch.repository.OrderSearchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IndexOrdersServiceTest {

    @Mock
    private OrdersApiPort ordersApiPort;

    @Mock
    private ItemsApiPort itemsApiPort;

    @Mock
    private CustomMapper mapper;

    @Mock
    private OrderSearchRepository repository;

    @InjectMocks
    private IndexOrdersService service;

    @Test
    void shouldIndexOrdersSuccessfully() {

        ReflectionTestUtils.setField(
                service,
                "orderSearchRepository",
                repository);

        OrderResponse order = new OrderResponse();
        order.setOrderRef("123");

        ItemResponse item = new ItemResponse();
        item.setItemId("123-456");

        OrderSearchDocument document = new OrderSearchDocument();
        document.setOrderRef("123");

        when(ordersApiPort.getOrdersResponse(""))
                .thenReturn(List.of(order));

        when(itemsApiPort.getItems())
                .thenReturn(List.of(item));

        when(mapper.toOrderSearchDocument(order, List.of(item)))
                .thenReturn(document);

        service.index();

        verify(repository).saveAll(anyList());
    }

    @Test
    void shouldIndexEmptyCollections() {

        ReflectionTestUtils.setField(
                service,
                "orderSearchRepository",
                repository);

        when(ordersApiPort.getOrdersResponse(""))
                .thenReturn(List.of());

        when(itemsApiPort.getItems())
                .thenReturn(List.of());

        service.index();

        verify(repository).saveAll(anyList());
    }

    @Test
    void shouldMapAllOrders() {

        ReflectionTestUtils.setField(
                service,
                "orderSearchRepository",
                repository);

        OrderResponse order1 = new OrderResponse();
        OrderResponse order2 = new OrderResponse();

        ItemResponse item = new ItemResponse();

        OrderSearchDocument doc1 = new OrderSearchDocument();
        OrderSearchDocument doc2 = new OrderSearchDocument();

        when(ordersApiPort.getOrdersResponse(""))
                .thenReturn(List.of(order1, order2));

        when(itemsApiPort.getItems())
                .thenReturn(List.of(item));

        when(mapper.toOrderSearchDocument(order1, List.of(item)))
                .thenReturn(doc1);

        when(mapper.toOrderSearchDocument(order2, List.of(item)))
                .thenReturn(doc2);

        service.index();

        verify(mapper, times(2))
                .toOrderSearchDocument(any(), anyList());
    }
}