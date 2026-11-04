package com.example.people.exception;

public class PersonNotFoundException extends RuntimeException {

	public PersonNotFoundException(Long id) {
		super("Pessoa não encontrada: " + id);
	}
}
