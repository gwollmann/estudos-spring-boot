package com.example.people.service;

import java.util.List;

import com.example.people.dto.PessoaRequest;
import com.example.people.dto.PessoaResponse;
import com.example.people.entity.Pessoa;
import com.example.people.repository.PessoaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PessoaService {

	private final PessoaRepository repository;

	public PessoaService(PessoaRepository repository) {
		this.repository = repository;
	}

	public PessoaResponse criar(PessoaRequest request) {
		Pessoa pessoa = repository.save(new Pessoa(
			request.nome(),
			request.email(),
			request.cpf(),
			request.endereco(),
			request.cep()
		));
		return PessoaResponse.from(pessoa);
	}

	public List<PessoaResponse> listar() {
		return repository.findAll().stream()
			.map(PessoaResponse::from)
			.toList();
	}

	public List<PessoaResponse> buscarPorEmail(String email) {
		return repository.findAllByEmail(email).stream()
			.map(PessoaResponse::from)
			.toList();
	}

	public List<PessoaResponse> buscarPorCep(String cep) {
		return repository.findAllByCep(cep).stream()
			.map(PessoaResponse::from)
			.toList();
	}

	public PessoaResponse buscarPorId(Long id) {
		Pessoa pessoa = repository.findById(id)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada"));
		return PessoaResponse.from(pessoa);
	}
}
