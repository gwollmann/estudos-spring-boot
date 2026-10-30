package com.example.people.service;

import java.util.List;

import com.example.people.dto.PessoaRequest;
import com.example.people.dto.PessoaResponse;
import com.example.people.entity.Endereco;
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
		Pessoa pessoa = new Pessoa(request.nome(), request.email(), request.cpf());
		request.enderecos().stream()
			.map(endereco -> new Endereco(endereco.endereco(), endereco.cep()))
			.forEach(pessoa::adicionarEndereco);
		pessoa = repository.save(pessoa);
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
		return repository.findDistinctByEnderecos_Cep(cep).stream()
			.map(PessoaResponse::from)
			.toList();
	}

	public PessoaResponse buscarPorId(Long id) {
		Pessoa pessoa = repository.findById(id)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada"));
		return PessoaResponse.from(pessoa);
	}
}
