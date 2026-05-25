package com.devf.hortilink.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.devf.hortilink.dto.RegistroDTO;
import com.devf.hortilink.entity.Carrinho;
import com.devf.hortilink.entity.ComercioProfile;
import com.devf.hortilink.entity.Endereco;
import com.devf.hortilink.entity.Foto;
import com.devf.hortilink.entity.Usuario;
import com.devf.hortilink.enums.Role;
import com.devf.hortilink.repository.UsuarioRepository;
import com.devf.hortilink.service.UsuarioService;

import jakarta.transaction.Transactional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public List<Usuario> listarTodos() {
		return repository.findAll();
	}

	@Override
	public Usuario buscarPorId(Long id) {
		return repository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado com ID: " + id));
	}

	@Override
	public Usuario buscarPorEmail(String email) {
		return repository.findByEmail(email).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado com email: " + email));
	}

	@Override
	public Usuario excluirPorId(Long id) {
		Usuario usuario = buscarPorId(id);
		repository.delete(usuario);
		return usuario;

	}

	@Override
	@Transactional // Garante que se der erro no meio, ele desfaz tudo (rollback)
	public Usuario salvar(RegistroDTO dto) {
	    
	    // 1. (Opcional) Verificar se o e-mail já existe para evitar erro 500 do banco
	    if (repository.findByEmail(dto.getEmail()).isPresent()) {
	        throw new RuntimeException("Este e-mail já está em uso."); // Trate na sua exception global
	    }

	    // 2. Mapeamento básico
	    Usuario usuario = new Usuario();
	    usuario.setEmail(dto.getEmail());
	    usuario.setNome(dto.getNome());
	    usuario.setRole(Role.valueOf(dto.getRole().toUpperCase())); // .toUpperCase() evita bugs caso o celular mande minúsculo
	    usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
	    usuario.setTelefone(dto.getTelefone());

	    if (usuario.getRole() == Role.PRODUTOR || usuario.getRole() == Role.COMERCIO) {
	        
	        ComercioProfile comercio = new ComercioProfile();
	        comercio.setTelefone(dto.getTelefone());
	        
	        usuario.setComercioProfile(comercio);

	    } else if (usuario.getRole() == Role.CONSUMIDOR) {
	        
	        Carrinho carrinho = new Carrinho();
	        carrinho.setComprador(usuario);
	        usuario.setCarrinho(carrinho);
	    }

	    // 4. Salva tudo em cascata (CascadeType.ALL fará o Hibernate salvar o Comércio e o Carrinho junto)
	    return repository.save(usuario);
	}
	
	@Override
	public Foto buscarFotoPorId(Long id) {
		Usuario usuario = repository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado com ID: " + id));
		
		return usuario.getFoto();
	}

	@Override
	public void atualizarEndereco(Long id, Endereco endereco) {
		Usuario usuario = buscarPorId(id);
		usuario.setEndereco(endereco);
		repository.save(usuario);
	}

	@Override
	public void atualizarFoto(Long id, Foto foto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean existeComEmail(String email) {
		return repository.existsByEmail(email);
	}

}
