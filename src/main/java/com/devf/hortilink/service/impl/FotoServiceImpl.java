package com.devf.hortilink.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.devf.hortilink.entity.ComercioProfile;
import com.devf.hortilink.entity.Foto;
import com.devf.hortilink.entity.Produto;
import com.devf.hortilink.repository.FotoRepository;
import com.devf.hortilink.service.FotoService;
import com.devf.hortilink.service.StorageService;

@Service
public class FotoServiceImpl implements FotoService {

	@Autowired
	private FotoRepository repository;
	
	@Autowired
	private StorageService storageService;

	@Override
	public Foto buscarPorId(Long id) {
		return repository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Foto não encontrada com o ID: " + id));
	}

	@Override
	public Foto excluirPorId(Long id) {
		Foto foto = buscarPorId(id);
		repository.delete(foto);
		return foto;
	}

	@Override
	public String buscarUrlPorId(Long id) {
		Foto foto = buscarPorId(id);
		return foto.getCaminhoArquivo();
	}
	
	public Foto salvarFoto(MultipartFile file, int ordem, String entityType, Long idEntity) throws IOException {
        
        // 1. Salva no disco usando o StorageService
        String caminhoRelativo = storageService.salvarArquivo(file, "produto", idEntity);

        // 2. Cria a entidade Foto
        Foto foto = new Foto();
        foto.setNomeArquivo(extrairNomeUnico(caminhoRelativo));
        foto.setCaminhoArquivo(caminhoRelativo);
        foto.setTipoConteudo(file.getContentType());
        foto.setOrdemExibicao(ordem);

        return foto;
    }
	

	private String extrairNomeUnico(String caminhoRelativo) {
	    if (caminhoRelativo == null || !caminhoRelativo.contains("/")) {
	        return caminhoRelativo;
	    }
	    return caminhoRelativo.substring(caminhoRelativo.lastIndexOf("/") + 1);
	}

	@Override
	public Foto salvarFotoProduto(MultipartFile file, Produto produto, int ordem) {
		try {
			Foto foto = salvarFoto(file, ordem, "produto", produto.getId());
			foto.setProduto(produto);
			return repository.save(foto);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar imagem: " + e.getMessage());
        }

	}

	@Override
	public Foto salvarFotoComercio(MultipartFile file, ComercioProfile comercio, int ordem) {
		try {
			Foto foto = salvarFoto(file, ordem, "comercio", comercio.getId());
			foto.setComercioProfile(comercio);
			return repository.save(foto);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar imagem: " + e.getMessage());
        }
	}
	
}
