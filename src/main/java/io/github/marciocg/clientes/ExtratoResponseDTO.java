package io.github.marciocg.clientes;
import java.util.List;

record ExtratoResponseDTO(SaldoDTO saldo, List<Transacao> ultimas_transacoes) {

    public static ExtratoResponseDTO of(Saldo saldo) {
        return new ExtratoResponseDTO(new SaldoDTO(saldo), saldo.transacoes);
    }
}
