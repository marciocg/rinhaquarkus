package io.github.marciocg.clientes;

import java.util.List;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

@Entity(name = "Saldo")
@Table(name = "saldo")
@OptimisticLocking(type = OptimisticLockType.VERSION)
public class Saldo extends PanacheEntity {

    public Integer total;
    public Integer limite;
    @JsonProperty("ultimas_transacoes")
    @OneToMany(
        mappedBy = "saldo",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @OrderBy("id DESC")
    public List<Transacoes> transacoes;
    
    public void addTransacoes(Transacoes transacao) {
        this.transacoes.add(transacao);
        transacao.saldo = this;
    }
    
}

