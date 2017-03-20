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
import static comm.service.tests.common.Data.*;
import static org.hamcrest.Matchers.*;

public class ProjectTest extends RestAssuredConfig {
    Random rndNum = new Random();
    int randomNumber = rndNum.nextInt(1000);
    int randomNumber1 = rndNum.nextInt(1000);
    int randomNumber2 = rndNum.nextInt(1000);
    int randomNumber3 = rndNum.nextInt(1000);
    int randomNumber4 = rndNum.nextInt(1000);
    int randomNumber5 = rndNum.nextInt(1000);
    int randomNumber6 = rndNum.nextInt(1000);
    int randomNumber7 = rndNum.nextInt(1000);

    @DataProvider(name = "Default Licensee")
    public Object[][] createLicData() {
        return new Object[][]{
                {new Licensee("Official Company"+ randomNumber1, "enrique@surgeforwoard.com", "34iij4j4u", "PATH")},
        };

    }

    @DataProvider(name = "Default long")
    public Object[][] createProjectLongData() {
        return new Object[][]{
                {new Projects(33, 129, "Default longDefault longDefault longDefault long", "PATH")},
        };
    }

    @DataProvider(name = "Default Project")
    public Object[][] createProjectData() {
        return new Object[][]{
                {new Projects(38, 144, "Default Project"+randomNumber2, "PATH")},
        };
    }

    @DataProvider(name = "Default Project2")
    public Object[][] createProjectData2() {
        return new Object[][]{
                {new Projects(36, 187, "Default Project2aq"+randomNumber4, "PATH")},
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
                {new Projects(42, 130, "DefaultGetProjectData"+randomNumber3, "PATH")},
        };
    }


    @DataProvider(name = "DefaultGetProjectDatah")
    public Object[][] getProjectQueryDatah() {
        return new Object[][]{
                {new Projects(43, 131, "DefaultGetProjectDatah"+randomNumber5, "PATH")},
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

    @Test(dataProvider = "Default Licensee")
    public void PostLicensee_ValidRequiredLiceseeInput_LicenseeRecordIsCreated201(Licensee licensee) {

        licenseeId = given()
                .contentType(ContentType.JSON)
                .body(licensee)
                .when()
                .post("/licensees")
                .then()
                .statusCode(201)
                .statusLine("HTTP/1.1 201 ")
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
                .statusLine("HTTP/1.1 201 ")
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
                .statusLine("HTTP/1.1 201 ")
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
                .statusLine("HTTP/1.1 200 ")
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
                .prettyPeek()
                .then()
                .statusCode(400)
                .statusLine("HTTP/1.1 400 ")
                .body("error",equalTo("Bad Request"))
                .body("exception",equalTo("org.springframework.web.bind.MethodArgumentNotValidException" ))
                .body("errors.defaultMessage", hasItem("Project Name cannot exceed 40 characters"));
    }

    @Test(dataProvider = "Unique Project Name Test Data")
    public void PostProject_ProjectNameNotUnique_SC_CONFLICT(Projects projects) {

            given()
                .contentType(ContentType.JSON)
                .body(projects)
                .when()
                .post("/projects")
                .prettyPeek()
                .then()
                .statusLine("HTTP/1.1 409 ")
                .body("message", is("Duplicate Constraint Error.  Error creating project"));
    }

    @Test( dataProvider = "DefaultGetProjectData")
    public void GetProject_ValidProject_DefaultGlobalValuesSet(Projects projects) {

        projectId = given()
                .contentType(ContentType.JSON)
                .body(projects)
            .when()
                .post("/projects")
                .peek()
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
                .body("entity.showSiteLocationDetails", equalTo(false));

    }

    @Test
    public void GetProject_ValidProject_DefaultGlobalValue() {
        Projects projects = new Projects(42, 130, "DefaultGlobal 3"+randomNumber+randomNumber2, "PATH");

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
                .body("entity.showSiteLocationDetails", equalTo(false));

    }


    @Test
    public void PutProject_EditDefaultToValidFresnelZoneRadius_NewFresnelZoneRadiusSaved() {
        Projects projects = new Projects(42, 130, "FresnelZone 2"+randomNumber7, "PATH");

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

        Map<String,Integer> ProjectMapI = new HashMap<String, Integer>();
        ProjectMapI.put("fresnelZoneRadius",33);

                given()
                .contentType(ContentType.JSON)
                .body(ProjectMapI)
                .pathParam("projectId", projectId)
                .log().all()
            .when()
                .put("/projects/{projectId}")
                .prettyPeek()
            .then()
                .statusCode(200)
                .body("message", is(PROJECT_SUCCESS_MESSAGE));



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
                .body("entity.fresnelZoneRadius", equalTo(33.0f))
                .body("entity.kFactor", equalTo(1.0f))
                .body("entity.minimumClearance", equalTo(0f))
                .body("entity.minimumClearanceUS", equalTo(0f))
                .body("entity.targetAvailability", equalTo(99.995f))
                .body("entity.showSiteLocationDetails", equalTo(false));

    }

    @Test
    public void PutProject_EditAlphaTarget_targetAvailabilityUnrecognizedToken() {
        Projects projects = new Projects(42, 130, "EditAlphaTarget"+randomNumber7, "PATH");

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

        Map<String,String> ProjectMapI = new HashMap<String, String>();
        ProjectMapI.put("targetAvailability","A");

        given()
                .contentType(ContentType.JSON)
                .body(ProjectMapI)
                .pathParam("projectId", projectId)
                .log().all()
                .when()
                .put("/projects/{projectId}")
                .prettyPeek()
                .then()
                .statusCode(400)
                .statusLine("HTTP/1.1 400 ")
                .body("error",is("Bad Request"))
                .body("message", containsString(ERROR_CANNOT_DESERIALIZE_FLOAT+"\"A\": not a valid Float value"));



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
                .body("entity.showSiteLocationDetails", equalTo(false));

    }

    @Test
    public void GetProject_InvalidProjectId_404Error() {

        given()
                .pathParam("projectId", "1234*&^%")
                .queryParam("unitType", "US")
                .when()
                .get("/projects/{projectId}")
                .prettyPeek()
                .then()
                .statusCode(500)
                .statusLine("HTTP/1.1 500 ");
    }

    @Test
    public void PutProject_EditMinimumClearanceWithIllegalAlpha_UnrecognizedToken400() {
        Projects projects = new Projects(42, 130, "EditAlphaminimum9ea95"+randomNumber7, "PATH");

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

        Map<String,String> ProjectMapI = new HashMap<String, String>();
        ProjectMapI.put("fresnelZoneRadius","A");

            given()
                .contentType(ContentType.JSON)
                .body(ProjectMapI)
                .pathParam("projectId", projectId)
                .log().all()
            .when()
                .put("/projects/{projectId}")
                .prettyPeek()
            .then()
                .statusCode(400)
                .statusLine("HTTP/1.1 400 ")
                .body("error",is("Bad Request"))
                .body("message", containsString(ERROR_CANNOT_DESERIALIZE_STRING+"\"A\": not a valid Integer value"));



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
                .body("entity.showSiteLocationDetails", equalTo(false));

    }


    @Test
    public void PutProject_EditMinimumClearanceWithAtSymbol_UnrecognizedToken400() {
        Projects projects = new Projects(42, 130, "EditAlphaminimumClea96"+randomNumber7, "PATH");

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

        Map<String,String> ProjectMapI = new HashMap<String, String>();
        ProjectMapI.put("fresnelZoneRadius","@");



            given()
                .contentType(ContentType.JSON)
                .body(ProjectMapI)
                .pathParam("projectId", projectId)
                .log().all()
            .when()
                .put("/projects/{projectId}")
                .prettyPeek()
            .then()
                .statusCode(400)
                .statusLine("HTTP/1.1 400 ")
                .body("error",is("Bad Request"))
                .body("message", containsString(ERROR_CANNOT_DESERIALIZE_INT +"\"@\": not a valid Integer value"));

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
                .body("entity.showSiteLocationDetails", equalTo(false));

    }

    @Test
    public void PutProject_EditAlphaFresnelZoneRadius_FresnelUnrecognizedToken() {
        Projects projects = new Projects(42, 130, "EditAlphaFresnelZ"+randomNumber7, "PATH");

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

        Map<String,String> ProjectMapI = new HashMap<String, String>();
        ProjectMapI.put("fresnelZoneRadius","A");

        given()
                .contentType(ContentType.JSON)
                .body(ProjectMapI)
                .pathParam("projectId", projectId)
                .log().all()
            .when()
                .put("/projects/{projectId}")
                .prettyPeek()
            .then()
                .statusCode(400)
                .statusLine("HTTP/1.1 400 ")
                .body("error",is("Bad Request"))
                .body("message", containsString(ERROR_CANNOT_DESERIALIZE_INT +"\"A\": not a valid Integer value"));



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
                .body("entity.showSiteLocationDetails", equalTo(false));

    }

    @Test
    public void PutProject_EditDefaultAlphaKFactor_KFactorUnrecognizedToken() {
        Projects projects = new Projects(42, 130, "EditDefaultAlpha"+randomNumber7, "PATH");

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

        Map<String,String> ProjectMapI = new HashMap<String, String>();
        ProjectMapI.put("kFactor","A");

            given()
                .contentType(ContentType.JSON)
                .body(ProjectMapI)
                .pathParam("projectId", projectId)
                .log().all()
            .when()
                .put("/projects/{projectId}")
                .prettyPeek()
            .then()
                .statusCode(400)
                .statusLine("HTTP/1.1 400 ")
                .body("error",is("Bad Request"))
                .body("message", containsString(ERROR_CANNOT_DESERIALIZE_FLOAT +"\"A\""));

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
                .body("entity.showSiteLocationDetails", equalTo(false));

    }

    @Test
    public void PutProject_EditOutsideBouderyHighFresnelZoneRadius_NewFresnelOutOfRangeError() {
        Projects projects = new Projects(42, 130, "FresnelOutOfRangeErrorHigh"+randomNumber1, "PATH");
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

        Map<String,Integer> ProjectMapI = new HashMap<String, Integer>();
        ProjectMapI.put("fresnelZoneRadius",1001);

            given()
                .contentType(ContentType.JSON)
                .body(ProjectMapI)
                .pathParam("projectId", projectId)
                .log().all()
                .when()
                .put("/projects/{projectId}")
                .prettyPeek()
                .then()
                .statusCode(400)
                .statusLine("HTTP/1.1 400 ")
                .body("error",is("Bad Request"))
                .body("errors.defaultMessage", hasItem(ERROR_FRESEL_ZONE_OUT_OF_RANGE));

            given()
                .pathParam("projectId", projectId)
                .when()
                .get("/projects/{projectId}")
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 ")
                .body("entity.projectName", is(projects.getProjectName()))
                .body("entity.projectType", is(projects.getProjectType()))
                .body("entity.createUser.userId", equalTo(1))
                .body("entity.createUser.userName", equalTo("Lego Admin"))
                .body("entity.fresnelZoneRadius", equalTo(60.0f))
                .body("entity.kFactor", equalTo(1.0f))
                .body("entity.minimumClearance", equalTo(0f))
                .body("entity.minimumClearanceUS", equalTo(0f))
                .body("entity.targetAvailability", equalTo(99.995f))
                .body("entity.showSiteLocationDetails", equalTo(false));
    }

    @Test
    public void PutProject_EditFresnelZoneRadiusToNegativeOne_NewFresnelOutOfRangeError() {
        Projects projects = new Projects(42, 130, "OutsideBouderyLowFres"+randomNumber6+randomNumber4, "PATH");
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

        Map<String,Integer> ProjectMapI = new HashMap<String, Integer>();
        ProjectMapI.put("fresnelZoneRadius", -1);

            given()
                .contentType(ContentType.JSON)
                .body(ProjectMapI)
                .pathParam("projectId", projectId)
                .log().all()
                .when()
                .put("/projects/{projectId}")
                .prettyPeek()
                .then()
                .statusCode(400)
                .statusLine("HTTP/1.1 400 ")
                .body("error",is("Bad Request"))
                .body("errors.defaultMessage", hasItem(ERROR_FRESEL_ZONE_OUT_OF_RANGE));

            given()
                .pathParam("projectId", projectId)
                //.queryParam("unitType", "US")
                .when()
                .get("/projects/{projectId}")
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 ")
                .body("entity.projectName", is(projects.getProjectName()))
                .body("entity.projectType", is(projects.getProjectType()))
                .body("entity.createUser.userId", equalTo(1))
                .body("entity.createUser.userName", equalTo("Lego Admin"))
                //.body("entity.unitType", equalTo("US"))
                .body("entity.fresnelZoneRadius", equalTo(60.0f))
                .body("entity.kFactor", equalTo(1.0f))
                .body("entity.minimumClearance", equalTo(0f))
                .body("entity.minimumClearanceUS", equalTo(0f))
                .body("entity.targetAvailability", equalTo(99.995f))
                .body("entity.showSiteLocationDetails", equalTo(false));

    }
    @Test
    public void PutProject_EditKFactorTo001_KFactorOutOfRangeError() {
        Projects projects = new Projects(42, 130, "OutsideBouderyLowKFac"+randomNumber5, "PATH");
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

        Map<String,Double> ProjectMapD = new HashMap<String, Double>();
        ProjectMapD.put("kFactor", -1.0);

            given()
                .contentType(ContentType.JSON)
                .body(ProjectMapD)
                .pathParam("projectId", projectId)
                .log().all()
                .when()
                .put("/projects/{projectId}")
                .prettyPeek()
                .then()
                .statusCode(400)
                .statusLine("HTTP/1.1 400 ")
                .body("error",is("Bad Request"))
                .body("errors.defaultMessage", hasItem(ERROR_K_FACTOR_OUT_OF_RANGE));

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
                .body("entity.showSiteLocationDetails", equalTo(false));

    }

    @Test
    public void PutProject_EditKFactorToPoint001_KFactorOutOfRangeError() {
        Projects projects = new Projects(42, 130, "BouderyHighKFactorA"+randomNumber6, "PATH");
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

        Map<String,Double> ProjectMapD = new HashMap<String, Double>();
        ProjectMapD.put("kFactor", 0.001);

            given()
                .contentType(ContentType.JSON)
                .body(ProjectMapD)
                .pathParam("projectId", projectId)
                .log().all()
                .when()
                .put("/projects/{projectId}")
                .prettyPeek()
                .then()
                .statusCode(400)
                .statusLine("HTTP/1.1 400 ")
                .body("error",is("Bad Request"))
                .body("errors.defaultMessage", hasItem(ERROR_K_FACTOR_OUT_OF_RANGE));

            given()
                .pathParam("projectId", projectId)
                //.queryParam("unitType", "US")
                .when()
                .get("/projects/{projectId}")
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 ")
                .body("entity.projectName", is(projects.getProjectName()))
                .body("entity.projectType", is(projects.getProjectType()))
                .body("entity.createUser.userId", equalTo(1))
                .body("entity.createUser.userName", equalTo("Lego Admin"))
                //.body("entity.unitType", equalTo("US"))
                .body("entity.fresnelZoneRadius", equalTo(60.0f))
                .body("entity.kFactor", equalTo(1.0f))
                .body("entity.minimumClearance", equalTo(0f))
                .body("entity.minimumClearanceUS", equalTo(0f))
                .body("entity.targetAvailability", equalTo(99.995f))
                .body("entity.showSiteLocationDetails", equalTo(false));

    }

    @Test
    public void PutProject_EditKFactorTo20_KFactorEditSaved() {
        Projects projects = new Projects(42, 130, "hInBouderyKFact"+randomNumber7, "PATH");

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

        Map<String,Integer> ProjectMapI = new HashMap<String, Integer>();
        ProjectMapI.put("kFactor", 20);

            given()
                .contentType(ContentType.JSON)
                .body(ProjectMapI)
                .pathParam("projectId", projectId)
                .log().all()
                .when()
                .put("/projects/{projectId}")
                .prettyPeek()
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 ");

            given()
                .pathParam("projectId", projectId)
                .when()
                .get("/projects/{projectId}")
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 ")
                .body("entity.projectName", is(projects.getProjectName()))
                .body("entity.projectType", is(projects.getProjectType()))
                .body("entity.createUser.userId", equalTo(1))
                .body("entity.createUser.userName", equalTo("Lego Admin"))
                //.body("entity.unitType", equalTo("US"))
                .body("entity.fresnelZoneRadius", equalTo(60.0f))
                .body("entity.kFactor", equalTo(20.0f))
                .body("entity.minimumClearance", equalTo(0f))
                .body("entity.minimumClearanceUS", equalTo(0f))
                .body("entity.targetAvailability", equalTo(99.995f))
                .body("entity.showSiteLocationDetails", equalTo(false));

    }



    @Test
    public void PutProject_EditMinimumClearanceToNegative1000_MinimumClearanceOutOfRangeError() {
        Projects projects = new Projects(42, 130, "Time Tested 82S"+randomNumber3, "PATH");
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

        Map<String,Integer> ProjectMapI = new HashMap<String, Integer>();
        ProjectMapI.put("minimumClearance", -1001);

            given()
                .contentType(ContentType.JSON)
                .body(ProjectMapI)
                .pathParam("projectId", projectId)
                .log().all()
                .when()
                .put("/projects/{projectId}")
                .prettyPeek()
                .then()
                .statusCode(400)
                .statusLine("HTTP/1.1 400 ")
                .body("error",is("Bad Request"))
                .body("errors.defaultMessage", hasItem(ERROR_MIN_CLEARANCE_OUT_OF_RANGE));

            given()
                .pathParam("projectId", projectId)
                //.queryParam("unitType", "US")
                .when()
                .get("/projects/{projectId}")
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 ")
                .body("entity.projectName", is(projects.getProjectName()))
                .body("entity.projectType", is(projects.getProjectType()))
                .body("entity.createUser.userId", equalTo(1))
                .body("entity.createUser.userName", equalTo("Lego Admin"))
                //.body("entity.unitType", equalTo("US"))
                .body("entity.fresnelZoneRadius", equalTo(60.0f))
                .body("entity.kFactor", equalTo(1.0f))
                .body("entity.minimumClearance", equalTo(0f))
                .body("entity.minimumClearanceUS", equalTo(0f))
                .body("entity.targetAvailability", equalTo(99.995f))
                .body("entity.showSiteLocationDetails", equalTo(false));

    }

    @Test
    public void PutProject_EditOutsideBouderyHighMinimumClearance_MinimumClearanceOutOfRange() {
        Projects projects = new Projects(42, 130, "HighMinimumClearance"+randomNumber2, "PATH");
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

        Map<String,Integer> ProjectMapI = new HashMap<String, Integer>();
        ProjectMapI.put("minimumClearance", 1001);

            given()
                .contentType(ContentType.JSON)
                .body(ProjectMapI)
                .pathParam("projectId", projectId)
                .log().all()
                .when()
                .put("/projects/{projectId}")
                .prettyPeek()
                .then()
                .statusCode(400)
                .statusLine("HTTP/1.1 400 ")
                .body("error",is("Bad Request"))
                .body("errors.defaultMessage", hasItem(ERROR_MIN_CLEARANCE_OUT_OF_RANGE));

            given()
                .pathParam("projectId", projectId)
                //.queryParam("unitType", "US")
                .when()
                .get("/projects/{projectId}")
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 ")
                .body("entity.projectName", is(projects.getProjectName()))
                .body("entity.projectType", is(projects.getProjectType()))
                .body("entity.createUser.userId", equalTo(1))
                .body("entity.createUser.userName", equalTo("Lego Admin"))
                //.body("entity.unitType", equalTo("US"))
                .body("entity.fresnelZoneRadius", equalTo(60.0f))
                .body("entity.kFactor", equalTo(1.0f))
                .body("entity.minimumClearance", equalTo(0f))
                .body("entity.minimumClearanceUS", equalTo(0f))
                .body("entity.targetAvailability", equalTo(99.995f))
                .body("entity.showSiteLocationDetails", equalTo(false));

    }
//TODO UI does not agree with API on US threshold 1001 should be accepted https://www.screencast.com/t/6QzlpNGj
    @Test
    public void PutProject_EditUsOutsideBouderyHighMinimumClearance_MinimumClearanceOutOfRange() {
        Projects projects = new Projects(42, 130, "HighMinimumClearance"+randomNumber3+randomNumber7, "PATH");
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

        Map<String,String> ProjectMapS = new HashMap<String, String>();
        Map<String,Integer> ProjectMapI = new HashMap<String, Integer>();
        ProjectMapI.put("minimumClearance", 1001);
        ProjectMapS.put("unitType", "US");

            given()
                .contentType(ContentType.JSON)
                .body(ProjectMapS)
                .body(ProjectMapI)
                .pathParam("projectId", projectId)
                .log().all()
                .when()
                .put("/projects/{projectId}")
                .prettyPeek()
                .then()
                .statusCode(400)
                .statusLine("HTTP/1.1 400 ")
                .body("error",is("Bad Request"))
                .body("errors.defaultMessage", hasItem(ERROR_MIN_CLEARANCE_OUT_OF_RANGE));

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
                .body("entity.showSiteLocationDetails", equalTo(false));

    }

    @Test
    public void PutProject_EditWithInBouderyMinimumClearance_MinimumClearanceSaved() {
        Projects projects = new Projects(42, 130, "InBouderyMinimumClea"+randomNumber1, "PATH");
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

        Map<String,Integer> ProjectMapI = new HashMap<String, Integer>();
        ProjectMapI.put("minimumClearance", 1000);

            given()
                .contentType(ContentType.JSON)
                .body(ProjectMapI)
                .pathParam("projectId", projectId)
                .log().all()
                .when()
                .put("/projects/{projectId}")
                .prettyPeek()
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 ");

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
                .body("entity.minimumClearance", equalTo(1000f))
                .body("entity.minimumClearanceUS", equalTo(3280.84f))
                .body("entity.targetAvailability", equalTo(99.995f))
                .body("entity.showSiteLocationDetails", equalTo(false));

    }
    @Test
    public void PutProject_EditShowSiteFalseLocationDetails_ShowSiteLocationDetailsSaved() {
        Projects projects = new Projects(42, 130, "ShowSiteFalseLocat"+randomNumber2, "PATH");
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

        Map<String,Boolean> ProjectMapI = new HashMap<String, Boolean>();
        ProjectMapI.put("showSiteLocationDetails", false);

            given()
                .contentType(ContentType.JSON)
                .body(ProjectMapI)
                .pathParam("projectId", projectId)
                .log().all()
                .when()
                .put("/projects/{projectId}")
                .prettyPeek()
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 ")
                .body("message", is(PROJECT_SUCCESS_MESSAGE));


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
                .body("entity.showSiteLocationDetails", equalTo(false));

    }
    @Test
    public void PutProject_EditShowSiteTrueLocationDetails_ShowSiteLocationDetailsSaved() {
        Projects projects = new Projects(42, 130, "ShowSiteTrueLocatAB"+randomNumber6, "PATH");
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

        Map<String,Boolean> ProjectMapI = new HashMap<String, Boolean>();
        ProjectMapI.put("showSiteLocationDetails", true);

            given()
                .contentType(ContentType.JSON)
                .body(ProjectMapI)
                .pathParam("projectId", projectId)
                .log().all()
                .when()
                .put("/projects/{projectId}")
                .prettyPeek()
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 ")
                .body("message", is(PROJECT_SUCCESS_MESSAGE));


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
                .body("entity.showSiteLocationDetails", equalTo(true));

    }
    @Test
    public void PutProject_EditOutsideBounderyLowTargetAvailabilityPercent_TargetAvailabilityPercentOutOfRangeError() {
        Projects projects = new Projects(42, 130, "OutsideBounderyLowTarget"+randomNumber7, "PATH");
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

        Map<String,Integer> ProjectMapI = new HashMap<String, Integer>();
        ProjectMapI.put("targetAvailability", 49);

            given()
                .contentType(ContentType.JSON)
                .body(ProjectMapI)
                .pathParam("projectId", projectId)
                .log().all()
                .when()
                .put("/projects/{projectId}")
                .prettyPeek()
                .then()
                .statusCode(400)
                .statusLine("HTTP/1.1 400 ")
                .body("error",is("Bad Request"))
                .body("errors.defaultMessage", hasItem(ERROR_TARGET_AVAILABILITY_OUT_OF_RANGE));

            given()
                .pathParam("projectId", projectId)
                //.queryParam("unitType", "US")
                .when()
                .get("/projects/{projectId}")
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 ")
                .body("entity.projectName", is(projects.getProjectName()))
                .body("entity.projectType", is(projects.getProjectType()))
                .body("entity.createUser.userId", equalTo(1))
                .body("entity.createUser.userName", equalTo("Lego Admin"))
                //.body("entity.unitType", equalTo("US"))
                .body("entity.fresnelZoneRadius", equalTo(60.0f))
                .body("entity.kFactor", equalTo(1.0f))
                .body("entity.minimumClearance", equalTo(0f))
                .body("entity.minimumClearanceUS", equalTo(0f))
                .body("entity.targetAvailability", equalTo(99.995f))
                .body("entity.showSiteLocationDetails", equalTo(false));

    }
    @Test
    public void PutProject_EditOutsideBounderyHighTargetAvailabilityPercent_TargetAvailabilityPercentOutOfRangeError() {
        Projects projects = new Projects(42, 130, "OutsideBounderyHighTarg"+randomNumber2, "PATH");
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

        Map<String,Integer> ProjectMapI = new HashMap<String, Integer>();
        ProjectMapI.put("targetAvailability", 101);

            given()
                .contentType(ContentType.JSON)
                .body(ProjectMapI)
                .pathParam("projectId", projectId)
                .log().all()
                .when()
                .put("/projects/{projectId}")
                .prettyPeek()
                .then()
                .statusCode(400)
                .statusLine("HTTP/1.1 400 ")
                .body("error",is("Bad Request"))
                .body("errors.defaultMessage", hasItem(ERROR_TARGET_AVAILABILITY_OUT_OF_RANGE));

            given()
                .pathParam("projectId", projectId)
                //.queryParam("unitType", "US")
                .when()
                .get("/projects/{projectId}")
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 ")
                .body("entity.projectName", is(projects.getProjectName()))
                .body("entity.projectType", is(projects.getProjectType()))
                .body("entity.createUser.userId", equalTo(1))
                .body("entity.createUser.userName", equalTo("Lego Admin"))
                //.body("entity.unitType", equalTo("US"))
                .body("entity.fresnelZoneRadius", equalTo(60.0f))
                .body("entity.kFactor", equalTo(1.0f))
                .body("entity.minimumClearance", equalTo(0f))
                .body("entity.minimumClearanceUS", equalTo(0f))
                .body("entity.targetAvailability", equalTo(99.995f))
                .body("entity.showSiteLocationDetails", equalTo(false));

    }

    @Test
    public void PutProject_EditWithInBounderyTargetAvailabilityPercent_TargetAvailabilityPercentSaved() {
        Projects projects = new Projects(42, 130, "InBounderyTargetAvailABCD"+randomNumber6, "PATH");
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

        Map<String,Integer> ProjectMapI = new HashMap<String, Integer>();
        ProjectMapI.put("targetAvailability", 50);

            given()
                .contentType(ContentType.JSON)
                .body(ProjectMapI)
                .pathParam("projectId", projectId)
                .log().all()
                .when()
                .put("/projects/{projectId}")
                .prettyPeek()
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 ")
                .body("message", is(PROJECT_SUCCESS_MESSAGE));



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
                .body("entity.targetAvailability", equalTo(50f))
                .body("entity.showSiteLocationDetails", equalTo(false));

    }
    @Test
    public void PutProject_EditWithUnitType_UnitTypeSaved() {
        Projects projects = new Projects(42, 130, "ct_EditWithUnitTypABCDE"+randomNumber6, "PATH");
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

        Map<String,String> ProjectMapI = new HashMap<String, String>();
        ProjectMapI.put("unitType", "SI");

            given()
                .contentType(ContentType.JSON)
                .body(ProjectMapI)
                .pathParam("projectId", projectId)
                .log().all()
                .when()
                .put("/projects/{projectId}")
                .prettyPeek()
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 ")
                .body("message", is(PROJECT_SUCCESS_MESSAGE));



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
                .body("entity.unitType", equalTo("SI"))
                .body("entity.fresnelZoneRadius", equalTo(60.0f))
                .body("entity.kFactor", equalTo(1.0f))
                .body("entity.minimumClearance", equalTo(0f))
                .body("entity.minimumClearanceUS", equalTo(0f))
                .body("entity.targetAvailability", equalTo(99.995f))
                .body("entity.showSiteLocationDetails", equalTo(false));

    }


}
