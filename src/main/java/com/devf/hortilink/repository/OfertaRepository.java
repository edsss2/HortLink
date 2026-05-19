package com.devf.hortilink.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devf.hortilink.entity.Oferta;

@Repository
public interface OfertaRepository extends JpaRepository<Oferta, Long> {

	List<Oferta> findByProdutoIdIn(List<Long> productIds);
	
	@EntityGraph(attributePaths = {"produto", "produto.fotos"})
    @Query("SELECT o FROM Oferta o")
    List<Oferta> buscarTodasOfertasParaApp();
	
}
