package com.example.people.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record AddressRequest(
	@JsonProperty("endereco") @NotBlank String streetAddress,
	@JsonProperty("cep") @NotBlank String postalCode
) {
}
