package com.devf.hortilink.service;

import com.devf.hortilink.entity.Carrinho;
import com.devf.hortilink.entity.Pedido;

public interface CarrinhoService {

    Carrinho obterCarrinhoAtivo(Long compradorId);
    Carrinho adicionarItem(Long compradorId, Long ofertaId, Integer quantidade);
    Carrinho atualizarQuantidadeItem(Long compradorId, Long itemCarrinhoId, Integer novaQuantidade);
    Carrinho removerItem(Long compradorId, Long itemCarrinhoId);
    void limparCarrinho(Long compradorId);
    Pedido realizarCheckout(Long compradorId, Long enderecoEntregaId, String formaPagamento);
}
