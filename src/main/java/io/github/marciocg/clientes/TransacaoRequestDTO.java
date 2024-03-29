package io.github.marciocg.clientes;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
@JsonPropertyOrder({ "valor", "tipo", "descricao" })
record TransacaoRequestDTO(String valor, String tipo, String descricao) {};