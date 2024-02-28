package io.github.marciocg.clientes;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.quarkus.runtime.annotations.RegisterForReflection;


@RegisterForReflection
@JsonPropertyOrder({ "total", "dataExtrato", "limite" })
public final class SaldoDTO {

    public final Integer total;
    @JsonProperty("data_extrato")
    public final String dataExtrato;
    public final Integer limite;
 
    public SaldoDTO(Saldo saldo, Temporal ts) {
        this.total = saldo.total;
        this.limite = saldo.limite;
        this.dataExtrato = ts.toString();
    }

    public SaldoDTO(Saldo saldo) {
        this(saldo, getDataExtrato());
    }

    public static SaldoDTO of(Saldo saldo) {
        return new SaldoDTO(saldo, Instant.now());
    }

    public static OffsetDateTime getDataExtrato () {
        return OffsetDateTime.ofInstant(Instant.now(), ZoneId.systemDefault() );
    }

}