package com.devf.hortilink.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devf.hortilink.dto.PerfilCompradorDTO;
import com.devf.hortilink.dto.RegistroDTO;
import com.devf.hortilink.entity.Endereco;
import com.devf.hortilink.entity.Foto;
import com.devf.hortilink.entity.Usuario;

@Service
public interface UsuarioService {

	List<Usuario> listarTodos();
	Usuario buscarPorId(Long id);
	Usuario buscarPorEmail(String email);
	Usuario excluirPorId(Long id);
	Usuario salvar(RegistroDTO usuario);
	Boolean existeComEmail(String email);
	Foto buscarFotoPorId(Long id);
	void atualizarEndereco(Long id, Endereco endereco);
	void atualizarFoto(Long id, Foto foto);
	PerfilCompradorDTO buscarPerfilPorId(Long id);
	PerfilCompradorDTO buscarPerfilClientePorId(Long clienteId, Long comercioId);
	Usuario atualizarPerfil(Long id, PerfilCompradorDTO dto);
}
