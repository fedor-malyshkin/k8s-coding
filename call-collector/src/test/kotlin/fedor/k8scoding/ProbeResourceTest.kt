package fedor.k8scoding

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

@QuarkusTest
class ProbeResourceTest {

    @Test
    fun live() {
        RestAssured.given()
            .`when`().get("/probes/live")
            .then()
            .statusCode(200)
    }

    @Test
    fun ready() {
        RestAssured.given()
            .`when`().get("/probes/ready")
            .then()
            .statusCode(200)
    }
}