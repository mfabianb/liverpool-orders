package com.liverpool.orders.application.service;

import com.liverpool.orders.application.ports.CustomerRepositoryPort;
import com.liverpool.orders.application.ports.OrdersApiPort;
import com.liverpool.orders.domain.exceptions.BusinessException;
import com.liverpool.orders.domain.exceptions.CustomerNotFoundException;
import com.liverpool.orders.domain.model.Customer;
import com.liverpool.orders.domain.model.Order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateCustomerServiceTest {

    @Mock
    private CustomerRepositoryPort customerRepositoryPort;

    @Mock
    private OrdersApiPort ordersApiPort;

    @InjectMocks
    private UpdateCustomerService service;

    private Customer existingCustomer;
    private Customer requestCustomer;

    @BeforeEach
    void setup() {

        existingCustomer = new Customer();
        existingCustomer.setUserId("user-123");
        existingCustomer.setEmail("old@test.com");

        requestCustomer = new Customer();
        requestCustomer.setUserId("user-123");
        requestCustomer.setEmail("new@test.com");
        requestCustomer.setFirstName("Martin");
        requestCustomer.setLastName("Fabian");
        requestCustomer.setMiddleName("Lopez");
        requestCustomer.setShippingAddress("Mexico City");
    }

    @Test
    void shouldUpdateCustomerSuccessfully() {

        Order order = new Order();
        order.setOrderRef("ORDER-1");

        when(customerRepositoryPort.findByUserId("user-123"))
                .thenReturn(Optional.of(existingCustomer));

        when(ordersApiPort.getOrders("userId=user-123"))
                .thenReturn(List.of(order));

        when(customerRepositoryPort.save(any(Customer.class)))
                .thenAnswer(invocation ->
                        Optional.of(invocation.getArgument(0)));

        Customer result = service.updateCustomer(requestCustomer);

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("new@test.com");
        assertThat(result.getFirstName()).isEqualTo("Martin");
        assertThat(result.getOrders()).hasSize(1);

        verify(customerRepositoryPort).save(any(Customer.class));
    }

    @Test
    void shouldThrowCustomerNotFoundException() {

        when(customerRepositoryPort.findByUserId("user-123"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                service.updateCustomer(requestCustomer))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessageContaining("userId=user-123");

        verify(customerRepositoryPort, never()).save(any());
    }

    @Test
    void shouldContinueWhenOrdersApiReturnsNotFound() {

        when(customerRepositoryPort.findByUserId("user-123"))
                .thenReturn(Optional.of(existingCustomer));

        when(ordersApiPort.getOrders("userId=user-123"))
                .thenThrow(new CustomerNotFoundException("No orders"));

        when(customerRepositoryPort.save(any(Customer.class)))
                .thenAnswer(invocation ->
                        Optional.of(invocation.getArgument(0)));

        Customer result = service.updateCustomer(requestCustomer);

        assertThat(result).isNotNull();
        assertThat(result.getOrders()).isEmpty();

        verify(customerRepositoryPort).save(any(Customer.class));
    }

    @Test
    void shouldThrowBusinessExceptionWhenSaveReturnsEmpty() {

        when(customerRepositoryPort.findByUserId("user-123"))
                .thenReturn(Optional.of(existingCustomer));

        when(ordersApiPort.getOrders("userId=user-123"))
                .thenReturn(List.of());

        when(customerRepositoryPort.save(any(Customer.class)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                service.updateCustomer(requestCustomer))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void shouldThrowBusinessExceptionWhenRepositoryFails() {

        when(customerRepositoryPort.findByUserId("user-123"))
                .thenReturn(Optional.of(existingCustomer));

        when(ordersApiPort.getOrders("userId=user-123"))
                .thenReturn(List.of());

        when(customerRepositoryPort.save(any(Customer.class)))
                .thenThrow(new RuntimeException("Mongo error"));

        assertThatThrownBy(() ->
                service.updateCustomer(requestCustomer))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Mongo error");
    }
}