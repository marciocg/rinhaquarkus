package io.github.marciocg.clientes;

import java.time.Instant;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name = "Transacoes")
@Table(name = "transacoes")
public class Transacoes extends PanacheEntity {

    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // public Integer id;
    public Integer valor;
    @Column(length = 1)
    public String tipo;
    @Column(length = 10)
    public String descricao;
    @JsonProperty("realizada_em")
    @Column(name = "realizada_em")
    public Instant realizadaEm;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    public Saldo saldo;

    public Transacoes() {

    }

    public Transacoes(Integer valor, String tipo, String descricao, Instant realizadaEm) {
        this.valor = valor;
        this.tipo = tipo;
        this.descricao = descricao;
        this.realizadaEm = realizadaEm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Transacoes))
            return false;
        return id != null && id.equals(((Transacoes) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}