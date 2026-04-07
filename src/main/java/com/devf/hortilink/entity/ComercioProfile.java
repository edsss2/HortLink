package com.devf.hortilink.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comercios_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComercioProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(mappedBy = "comercioProfile", cascade = CascadeType.ALL)
    private List<Usuario> users;
	
	private String nomeComercio;
	private Integer raioMaximoEntregaKm;
	
	@OneToMany(mappedBy = "comercio", cascade = CascadeType.ALL)
    private List<Oferta> ofertas;

	@OneToMany(mappedBy = "comercioProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Foto> fotos;
	
	@OneToOne
	@JoinColumn(name = "foto_perfil_id")
	private Foto fotoPerfil;
	
	
	
	public void addOferta(Oferta oferta) {
		this.ofertas.add(oferta);
	}
	
	public void removeOferta(Oferta oferta) {
		this.ofertas.remove(oferta);
	}
	
	public void removeOferta(Long idOferta) {
		this.ofertas.removeIf(oferta -> oferta.getId().equals(idOferta));
	}
	
	public void setUser(Usuario usuario) {
		this.users.add(usuario);
	}
	
}
