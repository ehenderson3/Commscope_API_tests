package comm.service.tests;


import com.jayway.restassured.http.ContentType;
import comm.service.model.RestAssuredConfig;
import org.testng.annotations.Test;

import java.util.Random;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;

public class CompanyTest extends RestAssuredConfig{
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
    public void GetCompanies_FindCompanyWithChildren_Returns200StatusCode() {
        given()
                .pathParam("companyId", 1)
                .contentType(ContentType.JSON)
                .when()
                .get("/companies/{companyId}")
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("message", is("Successfully retrieved company"))
                .body("entity.companyId", is(1))
                .body("entity.companyCode", is("VZW111"))
                .body("entity.companyName", is("Verizon"))
                .body("entity.children[0]", is("VZW222"))
                .body("entity.children[1]", is("VZW333"))
                .body("entity.children[2]", is("VZW444"))
                .body("entity.contacts.contactPhones.phone[0]", hasItem("123-456-7890"))
                .body("entity.contacts.contactPhones.phone[0]", hasItem("111-111-1234")) ;
    }
    @Test()
    public void GetCompanies_FindCompany2_Returns200StatusCode() {
        given()
                .pathParam("companyId", 2)
                .contentType(ContentType.JSON)
                .when()
                .get("/companies/{companyId}")
                .prettyPeek()
                .then()
                .statusLine("HTTP/1.1 200 ")
                .body("message", is("Successfully retrieved company"))
                .body("entity.companyId", is(2))
                .body("entity.companyCode", is("VZW222"))
                .body("entity.companyName", is("Verizon AZ"))
                .body("entity.companyType", is("Broadcast Radio"))
                .body("entity.parent", is("VZW111"))
                .body("entity.contacts.contactName", hasItem("Mike Modano"))
                .body("entity.contacts.contactPhones.phone[0]", hasItem("987-654-3210"))
                .body("entity.contacts.contactPhones.phone[0]", hasItem("222-222-9876"));
    }
    @Test()
    public void GetCompanies_FindCompany3_Returns200StatusCode() {
        given()
                .pathParam("companyId", 3)
                .contentType(ContentType.JSON)
                .when()
                .get("/companies/{companyId}")
                .prettyPeek()
                .then()
                .statusLine("HTTP/1.1 200 ")
                .body("message", is("Successfully retrieved company"))
                .body("entity.companyId", is(3))
                .body("entity.companyCode", is("VZW333"))
                .body("entity.companyName", is("Verizon TX"))
                .body("entity.companyType", is("Broadcast Television"))
                .body("entity.parent", is("VZW111"))
                .body("entity.contacts.contactName", hasItem("Joe Nieuwendyk"))
                .body("entity.contacts.contactPhones.phone[0]", hasItem("987-654-3210"))
                .body("entity.contacts.contactPhones.phone[0]", hasItem("222-222-9876"));
    }
    @Test()
    public void GetCompanies_FindCompanyWithParent_Returns200StatusCode() {
        given()
                .pathParam("companyId", 4)
                .contentType(ContentType.JSON)
                .when()
                .get("/companies/{companyId}")
                .prettyPeek()
                .then()
                .statusLine("HTTP/1.1 200 ")
                .body("message", is("Successfully retrieved company"))
                .body("entity.companyId", is(4))
                .body("entity.companyCode", is("VZW444"))
                .body("entity.companyName", is("Verizon UT"))
                .body("entity.companyType", is("Cable Television Operator"))
                .body("entity.parent", is("VZW111"))
                .body("entity.contacts.contactName", hasItem("Ed Belfour"))
                .body("entity.contacts.contactPhones.phone[0]", hasItem("987-654-3210"))
                .body("entity.contacts.contactPhones.phone[0]", hasItem("222-222-9876"));
    }
    @Test()
    public void GetCompanies_FindCompanyWithNullParent_Returns200StatusCode() {
        given()
                .pathParam("companyId", 5)
                .contentType(ContentType.JSON)
                .when()
                .get("/companies/{companyId}")
                .prettyPeek()
                .then()
                .statusLine("HTTP/1.1 200 ")
                .body("message", is("Successfully retrieved company"))
                .body("entity.companyId", is(5))
                .body("entity.companyCode", is("ABC123"))
                .body("entity.companyName", is("General Service"))
                .body("entity.companyType", is("Cellular Telephone"))
                .body("entity.parent", isEmptyOrNullString())
                .body("entity.contacts.contactName", hasItem("Ed Belfour"))
                .body("entity.contacts.contactPhones.phone[0]", hasItem("987-654-3210"))
                .body("entity.contacts.contactPhones.phone[0]", hasItem("222-222-9876"));
    }

    @Test()
    public void GetCompanies_FindCompanyThatDoesNotExist_Returns200StatusCode() {
        given()
                .pathParam("companyId", 69854)
                .contentType(ContentType.JSON)
                .when()
                .get("/companies/{companyId}")
                .prettyPeek()
                .then()
                .statusLine("HTTP/1.1 404 ")
;
    }


}


