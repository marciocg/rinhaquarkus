package io.github.marciocg.clientes;

import java.util.List;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@SuppressWarnings("unused")
@Entity(name = "Saldo")
@Table(name = "saldo")
// @OptimisticLocking(type = OptimisticLockType.VERSION)
public class Saldo extends PanacheEntity {

    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // public Integer id;
    public Integer total;
    public Integer limite;
    // @Version
    // public Integer version;
    @JsonProperty("ultimas_transacoes")
    @OneToMany(
        mappedBy = "saldo",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @OrderBy("realizadaEm DESC")
    public List<Transacoes> transacoes;
    
    public void addTransacoes(Transacoes transacao) {
        this.transacoes.add(transacao);
        transacao.saldo = this;
    }
    
}

