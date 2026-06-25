package com.liverpool.orders.infrastructure.adapters.elasticsearch.repository;

import com.liverpool.orders.infrastructure.adapters.elasticsearch.document.OrderSearchDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderSearchRepository
        extends ElasticsearchRepository<OrderSearchDocument, String> {
}
