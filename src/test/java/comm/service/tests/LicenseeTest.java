package comm.service.tests;


import com.jayway.restassured.http.ContentType;
import comm.service.model.Licensee;
import comm.service.model.RestAssuredConfig;
import org.testng.annotations.Test;

import java.util.Random;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.is;

public class LicenseeTest extends RestAssuredConfig{
    Random rndNum = new Random();
    int randomNumber = rndNum.nextInt(100000);

    /*
    MethodName_StateUnderTest_ExpectedBehavior:
    There are arguments against this strategy that if method
    names change as part of code refactoring than test name like this should also change or it becomes
    difficult to comprehend at a later stage. Following are some of the example:
        isAdult_AgeLessThan18_False
        withdrawMoney_InvalidAccount_ExceptionThrown
        admitStudent_MissingMandatoryFields_FailToAdmit
     */

    @Test()
    public void GetLicensee_AvailableEndPoint_Returns200StatusCode() {
        when()
                .get("/licensees")
                .then()
                .statusCode(200);
    }

    @Test
    public void GetLicensee_ValidLicenseeId_Returns200StatusCode() {


        Licensee licensee = new Licensee("New Test", "eh@thebestco.com", "121", "Harry Henderson");

        int licenseeId = given()
                .contentType(ContentType.JSON)
                .body(licensee)
        .when()
                .post("/licensees")
        .then()
                .statusCode(201)
                .body("entity.companyName", is(licensee.getCompanyName()))
                .body("entity.contactEmail", is(licensee.getContactEmail()))
                .body("entity.contactName", is(licensee.getContactName()))
                .body("entity.licenseeCode", is(licensee.getLicenseeCode()))
        .extract()
                .path("entity.licenseeId");

        given()
                .pathParam("licenseeId", licenseeId)
        .when()
                .get("/licensees/{licenseeId}")
                .prettyPeek()
        .then()
                .statusCode(200)
                .body("entity.licenseeId", is(licenseeId))
                .body("entity.companyName", is(licensee.getCompanyName()))
                .body("entity.contactEmail", is(licensee.getContactEmail()))
                .body("entity.contactName", is(licensee.getContactName()))
                .body("entity.licenseeCode", is(licensee.getLicenseeCode()));

    }



    @Test
    public void PostLicensee_WithoutName_LicenseeSaved() {


        Licensee licensee = new Licensee("", "eh@thebestco.com", "as8sa9a8safd89", "Harry Henderson");

        given()
                .contentType(ContentType.JSON)
                .body(licensee)
                .when()
                .post("/licensees")
                .then()
                .statusCode(201)
                .body("entity.companyName", is(licensee.getCompanyName()))
                .body("entity.contactEmail", is(licensee.getContactEmail()))
                .body("entity.contactName", is(licensee.getContactName()))
                .body("entity.licenseeCode", is(licensee.getLicenseeCode()));
    }
    @Test
    public void PostLicensee_WithoutEmail_LicenseeSaved() {


        Licensee licensee = new Licensee("", "", "as8sa9a8safd89", "Harry Henderson");

        given()
                .contentType(ContentType.JSON)
                .body(licensee)
                .when()
                .post("/licensees")
        .then()
                .statusCode(201)
                .body("entity.companyName", is(licensee.getCompanyName()))
                .body("entity.contactEmail", is(licensee.getContactEmail()))
                .body("entity.contactName", is(licensee.getContactName()))
                .body("entity.licenseeCode", is(licensee.getLicenseeCode()));
    }

    @Test
    public void PostLicensee_WithoutNameOrEmail_LicenseeSaved() {


        Licensee licensee = new Licensee("", "", "", "Harry Henderson");

        given()
                .contentType(ContentType.JSON)
                .body(licensee)
                .when()
                .post("/licensees")
        .then()
                .statusCode(201)
                .body("entity.companyName", is(licensee.getCompanyName()))
                .body("entity.contactEmail", is(licensee.getContactEmail()))
                .body("entity.contactName", is(licensee.getContactName()))
                .body("entity.licenseeCode", is(licensee.getLicenseeCode()));
    }

    @Test
    public void PostLicensee_WithoutNameEmailLicCodeORContact_LicenseeSaved() {


        Licensee licensee = new Licensee("", "", "", "");

        given()
                .contentType(ContentType.JSON)
                .body(licensee)
                .when()
                .post("/licensees")
        .then()
                .statusCode(201)
                .body("entity.companyName", is(licensee.getCompanyName()))
                .body("entity.contactEmail", is(licensee.getContactEmail()))
                .body("entity.contactName", is(licensee.getContactName()))
                .body("entity.licenseeCode", is(licensee.getLicenseeCode()));
    }



}


