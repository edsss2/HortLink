package com.devf.hortilink.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.devf.hortilink.enums.StatusPedido;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, nullable = false, length = 8)
	private String codigo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id")
	private Usuario cliente;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comercio_id")
	private ComercioProfile vendedor;
	
	private LocalDateTime dataPedido;
	
	private BigDecimal valorTotal;
	
	private String formaPagamento;
	
	@Enumerated(EnumType.STRING)
	private StatusPedido status;
	
	private String observacoes;
	
	@PrePersist
	public void prePersist() {
		this.dataPedido = LocalDateTime.now();
		
		if(this.codigo == null) {
			this.codigo = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
		}
	}
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens;
	
	public void addItem(ItemPedido item) {
		this.itens.add(item);
	}
	
	public void removeItem(ItemPedido item) {
		this.itens.remove(item);
	}
	
	public void removeItem(Long idItem) {
		this.itens.removeIf(item -> item.getId().equals(idItem));
	}
}
