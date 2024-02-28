package io.github.marciocg.clientes;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.LockModeType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@Entity(name = "Saldo")
@Table(name = "saldo")
public class Saldo extends PanacheEntity {

    public Integer total;
    public Integer limite;
    @JsonProperty("ultimas_transacoes")
    @OneToMany(mappedBy = "saldo", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("realizadaEm DESC")
    public List<Transacao> transacoes;

    public void addTransacoes(Transacao transacao) {
        this.transacoes.add(transacao);
        transacao.saldo = this;
    }

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

}
