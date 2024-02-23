package io.github.marciocg.clientes;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import io.quarkus.logging.Log;
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

    public SaldoDTO extrato(@PathParam("id") Integer id) {
        return SaldoDTO.of(getSaldoById(id));

    }

    @SuppressWarnings("resource")
    @POST
    @Transactional
    @Path("/{id}/transacoes")
    public TransacaoResponseDTO transacao(@PathParam("id") Integer id, final TransacaoRequestDTO transacaoRequest) {
        Saldo saldoCliente = getSaldoById(id);

        if (transacaoRequest.valor < 0) {
            // valor negativo não pode, informar o débito/crédito no tipo
            throw new WebApplicationException(Response.Status.fromStatusCode(422));
        }

        // Log.info("== Recebido transacaoRequest:" + transacaoRequest.toString());
        // Log.info(transacaoRequest.valor);
        // Log.info(transacaoRequest.tipo);

        if (transacaoRequest.tipo.equals("c")) {
            saldoCliente.total += transacaoRequest.valor;
        } else if (transacaoRequest.tipo.equals("d")) {
            saldoCliente.total -= transacaoRequest.valor;
        } else {
            // tipo de transação não é débito nem crédito
            throw new WebApplicationException(Response.Status.PRECONDITION_FAILED);
        }

        if ((saldoCliente.total < 0) && (saldoCliente.total * -1 > saldoCliente.limite)) {
            // estouro de limite - saldo insuficiente
            // throw new WebApplicationException(Response.Status.fromStatusCode(422));
            throw new WebApplicationException(
                    Response.status(422).entity(transacaoRequest.valor + " Saldo insuficiente").build());
        }
  //TODO: Creio que Transacoes não pode ser uma Entity, modelar ultimas_transacoes sem criar tabela no banco de dados de alguma forma.
/*
        Log.info("Lista antes: " + saldoCliente.transacoes.toString());
        for (Transacoes trn : saldoCliente.transacoes) {
            System.out.println("x ID " + trn.id);
            System.out.println("x valor " + trn.valor);
            System.out.println("x tipo " + trn.tipo);
            System.out.println("x descricao " + trn.descricao);
            System.out.println("x saldo_ID " + trn.saldoId);
        }

        Integer novoid = saldoCliente.transacoes.get(0).id + 1;
        saldoCliente.transacoes.add(0, new Transacoes(novoid, transacaoRequest.valor, transacaoRequest.tipo, transacaoRequest.descricao, Instant.now().toString(), saldoCliente.id));
        // saldoCliente.transacoes.add(Transacoes.ultimaTransacao(saldoCliente.transacoes, transacaoRequest));
        // saldoCliente.transacoes.add(0, Transacoes.prepend(saldoCliente.transacoes, transacaoRequest));
        // Transacoes.append(saldoCliente.transacoes, transacaoRequest);
        Log.info("Lista depois: " + saldoCliente.transacoes.toString());
        for (Transacoes trn : saldoCliente.transacoes) {
            System.out.println("n ID " + trn.id);
            System.out.println("n valor " + trn.valor);
            System.out.println("n tipo " + trn.tipo);
            System.out.println("n descricao " + trn.descricao);
            System.out.println("n saldo_ID " + trn.saldoId);
        }
        List<Transacoes> guardaTransacoes = new LinkedList<>();
        Collections.copy(guardaTransacoes, saldoCliente.transacoes);
*/
        // Integer novoId = saldoCliente.transacoes.get(0).id;
        int novoId = ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE);
        saldoCliente.transacoes.add(0, new Transacoes(novoId, transacaoRequest.valor, transacaoRequest.tipo, transacaoRequest.descricao, Instant.now().toString()));
        saldoCliente.persist();
        return TransacaoResponseDTO.of(saldoCliente);
    }

    private Saldo getSaldoById(Integer id) {
        Optional<Saldo> saldo = Saldo.findByIdOptional(id);
        if (saldo.isEmpty()) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } else {
            return saldo.get();
            // return new SaldoDTO(saldo.get());
        }
    }

}
