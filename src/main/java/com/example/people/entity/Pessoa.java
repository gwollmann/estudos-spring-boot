package com.example.people.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pessoas")
public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private String email;

	private String cpf;

	private String endereco;

	private String cep;

	protected Pessoa() {
	}

	public Pessoa(String nome, String email, String cpf, String endereco, String cep) {
		this.nome = nome;
		this.email = email;
		this.cpf = cpf;
		this.endereco = endereco;
		this.cep = cep;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getCpf() {
		return cpf;
	}

	public String getEndereco() {
		return endereco;
	}

	public String getCep() {
		return cep;
	}
}
