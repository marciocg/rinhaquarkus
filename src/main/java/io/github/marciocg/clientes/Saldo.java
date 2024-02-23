package io.github.marciocg.clientes;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class Saldo extends PanacheEntity {

/*  @Id
    @GeneratedValue
    public Integer id; */
    public Integer total;
    public Integer limite;
    @JsonProperty("ultimas_transacoes")
    @OneToMany(
        mappedBy = "saldo",
//        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    // @JoinColumn(name = "saldoId")
    public List<Transacoes> transacoes;
    
}

