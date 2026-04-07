package com.devf.hortilink.entity;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
	
	@ManyToOne
	@JoinColumn(name = "pedido_id")
	private Pedido pedido;
		
	@ManyToOne
	@JoinColumn(name = "oferta_id")
	private Oferta oferta;
	
	@NotNull
	private Integer quantidade;
	
	@NotNull
	private BigDecimal precoUnitario;
	
	@NotNull
	private BigDecimal subtotal;
}
