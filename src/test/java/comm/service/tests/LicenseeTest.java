package comm.service.tests;


import comm.service.model.Licensee;
import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import com.jayway.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;

public class LicenseeTest {
    Random rndNum = new Random();
    int randomNumber = rndNum.nextInt(100000);
    @BeforeClass
    public static void init(){
        baseURI = "https://comsearch.dev.surgeforward.com";
        port = 8443;
    }

    @Test()
    public void getlicensee() {
        when()
                .get("/licensees")
                .then()
                .statusCode(200);
    }

    @Test
    public void postLicensee() {


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
        .then()
                .statusCode(200)
                .body("entity.licenseeId", is(licenseeId))
                .body("entity.companyName", is(licensee.getCompanyName()))
                .body("entity.contactEmail", is(licensee.getContactEmail()))
                .body("entity.contactName", is(licensee.getContactName()))
                .body("entity.licenseeCode", is(licensee.getLicenseeCode()));

    }





    @Test
    public void postLicenseeNoName() {


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
    public void postLicenseeNoNameNoEmail() {


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
    public void postLicenseeNoNameNoEmailNoLicCode() {


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
    public void postLicenseeNoNameNoEmailNoLicCodeNoName() {


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

    {

    }

}


