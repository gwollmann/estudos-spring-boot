package com.example.people.dto;

import com.example.people.entity.Pessoa;

public record PessoaResponse(Long id, String nome, String email) {

	public static PessoaResponse from(Pessoa pessoa) {
		return new PessoaResponse(pessoa.getId(), pessoa.getNome(), pessoa.getEmail());
	}
}
