package io.github.marciocg.clientes;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.LockModeType;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@Entity
@Table(name = "saldo")
// @NamedQueries({
//     @NamedQuery(name = "Saldo.getSaldoWithUltimasTransacoesById", query = """
//             FROM Saldo s JOIN s.transacoes t 
//             WHERE s.id = ?1
//             ORDER BY t.realizadaEm DESC
//             LIMIT 10
//                 """)
//             })
public class Saldo extends PanacheEntity {

    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // public Integer id;
    public Integer total;
    public Integer limite;
    @OneToMany(mappedBy = "saldo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    // @OrderBy("realizadaEm DESC")
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

    public static Saldo getSaldoWithUltimasTransacoesById(BigInteger id) {
        return find("FROM Saldo s JOIN FETCH s.transacoes WHERE s.id = ?1 ORDER BY realizadaEm DESC LIMIT 10", id).firstResult();

    }
}
