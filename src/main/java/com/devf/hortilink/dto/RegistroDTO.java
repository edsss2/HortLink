package com.devf.hortilink.dto;

import lombok.Data;

@Data
public class RegistroDTO {
    private String nome;
    private String email;
    private String senha;
    private String role; // "PRODUTOR" ou "CONSUMIDOR"
    private String telefone;
}