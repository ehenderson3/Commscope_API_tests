package comm.service.tests;

import comm.service.model.RestAssuredConfig;
import org.testng.annotations.Test;

import java.util.Random;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AuthenticationResourceTest extends RestAssuredConfig {
    Random rndNum = new Random();
    private int randomNumber = rndNum.nextInt(1000);


    /*
    MethodName_StateUnderTest_ExpectedBehavior:
    There are arguments against this strategy that if method
    names change as part of code refactoring than test name like this should also change or it becomes
    difficult to comprehend at a later stage. Following are some of the example:
        isAdult_AgeLessThan18_False
        withdrawMoney_InvalidAccount_ExceptionThrown
        admitStudent_MissingMandatoryFields_FailToAdmit
     */

    private int domain = 1;


    @Test
    public void Auth_CorrectCreds_ReturnToken() {

        given()
                .queryParam("username", "Legouser1")
                .queryParam("password", "Welcome1")
                .when()
                .get("/authentication")
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("message", equalTo("Successfully created Token"));

    }
    @Test
    public void Auth_IncorrectCreds_ReturnToken() {

        given()
                .queryParam("username", "Legouser2")
                .queryParam("password", "Welcome2")
                .when()
                .get("/authentication")
                .prettyPeek()
                .then()
                .statusCode(401)
                .body("message", equalTo("LegoUser Id and password are not valid. Please try again"));

    }

    @Test
    public void Auth_IncorrectCreds2_ReturnToken() {

        given()
                .queryParam("username", "Legouser1")
                .queryParam("password", "Welcome2")
                .when()
                .get("/authentication")
                .prettyPeek()
                .then()
                .statusCode(401)
                .body("message", equalTo("LegoUser Id and password are not valid. Please try again"));

    }

}
