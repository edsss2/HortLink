package com.devf.hortilink.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devf.hortilink.entity.Oferta;

@Repository
public interface OfertaRepository extends JpaRepository<Oferta, Long> {

	List<Oferta> findByProdutoIdIn(List<Long> productIds);
	
	@EntityGraph(attributePaths = {"produto", "produto.fotos"})
    @Query("SELECT o FROM Oferta o")
    List<Oferta> buscarTodasOfertasParaApp();
	
	@Query("SELECT o FROM Oferta o " +
	           "JOIN FETCH o.produto " +
	           "JOIN FETCH o.comercio c " +
	           "JOIN FETCH c.users u " +
	           "JOIN FETCH u.endereco " +
	           "WHERE o.id = :id")
	Optional<Oferta> buscarOfertaDetalhadaPorId(@Param("id") Long id);
	
}
