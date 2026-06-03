package com.devf.hortilink.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioTokenDTO {
    private Long id;
    private String role;
    private Long commerceId;
    private String email;
    private Boolean cadastroIncompleto;

}
