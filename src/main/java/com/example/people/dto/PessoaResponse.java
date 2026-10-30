package com.example.people.dto;

import java.util.List;

import com.example.people.entity.Pessoa;

public record PessoaResponse(Long id, String nome, String email, String cpf, List<EnderecoResponse> enderecos) {

	public static PessoaResponse from(Pessoa pessoa) {
		return new PessoaResponse(
			pessoa.getId(),
			pessoa.getNome(),
			pessoa.getEmail(),
			pessoa.getCpf(),
			pessoa.getEnderecos().stream()
				.map(EnderecoResponse::from)
				.toList()
		);
	}
}
