package com.devf.hortilink.service;

import com.devf.hortilink.dto.CarrinhoResponseDTO;
import com.devf.hortilink.entity.Pedido;

public interface CarrinhoService {

	CarrinhoResponseDTO obterCarrinhoAtivo(Long compradorId);
	CarrinhoResponseDTO adicionarItem(Long compradorId, Long ofertaId, Integer quantidade);
	CarrinhoResponseDTO atualizarQuantidadeItem(Long compradorId, Long idItem, Integer novaQuantidade);
	CarrinhoResponseDTO removerItem(Long compradorId, Long idItem);
    void limparCarrinho(Long compradorId);
    Pedido realizarCheckout(Long compradorId, String formaPagamento);
}
