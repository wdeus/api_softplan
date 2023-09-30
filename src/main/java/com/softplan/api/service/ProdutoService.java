package com.softplan.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.softplan.api.dto.DadosCadastroProduto;
import com.softplan.api.entity.Produto;
import com.softplan.api.repository.ProdutoRepository;

@Service
public class ProdutoService {

	private ProdutoRepository repository;
	
	public ProdutoService(ProdutoRepository repository) {
		this.repository = repository;
	}
	
	public Produto cadastrar(DadosCadastroProduto dados) {
		var produto = new Produto(dados);
		repository.save(produto);
		return produto; 
	}
	
	public List<DadosCadastroProduto> listar(){
		return repository.findProdutosAtivos().stream().map(DadosCadastroProduto::new).toList();
	}
	
	public Produto alterar(DadosCadastroProduto dados, Long id) {
		var produto = repository.getReferenceById(id);
		produto.setNome(dados.nome());
		produto.setDescricao(dados.descricao());
		produto.setPreco(dados.preco());
		return produto;
	}
	
	public Produto deletar(Long id) {
		var produto = repository.getReferenceById(id);
		produto.setAtivo(false);
		return produto;
	}
}
