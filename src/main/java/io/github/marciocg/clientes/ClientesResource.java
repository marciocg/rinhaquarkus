package io.github.marciocg.clientes;

import java.time.Instant;
import java.util.Optional;

import io.quarkus.hibernate.orm.panache.Panache;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientesResource {

    @GET
    @Path("/{id}/extrato")

    public ExtratoResponseDTO extrato(@PathParam("id") Integer id) {
        return ExtratoResponseDTO.of(getSaldoById(id));
    }

    @SuppressWarnings("resource")
    @POST
    @Transactional
    @Path("/{id}/transacoes")
    public TransacaoResponseDTO transacao(@PathParam("id") Integer id, final TransacaoRequestDTO transacaoRequest) {
        if ((id < 1) || (id > 5)) {
            // if específico para tratar o caso da rinha
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        if ((transacaoRequest.descricao == null) || (transacaoRequest.valor == null)
                || (transacaoRequest.tipo == null)) {
            throw new WebApplicationException(Response.status(422).entity("Campo nulo").build());
        }

        Integer valor;
        try {
            valor = Integer.parseInt(transacaoRequest.valor);
        } catch (Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (valor <= 0) {
            // valor negativo ou zerado não pode, informar o débito/crédito no tipo
            throw new WebApplicationException(
                    Response.status(422).entity(valor.toString() + " Valor inválido").build());
        }

        Saldo saldoCliente = getSaldoByIdWriteLock(id);

        if (transacaoRequest.tipo.equals("c")) {
            saldoCliente.total += valor;
        } else if (transacaoRequest.tipo.equals("d")) {
            saldoCliente.total -= valor;
        } else {
            // tipo de transação não é débito nem crédito
            throw new WebApplicationException(
                    Response.status(422).entity(transacaoRequest.tipo + " Tipo de transação inválido").build());
        }

        if ((saldoCliente.total < 0) && (saldoCliente.total * -1 > saldoCliente.limite)) {
            // estouro de limite - saldo insuficiente
            throw new WebApplicationException(
                    Response.status(422).entity(valor.toString() + " Saldo insuficiente").build());
        }

        int tam = transacaoRequest.descricao.length();
        if (tam > 10) {
            tam = 10;
        } else if (tam == 0) {
            Response.status(422).entity(" Descricao com tamanho 0").build();
        }

        if (transacaoRequest.descricao.isBlank() || transacaoRequest.descricao.isEmpty()) {
            Response.status(422).entity(" Descricao vazia").build();
        }

        Transacoes novaTransacao = Panache.getEntityManager()
                .merge(new Transacoes(valor, transacaoRequest.tipo,
                        transacaoRequest.descricao.substring(0, tam), Instant.now()));

        saldoCliente.addTransacoes(novaTransacao);
        saldoCliente.persistAndFlush();

        return TransacaoResponseDTO.of(saldoCliente);
    }

    private Saldo getSaldoByIdWriteLock(Integer id) {
        Optional<Saldo> saldo = Saldo.findByIdOptional(id, LockModeType.PESSIMISTIC_WRITE);
        if (saldo.isEmpty()) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } else {
            return saldo.get();
        }
    }

    /*
     * private Saldo getSaldoByIdReadLock(Integer id) {
     * Optional<Saldo> saldo = Saldo.findByIdOptional(id,
     * LockModeType.PESSIMISTIC_READ);
     * if (saldo.isEmpty()) {
     * throw new WebApplicationException(Response.Status.NOT_FOUND);
     * } else {
     * return saldo.get();
     * }
     * }
     */

    private Saldo getSaldoById(Integer id) {
        Optional<Saldo> saldo = Saldo.findByIdOptional(id);
        if (saldo.isEmpty()) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } else {
            return saldo.get();
        }
    }

}
