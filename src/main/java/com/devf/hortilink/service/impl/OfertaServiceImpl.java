package com.devf.hortilink.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.devf.hortilink.dto.DetalheOfertaDTO;
import com.devf.hortilink.dto.NovaOfertaDTO;
import com.devf.hortilink.dto.OfertaDTO;
import com.devf.hortilink.dto.OfertaEdicaoDTO;
import com.devf.hortilink.dto.ProdutoCardDTO;
import com.devf.hortilink.entity.ComercioProfile;
import com.devf.hortilink.entity.Oferta;
import com.devf.hortilink.entity.Produto;
import com.devf.hortilink.repository.OfertaRepository;
import com.devf.hortilink.service.OfertaService;
import com.devf.hortilink.service.ProdutoService;

import jakarta.transaction.Transactional;

@Service
public class OfertaServiceImpl implements OfertaService {

	@Autowired
	private OfertaRepository repository;
	
	@Autowired
	private ProdutoService produtoService;

	@Override
	public List<Oferta> listarTodos() {
		return repository.findAll();
	}

	@Override
	public Oferta buscarPorId(Long idOferta) {
		return repository.findById(idOferta).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Oferta não encontrada com o ID: " + idOferta));
	}

	@Override
	public Oferta excluirPorId(Long id) {
		Oferta oferta = buscarPorId(id);
		repository.delete(oferta);
		return oferta;
	}
	
	@Override
	@Transactional
	public OfertaDTO salvar(Long comercioId, NovaOfertaDTO novaOferta) {
		boolean jaExiste = repository.existsByComercioIdAndProdutoIdAndDisponivelParaVendaTrue(comercioId, novaOferta.getProdutoId());
		
		if (jaExiste) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe uma oferta ativa para este produto neste comércio");
		}
		
		Oferta oferta = new Oferta();
		
		ComercioProfile comercio = new ComercioProfile();
		comercio.setId(comercioId);
		oferta.setComercio(comercio);
		
		Produto produto = new Produto();
		produto.setId(novaOferta.getProdutoId());
		oferta.setProduto(produto);
		
		oferta.setValor(novaOferta.getPreco());
		oferta.setEstoqueAtual(novaOferta.getEstoqueAtual());
		oferta.setDataColheita(novaOferta.getDataColheita());
		oferta.setDisponivelParaVenda(novaOferta.getDisponivelParaVenda());
		
		oferta = repository.save(oferta);
		
		return OfertaDTO.fromEntity(oferta);
	}
	
	@Override
	@Transactional
	public OfertaDTO atualizar(Long comercioId, Long ofertaId, NovaOfertaDTO novaOferta) {
		if(!repository.existsByIdAndComercioId(ofertaId, comercioId)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Oferta não encontrada para este comércio");
		}
		
		Oferta oferta = buscarPorId(ofertaId);
		
		oferta.setValor(novaOferta.getPreco());
		oferta.setEstoqueAtual(novaOferta.getEstoqueAtual());
		oferta.setDataColheita(novaOferta.getDataColheita());
		oferta.setDisponivelParaVenda(novaOferta.getDisponivelParaVenda());
		
		oferta = repository.save(oferta);
		
		return OfertaDTO.fromEntity(oferta);
	}

	public List<ProdutoCardDTO> transformOfertas(List<Oferta> ofertas) {
		return ofertas.stream().map(o -> ProdutoCardDTO.fromOferta(o)).collect(Collectors.toList());
	}
	
	@Override
	public List<OfertaDTO> listarOfertasParaApp() {
		List<Oferta> ofertas = repository.buscarTodasOfertasParaApp();
		return ofertas.stream()
                .map(OfertaDTO::fromEntity)
                .toList();
	}
	
	@Override
	public DetalheOfertaDTO buscarOfertaDetalhadaPorId(Long id) {
		Oferta oferta = repository.buscarOfertaDetalhadaPorId(id).orElseThrow();
		DetalheOfertaDTO dto = new DetalheOfertaDTO();
		return dto.fromEntity(oferta);
	}
	
	@Override
	public List<OfertaDTO> buscarOfertasPorComercioId(Long id) {
		List<Oferta> ofertas = repository.buscarOfertasByComercioId(id);
		
		return ofertas.stream()
				.map(OfertaDTO::fromEntity)
				.toList();
	}

	@Override
	public OfertaEdicaoDTO buscarOfertaEdicaoPorId(Long comercioId, Long ofertaId) {
		if (!repository.existsByIdAndComercioId(ofertaId, comercioId)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Oferta não encontrada para este comércio");
		} else {
			Oferta oferta = repository.findOfertaParaEdicaoById(ofertaId).orElseThrow();
			return OfertaEdicaoDTO.fromEntity(oferta);
		}

	}




}
