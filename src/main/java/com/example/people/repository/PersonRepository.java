package com.example.people.repository;

import java.util.List;

import com.example.people.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
	List<Person> findAllByEmail(String email);
	List<Person> findDistinctByAddresses_PostalCode(String postalCode);
}
