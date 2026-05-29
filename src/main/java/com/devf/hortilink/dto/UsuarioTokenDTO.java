package com.devf.hortilink.dto;

public class UsuarioTokenDTO {
    private Long id;
    private String role;
    private Long commerceId;
    private String email;

    public UsuarioTokenDTO() {}

    public UsuarioTokenDTO(Long id, String role, Long commerceId, String email) {
        this.id = id;
        this.role = role;
        this.commerceId = commerceId;
        this.email = email;
    }

    public Long getId() { return id; }
    public String getRole() { return role; }
    public Long getCommerceId() { return commerceId; }
    public String getEmail() { return email; }

    public void setId(Long id) { this.id = id; }
    public void setRole(String role) { this.role = role; }
    public void setCommerceId(Long commerceId) { this.commerceId = commerceId; }
    public void setEmail(String email) { this.email = email; }
}
