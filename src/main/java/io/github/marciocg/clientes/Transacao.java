package io.github.marciocg.clientes;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name = "Transacao")
@Table(name = "transacao")
public class Transacao extends PanacheEntity {

    public Integer valor;
    @Column(length = 1)
    public String tipo;
    @Column(length = 10)
    public String descricao;
    @Column(name = "realizada_em")
    public Instant realizadaEm;
    //@JsonIgnore                 //essa anotação está resolvendo magicamente o problema de N+1 do ExtratoResponseDTO, então retirei e botei EAGER
    @ManyToOne(fetch = FetchType.EAGER)
    public Saldo saldo;

    public Transacao() {

    }

    public Transacao(Integer valor, String tipo, String descricao, Instant realizadaEm) {
        this.valor = valor;
        this.tipo = tipo;
        this.descricao = descricao;
        this.realizadaEm = realizadaEm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Transacao))
            return false;
        return id != null && id.equals(((Transacao) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}