package com.devf.hortilink.service;

import org.springframework.web.multipart.MultipartFile;

import com.devf.hortilink.entity.ComercioProfile;
import com.devf.hortilink.entity.Foto;
import com.devf.hortilink.entity.Produto;

public interface FotoService {
	
	Foto buscarPorId(Long id);
	Foto excluirPorId(Long id);
	String buscarUrlPorId(Long id);
	Foto salvarFotoProduto(MultipartFile file, Produto produto, int ordem);
	Foto salvarFotoComercio(MultipartFile file, ComercioProfile comercio, int ordem);
	
}
