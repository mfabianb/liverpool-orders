package com.liverpool.orders.application.usecase;

import com.liverpool.orders.infrastructure.adapters.elasticsearch.document.OrderSearchDocument;

public interface IndexOrdersUseCase {
    void index();
}
