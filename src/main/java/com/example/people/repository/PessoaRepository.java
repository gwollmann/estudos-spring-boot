package com.example.people.repository;

import java.util.List;

import com.example.people.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	List<Pessoa> findAllByEmail(String email);
	List<Pessoa> findDistinctByEnderecos_Cep(String cep);
}
