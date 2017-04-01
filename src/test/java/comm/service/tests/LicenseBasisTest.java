package comm.service.tests;

import com.jayway.restassured.http.ContentType;
import comm.service.model.RestAssuredConfig;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class LicenseBasisTest extends RestAssuredConfig {

    @Test()
    public void GetlicenseBasis_ValidLicBasis_SuccessfullyRetrievedLicenseBasis() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/license-bases")//need a fix basis versus bases
                .prettyPeek()
                .then()
                .statusLine("HTTP/1.1 200 ")
                .body("message", is("Successfully retrieved 2 License Bases"))//need a fix basis versus bases
                .body("count", is(2))
                .body("page", is(0))
                .body("size", is(0))
                .body("entities.licenseBasisId[0]", is(1))
                .body("entities.licenseBasisId[1]", is(2))
                .body("entities.licenseBasis[0]", is("Primary"))
                .body("entities.licenseBasis[1]", is("Secondary"))

        ;
    }


}
