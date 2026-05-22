package com.devf.hortilink.dto;

import java.math.BigDecimal;

import com.devf.hortilink.entity.ComercioProfile;
import com.devf.hortilink.entity.Endereco;
import com.devf.hortilink.entity.Oferta;
import com.devf.hortilink.entity.Produto;

public class DetalheOfertaDTO {
    private Long id;
    private String nome;
    private BigDecimal valor;
    private String descricao;
    private String nomeProdutor;
    private String cidadeUf;
    private String telefone;
    
    public DetalheOfertaDTO fromEntity(Oferta oferta) {
        DetalheOfertaDTO dto = new DetalheOfertaDTO();
        
        Produto produto = oferta.getProduto();
        ComercioProfile comercio = oferta.getComercio();
        
        dto.setId(oferta.getId());
        dto.setNome(produto.getNome());
        dto.setValor(oferta.getValor());
        dto.setDescricao(produto.getDescricao());
        dto.setNomeProdutor(comercio.getNomeComercio());
        dto.setTelefone(comercio.getTelefone());

        // Validação de segurança para não dar Crash (IndexOutOfBoundsException)
        if (comercio.getUsers() != null && !comercio.getUsers().isEmpty()) {
            Endereco endereco = comercio.getUsers().get(0).getEndereco();
            if (endereco != null) {
                dto.setCidadeUf(endereco.getCidade() + " " + endereco.getEstado());
            }
        } else {
            dto.setCidadeUf("Localização não informada");
        }
        
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
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getNomeProdutor() {
		return nomeProdutor;
	}
	public void setNomeProdutor(String nomeProdutor) {
		this.nomeProdutor = nomeProdutor;
	}
	public String getCidadeUf() {
		return cidadeUf;
	}
	public void setCidadeUf(String cidadeUf) {
		this.cidadeUf = cidadeUf;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
    
    
}