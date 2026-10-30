package com.example.people.dto;

import com.example.people.entity.Pessoa;

public record PessoaResponse(Long id, String nome, String email, String cpf, String endereco, String cep) {

	public static PessoaResponse from(Pessoa pessoa) {
		return new PessoaResponse(
			pessoa.getId(),
			pessoa.getNome(),
			pessoa.getEmail(),
			pessoa.getCpf(),
			pessoa.getEndereco(),
			pessoa.getCep()
		);
	}
}
