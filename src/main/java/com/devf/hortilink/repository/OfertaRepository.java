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
	
	@EntityGraph(attributePaths = {"produto", "produto.foto", "comercio", "comercio.endereco"})
    @Query("SELECT o FROM Oferta o")
    List<Oferta> buscarTodasOfertasParaApp();
	
	@EntityGraph(attributePaths = {"produto", "produto.foto"})
	@Query("SELECT o FROM Oferta o WHERE o.comercio.id = :comercioId")
	List<Oferta> buscarOfertasByComercioId(@Param("comercioId") Long comercioId);
	
	@EntityGraph(attributePaths = {
	        "produto", 
	        "produto.foto",
	        "comercio.users.endereco"
	})
	@Query("SELECT o FROM Oferta o WHERE o.id = :id")
	Optional<Oferta> buscarOfertaDetalhadaPorId(@Param("id") Long id);
	
	@EntityGraph(attributePaths = {"produto", "produto.foto"})
	Optional<Oferta> findOfertaParaEdicaoById(@Param("id") Long id);
	
	/**
     * Verifica se existe uma Oferta específica vinculada a um Comércio específico.
     * Útil para validação de segurança antes de edições ou exclusões.
     */
    boolean existsByIdAndComercioId(Long idOferta, Long comercioId);
    
    boolean existsByComercioIdAndProdutoIdAndDisponivelParaVendaTrue(Long comercioId, Long produtoId);
}
