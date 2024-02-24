package io.github.marciocg.clientes;

import java.util.LinkedList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Saldo extends PanacheEntity {

    public Integer total;
    public Integer limite;
    @JsonProperty("ultimas_transacoes")
    @OneToMany(
        mappedBy = "saldo",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    public List<Transacoes> transacoes = new LinkedList<>();
    
    public void addTransacoes(Transacoes transacao) {
        this.transacoes.add(0, transacao);
        transacao.saldo = this;
    }
    
}

