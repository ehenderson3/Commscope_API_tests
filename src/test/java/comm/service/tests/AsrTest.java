package comm.service.tests;

import com.jayway.restassured.http.ContentType;
import comm.service.model.CompanyModel;
import comm.service.model.RestAssuredConfig;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Random;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class AsrTest extends RestAssuredConfig {
    private Random rndNum = new Random();
    private int randomNumber1 = rndNum.nextInt(1000);



    @DataProvider(name = "Default Licensee")
    public Object[][] createLicData() {
        return new Object[][]{
                {new CompanyModel("Official Company"+ randomNumber1, "enrique@surgeforwoard.com", "34iij4j4u", "PATH")},
        };

    }


    /*
    MethodName_StateUnderTest_ExpectedBehavior:
    There are arguments against this strategy that if method
    names change as part of code refactoring than test name like this should also change or it becomes
    difficult to comprehend at a later stage. Following are some of the example:
        isAdult_AgeLessThan18_False
        withdrawMoney_InvalidAccount_ExceptionThrown
        admitStudent_MissingMandatoryFields_FailToAdmit
     */

    private int licenseeId = 0;
    private int projectId = 0;


    @Test
    public void GetAsr_WithOutQuery_ReturnAsr() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/fcc/asrs")
                .prettyPeek()
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 ")
                .body("message", is("Successfully retrieved 1000 Asrs"))
                .body("count", is(1000))
                .body("size", is(1000));
    }

    @Test
    public void GetAsr_WithPageQueryOf2_ReturnAsr() {
        given()
                .queryParam("page", 2)
                .contentType(ContentType.JSON)
                .when()
                .get("/fcc/asrs")
                .prettyPeek()
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 ")
                .body("message", is("Successfully retrieved 1000 Asrs"))
                .body("count", is(1000))
                .body("page", is(2))
                .body("size", is(1000));
    }


    @Test
    public void GetAsr_WithSizeQuery500_ReturnAsr() {
        given()
                .queryParam("size", 500)
                .contentType(ContentType.JSON)
                .when()
                .get("/fcc/asrs")
                .prettyPeek()
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 ")
                .body("message", is("Successfully retrieved 500 Asrs"))
                .body("count", is(500))
                .body("page", is(0))
                .body("size", is(500));
    }

    @Test
    public void GetAsr_WithNegativeEightyEightSizeValue_ErrorGettingListOfSites() {
        given()
                .queryParam("size", -88)
                .contentType(ContentType.JSON)
                .when()
                .get("/fcc/asrs")
                .prettyPeek()
                .then()
                .statusCode(500)
                .statusLine("HTTP/1.1 500 ")
                .body("message", is("Internal Server Error.  Error getting list of asrs sites"))
                .body("count", is(0))
                .body("page", is(0))
                .body("size", is(0));
    }

    @Test
    public void GetAsr_WithNegativeEightyEightPageValue_ErrorGettingListOfSites() {
        given()
                .queryParam("page", -88)
                .contentType(ContentType.JSON)
                .when()
                .get("/fcc/asrs")
                .prettyPeek()
                .then()
                .statusCode(500)
                .statusLine("HTTP/1.1 500 ")
                .body("message", is("Internal Server Error.  Error getting list of asrs sites"))
                .body("count", is(0))
                .body("page", is(0))
                .body("size", is(0));
    }

    @Test
    public void GetAsr_ValidExistingAsr1000043_SelectedAsrRecordReturned() {


        given()
                .pathParam("asr", "1000043")
                .when()
                .get("/fcc/asrs/{asr}")
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("entity.registrationNumber", is("1000043"))
                .body("entity.latDegrees", is(32))
                .body("entity.latMinutes", is(35))
                .body("entity.latSeconds", is(35.6f))
                .body("entity.latDirection", is("N"))
                .body("entity.longDegrees", is(96))
                .body("entity.longMinutes", is(45))
                .body("entity.longSeconds", is(11.3f))
                .body("entity.longDirection", is("W"))
                .body("entity.groundElevation", is(157.9f))
                .body("entity.entityName", is("CCATT LLC"))
                .body("entity.dateReceived", is("05/09/2016"))
                .body("entity.dateConstructed", is("09/15/1996"))
                .body("entity.overallHeightAboveGround", is(32.0f))
                .body("entity.overallHeightAmsl", is(189.9f))
                .body("entity.heightOfStructure", is(30.5f))
                .body("entity.statusCode", is("C"));

    }




    @Test
    public void GetAsr_LatMinZero1003353_SelectedAsrRecordReturned() {


        given()
                .pathParam("asr", "1003353")
                .when()
                .get("/fcc/asrs/{asr}")
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("entity.registrationNumber", is("1003353"))
                .body("entity.latDegrees", is(40))
                .body("entity.latMinutes", is(0))
                .body("entity.latSeconds", is(11.5f))
                .body("entity.latDirection", is("N"))
                .body("entity.longDegrees", is(75))
                .body("entity.longMinutes", is(1))
                .body("entity.longSeconds", is(18.8f))
                .body("entity.longDirection", is("W"))
                .body("entity.groundElevation", is(5.8f))
                .body("entity.entityName", is("CCATT LLC"))
                .body("entity.dateReceived", is("05/09/2016"))
                .body("entity.dateConstructed", is("03/19/1997"))
                .body("entity.overallHeightAboveGround", is(51.2f))
                .body("entity.overallHeightAmsl", is(57.0f))
                .body("entity.heightOfStructure", is(45.1f))
                .body("entity.statusCode", is("C"));

    }
}
