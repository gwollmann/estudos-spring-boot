package com.example.people.controller;

import java.net.URI;
import java.util.List;

import com.example.people.dto.PessoaRequest;
import com.example.people.dto.PessoaResponse;
import com.example.people.service.PessoaService;
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
public class PessoaController {

	private final PessoaService service;

	public PessoaController(PessoaService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<PessoaResponse> criar(@Valid @RequestBody PessoaRequest request) {
		PessoaResponse pessoa = service.criar(request);
		return ResponseEntity.created(URI.create("/api/pessoas/" + pessoa.id())).body(pessoa);
	}

	@GetMapping
	public List<PessoaResponse> listar() {
		return service.listar();
	}

	@GetMapping(params = "email")
	public List<PessoaResponse> buscarPorEmail(@RequestParam String email) {
		return service.buscarPorEmail(email);
	}

	@GetMapping("/{id}")
	public PessoaResponse buscarPorId(@PathVariable Long id) {
		return service.buscarPorId(id);
	}
}
