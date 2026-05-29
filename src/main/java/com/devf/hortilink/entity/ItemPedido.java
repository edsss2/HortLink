package com.devf.hortilink.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pedido_id")
	@JsonIgnore
	private Pedido pedido;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "oferta_id")
	private Oferta oferta;
	
	@NotNull
	private Integer quantidade;
	
	private String nomeProduto;
	
	@NotNull
	private BigDecimal precoUnitario;
	
	/**
	 * Método calculado em tempo de execução.
	 * Não gera coluna no banco, mas aparece no JSON da API.
	 */
	public BigDecimal getSubtotal() {
		if (this.precoUnitario == null || this.quantidade == null) {
			return BigDecimal.ZERO;
		}
		return this.precoUnitario.multiply(BigDecimal.valueOf(this.quantidade));
	}
	
	
	
}
