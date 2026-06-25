package com.liverpool.orders.application.mappers;

import com.liverpool.orders.domain.model.Customer;
import com.liverpool.orders.domain.model.Item;
import com.liverpool.orders.domain.model.Order;
import com.liverpool.orders.infrastructure.adapters.api.dto.CustomerRequest;
import com.liverpool.orders.infrastructure.adapters.api.dto.CustomerResponse;
import com.liverpool.orders.infrastructure.adapters.api.dto.ItemResponse;
import com.liverpool.orders.infrastructure.adapters.api.dto.OrderResponse;
import com.liverpool.orders.infrastructure.adapters.elasticsearch.document.ItemSearchDocument;
import com.liverpool.orders.infrastructure.adapters.elasticsearch.document.OrderSearchDocument;
import com.liverpool.orders.infrastructure.adapters.mongo.documents.CustomerDocument;
import com.liverpool.orders.infrastructure.adapters.mongo.documents.OrderDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CustomMapper {

    @Mapping(target = "skuId", ignore = true)
    @Mapping(target = "displayName", ignore = true)
    @Mapping(target = "deliveryStatus", ignore = true)
    @Mapping(target = "id", ignore = true)
    ItemResponse toResponse(Item item);

    Item toDomain(ItemResponse itemResponse);

    default OrderDocument toDocument(Order order) {
        OrderDocument orderDocument = new OrderDocument();

        orderDocument.setCanal(order.getCanal());
        orderDocument.setEstimatedDeliveryDate(order.getEstimatedDeliveryDate());
        orderDocument.setOrderRef(order.getOrderRef());
        orderDocument.setQuantity(order.getQuantity());

        List<String> itemsId = new ArrayList<>();

        order.getItems().forEach(item -> itemsId.add(item.getItemId()));

        orderDocument.setItemsId(itemsId);

        return orderDocument;
    }

    default Order toDomain(OrderDocument document) {
        Order order = new Order();

        order.setCanal(document.getCanal());
        order.setEstimatedDeliveryDate(document.getEstimatedDeliveryDate());
        order.setOrderRef(document.getOrderRef());
        order.setQuantity(document.getQuantity());

        List<Item> items = new ArrayList<>();

        if(Objects.nonNull(document.getItemsId())){
            document.getItemsId().forEach(item -> {
                items.add(Item.builder().itemId(item).build());
            });
        }

        order.setItems(items);

        return order;
    }

    default Order toDomain(OrderResponse orderResponse) {
        Order order = new Order();

        order.setCanal(orderResponse.getCanal());
        order.setEstimatedDeliveryDate(LocalDate.parse(orderResponse.getOrderStatus()));
        order.setOrderRef(orderResponse.getOrderRef());

        List<Item> items = new ArrayList<>();

        orderResponse.getItems().forEach(item -> {
            items.add(Item.builder().itemId(item).build());
        });

        order.setItems(items);

        return order;
    }

    default OrderResponse toResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse();

        orderResponse.setCanal(order.getCanal());
        orderResponse.setOrderStatus(String.valueOf(order.getEstimatedDeliveryDate()));
        orderResponse.setOrderRef(order.getOrderRef());


        List<String> itemsId = new ArrayList<>();

        order.getItems().forEach(item -> itemsId.add(item.getItemId()));

        orderResponse.setItems(itemsId);

        return orderResponse;
    }

    List<OrderDocument> toDocuments(List<Order> orders);

    List<Order> toDomains(List<OrderDocument> documents);

    default CustomerDocument toDocument(Customer customer) {
        CustomerDocument customerDocument = new CustomerDocument();

        customerDocument.setEmail(customer.getEmail());
        customerDocument.setFirstName(customer.getFirstName());
        customerDocument.setLastName(customer.getLastName());
        customerDocument.setMiddleName(customer.getMiddleName());
        customerDocument.setShippingAddress(customer.getShippingAddress());
        customerDocument.setUserId(customer.getUserId());

        List<OrderDocument> orders = new ArrayList<>();

        if(Objects.nonNull(customer.getOrders())){
            customer.getOrders().stream().forEach(order -> orders.add(toDocument(order)));
        }

        customerDocument.setOrders(orders);

        return customerDocument;
    }

    Customer toDomain(CustomerDocument document);

    Customer toDomain(CustomerRequest request);

    default CustomerResponse toResponse(Customer customer) {

        List<OrderResponse> orderResponseList = new ArrayList<>();

        if(Objects.nonNull(customer.getOrders())){

            orderResponseList = customer.getOrders().stream().map(this::toResponse).toList();
        }

        return new CustomerResponse(customer.getUserId(),
                customer.getFirstName(), customer.getLastName(),
                customer.getMiddleName(), customer.getEmail(),
                customer.getShippingAddress(), orderResponseList);
    }

    default OrderSearchDocument toOrderSearchDocument(OrderResponse order, List<ItemResponse> items) {
        List<ItemSearchDocument> itemSearchDocumentList = new ArrayList<>();

        order.getItems().forEach(s -> {
            itemSearchDocumentList.addAll(createItemSearchDocumentList(s, items));
        });

        return OrderSearchDocument.builder()
                .id(order.getId())
                .orderRef(order.getOrderRef())
                .orderStatus(order.getOrderStatus())
                .storeName(order.getStoreName())
                .items(itemSearchDocumentList)
                .canal(order.getCanal())
                .build();

    }

    default List<ItemSearchDocument> createItemSearchDocumentList(String id, List<ItemResponse> items) {
        return items.stream().filter(
                        itemResponse -> id.contains(itemResponse.getSkuId()))
                .map(itemResponse -> {
                    return ItemSearchDocument.builder()
                            .itemId(itemResponse.getItemId())
                            .skuId(itemResponse.getSkuId())
                            .quantity(itemResponse.getQuantity())
                            .displayName(itemResponse.getDisplayName())
                            .deliveryStatus(itemResponse.getDeliveryStatus())
                            .id(itemResponse.getId())
                            .build();
                }).collect(Collectors.toList());
    }
}
