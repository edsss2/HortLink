package com.devf.hortilink.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.devf.hortilink.dto.ComercioDTO;
import com.devf.hortilink.dto.CompletarPerfilComercioDTO;
import com.devf.hortilink.entity.ComercioProfile;
import com.devf.hortilink.entity.Oferta;
import com.devf.hortilink.entity.Produto;
import com.devf.hortilink.repository.ComercioProfileRepository;
import com.devf.hortilink.repository.OfertaRepository;
import com.devf.hortilink.service.ComercioProfileService;
import com.devf.hortilink.service.ProdutoService;
import com.devf.hortilink.service.UsuarioService;

@Service
public class ComercioProfileServiceImpl implements ComercioProfileService {

	@Autowired
	private ComercioProfileRepository repository;
	
	@Autowired
	private OfertaRepository ofertaRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private UsuarioService usuarioService;

	@Override
	public List<ComercioProfile> listarTodos() {
		return repository.findAll();
	}

	@Override
	public ComercioProfile buscarPorId(Long id) {
		return repository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comércio não encontrado com o ID: " + id));
	}

	@Override
	public ComercioProfile excluirPorId(Long id) {
		ComercioProfile comercio = buscarPorId(id);
		repository.delete(comercio);
		return comercio;
	}

//	@Override
//	public ComercioProfile salvar(ComercioDTO comercio, Usuario usuario) {
//		ComercioProfile comercioProfile = new ComercioProfile();
//		comercioProfile.setNomeComercio(comercio.getNome());
//		comercioProfile.setRaioMaximoEntregaKm(comercio.getRaioMaximoEntregaKm());
//		comercioProfile.setUser(usuario);
//		
//		Endereco endereco = new Endereco();
//		endereco.setBairro(comercio.getBairro());
//		endereco.setCep(comercio.getCep());
//		endereco.setCidade(comercio.getCidade());
//		endereco.setComplemento(comercio.getComplemento());
//		endereco.setEstado(comercio.getEstado());
//		endereco.setNumero(comercio.getNumero());
//		endereco.setRua(comercio.getRua());
//		
//		usuarioService.atualizarEndereco(usuario.getId(), endereco);
//		
//		return repository.save(comercioProfile);
//	}


	@Override
	public Oferta buscarOfertaPorId(Long idOferta) {
		return ofertaRepository.findById(idOferta).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Oferta não encontrada com o ID: " + idOferta));
	}

	@Override
	public Oferta editarOferta(Long idOferta, Oferta oferta) {
		Oferta ofertaBanco = buscarOfertaPorId(idOferta);
		
		ofertaBanco.setEstoqueAtual(oferta.getEstoqueAtual());
		ofertaBanco.setPromocao(oferta.getPromocao());
		ofertaBanco.setDisponivelParaVenda(oferta.getDisponivelParaVenda());
		ofertaBanco.setValor(oferta.getValor());

		return ofertaRepository.save(ofertaBanco);
	}

	@Override
	public Oferta adicionarOferta(Long id, Long idProduto, Oferta oferta) {
		ComercioProfile comercio = buscarPorId(id);
		Produto produto = produtoService.buscarPorId(idProduto);
		
		comercio.addOferta(oferta);
		oferta.setProduto(produto);
		oferta.setComercio(comercio);
		
		return ofertaRepository.save(oferta);
		
	}

	@Override
	public Oferta excluirOferta(Long idOferta) {
		Oferta oferta = buscarOfertaPorId(idOferta);
		ofertaRepository.delete(oferta);
		return oferta;
	}

	@Override
	public ComercioProfile completarPerfil(CompletarPerfilComercioDTO dto, Long idUsuario) {
		ComercioProfile comercio = buscarPorId(dto.getComercioId());
		
		comercio.setNomeComercio(dto.getNomeComercio());
		comercio.setTelefone(dto.getTelefone());
		comercio.setDescricao(dto.getDescricao());
		comercio.setUser(usuarioService.buscarPorId(idUsuario));
		
		comercio.getEndereco().setCep(dto.getCep());
		comercio.getEndereco().setCidade(dto.getCidade());
		comercio.getEndereco().setBairro(dto.getBairro());
		comercio.getEndereco().setComplemento(dto.getComplemento());
		comercio.getEndereco().setEstado(dto.getEstado());
		comercio.getEndereco().setLatitude(dto.getLatitude());
		comercio.getEndereco().setLongitude(dto.getLongitude());
		
		return repository.save(comercio);
	}

	@Override
	public List<ComercioDTO> listarPorCidade(String cidade) {
		 List<ComercioProfile> comercios = repository.findByEndereco_Cidade(cidade);
		 
		 return comercios.stream()
				 .map(comercio -> new ComercioDTO().fromEntity(comercio))
				 .toList();
	}

	@Override
	public CompletarPerfilComercioDTO buscarPerfilComercioPorId(Long id) {
		ComercioProfile comercio = repository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comércio não encontrado com o ID: " + id));
		
		return new CompletarPerfilComercioDTO().fromEntity(comercio);
	}

}
