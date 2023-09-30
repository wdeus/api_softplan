package com.softplan.api.dto;

import com.softplan.api.entity.Produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroProduto(
		@NotBlank
		String nome, 
		@NotBlank
		String descricao, 
		@NotNull
		Double preco) {
	
	public DadosCadastroProduto(Produto produto) {
		this(produto.getNome(), produto.getDescricao(), produto.getPreco());
	}

}
