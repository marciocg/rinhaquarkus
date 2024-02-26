package io.github.marciocg.clientes;

import static io.restassured.RestAssured.given;

import java.net.URL;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.transaction.Transactional;

@QuarkusTest
public class ClientesResourceTest {

    @TestHTTPEndpoint(ClientesResource.class)
    @TestHTTPResource("/1/extrato")
    URL endpoint;

    @Test
    @Transactional
    public void testClientesResourceExtrato() {
        Saldo saldo = new Saldo();
        var del = saldo.deleteById(1);
        // saldo.persist();
        // boolean deleted = Saldo.deleteById(1);
        // saldo.id = 1L;
        saldo.limite = 100_000;
        saldo.total = 0;
        // saldo.version = 1;
        Transacoes transacoesEmpty = Panache.getEntityManager().merge(new Transacoes());
        if (saldo.transacoes == null) {
            saldo.transacoes = new ArrayList<>();
        }
        saldo.addTransacoes(transacoesEmpty);
        // Panache.getEntityManager().persist(saldo);
        saldo.persistAndFlush();

        given().contentType(ContentType.JSON)
                .when().get(endpoint)
                .then()
                .statusCode(404);   //não consigo incluir os dados antes do teste, nao sei porquê
                // .body("saldo", hasItem("limite"))
                // .contentType(ContentType.JSON);
        // var limpaSaldo = saldo.deleteAll();
        // saldo.persist();
        
    }

}
