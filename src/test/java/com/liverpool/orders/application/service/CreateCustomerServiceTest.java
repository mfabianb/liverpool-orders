package com.liverpool.orders.application.service;

import com.liverpool.orders.application.ports.CustomerRepositoryPort;
import com.liverpool.orders.domain.exceptions.BusinessException;
import com.liverpool.orders.domain.model.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCustomerServiceTest {

    @Mock
    private CustomerRepositoryPort customerRepositoryPort;

    @InjectMocks
    private CreateCustomerService service;

    @Test
    void shouldCreateCustomerSuccessfully() {

        Customer customer = new Customer();
        customer.setUserId("user-1");

        when(customerRepositoryPort.findByUserId("user-1"))
                .thenReturn(Optional.empty());

        when(customerRepositoryPort.save(customer))
                .thenReturn(Optional.of(customer));

        Customer result = service.execute(customer);

        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo("user-1");

        verify(customerRepositoryPort).save(customer);
    }

    @Test
    void shouldThrowBusinessExceptionWhenUserAlreadyExists() {

        Customer customer = new Customer();
        customer.setUserId("user-1");

        when(customerRepositoryPort.findByUserId("user-1"))
                .thenReturn(Optional.of(customer));

        assertThatThrownBy(() -> service.execute(customer))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("User already exists");

        verify(customerRepositoryPort, never()).save(any());
    }

    @Test
    void shouldThrowBusinessExceptionWhenSaveReturnsEmpty() {

        Customer customer = new Customer();
        customer.setUserId("user-1");

        when(customerRepositoryPort.findByUserId("user-1"))
                .thenReturn(Optional.empty());

        when(customerRepositoryPort.save(customer))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.execute(customer))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void shouldThrowBusinessExceptionWhenRepositoryFails() {

        Customer customer = new Customer();
        customer.setUserId("user-1");

        when(customerRepositoryPort.findByUserId("user-1"))
                .thenReturn(Optional.empty());

        when(customerRepositoryPort.save(customer))
                .thenThrow(new RuntimeException("Mongo Error"));

        assertThatThrownBy(() -> service.execute(customer))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Mongo Error");
    }
}