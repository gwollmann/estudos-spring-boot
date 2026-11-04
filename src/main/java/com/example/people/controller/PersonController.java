package com.example.people.controller;

import java.net.URI;
import java.util.List;

import com.example.people.dto.PersonRequest;
import com.example.people.dto.PersonResponse;
import com.example.people.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pessoas")
public class PersonController {

	private final PersonService personService;

	public PersonController(PersonService personService) {
		this.personService = personService;
	}

	@PostMapping
	public ResponseEntity<PersonResponse> create(@Valid @RequestBody PersonRequest request) {
		PersonResponse person = personService.create(request);
		return ResponseEntity.created(URI.create("/api/pessoas/" + person.id())).body(person);
	}

	@GetMapping
	public List<PersonResponse> findAll() {
		return personService.findAll();
	}

	@GetMapping(params = {"email", "!cep"})
	public List<PersonResponse> findByEmail(@RequestParam String email) {
		return personService.findByEmail(email);
	}

	@GetMapping(params = {"cep", "!email"})
	public List<PersonResponse> findByPostalCode(@RequestParam String cep) {
		return personService.findByPostalCode(cep);
	}

	@GetMapping("/{id}")
	public PersonResponse findById(@PathVariable Long id) {
		return personService.findById(id);
	}
}
