package io.github.marciocg.clientes;

import java.util.Optional;

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
        Optional<Saldo> saldo = Saldo.findByIdOptional(id);
        if (saldo.isEmpty()) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } else {
            return SaldoDTO.of(saldo.get());
            // return new SaldoDTO(saldo.get());
        }
        
    }

    // @POST
    // @Path("/{id}/transacoes")
    // public Saldo transacoes(@PathParam("id") Integer id) {

    // }

    
}
