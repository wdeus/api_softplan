package com.softplan.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.softplan.api.dto.DadosCadastroProduto;
import com.softplan.api.entity.Produto;
import com.softplan.api.repository.ProdutoRepository;
import com.softplan.api.service.ProdutoService;

import jakarta.persistence.EntityNotFoundException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.io.IOException;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ProdutorControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
    private JacksonTester<DadosCadastroProduto> dados;
	
	@MockBean
	private ProdutoService service;
	
	private DadosCadastroProduto dto = new DadosCadastroProduto("Carrinho", "branco", 25d);
		
	@Test
	@DisplayName("Deve retornar codigo http 201, pois todos os dados foram enviados corretamente")
	void cadastroRetornandoCodigo201() throws IOException, Exception {
		given(service.cadastrar(dto)).willReturn(new Produto());
	
		var response = mvc.perform(post("/produto")
				.contentType(MediaType.APPLICATION_JSON)
				.content(dados.write(dto).getJson()))
				.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
	}
	
	@Test
	@DisplayName("Deve retornar codigo http 400, pois nem todos os dados foram enviados corretamente")
	void cadastroRetornandoCodigo400() throws IOException, Exception {
		var response = mvc.perform(post("/produto")
				.contentType(MediaType.APPLICATION_JSON)
				.content(dados.write(new DadosCadastroProduto("Carrinho", "", 20d)).getJson()))
				.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	@DisplayName("Deve retornar codigo http 200 para listagem de produtos")
	void listagemRetornandoCodigo200() throws IOException, Exception {
		var response = mvc.perform(get("/produto")
				).andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	@DisplayName("Deve retornar codigo http 200 para alteração de produtos")
	void alteracaoRetornandoCodigo200() throws IOException, Exception {	
		given(service.alterar(dto, 1l)).willReturn(new Produto());
		
		var response = mvc.perform(put("/produto/{id}", "1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(dados.write(dto).getJson()))
				.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	@DisplayName("Deve retornar codigo http 404 para alteração de produtos de id inexistente ")
	void alteracaoRetornandoCodigo404() throws IOException, Exception {	
		given(service.alterar(dto, 1l)).willThrow(EntityNotFoundException.class);
		
		var response = mvc.perform(put("/produto/{id}", "1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(dados.write(dto).getJson()))
				.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	@DisplayName("Deve retornar codigo http 204 para exclusão de produto")
	void exclusaoRetornandoCodigo204() throws IOException, Exception {	
		given(service.deletar(1l)).willReturn(new Produto());
		
		var response = mvc.perform(delete("/produto/{id}", "1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(dados.write(dto).getJson()))
				.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
	}
	
	@Test
	@DisplayName("Deve retornar codigo http 404 para exclusão de produto de id inexistente")
	void exclusaoRetornandoCodigo404() throws IOException, Exception {	
		given(service.deletar(1l)).willThrow(EntityNotFoundException.class);
		
		var response = mvc.perform(delete("/produto/{id}", "1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(dados.write(dto).getJson()))
				.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}
	
	
}
