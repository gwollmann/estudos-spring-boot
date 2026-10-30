package com.example.people.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "enderecos")
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String endereco;

	@Column(nullable = false)
	private String cep;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "pessoa_id", nullable = false)
	private Pessoa pessoa;

	protected Endereco() {
	}

	public Endereco(String endereco, String cep) {
		this.endereco = endereco;
		this.cep = cep;
	}

	public Long getId() {
		return id;
	}

	public String getEndereco() {
		return endereco;
	}

	public String getCep() {
		return cep;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
}
