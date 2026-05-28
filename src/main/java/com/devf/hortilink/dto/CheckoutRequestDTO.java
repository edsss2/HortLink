package com.devf.hortilink.dto;

import lombok.Data;

@Data
public class CheckoutRequestDTO {
    private String formaPagamento;
    private Long enderecoEntregaId;
    private String observacoes;
}
