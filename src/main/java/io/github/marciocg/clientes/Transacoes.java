package io.github.marciocg.clientes;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Transacoes extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    public Integer id;
    public Integer valor;
    public String tipo;
    public String descricao;
    @JsonProperty("realizada_em")
    public String realizadaEm;
    // @JsonIgnore
    // public Long saldoId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    public Saldo saldo;

    public Transacoes() {

    }

    public Transacoes(Integer id, Integer valor, String tipo, String descricao, String realizadaEm) {
        this.id = id;
        this.valor = valor;
        this.tipo = tipo;
        this.descricao = descricao;
        this.realizadaEm = realizadaEm;
        // this.saldoId = saldoId;
    }

    public Transacoes(Transacoes ultimaTransacao) {
        this.id = ultimaTransacao.id + 1;
        this.valor = ultimaTransacao.valor;
        this.tipo = ultimaTransacao.tipo;
        this.descricao = ultimaTransacao.descricao;
        this.realizadaEm = ultimaTransacao.realizadaEm;
        // this.saldoId = ultimaTransacao.saldoId;
    }

/*     public static void append(List<Transacoes> transacoes, TransacaoRequestDTO transacaoRequest) {
        Transacoes ultimaTransacao = transacoes.get(0);    //a ideia eh criar uma copia e alterar com o request
        ultimaTransacao.id = ultimaTransacao.id + 1;
        ultimaTransacao.valor = transacaoRequest.valor;
        ultimaTransacao.tipo = transacaoRequest.tipo;
        ultimaTransacao.descricao = transacaoRequest.descricao;
        ultimaTransacao.realizadaEm = Instant.now().toString();
        transacoes.add(0, ultimaTransacao);
    }
 */
    public static Transacoes prepend(List<Transacoes> transacoes, TransacaoRequestDTO transacaoRequest) {
        Transacoes ultimaTransacao = transacoes.get(0);    //a ideia eh criar uma copia e alterar com o request
        ultimaTransacao.id = ultimaTransacao.id + 1;
        ultimaTransacao.valor = transacaoRequest.valor;
        ultimaTransacao.tipo = transacaoRequest.tipo;
        ultimaTransacao.descricao = transacaoRequest.descricao;
        ultimaTransacao.realizadaEm = Instant.now().toString();
        return new Transacoes(ultimaTransacao);
    }

    public static Transacoes ultimaTransacao(List<Transacoes> transacoes, TransacaoRequestDTO transacaoRequest) {
        Transacoes ultimaTransacao = transacoes.get(0);    //a ideia eh criar uma copia e alterar com o request
        ultimaTransacao.id = ultimaTransacao.id + 1;
        ultimaTransacao.valor = transacaoRequest.valor;
        ultimaTransacao.tipo = transacaoRequest.tipo;
        ultimaTransacao.descricao = transacaoRequest.descricao;
        ultimaTransacao.realizadaEm = Instant.now().toString();
        return ultimaTransacao;
    }

}