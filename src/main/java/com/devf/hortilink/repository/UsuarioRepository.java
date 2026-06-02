package com.devf.hortilink.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devf.hortilink.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	Optional<Usuario> findByEmail(String email);
	Boolean existsByEmail(String email);
	
	@EntityGraph(attributePaths = {
			"endereco"
	})
	Optional<Usuario> findPerfilById(Long id);
}
