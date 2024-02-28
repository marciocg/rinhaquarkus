package io.github.marciocg.clientes;
import java.util.List;

public final class ExtratoResponseDTO {
    public final SaldoDTO saldo;
    public final List<Transacao> ultimas_transacoes;


    public ExtratoResponseDTO(Saldo saldo) {
        this.saldo = new SaldoDTO(saldo);
        // this.ultimas_transacoes = saldo.transacoes.stream().limit(10).collect(Collectors.toList());
        this.ultimas_transacoes = saldo.transacoes;
    }
    
}
