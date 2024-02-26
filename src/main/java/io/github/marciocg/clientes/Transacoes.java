package io.github.marciocg.clientes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name = "Transacoes")
@Table(name = "transacoes")
public class Transacoes extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    // @JsonIgnore
    public Integer id;
    public Integer valor;
    @Column(length = 1)
    public String tipo;
    public String descricao;
    @JsonProperty("realizada_em")
    public String realizadaEm;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    public Saldo saldo;

    public Transacoes() {

    }

    public Transacoes(Integer valor, String tipo, String descricao, String realizadaEm) {
        this.valor = valor;
        this.tipo = tipo;
        this.descricao = descricao;
        this.realizadaEm = realizadaEm;
    }

    /*
     * public Transacoes(Integer id, Integer valor, String tipo, String descricao,
     * String realizadaEm) {
     * this.id = id;
     * this.valor = valor;
     * this.tipo = tipo;
     * this.descricao = descricao;
     * this.realizadaEm = realizadaEm;
     * }
     * 
     * 
     * public static void append(List<Transacoes> transacoes, TransacaoRequestDTO
     * transacaoRequest) {
     * Transacoes ultimaTransacao = transacoes.get(0); //a ideia eh criar uma copia
     * e alterar com o request
     * ultimaTransacao.id = ultimaTransacao.id + 1;
     * ultimaTransacao.valor = transacaoRequest.valor;
     * ultimaTransacao.tipo = transacaoRequest.tipo;
     * ultimaTransacao.descricao = transacaoRequest.descricao;
     * ultimaTransacao.realizadaEm = Instant.now().toString();
     * transacoes.add(0, ultimaTransacao);
     * }
     * 
     * public static Transacoes prepend(List<Transacoes> transacoes,
     * TransacaoRequestDTO transacaoRequest) {
     * Transacoes ultimaTransacao = transacoes.get(0); //a ideia eh criar uma copia
     * e alterar com o request
     * ultimaTransacao.id = ultimaTransacao.id + 1;
     * ultimaTransacao.valor = transacaoRequest.valor;
     * ultimaTransacao.tipo = transacaoRequest.tipo;
     * ultimaTransacao.descricao = transacaoRequest.descricao;
     * ultimaTransacao.realizadaEm = Instant.now().toString();
     * return new Transacoes(ultimaTransacao);
     * }
     * 
     * public static Transacoes ultimaTransacao(List<Transacoes> transacoes,
     * TransacaoRequestDTO transacaoRequest) {
     * Transacoes ultimaTransacao = transacoes.get(0); //a ideia eh criar uma copia
     * e alterar com o request
     * ultimaTransacao.id = ultimaTransacao.id + 1;
     * ultimaTransacao.valor = transacaoRequest.valor;
     * ultimaTransacao.tipo = transacaoRequest.tipo;
     * ultimaTransacao.descricao = transacaoRequest.descricao;
     * ultimaTransacao.realizadaEm = Instant.now().toString();
     * return ultimaTransacao;
     * }
     */

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