package com.example.people.dto;

import jakarta.validation.constraints.NotBlank;

public record EnderecoRequest(
	@NotBlank String endereco,
	@NotBlank String cep
) {
}
