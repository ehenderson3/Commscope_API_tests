package comm.service.tests;

import com.jayway.restassured.http.ContentType;
import comm.service.model.CompanyModel;
import comm.service.model.RestAssuredConfig;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Random;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class CallSignTest extends RestAssuredConfig {
    Random rndNum = new Random();
    private int randomNumber1 = rndNum.nextInt(1000);


    @DataProvider(name = "Default Licensee")
    public Object[][] createLicData() {
        return new Object[][]{
                {new CompanyModel("Official Company"+ randomNumber1, "enrique@surgeforwoard.com", "34iij4j4u", "PATH")},
        };

    }

    private int licenseeId = 0;
    private int projectId = 0;


    @Test
    public void GetCallSign_WithOutQuery_Return1000CallSigns() {
        given()
                .contentType(ContentType.JSON)
            .when()
                .get("/fcc/callsigns")
                .prettyPeek()
            .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 ")
                .body("message", is("Successfully retrieved 1000 Call Signs"))
                .body("count", is(1000))
                .body("size", is(1000));
    }

    @Test
    public void GetCallSign_With2PageQuery_Return1000CallSigns() {
        given()
                .queryParam("page", 2)
                .contentType(ContentType.JSON)
                .when()
                .get("/fcc/callsigns")
                .prettyPeek()
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 ")
                .body("message", is("Successfully retrieved 1000 Call Signs"))
                .body("count", is(1000))
                .body("page", is(2))
                .body("size", is(1000));
    }


    @Test
    public void GetCallSign_With500SizeQuery_Return500CallSigns() {
        given()
                .queryParam("size", 500)
                .contentType(ContentType.JSON)
                .when()
                .get("/fcc/callsigns")
                .prettyPeek()
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 ")
                .body("message", is("Successfully retrieved 500 Call Signs"))
                .body("count", is(500))
                .body("page", is(0))
                .body("size", is(500));
    }

    @Test
    public void GetCallSign_WithNegativeSevenSizeValue_ErrorGettingListOfSites() {
        given()
                .queryParam("size", -11)
                .contentType(ContentType.JSON)
                .when()
                .get("/fcc/callsigns")
                .prettyPeek()
                .then()
                .statusCode(500)
                .statusLine("HTTP/1.1 500 ")
                .body("message", is("Internal Server Error.  Error getting list of sites"))
                .body("count", is(0))
                .body("page", is(0))
                .body("size", is(0));
    }

    @Test
    public void GetCallSign_WithNegativeTwentyTwoPageValue_ErrorGettingListOfSites() {
        given()
                .queryParam("page", -22)
                .contentType(ContentType.JSON)
                .when()
                .get("/fcc/callsigns")
                .prettyPeek()
                .then()
                .statusCode(500)
                .statusLine("HTTP/1.1 500 ")
                .body("message", is("Internal Server Error.  Error getting list of sites"))
                .body("count", is(0))
                .body("page", is(0))
                .body("size", is(0));
    }

    @Test
    public void GetCallSign_ValidExistingCallSignKA20003_SelectedCallSignRecordReturned() {


        given()
                .pathParam("callSign", "KA20003")
                .when()
                .get("/fcc/callsigns/{callSign}")
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("entity.callSign", is("KA20003"))
                .body("entity.latDegrees", is(40))
                .body("entity.latMinutes", is(44))
                .body("entity.latSeconds", is(54.0f))
                .body("entity.latDirection", is("N"))
                .body("entity.longDegrees", is(73))
                .body("entity.longMinutes", is(59))
                .body("entity.longSeconds", is(9.0f))
                .body("entity.longDirection", is("W"))
                .body("entity.groundElevation", is(nullValue()))
                .body("entity.locationName", is("New York"))
                .body("entity.locationAddress", is("VIC: NEW YORK NY"))
                .body("entity.locationCity", is("NEW YORK"))
                .body("entity.locationCounty", is("NEW YORK"))
                .body("entity.locationState", is("NY"));

    }



    @Test
    public void GetCallSign_NullGELocNamAndLocAddyKA2132_SelectedCallSignRecordReturned() {


        given()
                .pathParam("callSign", "KA2132")
                .when()
                .get("/fcc/callsigns/{callSign}")
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("entity.callSign", is("KA2132"))
                .body("entity.latDegrees", is(34))
                .body("entity.latMinutes", is(44))
                .body("entity.latSeconds", is(46.3f))
                .body("entity.latDirection", is("N"))
                .body("entity.longDegrees", is(92))
                .body("entity.longMinutes", is(16))
                .body("entity.longSeconds", is(20.5f))
                .body("entity.longDirection", is("W"))
                .body("entity.groundElevation", is(nullValue()))
                .body("entity.locationName", is(nullValue()))
                .body("entity.locationAddress", is(nullValue()))
                .body("entity.locationCity", is("LITTLE ROCK"))
                .body("entity.locationCounty", is("PULASKI"))
                .body("entity.locationState", is("AR"));

    }

    @Test
    public void GetCallSign_LatMinZeroKB55979_SelectedCallSignRecordReturned() {


        given()
                .pathParam("callSign", "KB55979")
                .when()
                .get("/fcc/callsigns/{callSign}")
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("entity.callSign", is("KB55979"))
                .body("entity.latDegrees", is(39))
                .body("entity.latMinutes", is(0))
                .body("entity.latSeconds", is(36.3f))
                .body("entity.latDirection", is("N"))
                .body("entity.longDegrees", is(76))
                .body("entity.longMinutes", is(36))
                .body("entity.longSeconds", is(31.8f))
                .body("entity.longDirection", is("W"))
                .body("entity.groundElevation", is(nullValue()))
                .body("entity.locationName", is("Annapolis"))
                .body("entity.locationAddress", is("1690 HAWKINS RD"))
                .body("entity.locationCity", is("ANNAPOLIS"))
                .body("entity.locationCounty", is("ANNE ARUNDEL"))
                .body("entity.locationState", is("MD"));

    }
}
