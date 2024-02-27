package io.github.marciocg.clientes;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
@JsonPropertyOrder({ "valor", "tipo", "descricao" })
public class TransacaoRequestDTO {

    public String valor;
    public String tipo;
    public String descricao;
    
}