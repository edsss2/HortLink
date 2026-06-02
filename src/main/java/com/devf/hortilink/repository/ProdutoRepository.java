package com.devf.hortilink.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devf.hortilink.entity.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	@EntityGraph(attributePaths = {"foto"})
	List<Produto> findByComercioId(Long comercioId);
	
	@EntityGraph(attributePaths = {"foto"})
    @Query("SELECT p FROM Produto p WHERE p.comercio.id = :comercioId " +
           "AND p.id NOT IN (" +
           "  SELECT o.produto.id FROM Oferta o " +
           "  WHERE o.comercio.id = :comercioId AND o.disponivelParaVenda = true" +
           ")")
    List<Produto> buscarProdutosSemOfertaAtiva(@Param("comercioId") Long comercioId);
}
