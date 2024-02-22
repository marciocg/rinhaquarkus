package io.github.marciocg.clientes;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class UltimasTransacoes extends PanacheEntity {

    public Integer valor;
    public String tipo;
    public String descricao;
    public String realizadaEm;
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "saldo_id", insertable = false, updatable = false)
    // public Saldo saldo;
    
}