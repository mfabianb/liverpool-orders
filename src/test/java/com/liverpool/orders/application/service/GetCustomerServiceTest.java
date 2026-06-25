package com.liverpool.orders.application.service;

import com.liverpool.orders.application.ports.CustomerRepositoryPort;
import com.liverpool.orders.application.ports.OrdersApiPort;
import com.liverpool.orders.domain.exceptions.CustomerNotFoundException;
import com.liverpool.orders.domain.model.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetCustomerServiceTest {

    @Mock
    private CustomerRepositoryPort customerRepositoryPort;

    @Mock
    private OrdersApiPort ordersApiPort;

    @InjectMocks
    private GetCustomerService service;

    @Test
    void shouldReturnCustomer() {

        Customer customer = new Customer();
        customer.setUserId("user-1");

        when(customerRepositoryPort.findByUserId("user-1"))
                .thenReturn(Optional.of(customer));

        Customer result = service.getCustomer("user-1");

        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo("user-1");
    }

    @Test
    void shouldThrowCustomerNotFoundException() {

        when(customerRepositoryPort.findByUserId("user-1"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getCustomer("user-1"))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessageContaining("userId=user-1");
    }

    @Test
    void shouldReturnCustomerList() {

        Customer customer1 = new Customer();
        Customer customer2 = new Customer();

        when(customerRepositoryPort.findAll())
                .thenReturn(List.of(customer1, customer2));

        List<Customer> result = service.getCustomerList();

        assertThat(result).hasSize(2);
    }

    @Test
    void shouldReturnEmptyCustomerList() {

        when(customerRepositoryPort.findAll())
                .thenReturn(List.of());

        List<Customer> result = service.getCustomerList();

        assertThat(result).isEmpty();
    }
}