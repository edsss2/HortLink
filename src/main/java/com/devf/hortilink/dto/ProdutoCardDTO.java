package com.devf.hortilink.dto;

import java.math.BigDecimal;

import com.devf.hortilink.entity.ComercioProfile;
import com.devf.hortilink.entity.Oferta;
import com.devf.hortilink.entity.Produto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

}
