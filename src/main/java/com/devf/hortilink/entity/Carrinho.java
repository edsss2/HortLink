package com.devf.hortilink.entity;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carrinho {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemCarrinho> itens;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id") // FK fica na tabela carrinho
	@ToString.Exclude
	private Usuario comprador;
	
	public void addItem(ItemCarrinho item) {
		this.itens.add(item);
	}
	
	public void removeItem(ItemCarrinho item) {
		this.itens.remove(item);
	}
	
	public void removeItem(Long idItem) {
		this.itens.removeIf(item -> item.getId().equals(idItem));
	}
	
	public Optional<ItemCarrinho> buscarItem(Long idItem) {
	    return this.itens.stream()
	            .filter(i -> i.getId().equals(idItem))
	            .findFirst(); // O findFirst() já retorna um Optional nativamente!
	}
	
	public Optional<ItemCarrinho> buscarItemIdOferta(Long idOferta) {
	    return this.itens.stream()
	            .filter(i -> i.getOferta().getId().equals(idOferta))
	            .findFirst();
	}
	
	public void limparCarrinho() {
		this.itens.clear();
	}

}
