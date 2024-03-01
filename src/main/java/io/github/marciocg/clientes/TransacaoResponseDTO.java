package io.github.marciocg.clientes;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
@JsonPropertyOrder({ "limite", "saldo" })
record TransacaoResponseDTO (Integer limite, Integer saldo) {

    public TransacaoResponseDTO(Saldo saldo) {
        this(saldo.limite, saldo.total);
    }};