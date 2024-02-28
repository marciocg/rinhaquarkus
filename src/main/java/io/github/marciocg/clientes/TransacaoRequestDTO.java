package io.github.marciocg.clientes;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
@JsonPropertyOrder({ "valor", "tipo", "descricao" })
public class TransacaoRequestDTO {
// essa classe não pode ser final, por causa do jackson
    public String valor;
    public String tipo;
    public String descricao;
    
}