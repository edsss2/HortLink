package com.devf.hortilink.dto;

import java.math.BigDecimal;

import com.devf.hortilink.entity.ComercioProfile;
import com.devf.hortilink.entity.Oferta;
import com.devf.hortilink.entity.Produto;

public class ProdutoCardDTO {

	private Long id;
	private String nome;
	private String nomeComercio;
	private String valor;
	private String promocao;
	private Boolean organico;
	private String unidadeMedida;
	private String tipoVendedor;
	private BigDecimal quantidadeDisponivel;
	
	private String imageUrl;
	
	public static ProdutoCardDTO fromOferta(Oferta oferta) {
		ProdutoCardDTO dto = new ProdutoCardDTO();
		Produto produto = oferta.getProduto();
		ComercioProfile comercio = oferta.getComercio();
		
		
		dto.setId(produto.getId());
		dto.setNome(produto.getNome());
		dto.setNomeComercio(comercio.getNomeComercio());
		dto.setValor(oferta.getValor().toString());
		dto.setPromocao(oferta.getPromocao().toString());
		dto.setOrganico(produto.getCertificadoOrganico());
		dto.setUnidadeMedida(produto.getUnidadeMedida().getSimbolo());
		dto.setTipoVendedor(comercio.getUsers().getFirst().getRole().getNome());
		dto.setQuantidadeDisponivel(oferta.getEstoqueAtual());
		String caminhoDaFoto = produto.getFotoPrimaria().getCaminhoArquivo();
		dto.setImageUrl("http://localhost:8080/uploads/" + caminhoDaFoto);
		
		return dto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeComercio() {
		return nomeComercio;
	}

	public void setNomeComercio(String nomeComercio) {
		this.nomeComercio = nomeComercio;
	}

	public String getValor() {
		return valor;
	}

	public BigDecimal getQuantidadeDisponivel() {
		return quantidadeDisponivel;
	}

	public void setQuantidadeDisponivel(BigDecimal quantidadeDisponivel) {
		this.quantidadeDisponivel = quantidadeDisponivel;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getPromocao() {
		return promocao;
	}

	public void setPromocao(String promocao) {
		this.promocao = promocao;
	}

	public Boolean getOrganico() {
		return organico;
	}

	public void setOrganico(Boolean organico) {
		this.organico = organico;
	}

	public String getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(String unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public String getTipoVendedor() {
		return tipoVendedor;
	}

	public void setTipoVendedor(String tipoVendedor) {
		this.tipoVendedor = tipoVendedor;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
	
	
}
