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

@Entity
@Table(name = "comercios_profiles")
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
	
	
	public Foto getFotoPerfil() {
		return fotoPerfil;
	}

	public void setFotoPerfil(Foto fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}

	public void addOferta(Oferta oferta) {
		this.ofertas.add(oferta);
	}
	
	public void removeOferta(Oferta oferta) {
		this.ofertas.remove(oferta);
	}
	
	public void removeOferta(Long idOferta) {
		this.ofertas.removeIf(oferta -> oferta.getId().equals(idOferta));
	}
	
	//Getters e Setters
	public Long getId() {
		return id;
	}

	public List<Foto> getFotos() {
		return fotos;
	}

	public void setFotos(List<Foto> fotos) {
		this.fotos = fotos;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Usuario> getUsers() {
		return users;
	}

	public void setUsers(List<Usuario> users) {
		this.users = users;
	}

	public void setUser(Usuario usuario) {
		this.users.add(usuario);
	}

	public String getNomeComercio() {
		return nomeComercio;
	}

	public void setNomeComercio(String nomeComercio) {
		this.nomeComercio = nomeComercio;
	}

	public Integer getRaioMaximoEntregaKm() {
		return raioMaximoEntregaKm;
	}

	public void setRaioMaximoEntregaKm(Integer raioMaximoEntregaKm) {
		this.raioMaximoEntregaKm = raioMaximoEntregaKm;
	}


	public List<Oferta> getOfertas() {
		return ofertas;
	}

	public void setOfertas(List<Oferta> ofertas) {
		this.ofertas = ofertas;
	}
	
	
}
