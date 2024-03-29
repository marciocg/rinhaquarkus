package io.github.marciocg.clientes;

import java.util.List;
import java.util.Optional;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.LockModeType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@Entity
@Table(name = "saldo")
public class Saldo extends PanacheEntity {

    public Integer total;
    public Integer limite;
    @OneToMany(mappedBy = "saldo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<Transacao> transacoes;

    public static Saldo getSaldoByIdWriteLock(Integer id) {
        Optional<Saldo> saldo = Saldo.findByIdOptional(id, LockModeType.PESSIMISTIC_WRITE);
        if (saldo.isEmpty()) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } else {
            return saldo.get();
        }
    }

    public static Saldo getSaldoById(Integer id) {
        Optional<Saldo> saldo = Saldo.findByIdOptional(id);
        if (saldo.isEmpty()) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } else {
            return saldo.get();
        }
    }

    public static Saldo getSaldoWithUltimasTransacoesById(Integer id) {
        Saldo saldo = getSaldoById(id);
        PanacheQuery<Transacao> transacoesQuery = Transacao.getUltimas10Transacoes(saldo);
        saldo.transacoes = transacoesQuery.list();
        return saldo;
    }

    public static void atualizaSaldoComNovaTransacao(Saldo saldoCliente, Transacao novaTransacao) {
        saldoCliente.transacoes.add(novaTransacao);
        novaTransacao.saldo = saldoCliente;
        saldoCliente.persistAndFlush();
    }
}
