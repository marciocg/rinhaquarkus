package io.github.marciocg.clientes;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
@JsonPropertyOrder({ "limite", "saldo" })
public final class TransacaoResponseDTO {

    public final Integer limite;
    public final Integer saldo;
 
    public TransacaoResponseDTO(Integer limite, Integer saldo) {
        this.limite = limite;
        this.saldo = saldo;
    }

    public TransacaoResponseDTO(Saldo saldo) {
        this(saldo.limite, saldo.total);
    }

    public static TransacaoResponseDTO of(Saldo saldo) {
        return new TransacaoResponseDTO(saldo.limite, saldo.total);
    }

}