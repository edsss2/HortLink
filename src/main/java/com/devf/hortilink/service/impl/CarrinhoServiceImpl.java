package com.devf.hortilink.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devf.hortilink.dto.CarrinhoResponseDTO;
import com.devf.hortilink.dto.ItemCarrinhoResponseDTO;
import com.devf.hortilink.entity.Carrinho;
import com.devf.hortilink.entity.ComercioProfile;
import com.devf.hortilink.entity.Foto;
import com.devf.hortilink.entity.ItemCarrinho;
import com.devf.hortilink.entity.ItemPedido;
import com.devf.hortilink.entity.Oferta;
import com.devf.hortilink.entity.Pedido;
import com.devf.hortilink.entity.Produto;
import com.devf.hortilink.entity.Usuario;
import com.devf.hortilink.enums.StatusPedido;
import com.devf.hortilink.repository.CarrinhoRepository;
import com.devf.hortilink.service.CarrinhoService;
import com.devf.hortilink.service.OfertaService;
import com.devf.hortilink.service.UsuarioService;

@Service
public class CarrinhoServiceImpl implements CarrinhoService {
	
	@Autowired
	private CarrinhoRepository repository;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private OfertaService ofertaService;

    @Override
	@Transactional(readOnly = true)
	public CarrinhoResponseDTO obterCarrinhoAtivo(Long compradorId) {
        Carrinho carrinho = obterCarrinhoAtivoService(compradorId);
        return converterParaDto(carrinho);
	}

	@Override
    @Transactional
	public CarrinhoResponseDTO adicionarItem(Long compradorId, Long ofertaId, Integer quantidade) {
		Carrinho carrinho = obterCarrinhoAtivoService(compradorId);
		Oferta novaOferta = ofertaService.buscarPorId(ofertaId);
		
		if(!carrinho.getItens().isEmpty()) {
			ComercioProfile comercioAtualDoCarrinho = obterComercio(carrinho);
			
			if(!novaOferta.getComercio().getId().equals(comercioAtualDoCarrinho.getId())) {
				throw new RuntimeException("Você já tem itens de outro comércio no carrinho. Limpe o carrinho para adicionar itens desta loja.");
			}
		}
		
        // Busca o item ou cria um novo se não existir
		ItemCarrinho item = carrinho.buscarItemIdOferta(ofertaId)
                .orElseGet(() -> {
                    ItemCarrinho novoItem = new ItemCarrinho();
                    novoItem.setCarrinho(carrinho);
                    novoItem.setOferta(novaOferta);
                    novoItem.setQuantidade(0); // Inicia com 0
                    carrinho.addItem(novoItem);
                    return novoItem;
                });
		
		item.setQuantidade(item.getQuantidade() + quantidade);
		
        Carrinho carrinhoSalvo = repository.save(carrinho);
        return converterParaDto(carrinhoSalvo);
	}

	@Override
    @Transactional
	public CarrinhoResponseDTO atualizarQuantidadeItem(Long compradorId, Long idItem, Integer novaQuantidade) {
		Carrinho carrinho = obterCarrinhoAtivoService(compradorId);
		ItemCarrinho item = carrinho.buscarItem(idItem)
                .orElseThrow(() -> new RuntimeException("Item não encontrado no carrinho"));
		
        item.setQuantidade(novaQuantidade);
		Carrinho carrinhoSalvo = repository.save(carrinho);
        
		return converterParaDto(carrinhoSalvo);
	}

	@Override
    @Transactional
	public CarrinhoResponseDTO removerItem(Long compradorId, Long idItem) {
		Carrinho carrinho = obterCarrinhoAtivoService(compradorId);
		carrinho.removeItem(idItem);
		Carrinho carrinhoSalvo = repository.save(carrinho);
        
		return converterParaDto(carrinhoSalvo);
	}

	@Override
    @Transactional
	public void limparCarrinho(Long compradorId) {
		Carrinho carrinho = obterCarrinhoAtivoService(compradorId);
		carrinho.limparCarrinho();
		repository.save(carrinho);
	}

	@Override
    @Transactional
	public Pedido realizarCheckout(Long compradorId, String formaPagamento) {
		Carrinho carrinho = obterCarrinhoAtivoService(compradorId);
		Pedido pedido = new Pedido();
		BigDecimal valorTotal = BigDecimal.ZERO;
		
		pedido.setCliente(usuarioService.buscarPorId(compradorId));
		pedido.setVendedor(obterComercio(carrinho));
		pedido.setDataPedido(LocalDateTime.now());
		pedido.setStatus(StatusPedido.PENDENTE);
		
		for(ItemCarrinho itemC : carrinho.getItens()) {
			ItemPedido itemP = new ItemPedido();
			itemP.setOferta(itemC.getOferta());
			itemP.setPedido(pedido);
			itemP.setPrecoUnitario(itemC.getOferta().getValor());
			itemP.setQuantidade(itemC.getQuantidade());
			
			valorTotal = valorTotal.add(itemP.getSubtotal()); 
			
			pedido.addItem(itemP);
		}
		
		pedido.setValorTotal(valorTotal);
		
        carrinho.limparCarrinho();
        repository.save(carrinho);

		return pedido; 
	}
	
    // =========================================================
    // MÉTODOS PRIVADOS
    // =========================================================

	private Carrinho obterCarrinhoAtivoService(Long compradorId) {
		return repository.findByCompradorId(compradorId).orElseGet(() -> {
	            Usuario usuario = usuarioService.buscarPorId(compradorId);
	            Carrinho novoCarrinho = new Carrinho();
	            novoCarrinho.setComprador(usuario);
	            novoCarrinho.setItens(new ArrayList<>()); 
	            return repository.save(novoCarrinho);
	        });
	}

	private ComercioProfile obterComercio(Carrinho carrinho) {
		return carrinho.getItens().getFirst().getOferta().getComercio();
	}

	private CarrinhoResponseDTO converterParaDto(Carrinho carrinho) {
        // 1. Early return: se não tem carrinho, devolve null direto e acaba aqui.
        if (carrinho == null) return null;

        CarrinhoResponseDTO dto = new CarrinhoResponseDTO();
        dto.setId(carrinho.getId());
        
        if (carrinho.getComprador() != null) {
            dto.setCompradorId(carrinho.getComprador().getId());
        }
        
        // 2. Extraímos a conversão dos itens usando a API de Streams do Java
        List<ItemCarrinhoResponseDTO> itensDto = carrinho.getItens() == null 
            ? new ArrayList<>() 
            : carrinho.getItens().stream()
                .map(this::converterItemParaDto) // Chama o método auxiliar para cada item
                .toList();
        
        // 3. Calculamos o valor total somando os subtotais já processados nos DTOs
        BigDecimal valorTotal = itensDto.stream()
            .map(ItemCarrinhoResponseDTO::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        dto.setItens(itensDto);
        dto.setValorTotal(valorTotal);
        
        return dto;
    }

    // Método auxiliar isolado que só tem UMA responsabilidade: converter UM item.
    private ItemCarrinhoResponseDTO converterItemParaDto(ItemCarrinho item) {
        ItemCarrinhoResponseDTO dto = new ItemCarrinhoResponseDTO();
        dto.setId(item.getId());
        
        int quantidade = (item.getQuantidade() != null) ? item.getQuantidade() : 0;
        dto.setQuantidade(quantidade);
        
        // Early return: se a oferta for nula, devolve o DTO só com o ID e quantidade (evita NPE)
        if (item.getOferta() == null) {
            dto.setPrecoUnitario(BigDecimal.ZERO);
            dto.setSubtotal(BigDecimal.ZERO);
            return dto;
        }
        
        // A partir daqui, temos certeza absoluta de que a Oferta existe. Não precisamos mais do "if"
        Oferta oferta = item.getOferta();
        dto.setOfertaId(oferta.getId());
        
        if (oferta.getComercio() != null) {
            dto.setProdutorId(oferta.getComercio().getId()); 
        }
        
        BigDecimal precoAplicado = oferta.getValor() != null ? oferta.getValor() : BigDecimal.ZERO; 
        dto.setPrecoUnitario(precoAplicado);
        dto.setSubtotal(precoAplicado.multiply(BigDecimal.valueOf(quantidade)));
        
        // Proteção isolada para os dados do Produto e da Foto
        Produto produto = oferta.getProduto();
        if (produto != null) {
            dto.setNomeProduto(produto.getNome()); 
            
            if (produto.getUnidadeMedida() != null) {
                dto.setUnidade(produto.getUnidadeMedida().name());
            }

            // Uso elegante do Optional para buscar a URL da foto sem precisar de outro "if"
            java.util.Optional.ofNullable(produto.getFoto())
                    .map(Foto::getCaminhoArquivo)
                    .ifPresent(dto::setFotoUrl);
        }
        
        return dto;
    }
}