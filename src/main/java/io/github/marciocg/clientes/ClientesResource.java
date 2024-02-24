package io.github.marciocg.clientes;

import java.time.Instant;
import java.util.Optional;

import io.quarkus.hibernate.orm.panache.Panache;
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
        Saldo saldoCliente = getSaldoById(id);

        if (transacaoRequest.valor <= 0) {
            // valor negativo não pode, informar o débito/crédito no tipo
            throw new WebApplicationException(Response.status(422).entity(transacaoRequest.valor + " Valor inválido").build());
        }

        if (transacaoRequest.tipo.equals("c")) {
            saldoCliente.total += transacaoRequest.valor;
        } else if (transacaoRequest.tipo.equals("d")) {
            saldoCliente.total -= transacaoRequest.valor;
        } else {
            // tipo de transação não é débito nem crédito
            throw new WebApplicationException(Response.status(422).entity(transacaoRequest.tipo + " Tipo de transação inválido").build());
        }

        if ((saldoCliente.total < 0) && (saldoCliente.total * -1 > saldoCliente.limite)) {
            // estouro de limite - saldo insuficiente
            // throw new WebApplicationException(Response.Status.fromStatusCode(422));
            throw new WebApplicationException(
                    Response.status(422).entity(transacaoRequest.valor + " Saldo insuficiente").build());
        }

        Transacoes novaTransacao = Panache.getEntityManager().merge(new Transacoes(transacaoRequest.valor, transacaoRequest.tipo,
                transacaoRequest.descricao, Instant.now().toString()));

        saldoCliente.addTransacoes(novaTransacao);
        saldoCliente.persist();

        return TransacaoResponseDTO.of(saldoCliente);
    }

    private Saldo getSaldoById(Integer id) {
        Optional<Saldo> saldo = Saldo.findByIdOptional(id);
        if (saldo.isEmpty()) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } else {
            return saldo.get();
        }
    }

}
