package com.example.people;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.people.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApiControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void limparPessoas() {
		pessoaRepository.deleteAll();
	}

	@Test
	void createsAndQueriesPeople() throws Exception {
		String response = mockMvc.perform(post("/api/pessoas")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"nome\":\"Ana Silva\",\"email\":\"ana@example.com\","
					+ "\"cpf\":\"12345678901\",\"enderecos\":["
					+ "{\"endereco\":\"Rua das Flores, 10\",\"cep\":\"01001000\"},"
					+ "{\"endereco\":\"Av. Brasil, 20\",\"cep\":\"01001000\"}]}"))
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", org.hamcrest.Matchers.startsWith("/api/pessoas/")))
			.andExpect(jsonPath("$.id").isNumber())
			.andExpect(jsonPath("$.nome").value("Ana Silva"))
			.andExpect(jsonPath("$.email").value("ana@example.com"))
			.andExpect(jsonPath("$.cpf").value("12345678901"))
			.andExpect(jsonPath("$.enderecos").isArray())
			.andExpect(jsonPath("$.enderecos.length()").value(2))
			.andExpect(jsonPath("$.enderecos[0].endereco").value("Rua das Flores, 10"))
			.andExpect(jsonPath("$.enderecos[0].cep").value("01001000"))
			.andReturn()
			.getResponse()
			.getContentAsString();

		Long id = objectMapper.readTree(response).get("id").asLong();

		mockMvc.perform(get("/api/pessoas"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].id").value(id))
			.andExpect(jsonPath("$[0].nome").value("Ana Silva"));

		mockMvc.perform(get("/api/pessoas").param("email", "ana@example.com"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].id").value(id))
			.andExpect(jsonPath("$[0].email").value("ana@example.com"));

		mockMvc.perform(get("/api/pessoas").param("cep", "01001000"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.length()").value(1))
			.andExpect(jsonPath("$[0].id").value(id))
			.andExpect(jsonPath("$[0].enderecos.length()").value(2));

		mockMvc.perform(get("/api/pessoas/{id}", id))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(id))
			.andExpect(jsonPath("$.email").value("ana@example.com"));
	}

	@Test
	void returnsEmptyListWhenEmailDoesNotMatch() throws Exception {
		mockMvc.perform(get("/api/pessoas").param("email", "ausente@example.com"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").isEmpty());
	}

	@Test
	void returnsEmptyListWhenCepDoesNotMatch() throws Exception {
		mockMvc.perform(get("/api/pessoas").param("cep", "99999999"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").isEmpty());
	}

	@Test
	void acceptsPersonWithoutAddresses() throws Exception {
		mockMvc.perform(post("/api/pessoas")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"nome\":\"João Silva\",\"email\":\"joao@example.com\"}"))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.enderecos").isEmpty());
	}

	@Test
	void rejectsInvalidPersonAndReturnsNotFoundForMissingId() throws Exception {
		mockMvc.perform(post("/api/pessoas")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"nome\":\"\",\"email\":\"inválido\"}"))
			.andExpect(status().isBadRequest());

		mockMvc.perform(get("/api/pessoas/999"))
			.andExpect(status().isNotFound());
	}
}
