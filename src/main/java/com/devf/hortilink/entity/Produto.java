package com.devf.hortilink.entity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.devf.hortilink.enums.Categoria;
import com.devf.hortilink.enums.UnidadeMedida;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
	
	@PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataColheita;
	

	private Boolean certificadoOrganico = false;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private Categoria categoria;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "unidade_medida")
	@NotNull
    private UnidadeMedida unidadeMedida;

	@OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Foto> fotos;
	
	public Foto getFotoPrimaria() {
	    return this.fotos.stream()
	        .filter(f -> f.getOrdemExibicao() == 0)
	        .findFirst()
	        .orElse(null);
	}
}
