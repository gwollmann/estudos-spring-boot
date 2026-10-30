package com.example.people.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PessoaRequest(
	@NotBlank String nome,
	@NotBlank @Email String email,
	String cpf,
	List<@Valid EnderecoRequest> enderecos
) {
	public PessoaRequest {
		enderecos = enderecos == null ? List.of() : List.copyOf(enderecos);
	}
}
