package com.example.people.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PersonRequest(
	@JsonProperty("nome") @NotBlank String name,
	@JsonProperty("email") @NotBlank @Email String email,
	@JsonProperty("cpf") String taxId,
	@JsonProperty("enderecos") List<@Valid AddressRequest> addresses
) {
	public PersonRequest {
		addresses = addresses == null ? List.of() : List.copyOf(addresses);
	}
}
