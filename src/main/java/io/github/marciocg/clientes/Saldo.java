package io.github.marciocg.clientes;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Saldo extends PanacheEntity {

    Integer total;
    Integer limite;
    
}

