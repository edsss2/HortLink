package com.devf.hortilink.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.devf.hortilink.dto.PerfilCompradorDTO;
import com.devf.hortilink.dto.RegistroDTO;
import com.devf.hortilink.entity.ComercioProfile;
import com.devf.hortilink.entity.Endereco;
import com.devf.hortilink.entity.Foto;
import com.devf.hortilink.entity.Usuario;
import com.devf.hortilink.enums.Role;
import com.devf.hortilink.repository.PedidoRepository;
import com.devf.hortilink.repository.UsuarioRepository;
import com.devf.hortilink.service.UsuarioService;

import jakarta.transaction.Transactional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	@Autowired
	private PedidoRepository pedidoRepository;
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
	    
	    if (repository.findByEmail(dto.getEmail()).isPresent()) {
	    	throw new ResponseStatusException(HttpStatus.CONFLICT, "Este e-mail já está em uso.");
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
	public void atualizarFoto(Long id, Foto foto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean existeComEmail(String email) {
		return repository.existsByEmail(email);
	}

	@Override
	public PerfilCompradorDTO buscarPerfilPorId(Long id) {
		Usuario usuario = repository.findPerfilById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado com ID: " + id));
		
		return new PerfilCompradorDTO().fromEntity(usuario);
	}
	
	@Override
	public PerfilCompradorDTO buscarPerfilClientePorId(Long clienteId, Long comercioId) {
		boolean temVinculo = pedidoRepository.existsByClienteIdAndVendedorId(clienteId, comercioId);

        if (!temVinculo) {
            // Lança um erro 403 (Forbidden). O usuário logado não tem negócios com este cliente.
            throw new AccessDeniedException("Acesso negado: Você não possui permissão para ver os dados deste cliente.");
        }
		
		Usuario usuario = repository.findPerfilById(clienteId).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado com ID: " + clienteId));
		
		return new PerfilCompradorDTO().fromEntity(usuario);
	}

	@Override
	public PerfilCompradorDTO atualizarPerfil(Long id, PerfilCompradorDTO dto) {
		Usuario usuario = buscarPorId(id);
		usuario.setTelefone(dto.getTelefone());
		
		Endereco endereco = usuario.getEndereco();
		if(endereco == null) {
			endereco = new Endereco();
		}
		
		endereco.setCep(dto.getCep());
		endereco.setCidade(dto.getCidade());
		endereco.setEstado(dto.getEstado());
		endereco.setBairro(dto.getBairro());
		endereco.setComplemento(dto.getComplemento());
		
		usuario.setEndereco(endereco);
		usuario = repository.save(usuario);
		return new PerfilCompradorDTO().fromEntity(usuario);
		
	}

}
