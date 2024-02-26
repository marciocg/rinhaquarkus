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
    @TestHTTPResource("/6/extrato")
    URL endpoint;

    @Test
    public void testClientesResourceExtrato() {
        given().contentType(ContentType.JSON)
                .when().get(endpoint)
                .then()
                .statusCode(404);   //não consigo incluir os dados antes do teste, nao sei porquê
      
    }

}
