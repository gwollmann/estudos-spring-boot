package com.example.people.dto;

import com.example.people.entity.Endereco;

public record EnderecoResponse(Long id, String endereco, String cep) {

	public static EnderecoResponse from(Endereco endereco) {
		return new EnderecoResponse(endereco.getId(), endereco.getEndereco(), endereco.getCep());
	}
}
