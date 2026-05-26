package com.devf.hortilink.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemCarrinhoResponseDTO {
    
    private Long id;
    private Long ofertaId;
    private String nomeProduto;
    private Integer quantidade;
    private BigDecimal precoUnitario; 
    private BigDecimal subtotal; 
    
    private String fotoUrl;
    private String unidade;
    private Long produtorId;
    
}