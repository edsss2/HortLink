package com.devf.hortilink.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devf.hortilink.entity.ComercioProfile;

@Repository
public interface ComercioProfileRepository extends JpaRepository<ComercioProfile, Long> {

	@EntityGraph(attributePaths = { "endereco", "fotoPerfil" }, type = EntityGraph.EntityGraphType.FETCH)
	List<ComercioProfile> findByEndereco_Cidade(String cidade);

	@EntityGraph(attributePaths = { "endereco" }, type = EntityGraph.EntityGraphType.FETCH)
	Optional<ComercioProfile> findComEnderecoById(Long id);

	@EntityGraph(attributePaths = { "endereco", "fotoPerfil" }, type = EntityGraph.EntityGraphType.FETCH)
	Optional<ComercioProfile> findPerfilCompletoById(Long id);

}
