package io.github.marciocg.clientes;

import java.time.Instant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transacao") //, indexes = @Index(name = "idx_realizada_em", columnList = "realizada_em DESC"))
public class Transacao extends PanacheEntity {

    public Integer valor;
    @Column(length = 1)
    public String tipo;
    @Column(length = 10)
    public String descricao;
    @Column(name = "realizada_em")
    public Instant realizadaEm;
    @JsonIgnore(value = true) // essa anotação está resolvendo magicamente o problema de JSON INFINITO do ExtratoResponseDTO
    @ManyToOne(fetch = FetchType.LAZY)
    public Saldo saldo;

    public Transacao() {

    }

    public Transacao(Integer valor, String tipo, String descricao, Instant realizadaEm) {
        this.valor = valor;
        this.tipo = tipo;
        this.descricao = descricao;
        this.realizadaEm = realizadaEm;
    }

    public static PanacheQuery<Transacao> getUltimas10Transacoes(Saldo saldo) {
        PanacheQuery<Transacao> transacoesQuery = Transacao
                .find("FROM Transacao t WHERE t.saldo = ?1", Sort.descending("realizadaEm"), saldo);
        transacoesQuery.range(0, 9);
        return transacoesQuery;
    }

    // @Override
    // public boolean equals(Object o) {
    //     if (this == o)
    //         return true;
    //     if (!(o instanceof Transacao))
    //         return false;
    //     return id != null && id.equals(((Transacao) o).id);
    // }

    // @Override
    // public int hashCode() {
    //     return getClass().hashCode();
    // }

}