package com.softplan.api.entity;


import com.softplan.api.dto.DadosCadastroProduto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String descricao;
	private Double preco;
	private boolean ativo;
	
	public Produto(DadosCadastroProduto dados) {
		this.nome = dados.nome();
		this.descricao = dados.descricao();
		this.preco = dados.preco();
		this.ativo = true;
	}
}
