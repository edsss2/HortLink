package com.devf.hortilink.service;

import java.util.List;

import com.devf.hortilink.dto.ComercioDTO;
import com.devf.hortilink.entity.ComercioProfile;
import com.devf.hortilink.entity.Foto;
import com.devf.hortilink.entity.Oferta;
import com.devf.hortilink.entity.Usuario;


public interface ComercioProfileService {

	List<ComercioProfile> listarTodos();
	ComercioProfile buscarPorId(Long id);
	ComercioProfile excluirPorId(Long id);
	ComercioProfile salvar(ComercioDTO comercioProfile, Usuario usuario);
	List<Foto> buscarFotosPorId(Long id);
	Oferta buscarOfertaPorId(Long idOferta);
	Oferta editarOferta(Long idOferta, Oferta oferta);
	Oferta adicionarOferta(Long id, Long idProduto, Oferta oferta);
	Oferta excluirOferta(Long idOferta);
	List<Oferta> buscarOfertasPorId(Long id);
}
