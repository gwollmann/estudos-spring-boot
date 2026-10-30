package com.example.people.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PessoaRequest(
	@NotBlank String nome,
	@NotBlank @Email String email,
	String cpf,
	String endereco,
	String cep
) {
}
