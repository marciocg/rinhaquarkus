package io.github.marciocg.clientes;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class ExtratoResponseDTO {
    public final SaldoDTO saldo;
    @JsonProperty("ultimas_transacoes")
    public final List<Transacoes> transacoes;


    public ExtratoResponseDTO(SaldoDTO saldo, List<Transacoes> transacoes) {
        this.saldo = saldo;
        this.transacoes = transacoes.stream().limit(10).collect(Collectors.toList());
    }

    public static ExtratoResponseDTO of(Saldo saldo) {
        return new ExtratoResponseDTO(SaldoDTO.of(saldo), saldo.transacoes);
    }

    
}
