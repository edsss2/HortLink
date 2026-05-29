package com.devf.hortilink.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusPedido {
    PENDENTE("pendente"),
    ACEITO("aceito"), // Trocado de CONFIRMADO para ACEITO
    ENTREGUE("entregue"),
    CANCELADO("cancelado");

    private final String valor;

    StatusPedido(String valor) {
        this.valor = valor;
    }

    @JsonValue // Faz o Spring Boot serializar e desserializar usando este valor em minúsculo
    public String getValor() {
        return valor;
    }
}