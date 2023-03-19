package fedor.k8scoding

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test

@QuarkusTest
class AskResourceTest {

    @Test
    fun testHelloEndpoint() {
        given()
            .`when`().get("/ask")
            .then()
            .statusCode(200)
            .body(`is`("Yo!"))
    }

}