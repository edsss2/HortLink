package com.devf.hortilink.entity;

import com.devf.hortilink.enums.Categoria;
import com.devf.hortilink.enums.UnidadeMedida;

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
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String nome;
	
	@Size(max = 250)
	private String descricao;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private Categoria categoria;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "unidade_medida")
	@NotNull
    private UnidadeMedida unidadeMedida;

	@OneToOne(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Foto foto;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comercio_profile_id", nullable = false) // nullable = false garante que todo produto tenha um dono
    private ComercioProfile comercio;
	
	private Boolean ativo = true; // Por padrão, o produto é ativo quando criado
	
	/**
     * Verifica se o produto NÃO possui nenhuma foto válida associada.
     * Funciona como um "isEmpty()" personalizado.
     */
    public boolean isSemFoto() {
        return this.foto == null 
            || this.foto.getCaminhoArquivo() == null 
            || this.foto.getCaminhoArquivo().trim().isEmpty();
    }

    public boolean temFoto() {
        return !isSemFoto();
    }

}
