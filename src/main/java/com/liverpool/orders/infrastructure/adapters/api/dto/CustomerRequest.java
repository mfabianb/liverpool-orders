package com.liverpool.orders.infrastructure.adapters.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest{

    private String userId;

    @NotBlank(message = "firstName is mandatory")
    @Pattern(regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚäöüÄÖÜñÑ. ]*${1,256}$", message = "must be of 1 to 256 length with no special characters")
    private String firstName;

    @NotBlank(message = "lastName is mandatory")
    @Pattern(regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚäöüÄÖÜñÑ. ]*${1,256}$", message = "must be of 1 to 256 length with no special characters")
    private String lastName;

    @Pattern(regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚäöüÄÖÜñÑ. ]*${1,256}$", message = "must be of 1 to 256 length with no special characters")
    private String middleName;

    @NotBlank(message = "email is mandatory")
    @Pattern(regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "must be of 2 to 256 length with no special characters")
    private String email;

    @NotBlank(message = "shippingAddress is mandatory")
    @Pattern(regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚäöüÄÖÜñÑ#. ]*${1,256}$", message = "must be of 1 to 256 length with no special characters")
    private String shippingAddress;

    private List<OrderResponse> orders;

}
