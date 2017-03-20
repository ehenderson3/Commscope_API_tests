package comm.service.tests;

import com.jayway.restassured.http.ContentType;
import comm.service.model.Licensee;
import comm.service.model.Projects;
import comm.service.model.RestAssuredConfig;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class DomainBands extends RestAssuredConfig {
    Random rndNum = new Random();
    int randomNumber = rndNum.nextInt(1000);


    /*
    MethodName_StateUnderTest_ExpectedBehavior:
    There are arguments against this strategy that if method
    names change as part of code refactoring than test name like this should also change or it becomes
    difficult to comprehend at a later stage. Following are some of the example:
        isAdult_AgeLessThan18_False
        withdrawMoney_InvalidAccount_ExceptionThrown
        admitStudent_MissingMandatoryFields_FailToAdmit
     */

    int licenseeId = 0;
    int domain = 1;


    @Test
    public void GetBands_ValidDomain_ReturnBandsAssociatedToDomain() {

        given()
                .queryParam("domainId", domain)
                .when()
                .get("/bands")
                .prettyPeek()
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 ")
                .body("message", is("Successfully retrieved 15 Bands"))
                .body("count", is(15))
                .body("page", is(0))
                .body("entities.bandId", hasItems(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15));

    }

    @Test
    public void GetBands_Band12_ReturnSpecificsToBand200() {

        given()
                .pathParam("bandId", 12)
                .when()
                .get("/bands/{bandId}")
                .prettyPeek()
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 ")
                .body("entity.bandId", is(12))
                .body("entity.domainId", is(1))
                .body("entity.startFrequency", is(12700.0f))
                .body("entity.endFrequency", is(13250.0f))
                .body("entity.buckRadius", nullValue())
                .body("entity.bandDescription", is("13.0 GHz"));
    }

    @Test
    public void GetBands_Band6_ReturnSpecificsToBand200() {

        given()
                .pathParam("bandId", 6)
                .when()
                .get("/bands/{bandId}")
                .prettyPeek()
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 ")
                .body("entity.bandId", is(6))
                .body("entity.domainId", is(1))
                .body("entity.startFrequency", is(6425.0f))
                .body("entity.endFrequency", is(6525.0f))
                .body("entity.buckRadius", nullValue())
                .body("entity.bandDescription", is("6.4 GHz"));
    }


    @Test
    public void GetBands_Band8_ReturnSpecificsToBand200() {

        given()
                .pathParam("bandId", 8)
                .when()
                .get("/bands/{bandId}")
                .prettyPeek()
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 ")
                .body("entity.bandId", is(8))
                .body("entity.domainId", is(1))
                .body("entity.startFrequency", is(6875.0f))
                .body("entity.endFrequency", is(7125.0f))
                .body("entity.buckRadius", nullValue())
                .body("entity.bandDescription", is("7.0 GHz"));
    }
    @Test
    public void GetBands_Band100_Error404() {

        given()
                .pathParam("bandId", 100)
                .when()
                .get("/bands/{bandId}")
                .prettyPeek()
                .then()
                .statusCode(404)
                .statusLine("HTTP/1.1 404 ");
    }

    @Test
    public void GetBands_DomainNotExist2_404Error() {

        given()
                .pathParam("domainId", 2)
                .when()
                .get("/domain/{domainId}")
                .prettyPeek()
                .then()
                .statusCode(404)
                .statusLine("HTTP/1.1 404 ")
                .body("error",is("Not Found"));
    }
}
