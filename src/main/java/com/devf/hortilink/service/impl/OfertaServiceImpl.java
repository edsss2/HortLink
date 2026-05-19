package com.devf.hortilink.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.devf.hortilink.dto.OfertaDTO;
import com.devf.hortilink.dto.ProdutoCardDTO;
import com.devf.hortilink.dto.ProdutoFormDTO;
import com.devf.hortilink.entity.ComercioProfile;
import com.devf.hortilink.entity.Foto;
import com.devf.hortilink.entity.Oferta;
import com.devf.hortilink.entity.Produto;
import com.devf.hortilink.entity.Usuario;
import com.devf.hortilink.enums.Categoria;
import com.devf.hortilink.enums.UnidadeMedida;
import com.devf.hortilink.repository.OfertaRepository;
import com.devf.hortilink.repository.ProdutoRepository;
import com.devf.hortilink.service.FotoService;
import com.devf.hortilink.service.OfertaService;
import com.devf.hortilink.service.UsuarioService;

import jakarta.transaction.Transactional;

@Service
public class OfertaServiceImpl implements OfertaService {

	@Autowired
	private OfertaRepository repository;

	@Autowired
	private UsuarioService userService;

	@Autowired
	private FotoService fotoService;

	@Autowired
	private ProdutoRepository produtoRepository;

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
	public Oferta atualizar(Oferta oferta) {
		return repository.save(oferta);
	}

	@Override
	public List<Oferta> buscarCarrinho(List<Long> ids) {
		return repository.findByProdutoIdIn(ids);
	}

	public List<ProdutoCardDTO> transformOfertas(List<Oferta> ofertas) {
		return ofertas.stream().map(o -> ProdutoCardDTO.fromOferta(o)).collect(Collectors.toList());
	}
	
	@Override
	public List<OfertaDTO> listarOfertasParaApp() {
		List<Oferta> ofertas = repository.buscarTodasOfertasParaApp();
		return ofertas.stream()
                .map(oferta -> new OfertaDTO().fromEntity(oferta))
                .collect(Collectors.toList());
	}

	@Override
	@Transactional 
	public void salvarNovoProduto(String emailUsuario, ProdutoFormDTO formData, MultipartFile imagemPrincipal,
	        List<MultipartFile> imagensAdicionais) {
	    Usuario usuario = userService.buscarPorEmail(emailUsuario);
	    ComercioProfile comercioProfile = usuario.getComercioProfile();

	    Produto produto = new Produto();
	    produto.setNome(formData.getNome());
	    produto.setDescricao(formData.getDescricao());
	    produto.setDataColheita(formData.getDataColheira());
	    produto.setCertificadoOrganico(formData.getIsOrganico());

	    Categoria categoriaEnum = Categoria.valueOf(formData.getCategoria());
	    UnidadeMedida unMedidaEnum = UnidadeMedida.valueOf(formData.getUnidadeMedida());

	    produto.setCategoria(categoriaEnum);
	    produto.setUnidadeMedida(unMedidaEnum);

	    Produto produtoSalvo = produtoRepository.save(produto);

	    Oferta oferta = new Oferta();
	    oferta.setComercio(comercioProfile);
	    oferta.setValor(formData.getPreco());
	    oferta.setPromocao(formData.getPromocao());
	    oferta.setEstoqueAtual(BigDecimal.valueOf(formData.getQuantidade()));

	    oferta.setProduto(produtoSalvo);

	    List<Foto> fotosSalvas = new ArrayList<>();
	    Foto foto = fotoService.salvarFotoProduto(imagemPrincipal, produtoSalvo, 0);
	    fotosSalvas.add(foto);

	    int ordem = 1;
	    if (imagensAdicionais != null) {
	        for (MultipartFile file : imagensAdicionais) {
	            if (file != null && !file.isEmpty()) {
	                fotosSalvas.add(
	                    fotoService.salvarFotoProduto(file, produtoSalvo, ordem++)
	                );
	            }
	        }
	    }

	    repository.save(oferta);
	    
	    produtoSalvo.setFotos(fotosSalvas);
	    produtoRepository.save(produtoSalvo); 

	}

}
