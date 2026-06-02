package com.devf.hortilink.dto;

import java.math.BigDecimal;

import com.devf.hortilink.entity.Foto;
import com.devf.hortilink.entity.Oferta;
import com.devf.hortilink.entity.Produto;
import com.devf.hortilink.enums.Categoria;
import com.devf.hortilink.enums.UnidadeMedida;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OfertaDTO {

	private Long id;
	private String nome;
	private BigDecimal preco;
	private BigDecimal promocao;
	private Categoria categoria;
	private String imagemUrl;
	private String descricao;
	private UnidadeMedida unidade;
	
	private Double latitude;
	private Double longitude;
	
	public static OfertaDTO fromEntity(Oferta oferta) {
		OfertaDTO dto = new OfertaDTO();
		Produto produto = oferta.getProduto();
		dto.setId(oferta.getId());
		dto.setNome(produto.getNome());
		dto.setPreco(oferta.getValor());
		dto.setPromocao(oferta.getPromocao());
		dto.setCategoria(produto.getCategoria());
		dto.setDescricao(produto.getDescricao());
		dto.setUnidade(produto.getUnidadeMedida());
		dto.setLatitude(oferta.getComercio().getEndereco().getLatitude());
		dto.setLongitude(oferta.getComercio().getEndereco().getLongitude());
		
		Foto fotoPrincipal = produto.getFoto();
		if (fotoPrincipal != null) {
		    dto.setImagemUrl(fotoPrincipal.getCaminhoArquivo());
		} else {
		    dto.setImagemUrl(null); // ou uma URL de imagem padrão "sem_foto.png"
		}
		
		return dto;
	}
}
