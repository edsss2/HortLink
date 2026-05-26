package com.devf.hortilink.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devf.hortilink.entity.Carrinho;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {

	@EntityGraph(attributePaths = {
	        "comprador",              
	        "itens",                   
	        "itens.oferta", 
	        "itens.oferta.comercio",
	        "itens.oferta.produto",
	        "itens.oferta.produto.foto"
	    })
	    Optional<Carrinho> findByCompradorId(Long compradorId);
}
