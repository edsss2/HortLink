package com.devf.hortilink.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devf.hortilink.entity.Carrinho;
import com.devf.hortilink.entity.ComercioProfile;
import com.devf.hortilink.entity.ItemCarrinho;
import com.devf.hortilink.entity.ItemPedido;
import com.devf.hortilink.entity.Oferta;
import com.devf.hortilink.entity.Pedido;
import com.devf.hortilink.entity.Usuario;
import com.devf.hortilink.enums.StatusPedido;
import com.devf.hortilink.repository.CarrinhoRepository;
import com.devf.hortilink.service.CarrinhoService;
import com.devf.hortilink.service.OfertaService;
import com.devf.hortilink.service.UsuarioService;

import jakarta.transaction.Transactional;

@Service
public class CarrinhoServiceImpl implements CarrinhoService {
	
	@Autowired
	private CarrinhoRepository repository;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private OfertaService ofertaService;

	@Override
	@Transactional
	public Carrinho obterCarrinhoAtivo(Long compradorId) {
		return repository.findByCompradorId(compradorId).orElseGet(() -> {
		            
	            // 1. Busca o usuário
	            Usuario usuario = usuarioService.buscarPorId(compradorId);
	                
	            // 2. Cria o carrinho vazio
	            Carrinho novoCarrinho = new Carrinho();
	            novoCarrinho.setComprador(usuario);
	            novoCarrinho.setItens(new ArrayList<>()); // Se não estiver inicializado na entidade
	            
	            // 3. Salva e devolve
	            return repository.save(novoCarrinho);
	        });
	}

	@Override
	public Carrinho adicionarItem(Long compradorId, Long ofertaId, Integer quantidade) {
		Carrinho carrinho = obterCarrinhoAtivo(compradorId);
		ComercioProfile comercioAtualDoCarrinho = null;
		Oferta novaOferta = ofertaService.buscarPorId(ofertaId);
		
		if(!carrinho.getItens().isEmpty()) {
			comercioAtualDoCarrinho = obterComercio(carrinho);
			
			if(!novaOferta.getComercio().getId().equals(comercioAtualDoCarrinho.getId())) {
				throw new RuntimeException("Você já tem itens de outro comércio no carrinho. Limpe o carrinho para adicionar itens desta loja.");
			}
		}
		
		ItemCarrinho item = carrinho.buscarItemIdOferta(ofertaId)
                .orElseGet(() -> {
		
            	ItemCarrinho novoItem = new ItemCarrinho();
                novoItem.setCarrinho(carrinho);
                novoItem.setOferta(novaOferta);
                novoItem.setQuantidade(quantidade);
                carrinho.addItem(novoItem);
                
                // O RETURN É OBRIGATÓRIO AQUI:
                return novoItem;
        });
		
		item.setQuantidade(item.getQuantidade() + quantidade);
		return repository.save(carrinho);
	}

	@Override
	public Carrinho atualizarQuantidadeItem(Long compradorId, Long idItem, Integer novaQuantidade) {
		Carrinho carrinho = obterCarrinhoAtivo(compradorId);
		ItemCarrinho item = carrinho.buscarItem(idItem).orElseThrow(() -> new RuntimeException("Item não encontrado no carrinho"));
		item.setQuantidade(novaQuantidade);
		
		return carrinho;
	}

	@Override
	public Carrinho removerItem(Long compradorId, Long idItem) {
		Carrinho carrinho = obterCarrinhoAtivo(compradorId);
		carrinho.removeItem(idItem);
		return carrinho;
	}

	@Override
	public void limparCarrinho(Long compradorId) {
		Carrinho carrinho = obterCarrinhoAtivo(compradorId);
		carrinho.limparCarrinho();
		
		repository.save(carrinho);
	}

	@Override
	public Pedido realizarCheckout(Long compradorId, String formaPagamento) {
		Carrinho carrinho = obterCarrinhoAtivo(compradorId);
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
			
			valorTotal.add(itemP.getSubtotal());
			
			pedido.addItem(itemP);
		}
		
		
		pedido.setValorTotal(valorTotal);
		
		return pedido;
	}
	
	private ComercioProfile obterComercio(Carrinho carrinho) {
		return carrinho.getItens().getFirst().getOferta().getComercio();
	}

}
