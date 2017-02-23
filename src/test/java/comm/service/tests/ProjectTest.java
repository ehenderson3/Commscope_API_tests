package comm.service.tests;

import comm.service.model.Projects;
import comm.service.model.Licensee;
import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import com.jayway.restassured.http.ContentType;
import comm.service.model.RestAssuredConfig;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Random;

public class ProjectTest extends RestAssuredConfig {
    Random rndNum = new Random();
    int randomNumber = rndNum.nextInt(100000);


    @DataProvider(name = "Default Licensee")
    public Object[][] createLicData() {
        return new Object[][]{
                {new Licensee("Official Company"+ randomNumber, "enrique@surgeforwoard.com", "34iij4j4u", "PATH")},
        };
    }

    @DataProvider(name = "Default long")
    public Object[][] createProjectLongData() {
        return new Object[][]{
                {new Projects(33, 129, "The ProJect Ifffffffffrfffffffffffffs Ready1", "PATH")},
        };
    }

    @DataProvider(name = "Default Project")
    public Object[][] createProjectData() {
        return new Object[][]{
                {new Projects(38, 144, "Ilk Is Ready"+randomNumber, "PATH")},
        };
    }

    @DataProvider(name = "Default Project2")
    public Object[][] createProjectData2() {
        return new Object[][]{
                {new Projects(36, 187, "The ProJect Is Ready"+randomNumber, "PATH")},
        };
    }

    @DataProvider(name = "Unique Project Name Test Data")
    public Object[][] createProjectQueryData() {
        return new Object[][]{
                {new Projects(331, 1291, "Kingston11", "PATH")},
                {new Projects(381, 1241, "Kingston11", "PATH")},
        };
    }

    @DataProvider(name = "DefaultGetProjectData")
    public Object[][] getProjectQueryData() {
        return new Object[][]{
                {new Projects(42, 130, "Time Tested"+randomNumber, "PATH")},
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

    int licenseeId = 0;
    int projectId = 0;

    @Test(priority = 0, dataProvider = "Default Licensee")
    public void PostLicensee_ValidRequiredLiceseeInput_LicenseeRecordIsCreated201(Licensee licensee) {

        licenseeId = given()
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
    }

    @Test(dataProvider = "Default Project")
    public void PostProject_ValidRequiredProjectInput_ProjectRecordIsCreated201(Projects projects) {

        projectId = given()
                .contentType(ContentType.JSON)
                .body(projects)
                .when()
                .post("/projects")
                .then()
                .statusCode(201)
                .body("entity.projectName", is(projects.getProjectName()))
                .body("entity.projectType", is(projects.getProjectType()))
                .extract()
                .path("entity.projectId");
    }

    @Test( dataProvider = "Default Project2")
    public void GetProject_ValidExistingProjectRecord_ProjectRecordIsCLocated200(Projects projects) {

        projectId = given()
                .contentType(ContentType.JSON)
                .body(projects)
                .when()
                .post("/projects")
                .then()
                .statusCode(201)
                .body("entity.projectName", is(projects.getProjectName()))
                .body("entity.projectType", is(projects.getProjectType()))
                .extract()
                .path("entity.projectId");


        given()
                .pathParam("projectId", projectId)
                .when()
                .get("/projects/{projectId}")
                .then()
                .statusCode(200)
                .body("entity.projectName", is(projects.getProjectName()))
                .body("entity.projectType", is(projects.getProjectType()));
    }

    @Test(dataProvider = "Default long")
    public void PostProject_ProjectNameMoreTban40_InvalidLength400(Projects projects) {

                 given()
                .contentType(ContentType.JSON)
                .body(projects)
                .when()
                .post("/projects")
                .then()
                .statusCode(400)
                .body("error",equalTo("Bad Request"))
                .body("exception",equalTo("org.springframework.web.bind.MethodArgumentNotValidException" ));
                //.body("errors.codes",equalTo("[[projectName[invalidLength.projectCreateDto.projectName, projectName[invalidLength.projectName, projectName[invalidLength.java.lang.String, projectName[invalidLength]]"));
                //.body("errors.defaultMessage",equalTo("[Project Name cannot exceed 40 characters]"));
    }



    @Test(dataProvider = "Unique Project Name Test Data")
    public void PostProject_ProjectNameNotUnique_SC_CONFLICT(Projects projects) {

        given()
                .contentType(ContentType.JSON)
                .body(projects)
                .when()
                .post("/projects")
                .then()
                .statusCode(HttpStatus.SC_CONFLICT);

    }




    @Test( dataProvider = "DefaultGetProjectData")
    public void GetProject_ValidProject_DefaultGlobalValuesSet(Projects projects) {

        projectId = given()
                .contentType(ContentType.JSON)
                .body(projects)
        .when()
                .post("/projects")
        .then()
                .statusCode(201)
                .body("entity.projectName", is(projects.getProjectName()))
                .body("entity.projectType", equalTo(projects.getProjectType()))
        .extract()
                .path("entity.projectId");


        given()
                .pathParam("projectId", projectId)
                .queryParam("unitType", "US")
        .when()
                .get("/projects/{projectId}")
        .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 ")
                .body("entity.projectName", is(projects.getProjectName()))
                .body("entity.projectType", is(projects.getProjectType()))
                .body("entity.createUser.userId", equalTo(1))
                .body("entity.createUser.userName", equalTo("Lego Admin"))
                .body("entity.unitType", equalTo("US"))
                .body("entity.fresnelZoneRadius", equalTo(60.0f))
                .body("entity.kFactor", equalTo(1.0f))
                .body("entity.minimumClearance", equalTo(0f))
                .body("entity.minimumClearanceUS", equalTo(0f))
                .body("entity.targetAvailability", equalTo(99.995f))
                .body("entity.showSiteLocationDetails", equalTo(false))


        ;

    }



}
