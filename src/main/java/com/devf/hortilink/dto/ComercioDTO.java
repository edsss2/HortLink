package com.devf.hortilink.dto;

import com.devf.hortilink.entity.ComercioProfile;
import com.devf.hortilink.entity.Endereco;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComercioDTO {
	
	private Long id;
	
	private String nome;
	private String cidade;
	private String telefone;
	private String img_url;
	private Double avaliacao;
	
	public ComercioDTO fromEntity(ComercioProfile comercio) {
	    ComercioDTO dto = new ComercioDTO();
	    
	    dto.setId(comercio.getId());
	    dto.setNome(comercio.getNomeComercio());
	    dto.setTelefone(comercio.getTelefone());
	    
	    dto.setImg_url(comercio.getFotoPerfil() != null ? comercio.getFotoPerfil().getCaminhoArquivo() : "");
	    dto.setAvaliacao(gerarAvaliacaoFicticia());

	    String cidade = "Cidade não informada"; // Valor padrão caso não encontre

	    if (comercio.getUsers() != null && !comercio.getUsers().isEmpty()) {
	        Endereco endereco = comercio.getUsers().get(0).getEndereco();
	        
	        // 3. Verifica se o usuário realmente tem um endereço salvo
	        if (endereco != null && endereco.getCidade() != null) {
	            cidade = endereco.getCidade();
	        }	
	    }
	    
	    dto.setCidade(cidade);
	    // ──────────────────────────────────────────────────────

	    return dto;
	}
	
	/**
     * MÉTODOS AUXILIARES (Temporários)
     * TODO: Remover quando o sistema de avaliações real for implementado.
     */
    private Double gerarAvaliacaoFicticia() {
        double nota;
        double probabilidade = Math.random(); // Gera um número entre 0.0 e 1.0

        if (probabilidade > 0.2) {
            // 80% de chance: Gera uma nota boa (entre 3.0 e 5.0)
            nota = 3.0 + (Math.random() * 2.0); 
        } else {
            // 20% de chance: Gera uma nota mais baixa (entre 1.0 e 2.9)
            // Isso garante que o app seja testado com notas variadas
            nota = 1.0 + (Math.random() * 1.9);
        }

        // Truque para deixar com 1 casa decimal (Ex: 4.5768 vira 4.6)
        return Math.round(nota * 10.0) / 10.0;
    }
	
}
