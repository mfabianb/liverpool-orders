package com.liverpool.orders.infrastructure.adapters.elasticsearch;

import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import com.liverpool.orders.application.ports.OrderSearchPort;
import com.liverpool.orders.application.usecase.IndexOrdersUseCase;
import com.liverpool.orders.infrastructure.adapters.elasticsearch.document.ItemSearchDocument;
import com.liverpool.orders.infrastructure.adapters.elasticsearch.document.OrderSearchDocument;
import com.liverpool.orders.infrastructure.adapters.elasticsearch.dto.ItemSearchResponse;
import com.liverpool.orders.infrastructure.adapters.elasticsearch.dto.OrderSearchResponse;
import lombok.RequiredArgsConstructor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.data.elasticsearch.client.elc.Queries.matchQuery;

@Component
@RequiredArgsConstructor
public class ElasticOrderSearchAdapter
        implements OrderSearchPort {

    @Autowired
    private ElasticsearchOperations operations;

    @Autowired
    private IndexOrdersUseCase indexOrdersUseCase;

    private static final Log log = LogFactory.getLog(ElasticOrderSearchAdapter.class);

    @Override
    public List<OrderSearchResponse> search(String orderRef,
                                            String orderStatus,
                                            String storeName,
                                            String displayName) {
        indexOrdersUseCase.index();

        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.multiMatch(
                        m -> m
                                .query(orderRef)
                                .fields( "orderRef")

                                .fuzziness("AUTO")
                        )
                )
                .build();

        return operations.search(
                        query,
                        OrderSearchDocument.class)
                .stream()
                .map(SearchHit::getContent)
                .map(this::toResponse)
                .toList();
    }

    private OrderSearchResponse toResponse(
            OrderSearchDocument document) {

        return OrderSearchResponse.builder()
                .orderRef(document.getOrderRef())
                .userId(document.getId())
                .canal(document.getCanal())
                .orderStatus(document.getOrderStatus())
                .marketPlace(false)
                .giftRegistry(false)
                .storeName(document.getStoreName())
                .id(document.getId())
                .items(
                        document.getItems().stream().map(this::toItemSearchResponse).toList()
                )
                .build();
    }

    private ItemSearchResponse toItemSearchResponse(ItemSearchDocument itemSearchDocument){
        return ItemSearchResponse.builder()
                .itemId(itemSearchDocument.getItemId())
                .skuId(itemSearchDocument.getSkuId())
                .quantity(itemSearchDocument.getQuantity())
                .displayName(itemSearchDocument.getDisplayName())
                .deliveryStatus(itemSearchDocument.getDeliveryStatus())
                .id(itemSearchDocument.getId())
                .build();
    }
}
