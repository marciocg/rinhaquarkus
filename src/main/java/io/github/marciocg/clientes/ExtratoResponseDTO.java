package io.github.marciocg.clientes;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.logging.Log;

public final class ExtratoResponseDTO {
    public final SaldoDTO saldo;
    public final List<Transacao> ultimas_transacoes;


    public ExtratoResponseDTO(Saldo saldo) {
        Log.info("ID:::: " + saldo.id.toString());
        Log.info("tr:::: " + saldo.transacoes.toString());
        Log.info("ISpers " + saldo.isPersistent());
        this.saldo = new SaldoDTO(saldo);
        // this.ultimas_transacoes = saldo.transacoes.stream().limit(10).collect(Collectors.toList());
        this.ultimas_transacoes = saldo.transacoes;
    }
    
}
