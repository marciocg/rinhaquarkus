package io.github.marciocg.clientes;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class ExtratoResponseDTO {
    public final SaldoDTO saldo;
    @JsonProperty("ultimas_transacoes")
    public final List<Transacao> transacoes;


    public ExtratoResponseDTO(Saldo saldo) {
        this.saldo = new SaldoDTO(saldo);
        this.transacoes = saldo.transacoes.stream().limit(10).collect(Collectors.toList());
    }
    
}
