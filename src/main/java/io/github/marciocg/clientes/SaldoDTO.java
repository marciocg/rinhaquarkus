package io.github.marciocg.clientes;

import java.time.Instant;
import java.time.temporal.Temporal;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({ "total", "dataExtrato", "limite" })
record SaldoDTO(Integer total, @JsonProperty("data_extrato") String dataExtrato, Integer limite) {

    public SaldoDTO(Saldo saldo, Temporal ts) {
        this(saldo.total, ts.toString(), saldo.limite);
    }

	public SaldoDTO(Saldo saldo) {
		this(saldo.total, Instant.now().toString(), saldo.limite);
	}

}