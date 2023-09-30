package com.softplan.api.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.given;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.softplan.api.dto.DadosCadastroProduto;
import com.softplan.api.entity.Produto;
import com.softplan.api.repository.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

	@InjectMocks
	private ProdutoService service;
	
	@Mock 
	private ProdutoRepository repository;
	
	private DadosCadastroProduto dados;
	
	@Captor
	private ArgumentCaptor<Produto> produtoCaptor;
	
	@Mock
	private Produto protudo;
	
	@Test
	@DisplayName("Deve cadastrar produto")
	void cadastrarProduto() {
		this.dados = new DadosCadastroProduto("Carrinho", "Carrinho bacana", 20d);
		
		service.cadastrar(dados);
		
		then(repository).should().save(produtoCaptor.capture());
		Produto produto = produtoCaptor.getValue();
		assertEquals("Carrinho", produto.getNome());
		assertEquals("Carrinho bacana", produto.getDescricao());
		assertEquals(20d, produto.getPreco());
	} 
	
	@Test 
	@DisplayName("Deve listar produto")
	void listarProduto() {
		service.listar();
		
		then(repository).should().findProdutosAtivos();
	}
	
	@Test
	@DisplayName("Deve alterar produto")
	void alterarProduto() {
		given(repository.getReferenceById(1l)).willReturn(protudo);
		this.dados = new DadosCadastroProduto("carro", "vermelho", 200d);
		
		service.alterar(dados, 1l);
		
		then(this.protudo).should().setNome("carro");
		then(this.protudo).should().setDescricao("vermelho");
		then(this.protudo).should().setPreco(200d);
		
	}
	
	
	@Test
	@DisplayName("Deve excluir produto")
	void excluirProduto() {
		given(repository.getReferenceById(1l)).willReturn(protudo);
		
		service.deletar(1l);
		
		then(this.protudo).should().setAtivo(false);
	}
}
