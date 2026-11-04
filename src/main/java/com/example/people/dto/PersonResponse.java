package com.example.people.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.people.entity.Person;

public record PersonResponse(
	Long id,
	@JsonProperty("nome") String name,
	String email,
	@JsonProperty("cpf") String taxId,
	@JsonProperty("enderecos") List<AddressResponse> addresses
) {

	public static PersonResponse from(Person person) {
		return new PersonResponse(
			person.getId(),
			person.getName(),
			person.getEmail(),
			person.getTaxId(),
			person.getAddresses().stream()
				.map(AddressResponse::from)
				.toList()
		);
	}
}
