package io.github.marciocg.clientes;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import io.quarkus.runtime.annotations.RegisterForReflection;


@RegisterForReflection
// @JsonTypeInfo(use=Id.NAME, include=As.WRAPPER_OBJECT)
// @JsonTypeName("saldo")
@JsonPropertyOrder({ "total", "dataExtrato", "limite" })
public final class SaldoDTO {

    public final Integer total;
    @JsonProperty("data_extrato")
    public final String dataExtrato;
    public final Integer limite;
    // @JsonProperty("ultimas_transacoes")
    // public final List<Transacoes> transacoes;

 
    public SaldoDTO(Saldo saldo, Temporal ts) {
        this.total = saldo.total;
        this.limite = saldo.limite;
        this.dataExtrato = ts.toString();
        // this.transacoes = saldo.transacoes;
    }

    public SaldoDTO(Saldo saldo) {
        this(saldo, getDataExtrato());
    }

    public static SaldoDTO of(Saldo saldo) {
        return new SaldoDTO(saldo, Instant.now());
        // return new SaldoDTO(saldo, getDataExtrato());
    }

    public static OffsetDateTime getDataExtrato () {
        return OffsetDateTime.ofInstant(Instant.now(), ZoneId.systemDefault() );
    }

}