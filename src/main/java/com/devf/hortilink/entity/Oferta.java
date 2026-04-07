package com.devf.hortilink.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ofertas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Oferta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comercio_profile_id", nullable = false)
    private ComercioProfile comercio;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;
	
	@DecimalMin("0.01")
	private BigDecimal valor;
	@DecimalMin("0.00")
	private BigDecimal promocao;
	
	@NotNull
    @DecimalMin("0.00")
    private BigDecimal estoqueAtual; 
    
    private Boolean disponivelParaVenda = true;

}
