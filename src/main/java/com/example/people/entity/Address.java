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
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "endereco", nullable = false)
	private String streetAddress;

	@Column(name = "cep", nullable = false)
	private String postalCode;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "pessoa_id", nullable = false)
	private Person person;

	protected Address() {
	}

	public Address(String streetAddress, String postalCode) {
		this.streetAddress = streetAddress;
		this.postalCode = postalCode;
	}

	public Long getId() {
		return id;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public Person getPerson() {
		return person;
	}

	void setPerson(Person person) {
		this.person = person;
	}
}
