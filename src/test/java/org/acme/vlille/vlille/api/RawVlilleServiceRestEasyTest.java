package org.acme.vlille.vlille.api;

import com.fasterxml.jackson.databind.JsonNode;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;


@QuarkusTest
@QuarkusTestResource(WireMockExtensions.class)
public class RawVlilleServiceRestEasyTest {
    @Test
    public void testHelloEndpoint() {
        var resp = given()
                .when().get("/api/stations")
                .then().extract();

        resp.response().then().statusCode(200)
                .body("stations.size()", is(12));

        var actual = resp.jsonPath().getList("stations", JsonNode.class);
        Assertions
                .assertThat(actual)
                .filteredOn(jsonNode -> jsonNode.get("nom").toString().matches(".*RUE BONTE-POLLET.*")
                    && jsonNode.get("nbvelosdispo").toString().equals("16"))
                .isNotEmpty()
                .withFailMessage("Bont-pollet is from database scheduled task and should have been there");


    }
}
