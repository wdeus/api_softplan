package com.softplan.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.softplan.api.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	@Query("Select p from Produto p where ativo = true")
	public List<Produto> findProdutosAtivos();
}
