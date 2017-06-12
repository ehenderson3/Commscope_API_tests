package comm.service.tests;


import com.jayway.restassured.http.ContentType;
import comm.service.model.RestAssuredConfig;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

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
    String search ="/companies?search=";
    String active ="&active=true";
    String notActive ="&active=false";

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
                .body("entity.parent", isEmptyOrNullString())
                .body("entity.contacts.contactName", hasItem("Ed Belfour"))
                .body("entity.contacts.contactPhones.phone[0]", hasItem("987-654-3210"))
                .body("entity.contacts.contactPhones.phone[0]", hasItem("222-222-9876"));
    }

    @Test()
    public void GetCompanies_FindCompanyThatDoesNotExist_Returns404StatusCode() {
        given()
                .pathParam("companyId", 69854)
                .contentType(ContentType.JSON)
                .when()
                .get("/companies/{companyId}")
                .prettyPeek()
                .then()
                .statusLine("HTTP/1.1 404 ")
                .body("message" ,is("Company 69854 not found"))
;
    }

    @Test
    public void getCompanySearch_valueVerizonFieldValuecompanyNameopEQ_ResultsetContainsVerizon() throws UnsupportedEncodingException {
        String valueVal = "Verizon";
        String fieldVal = "companyName";
        String operatorVal = "EQ";
        String url = "[[{\"value\":\"" + valueVal + "\",\"field\":\"" + fieldVal + "\",\"operator\":\""+operatorVal+"\"}]]";
        String stringEncoded = URLEncoder.encode(url, "UTF-8");
        given()
                .urlEncodingEnabled(false)
                .when()
                .log().all()
                .get(search+stringEncoded)
                .prettyPeek()
                .then()
                .body("message", equalTo("Successfully retrieved 1 Companies"))
                .body("entities.companyId", hasItem(1))
                .body("entities.companyCode", hasItem("VZW111"))
                .body("entities.companyName", hasItem("Verizon"))
                .body("entities.children[0]", hasItem("VZW222"))
                .body("entities.children[0]", hasItem("VZW333"))
                .body("entities.children[0]", hasItem("VZW444"))

                .body("entities.contacts.contactName[0]", hasItem("John Doe"))
                .body("entities.contacts[0].contactPhones.phone[0]", hasItem("123-456-7890"))
                .body("entities.contacts[0].contactPhones.phone[0]", hasItem("111-111-1234"));

    }
    @Test
    public void getCompanySearch_valueVerizonFieldValuecompanyNameopLIKE_ResultsetContainsVerizon() throws UnsupportedEncodingException {
        String valueVal = "Verizon";
        String fieldVal = "companyName";
        String operatorVal = "LIKE";
        String url = "[[{\"value\":\"" + valueVal + "\",\"field\":\"" + fieldVal + "\",\"operator\":\""+operatorVal+"\"}]]";
        String stringEncoded = URLEncoder.encode(url, "UTF-8");
        given()
                .urlEncodingEnabled(false)
                .when()
                .log().all()
                .get(search+stringEncoded)
                .prettyPeek()
                .then()
                .body("message", equalTo("Successfully retrieved 4 Companies"))
                .body("entities.companyId", hasItem(4))
                .body("entities.companyName", hasItem("Verizon"))
                .body("entities.companyName", hasItem("Verizon AZ"))
                .body("entities.companyName", hasItem("Verizon TX"))
                .body("entities.companyName", hasItem("Verizon UT"))

                .body("entities.contacts.contactName[0]", hasItem("John Doe"))
                .body("entities.contacts[0].contactPhones.phone[0]", hasItem("123-456-7890"))
                .body("entities.contacts[0].contactPhones.phone[0]", hasItem("111-111-1234"));

    }

    @Test
    public void getCompanySearch_valueVZW111FieldValuecompanyNameopEQ_ResultsetContainsVZW111() throws UnsupportedEncodingException {
        String valueVal = "VZW111";
        String fieldVal = "companyCode";
        String operatorVal = "EQ";
        String url = "[[{\"value\":\"" + valueVal + "\",\"field\":\"" + fieldVal + "\",\"operator\":\""+operatorVal+"\"}]]";
        String stringEncoded = URLEncoder.encode(url, "UTF-8");
        given()
                .urlEncodingEnabled(false)
                .when()
                .log().all()
                .get(search+stringEncoded)
                .prettyPeek()
                .then()
                .body("message", equalTo("Successfully retrieved 1 Companies"))
                .body("entities.companyId", hasItem(1))
                .body("entities.companyCode", hasItem("VZW111"))
                .body("entities.contacts.contactName[0]", hasItem("John Doe"))
                .body("entities.contacts[0].contactPhones.phone[0]", hasItem("123-456-7890"))
                .body("entities.contacts[0].contactPhones.phone[0]", hasItem("111-111-1234"));
    }

    @Test
    public void getCompanySearch_valueVZWFieldValuecompanyNameopLIKE_ResultsetContainsVZW() throws UnsupportedEncodingException {
        String valueVal = "VZW";
        String fieldVal = "companyCode";
        String operatorVal = "LIKE";
        String url = "[[{\"value\":\"" + valueVal + "\",\"field\":\"" + fieldVal + "\",\"operator\":\""+operatorVal+"\"}]]";
        String stringEncoded = URLEncoder.encode(url, "UTF-8");
        given()
                .urlEncodingEnabled(false)
                .when()
                .log().all()
                .get(search+stringEncoded)
                .prettyPeek()
                .then()
                .body("message", equalTo("Successfully retrieved 4 Companies"))
                .body("entities.companyId", hasItem(4))
                .body("entities.companyName", hasItem("Verizon"))
                .body("entities.companyName", hasItem("Verizon AZ"))
                .body("entities.companyName", hasItem("Verizon TX"))
                .body("entities.companyName", hasItem("Verizon UT"))

                .body("entities.contacts.contactName[0]", hasItem("John Doe"))
                .body("entities.contacts[0].contactPhones.phone[0]", hasItem("123-456-7890"))
                .body("entities.contacts[0].contactPhones.phone[0]", hasItem("111-111-1234"));

    }

    @Test
    public void getCompanySearch_valueJohnDoeFieldValuecontactNameopEQ_ResultsetContainsJohnDoe() throws UnsupportedEncodingException {
        String valueVal = "John Doe";
        String fieldVal = "contactName";
        String operatorVal = "EQ";
        String url = "[[{\"value\":\"" + valueVal + "\",\"field\":\"" + fieldVal + "\",\"operator\":\""+operatorVal+"\"}]]";
        String stringEncoded = URLEncoder.encode(url, "UTF-8");
        given()
                .urlEncodingEnabled(false)
                .when()
                .log().all()
                .get(search+stringEncoded)
                .prettyPeek()
                .then()
                .body("message", equalTo("Successfully retrieved 1 Companies"))
                .body("entities.companyId", hasItem(1))
                .body("entities.companyName", hasItem("Verizon"))
                .body("entities.contacts.contactName[0]", hasItem("John Doe"))
                .body("entities.contacts[0].contactPhones.phone[0]", hasItem("123-456-7890"))
                .body("entities.contacts[0].contactPhones.phone[0]", hasItem("111-111-1234"));

    }

    @Test
    public void getCompanySearch_valueJohnDoeFieldValuecontactNameopLIKE_ResultsetContainsJohnDoe() throws UnsupportedEncodingException {
        String valueVal = "John Doe";
        String fieldVal = "contactName";
        String operatorVal = "LIKE";
        String url = "[[{\"value\":\"" + valueVal + "\",\"field\":\"" + fieldVal + "\",\"operator\":\""+operatorVal+"\"}]]";
        String stringEncoded = URLEncoder.encode(url, "UTF-8");
        given()
                .urlEncodingEnabled(false)
                .when()
                .log().all()
                .get(search+stringEncoded)
                .prettyPeek()
                .then()
                .body("message", equalTo("Successfully retrieved 1 Companies"))
                .body("entities.companyId", hasItem(1))
                .body("entities.companyName", hasItem("Verizon"))
                .body("entities.contacts.contactName[0]", hasItem("John Doe"))
                .body("entities.contacts[0].contactPhones.phone[0]", hasItem("123-456-7890"))
                .body("entities.contacts[0].contactPhones.phone[0]", hasItem("111-111-1234"));

    }

    @Test
    public void getCompanySearch_valueLicenseeFieldValueserviceNameopEQ_ResultsetContainsJohnDoe() throws UnsupportedEncodingException {
        String valueVal = "Licensee";
        String fieldVal = "serviceName";
        String operatorVal = "EQ";
        String url = "[[{\"value\":\"" + valueVal + "\",\"field\":\"" + fieldVal + "\",\"operator\":\""+operatorVal+"\"}]]";
        String stringEncoded = URLEncoder.encode(url, "UTF-8");
        given()
                .urlEncodingEnabled(false)
                .when()
                .log().all()
                .get(search+stringEncoded)
                .prettyPeek()
                .then()
                .body("message", equalTo("Successfully retrieved 4 Companies"))
                .body("entities.companyId", hasItem(4))

                .body("entities.companyName", hasItem("Verizon"))
                .body("entities.companyName", hasItem("Verizon AZ"))
                .body("entities.companyName", hasItem("Verizon TX"))
                .body("entities.companyName", hasItem("Verizon UT"))
                .body("entities.services[0]", hasItem("Licensee"))
                .body("entities.services[1]", hasItem("Licensee"))
                .body("entities.services[2]", hasItem("Licensee"))
                .body("entities.services[3]", hasItem("Licensee"))

                .body("entities.contacts.contactName[0]", hasItem("John Doe"))
                .body("entities.contacts[0].contactPhones.phone[0]", hasItem("123-456-7890"))
                .body("entities.contacts[0].contactPhones.phone[0]", hasItem("111-111-1234"));

    }
    @Test
    public void getCompanySearch_valueLicenseeFieldValueserviceNameopLIKE_ResultsetContainsJohnDoe() throws UnsupportedEncodingException {
        String valueVal = "Licensee";
        String fieldVal = "serviceName";
        String operatorVal = "LIKE";
        String url = "[[{\"value\":\"" + valueVal + "\",\"field\":\"" + fieldVal + "\",\"operator\":\""+operatorVal+"\"}]]";
        String stringEncoded = URLEncoder.encode(url, "UTF-8");
        given()
                .urlEncodingEnabled(false)
                .when()
                .log().all()
                .get(search+stringEncoded)
                .prettyPeek()
                .then()
                .body("message", equalTo("Successfully retrieved 4 Companies"))
                .body("entities.companyId", hasItem(4))

                .body("entities.companyName", hasItem("Verizon"))
                .body("entities.companyName", hasItem("Verizon AZ"))
                .body("entities.companyName", hasItem("Verizon TX"))
                .body("entities.companyName", hasItem("Verizon UT"))
                .body("entities.services[0]", hasItem("Licensee"))
                .body("entities.services[1]", hasItem("Licensee"))
                .body("entities.services[2]", hasItem("Licensee"))
                .body("entities.services[3]", hasItem("Licensee"))

                .body("entities.contacts.contactName[0]", hasItem("John Doe"))
                .body("entities.contacts[0].contactPhones.phone[0]", hasItem("123-456-7890"))
                .body("entities.contacts[0].contactPhones.phone[0]", hasItem("111-111-1234"));

    }

    @Test
    public void getCompanySearch_valueTrueFieldValueActiveopEQ_ResultsetContainsTrue() throws UnsupportedEncodingException {
        String valueVal = "Licensee";
        String fieldVal = "serviceName";
        String operatorVal = "LIKE";
        String url = "[[{\"value\":\"" + valueVal + "\",\"field\":\"" + fieldVal + "\",\"operator\":\""+operatorVal+"\"}]]";
        String stringEncoded = URLEncoder.encode(url, "UTF-8");
        given()
                .urlEncodingEnabled(false)
                .when()
                .log().all()
                .get(search+stringEncoded+active)
                .prettyPeek()
                .then()
                .body("message", equalTo("Successfully retrieved 4 Companies"))
                .body("entities.companyId", hasItem(4))

                .body("entities.companyName", hasItem("Verizon"))
                .body("entities.companyName", hasItem("Verizon AZ"))
                .body("entities.companyName", hasItem("Verizon TX"))
                .body("entities.companyName", hasItem("Verizon UT"))
                .body("entities.services[0]", hasItem("Licensee"))
                .body("entities.services[1]", hasItem("Licensee"))
                .body("entities.services[2]", hasItem("Licensee"))
                .body("entities.services[3]", hasItem("Licensee"))

                .body("entities.contacts.contactName[0]", hasItem("John Doe"))
                .body("entities.contacts[0].contactPhones.phone[0]", hasItem("123-456-7890"))
                .body("entities.contacts[0].contactPhones.phone[0]", hasItem("111-111-1234"));

    }

    @Test
    public void getCompanySearch_valueTrueFieldValueActiveopEQ_ResultsetContainsFalse() throws UnsupportedEncodingException {
        String valueVal = "Licensee";
        String fieldVal = "serviceName";
        String operatorVal = "LIKE";
        String url = "[[{\"value\":\"" + valueVal + "\",\"field\":\"" + fieldVal + "\",\"operator\":\""+operatorVal+"\"}]]";
        String stringEncoded = URLEncoder.encode(url, "UTF-8");
        given()
                .urlEncodingEnabled(false)
                .when()
                .log().all()
                .get(search+stringEncoded+notActive)
                .prettyPeek()
                .then()
                .body("message", equalTo("Successfully retrieved 4 Companies"))
                .body("entities.companyId", hasItem(4))

                .body("entities.companyName", hasItem("Verizon"))
                .body("entities.companyName", hasItem("Verizon AZ"))
                .body("entities.companyName", hasItem("Verizon TX"))
                .body("entities.companyName", hasItem("Verizon UT"))
                .body("entities.services[0]", hasItem("Licensee"))
                .body("entities.services[1]", hasItem("Licensee"))
                .body("entities.services[2]", hasItem("Licensee"))
                .body("entities.services[3]", hasItem("Licensee"))

                .body("entities.contacts.contactName[0]", hasItem("John Doe"))
                .body("entities.contacts[0].contactPhones.phone[0]", hasItem("123-456-7890"))
                .body("entities.contacts[0].contactPhones.phone[0]", hasItem("111-111-1234"));

    }
}


