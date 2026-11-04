package com.example.people.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.people.entity.Address;

public record AddressResponse(
	Long id,
	@JsonProperty("endereco") String streetAddress,
	@JsonProperty("cep") String postalCode
) {

	public static AddressResponse from(Address address) {
		return new AddressResponse(address.getId(), address.getStreetAddress(), address.getPostalCode());
	}
}
