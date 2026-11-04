package com.example.people.service;

import java.util.List;

import com.example.people.dto.PersonRequest;
import com.example.people.dto.PersonResponse;
import com.example.people.entity.Address;
import com.example.people.entity.Person;
import com.example.people.exception.PersonNotFoundException;
import com.example.people.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {

	private final PersonRepository personRepository;

	public PersonService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@Transactional
	public PersonResponse create(PersonRequest request) {
		Person person = new Person(request.name(), request.email(), request.taxId());
		request.addresses().stream()
			.map(address -> new Address(address.streetAddress(), address.postalCode()))
			.forEach(person::addAddress);
		person = personRepository.save(person);
		return PersonResponse.from(person);
	}

	@Transactional(readOnly = true)
	public List<PersonResponse> findAll() {
		return personRepository.findAll().stream()
			.map(PersonResponse::from)
			.toList();
	}

	@Transactional(readOnly = true)
	public List<PersonResponse> findByEmail(String email) {
		return personRepository.findAllByEmail(email).stream()
			.map(PersonResponse::from)
			.toList();
	}

	@Transactional(readOnly = true)
	public List<PersonResponse> findByPostalCode(String postalCode) {
		return personRepository.findDistinctByAddresses_PostalCode(postalCode).stream()
			.map(PersonResponse::from)
			.toList();
	}

	@Transactional(readOnly = true)
	public PersonResponse findById(Long id) {
		Person person = personRepository.findById(id)
			.orElseThrow(() -> new PersonNotFoundException(id));
		return PersonResponse.from(person);
	}
}
