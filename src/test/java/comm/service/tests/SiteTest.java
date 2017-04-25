package comm.service.tests;

import com.jayway.restassured.http.ContentType;
import comm.service.model.CompanyModel;
import comm.service.model.Projects;
import comm.service.model.RestAssuredConfig;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class SiteTest extends RestAssuredConfig {
    Random rndNum = new Random();
    int randomNumber = rndNum.nextInt(1000);
    int pathId = 0;
    String siteName1 = null;

    @DataProvider(name = "Default Licensee")
    public Object[][] createLicData() {
        return new Object[][]{
                {new CompanyModel("Official Company"+ randomNumber, "enrique@surgeforwoard.com", "34iij4j4u", "PATH")},
        };
    }

    int projectId = 0;

    @Test
    public void getSingleSiteSearch_SearchCriteriaSiteNameuniqueSite100_ResultsetContainsSiteNameuniqueSite100(){
        Projects project = new Projects(32, 1, "SearchCriteriaSiteNameuniqueSite100 " + randomNumber, "PATH");

        projectId = given()
                .contentType(ContentType.JSON)
                .body(project)
                .when()
                .post("/projects")
                .then()
                .statusCode(201)
                .body("entity.projectName", is(project.getProjectName()))
                .body("entity.projectType", equalTo(project.getProjectType()))
                .extract()
                .path("entity.projectId");

        JSONObject pathDetailsJson = new JSONObject();
        JSONArray segmentsArray = new JSONArray();
        JSONArray segmentEndsArray = new JSONArray();
        JSONObject segmentEndJson1 = new JSONObject();
        JSONObject segmentEndJson2 = new JSONObject();
        JSONObject segment = new JSONObject();

        try {
            segmentEndJson1.put("callSign", "KA2071");
            segmentEndJson1.put("elevation", 11.11);
            segmentEndJson1.put("latitude", 71);
            segmentEndJson1.put("longitude", 22.22);
            segmentEndJson1.put("siteName", "uniqueSite100");

            segmentEndJson2.put("asr", "1000037");
            segmentEndJson2.put("elevation", 12.12);
            segmentEndJson2.put("latitude", 80);
            segmentEndJson2.put("longitude", 23.23);
            segmentEndJson2.put("siteName", "uniqueSite101");

            pathDetailsJson.put("projectId", projectId);
            pathDetailsJson.put("bandId", 2);
            pathDetailsJson.put("pathName", "Test Path 1");

            segmentEndsArray.put(segmentEndJson1);
            segmentEndsArray.put(segmentEndJson2);

            segment.put("segmentEnds", segmentEndsArray);
            segmentsArray.put(segment);
            pathDetailsJson.put("segments", segmentsArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("jsonPath = " + pathDetailsJson);

        pathId = given()
                .contentType(ContentType.JSON)
                .body(pathDetailsJson.toString())
                .when()
                .post("/paths")
                .prettyPeek()
                .then()
                .extract()
                .path("entity.pathId");

        String valueVal = "uniqueSite100";
        String fieldVal = "siteName";
        String operatorVal = "EQ";

        given().urlEncodingEnabled(false)
                .when()
                .log().all()
                .get("/sites?search=%5B%7B" +
                        "%22value%22%3A%20%22"+ valueVal + "%22%2C%20" +
                        "%22field%22%3A%20%22"+ fieldVal + "%22%2C%20" +
                        "%22operator%22%3A%20%22" + operatorVal + "%22%7D%5D")
                .prettyPeek()
                .then()
                .body("entities.siteName", hasItem("uniqueSite100"))
                .body("entities.callSign", hasItem("KA2071"))
                .body("entities.latitude", hasItem(71.0f))
                .body("entities.longitude", hasItem(22.22f))
                .body("entities.elevation", hasItem(11.11f))
                .body("entities.elevationUS", hasItem(36.45f));

        DeletePath_CleanUp(pathId);

    }


    @Test
    public void getSingleSiteSearch_SearchCriteriaCallSignKA2071_ResultsetContainsCallSignKA2071(){
        Projects project = new Projects(32, 1, "SearchCriteriaCallSignKA2071 " + randomNumber, "PATH");

        projectId = given()
                .contentType(ContentType.JSON)
                .body(project)
                .when()
                .post("/projects")
                .then()
                .statusCode(201)
                .body("entity.projectName", is(project.getProjectName()))
                .body("entity.projectType", equalTo(project.getProjectType()))
                .extract()
                .path("entity.projectId");

        JSONObject pathDetailsJson = new JSONObject();
        JSONArray segmentsArray = new JSONArray();
        JSONArray segmentEndsArray = new JSONArray();
        JSONObject segmentEndJson1 = new JSONObject();
        JSONObject segmentEndJson2 = new JSONObject();
        JSONObject segment = new JSONObject();

        try {
            segmentEndJson1.put("callSign", "KA2071");
            segmentEndJson1.put("elevation", 11.11);
            segmentEndJson1.put("latitude", 71);
            segmentEndJson1.put("longitude", 22.22);
            segmentEndJson1.put("siteName", "uniqueSite100");

            segmentEndJson2.put("asr", "1000037");
            segmentEndJson2.put("elevation", 12.12);
            segmentEndJson2.put("latitude", 80);
            segmentEndJson2.put("longitude", 23.23);
            segmentEndJson2.put("siteName", "uniqueSite101");

            pathDetailsJson.put("projectId", projectId);
            pathDetailsJson.put("bandId", 2);
            pathDetailsJson.put("pathName", "Test Path 1");

            segmentEndsArray.put(segmentEndJson1);
            segmentEndsArray.put(segmentEndJson2);

            segment.put("segmentEnds", segmentEndsArray);
            segmentsArray.put(segment);
            pathDetailsJson.put("segments", segmentsArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("jsonPath = " + pathDetailsJson);

        pathId = given()
                .contentType(ContentType.JSON)
                .body(pathDetailsJson.toString())
                .when()
                .post("/paths")
                .prettyPeek()
                .then()
                .extract()
                .path("entity.pathId");

        String valueVal = "KA2071";
        String fieldVal = "callsign";
        String operatorVal = "EQ";

        given().urlEncodingEnabled(false)
                .when()
                .log().all()
                .get("/sites?search=%5B%7B" +
                        "%22value%22%3A%20%22"+ valueVal + "%22%2C%20" +
                        "%22field%22%3A%20%22"+ fieldVal + "%22%2C%20" +
                        "%22operator%22%3A%20%22" + operatorVal + "%22%7D%5D")
                .prettyPeek()
                .then()
                .body("entities.siteName", hasItem("uniqueSite100"))
                .body("entities.callSign", hasItem("KA2071"))
                .body("entities.latitude", hasItem(71.0f))
                .body("entities.longitude", hasItem(22.22f))
                .body("entities.elevation", hasItem(11.11f))
                .body("entities.elevationUS", hasItem(36.45f));

        DeletePath_CleanUp(pathId);
    }

    @Test
    public void getSingleSiteSearch_SearchCriteriaASR1000037_ResultsetContainsASR1000037(){
        Projects project = new Projects(32, 5, "SearchCriteriaASR1000037 " + randomNumber, "PATH");

        projectId = given()
                .contentType(ContentType.JSON)
                .body(project)
                .when()
                .post("/projects")
                .then()
                .statusCode(201)
                .body("entity.projectName", is(project.getProjectName()))
                .body("entity.projectType", equalTo(project.getProjectType()))
                .extract()
                .path("entity.projectId");

        JSONObject pathDetailsJson = new JSONObject();
        JSONArray segmentsArray = new JSONArray();
        JSONArray segmentEndsArray = new JSONArray();
        JSONObject segmentEndJson1 = new JSONObject();
        JSONObject segmentEndJson2 = new JSONObject();
        JSONObject segment = new JSONObject();

        try {
            segmentEndJson1.put("callSign", "KA2071");
            segmentEndJson1.put("elevation", 11.11);
            segmentEndJson1.put("latitude", 71);
            segmentEndJson1.put("longitude", 22.22);
            segmentEndJson1.put("siteName", "SearchCriteriaASR100");

            segmentEndJson2.put("asr", "1000037");
            segmentEndJson2.put("elevation", 12.12);
            segmentEndJson2.put("latitude", 80);
            segmentEndJson2.put("longitude", 23.23);
            segmentEndJson2.put("siteName", "SearchCriteriaASR101");

            pathDetailsJson.put("projectId", projectId);
            pathDetailsJson.put("bandId", 2);
            pathDetailsJson.put("pathName", "SearchCriteriaASR Path");

            segmentEndsArray.put(segmentEndJson1);
            segmentEndsArray.put(segmentEndJson2);

            segment.put("segmentEnds", segmentEndsArray);
            segmentsArray.put(segment);
            pathDetailsJson.put("segments", segmentsArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("jsonPath = " + pathDetailsJson);

        pathId = given()
                .contentType(ContentType.JSON)
                .body(pathDetailsJson.toString())
                .when()
                .post("/paths")
                .prettyPeek()
                .then()
                .extract()
                .path("entity.pathId");

        String valueVal = "1000037";
        String fieldVal = "asr";
        String operatorVal = "EQ";

        given().urlEncodingEnabled(false)
                .when()
                .log().all()
                .get("/sites?search=%5B%7B" +
                        "%22value%22%3A%20%22"+ valueVal + "%22%2C%20" +
                        "%22field%22%3A%20%22"+ fieldVal + "%22%2C%20" +
                        "%22operator%22%3A%20%22" + operatorVal + "%22%7D%5D")
                .prettyPeek()
                .then()
                .body("entities.siteName", hasItem("SearchCriteriaASR101"))
                .body("entities.asr", hasItem(1000037))
                .body("entities.latitude", hasItem(80.0f))
                .body("entities.longitude", hasItem(23.23f))
                .body("entities.elevation", hasItem(12.12f))
                .body("entities.elevationUS", hasItem(39.76f));

        DeletePath_CleanUp(pathId);
    }

    @Test
    public void getSingleSiteSearch_SearchCriteriaLatitude88_ResultsetContainsLatitude88(){
        Projects project = new Projects(32, 1, "SearchCriteriaLatitude88 " + randomNumber, "PATH");

        projectId = given()
                .contentType(ContentType.JSON)
                .body(project)
                .when()
                .post("/projects")
                .then()
                .statusCode(201)
                .body("entity.projectName", is(project.getProjectName()))
                .body("entity.projectType", equalTo(project.getProjectType()))
                .extract()
                .path("entity.projectId");

        JSONObject pathDetailsJson = new JSONObject();
        JSONArray segmentsArray = new JSONArray();
        JSONArray segmentEndsArray = new JSONArray();
        JSONObject segmentEndJson1 = new JSONObject();
        JSONObject segmentEndJson2 = new JSONObject();
        JSONObject segment = new JSONObject();

        try {
            segmentEndJson1.put("callSign", "KA2071");
            segmentEndJson1.put("elevation", 11.11);
            segmentEndJson1.put("latitude", 88);
            segmentEndJson1.put("longitude", 22.22);
            segmentEndJson1.put("siteName", "SearchLatitude88");

            segmentEndJson2.put("asr", "1000037");
            segmentEndJson2.put("elevation", 12.12);
            segmentEndJson2.put("latitude", 88);
            segmentEndJson2.put("longitude", 23.23);
            segmentEndJson2.put("siteName", "SearchLatitude88b");

            pathDetailsJson.put("projectId", projectId);
            pathDetailsJson.put("bandId", 2);
            pathDetailsJson.put("pathName", "SearchLatitude88 Path");

            segmentEndsArray.put(segmentEndJson1);
            segmentEndsArray.put(segmentEndJson2);

            segment.put("segmentEnds", segmentEndsArray);
            segmentsArray.put(segment);
            pathDetailsJson.put("segments", segmentsArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("jsonPath = " + pathDetailsJson);

        pathId = given()
                .contentType(ContentType.JSON)
                .body(pathDetailsJson.toString())
                .when()
                .post("/paths")
                .prettyPeek()
                .then()
                .extract()
                .path("entity.pathId");

        String valueVal = "88";
        String fieldVal = "latitude";
        String operatorVal = "EQ";

        given().urlEncodingEnabled(false)
                .when()
                .log().all()
                .get("/sites?search=%5B%7B" +
                        "%22value%22%3A%20%22"+ valueVal + "%22%2C%20" +
                        "%22field%22%3A%20%22"+ fieldVal + "%22%2C%20" +
                        "%22operator%22%3A%20%22" + operatorVal + "%22%7D%5D")
                .prettyPeek()
                .then()
                .body("entities.siteName", hasItem("SearchLatitude88b"))
                .body("entities.asr", hasItem(1000037))
                .body("entities.latitude", hasItem(88.0f))
                .body("entities.longitude", hasItem(23.23f))
                .body("entities.elevation", hasItem(12.12f))
                .body("entities.elevationUS", hasItem(39.76f));

        DeletePath_CleanUp(pathId);
    }

    @Test
    public void getSingleSiteSearch_SearchCriteriaLongitude3322_ResultsetContainsLongitude3322(){
        Projects project = new Projects(32, 1, "SearchCriteriaLongitude3322 " + randomNumber, "PATH");

        projectId = given()
                .contentType(ContentType.JSON)
                .body(project)
                .when()
                .post("/projects")
                .then()
                .statusCode(201)
                .body("entity.projectName", is(project.getProjectName()))
                .body("entity.projectType", equalTo(project.getProjectType()))
                .extract()
                .path("entity.projectId");

        JSONObject pathDetailsJson = new JSONObject();
        JSONArray segmentsArray = new JSONArray();
        JSONArray segmentEndsArray = new JSONArray();
        JSONObject segmentEndJson1 = new JSONObject();
        JSONObject segmentEndJson2 = new JSONObject();
        JSONObject segment = new JSONObject();

        try {
            segmentEndJson1.put("callSign", "KA2071");
            segmentEndJson1.put("elevation", 11.11);
            segmentEndJson1.put("latitude", 88);
            segmentEndJson1.put("longitude", 22.22);
            segmentEndJson1.put("siteName", "SearchLongitude3322");

            segmentEndJson2.put("asr", "1000037");
            segmentEndJson2.put("elevation", 12.12);
            segmentEndJson2.put("latitude", 88);
            segmentEndJson2.put("longitude", 33.22);
            segmentEndJson2.put("siteName", "SearchLongitude3322b");

            pathDetailsJson.put("projectId", projectId);
            pathDetailsJson.put("bandId", 2);
            pathDetailsJson.put("pathName", "SearchLongitude3322 Path");

            segmentEndsArray.put(segmentEndJson1);
            segmentEndsArray.put(segmentEndJson2);

            segment.put("segmentEnds", segmentEndsArray);
            segmentsArray.put(segment);
            pathDetailsJson.put("segments", segmentsArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("jsonPath = " + pathDetailsJson);

        pathId = given()
                .contentType(ContentType.JSON)
                .body(pathDetailsJson.toString())
                .when()
                .post("/paths")
                .prettyPeek()
                .then()
                .extract()
                .path("entity.pathId");

        String valueVal = "33.22";
        String fieldVal = "longitude";
        String operatorVal = "EQ";

        given().urlEncodingEnabled(false)
                .when()
                .log().all()
                .get("/sites?search=%5B%7B" +
                        "%22value%22%3A%20%22"+ valueVal + "%22%2C%20" +
                        "%22field%22%3A%20%22"+ fieldVal + "%22%2C%20" +
                        "%22operator%22%3A%20%22" + operatorVal + "%22%7D%5D")
                .prettyPeek()
                .then()
                .body("entities.siteName", hasItem("SearchLongitude3322b"))
                .body("entities.asr", hasItem(1000037))
                .body("entities.latitude", hasItem(88.0f))
                .body("entities.longitude", hasItem(33.22f))
                .body("entities.elevation", hasItem(12.12f))
                .body("entities.elevationUS", hasItem(39.76f));

        DeletePath_CleanUp(pathId);
    }

    @Test
    public void getSingleSiteSearch_SearchCriteriaCompanyId5_ResultsetContainsCompanyId5(){
        Projects project = new Projects(32, 1, "SearchCriteriaCompanyId5 " + randomNumber, "PATH");

        projectId = given()
                .contentType(ContentType.JSON)
                .body(project)
                .when()
                .post("/projects")
                .then()
                .statusCode(201)
                .body("entity.projectName", is(project.getProjectName()))
                .body("entity.projectType", equalTo(project.getProjectType()))
                .extract()
                .path("entity.projectId");

        JSONObject pathDetailsJson = new JSONObject();
        JSONArray segmentsArray = new JSONArray();
        JSONArray segmentEndsArray = new JSONArray();
        JSONObject segmentEndJson1 = new JSONObject();
        JSONObject segmentEndJson2 = new JSONObject();
        JSONObject segment = new JSONObject();

        try {
            segmentEndJson1.put("companyId", "1");

            segmentEndJson1.put("callSign", "KA2071");
            segmentEndJson1.put("elevation", 11.11);
            segmentEndJson1.put("latitude", 88);
            segmentEndJson1.put("longitude", 22.22);
            segmentEndJson1.put("siteName", "SearchCompanyId1");

            segmentEndJson2.put("asr", "1000037");
            segmentEndJson2.put("elevation", 12.12);
            segmentEndJson2.put("latitude", 88);
            segmentEndJson2.put("longitude", 33.22);
            segmentEndJson2.put("siteName", "SearchCompanyId1b");

            pathDetailsJson.put("projectId", projectId);
            pathDetailsJson.put("bandId", 2);
            pathDetailsJson.put("pathName", "SearchCompanyId1 Path");

            segmentEndsArray.put(segmentEndJson1);
            segmentEndsArray.put(segmentEndJson2);

            segment.put("segmentEnds", segmentEndsArray);
            segmentsArray.put(segment);
            pathDetailsJson.put("segments", segmentsArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("jsonPath = " + pathDetailsJson);

        pathId = given()
                .contentType(ContentType.JSON)
                .body(pathDetailsJson.toString())
                .when()
                .post("/paths")
                .prettyPeek()
                .then()
                .extract()
                .path("entity.pathId");

        String valueVal = "1";
        String fieldVal = "companyId";
        String operatorVal = "EQ";

        given().urlEncodingEnabled(false)
                .when()
                .log().all()
                .get("/sites?search=%5B%7B" +
                        "%22value%22%3A%20%22"+ valueVal + "%22%2C%20" +
                        "%22field%22%3A%20%22"+ fieldVal + "%22%2C%20" +
                        "%22operator%22%3A%20%22" + operatorVal + "%22%7D%5D")
                .prettyPeek()
                .then()
                .body("entities.siteName", hasItem("SearchCompanyId1"))
                .body("entities.company.companyId", hasItem(1))
                .body("entities.callSign", hasItem("KA2071"))
                .body("entities.latitude", hasItem(88.0f))
                .body("entities.longitude", hasItem(22.22f))
                .body("entities.elevation", hasItem(11.11f))
                .body("entities.elevationUS", hasItem(36.45f));;

        DeletePath_CleanUp(pathId);
    }

    @Test
    public void getSingleSiteSearch_SearchCriteriaSiteNameLIKEuniqueSite100_ResultsetContainsSiteNameuniqueSite100(){
        Projects project = new Projects(32, 1, "SearchCriteriaLIKESiteNameunique " + randomNumber, "PATH");

        projectId = given()
                .contentType(ContentType.JSON)
                .body(project)
                .when()
                .post("/projects")
                .then()
                .statusCode(201)
                .body("entity.projectName", is(project.getProjectName()))
                .body("entity.projectType", equalTo(project.getProjectType()))
                .extract()
                .path("entity.projectId");

        JSONObject pathDetailsJson = new JSONObject();
        JSONArray segmentsArray = new JSONArray();
        JSONArray segmentEndsArray = new JSONArray();
        JSONObject segmentEndJson1 = new JSONObject();
        JSONObject segmentEndJson2 = new JSONObject();
        JSONObject segment = new JSONObject();

        try {
            segmentEndJson1.put("callSign", "KA2071");
            segmentEndJson1.put("elevation", 11.11);
            segmentEndJson1.put("latitude", 71);
            segmentEndJson1.put("longitude", 22.22);
            segmentEndJson1.put("siteName", "uniqueSite100");

            segmentEndJson2.put("asr", "1000037");
            segmentEndJson2.put("elevation", 12.12);
            segmentEndJson2.put("latitude", 80);
            segmentEndJson2.put("longitude", 23.23);
            segmentEndJson2.put("siteName", "uniqueSite101");

            pathDetailsJson.put("projectId", projectId);
            pathDetailsJson.put("bandId", 2);
            pathDetailsJson.put("pathName", "Test Path 1");

            segmentEndsArray.put(segmentEndJson1);
            segmentEndsArray.put(segmentEndJson2);

            segment.put("segmentEnds", segmentEndsArray);
            segmentsArray.put(segment);
            pathDetailsJson.put("segments", segmentsArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("jsonPath = " + pathDetailsJson);

        pathId = given()
                .contentType(ContentType.JSON)
                .body(pathDetailsJson.toString())
                .when()
                .post("/paths")
                .prettyPeek()
                .then()
                .extract()
                .path("entity.pathId");

        String valueVal = "uniqueSite100";
        String fieldVal = "siteName";
        String operatorVal = "LIKE";

        given().urlEncodingEnabled(false)
                .when()
                .log().all()
                .get("/sites?search=%5B%7B" +
                        "%22value%22%3A%20%22"+ valueVal + "%22%2C%20" +
                        "%22field%22%3A%20%22"+ fieldVal + "%22%2C%20" +
                        "%22operator%22%3A%20%22" + operatorVal + "%22%7D%5D")
                .prettyPeek()
                .then()
                .body("entities.siteName", hasItem("uniqueSite100"))
                .body("entities.callSign", hasItem("KA2071"))
                .body("entities.latitude", hasItem(71.0f))
                .body("entities.longitude", hasItem(22.22f))
                .body("entities.elevation", hasItem(11.11f))
                .body("entities.elevationUS", hasItem(36.45f));

        DeletePath_CleanUp(pathId);
    }

    @Test
    public void getSingleSiteSearch_SearchCriteriaCallSignLIKEKA2071_ResultsetContainsCallSignKA2071(){
        Projects project = new Projects(32, 1, "SearchCriteriaLIKECallSignKA2071 " + randomNumber, "PATH");

        projectId = given()
                .contentType(ContentType.JSON)
                .body(project)
                .when()
                .post("/projects")
                .then()
                .statusCode(201)
                .body("entity.projectName", is(project.getProjectName()))
                .body("entity.projectType", equalTo(project.getProjectType()))
                .extract()
                .path("entity.projectId");

        JSONObject pathDetailsJson = new JSONObject();
        JSONArray segmentsArray = new JSONArray();
        JSONArray segmentEndsArray = new JSONArray();
        JSONObject segmentEndJson1 = new JSONObject();
        JSONObject segmentEndJson2 = new JSONObject();
        JSONObject segment = new JSONObject();

        try {
            segmentEndJson1.put("callSign", "KA2071");
            segmentEndJson1.put("elevation", 11.11);
            segmentEndJson1.put("latitude", 71);
            segmentEndJson1.put("longitude", 22.22);
            segmentEndJson1.put("siteName", "uniqueSite100");

            segmentEndJson2.put("asr", "1000037");
            segmentEndJson2.put("elevation", 12.12);
            segmentEndJson2.put("latitude", 80);
            segmentEndJson2.put("longitude", 23.23);
            segmentEndJson2.put("siteName", "uniqueSite101");

            pathDetailsJson.put("projectId", projectId);
            pathDetailsJson.put("bandId", 2);
            pathDetailsJson.put("pathName", "Test Path 1");

            segmentEndsArray.put(segmentEndJson1);
            segmentEndsArray.put(segmentEndJson2);

            segment.put("segmentEnds", segmentEndsArray);
            segmentsArray.put(segment);
            pathDetailsJson.put("segments", segmentsArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("jsonPath = " + pathDetailsJson);

        pathId = given()
                .contentType(ContentType.JSON)
                .body(pathDetailsJson.toString())
                .when()
                .post("/paths")
                .prettyPeek()
                .then()
                .extract()
                .path("entity.pathId");


        String valueVal = "KA2071";
        String fieldVal = "callsign";
        String operatorVal = "LIKE";

        given().urlEncodingEnabled(false)
                .when()
                .log().all()
                .get("/sites?search=%5B%7B" +
                        "%22value%22%3A%20%22"+ valueVal + "%22%2C%20" +
                        "%22field%22%3A%20%22"+ fieldVal + "%22%2C%20" +
                        "%22operator%22%3A%20%22" + operatorVal + "%22%7D%5D")
                .prettyPeek()
                .then()
                .body("entities.siteName", hasItem("uniqueSite100"))
                .body("entities.callSign", hasItem("KA2071"))
                .body("entities.latitude", hasItem(71.0f))
                .body("entities.longitude", hasItem(22.22f))
                .body("entities.elevation", hasItem(11.11f))
                .body("entities.elevationUS", hasItem(36.45f));

        DeletePath_CleanUp(pathId);
    }

    @Test
    public void getSingleSiteSearch_SearchCriteriaASRLIKE1000037_ResultsetContainsASR1000037(){
        Projects project = new Projects(32, 1, "SearchCriteriaLIKEASR1000037 " + randomNumber, "PATH");

        projectId = given()
                .contentType(ContentType.JSON)
                .body(project)
                .when()
                .post("/projects")
                .then()
                .statusCode(201)
                .body("entity.projectName", is(project.getProjectName()))
                .body("entity.projectType", equalTo(project.getProjectType()))
                .extract()
                .path("entity.projectId");

        JSONObject pathDetailsJson = new JSONObject();
        JSONArray segmentsArray = new JSONArray();
        JSONArray segmentEndsArray = new JSONArray();
        JSONObject segmentEndJson1 = new JSONObject();
        JSONObject segmentEndJson2 = new JSONObject();
        JSONObject segment = new JSONObject();

        try {
            segmentEndJson1.put("callSign", "KA2071");
            segmentEndJson1.put("elevation", 11.11);
            segmentEndJson1.put("latitude", 71);
            segmentEndJson1.put("longitude", 22.22);
            segmentEndJson1.put("siteName", "SearchCriteriaASR100");


            segmentEndJson2.put("asr", "1000037");
            segmentEndJson2.put("elevation", 12.12);
            segmentEndJson2.put("latitude", 80);
            segmentEndJson2.put("longitude", 23.23);
            segmentEndJson2.put("siteName", "SearchCriteriaASR101");

            pathDetailsJson.put("projectId", projectId);
            pathDetailsJson.put("bandId", 2);
            pathDetailsJson.put("pathName", "SearchCriteriaASR Path");

            segmentEndsArray.put(segmentEndJson1);
            segmentEndsArray.put(segmentEndJson2);

            segment.put("segmentEnds", segmentEndsArray);
            segmentsArray.put(segment);
            pathDetailsJson.put("segments", segmentsArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("jsonPath = " + pathDetailsJson);

        pathId = given()
                .contentType(ContentType.JSON)
                .body(pathDetailsJson.toString())
                .when()
                .post("/paths")
                .prettyPeek()
                .then()
                .extract()
                .path("entity.pathId");


        String valueVal = "1000037";
        String fieldVal = "asr";
        String operatorVal = "LIKE";

        given().urlEncodingEnabled(false)
                .when()
                .log().all()
                .get("/sites?search=%5B%7B" +
                        "%22value%22%3A%20%22"+ valueVal + "%22%2C%20" +
                        "%22field%22%3A%20%22"+ fieldVal + "%22%2C%20" +
                        "%22operator%22%3A%20%22" + operatorVal + "%22%7D%5D")
                .prettyPeek()
                .then()
                .body("entities.siteName", hasItem("SearchCriteriaASR101"))
                .body("entities.asr", hasItem(1000037))
                .body("entities.latitude", hasItem(80.0f))
                .body("entities.longitude", hasItem(23.23f))
                .body("entities.elevation", hasItem(12.12f))
                .body("entities.elevationUS", hasItem(39.76f));

        DeletePath_CleanUp(pathId);
    }

    @Test
    public void getSingleSiteSearch_SearchCriteriaLatitudeLIKE88_ResultsetContainsLatitude88(){
        Projects project = new Projects(32, 1, "SearchCriteriaLIKELatitude88 " + randomNumber, "PATH");

        projectId = given()
                .contentType(ContentType.JSON)
                .body(project)
                .when()
                .post("/projects")
                .then()
                .statusCode(201)
                .body("entity.projectName", is(project.getProjectName()))
                .body("entity.projectType", equalTo(project.getProjectType()))
                .extract()
                .path("entity.projectId");

        JSONObject pathDetailsJson = new JSONObject();
        JSONArray segmentsArray = new JSONArray();
        JSONArray segmentEndsArray = new JSONArray();
        JSONObject segmentEndJson1 = new JSONObject();
        JSONObject segmentEndJson2 = new JSONObject();
        JSONObject segment = new JSONObject();

        try {
            segmentEndJson1.put("callSign", "KA2071");
            segmentEndJson1.put("elevation", 11.11);
            segmentEndJson1.put("latitude", 88);
            segmentEndJson1.put("longitude", 22.22);
            segmentEndJson1.put("siteName", "SearchLatitude88");

            segmentEndJson2.put("asr", "1000037");
            segmentEndJson2.put("elevation", 12.12);
            segmentEndJson2.put("latitude", 88);
            segmentEndJson2.put("longitude", 23.23);
            segmentEndJson2.put("siteName", "SearchLatitude88b");

            pathDetailsJson.put("projectId", projectId);
            pathDetailsJson.put("bandId", 2);
            pathDetailsJson.put("pathName", "SearchLatitude88 Path");

            segmentEndsArray.put(segmentEndJson1);
            segmentEndsArray.put(segmentEndJson2);

            segment.put("segmentEnds", segmentEndsArray);
            segmentsArray.put(segment);
            pathDetailsJson.put("segments", segmentsArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("jsonPath = " + pathDetailsJson);

        pathId = given()
                .contentType(ContentType.JSON)
                .body(pathDetailsJson.toString())
                .when()
                .post("/paths")
                .prettyPeek()
                .then()
                .extract()
                .path("entity.pathId");

        String valueVal = "88";
        String fieldVal = "latitude";
        String operatorVal = "LIKE";

        given().urlEncodingEnabled(false)
                .when()
                .log().all()
                .get("/sites?search=%5B%7B" +
                        "%22value%22%3A%20%22"+ valueVal + "%22%2C%20" +
                        "%22field%22%3A%20%22"+ fieldVal + "%22%2C%20" +
                        "%22operator%22%3A%20%22" + operatorVal + "%22%7D%5D")
                .prettyPeek()
                .then()
                .body("entities.siteName", hasItem("SearchLatitude88b"))
                .body("entities.asr", hasItem(1000037))
                .body("entities.latitude", hasItem(88.0f))
                .body("entities.longitude", hasItem(23.23f))
                .body("entities.elevation", hasItem(12.12f))
                .body("entities.elevationUS", hasItem(39.76f));

        DeletePath_CleanUp(pathId);
    }

    @Test
    public void getSingleSiteSearch_SearchCriteriaLongitudeLIKE3322_ResultsetContainsLongitude3322(){
        Projects project = new Projects(32, 1, "SearchCriteriaLIKELongitude3322EH " + randomNumber, "PATH");

        projectId = given()
                .contentType(ContentType.JSON)
                .body(project)
                .when()
                .post("/projects")
                .then()
                .statusCode(201)
                .body("entity.projectName", is(project.getProjectName()))
                .body("entity.projectType", equalTo(project.getProjectType()))
                .extract()
                .path("entity.projectId");

        JSONObject pathDetailsJson = new JSONObject();
        JSONArray segmentsArray = new JSONArray();
        JSONArray segmentEndsArray = new JSONArray();
        JSONObject segmentEndJson1 = new JSONObject();
        JSONObject segmentEndJson2 = new JSONObject();
        JSONObject segment = new JSONObject();

        try {
            segmentEndJson1.put("callSign", "KA2071");
            segmentEndJson1.put("elevation", 11.11);
            segmentEndJson1.put("latitude", 88);
            segmentEndJson1.put("longitude", 22.22);
            segmentEndJson1.put("siteName", "SearchLongitude3322");

            segmentEndJson2.put("asr", "1000037");
            segmentEndJson2.put("elevation", 12.12);
            segmentEndJson2.put("latitude", 88);
            segmentEndJson2.put("longitude", 33.22);
            segmentEndJson2.put("siteName", "SearchLongitude3322b");

            pathDetailsJson.put("projectId", projectId);
            pathDetailsJson.put("bandId", 2);
            pathDetailsJson.put("pathName", "SearchLongitude3322 Path");

            segmentEndsArray.put(segmentEndJson1);
            segmentEndsArray.put(segmentEndJson2);

            segment.put("segmentEnds", segmentEndsArray);
            segmentsArray.put(segment);
            pathDetailsJson.put("segments", segmentsArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("jsonPath = " + pathDetailsJson);

        pathId = given()
                .contentType(ContentType.JSON)
                .body(pathDetailsJson.toString())
                .when()
                .post("/paths")
                .prettyPeek()
                .then()
                .extract()
                .path("entity.pathId");

        String valueVal = "33.2";
        String fieldVal = "longitude";
        String operatorVal = "LIKE";

        given().urlEncodingEnabled(false)
                .when()
                .log().all()
                .get("/sites?search=%5B%7B" +
                        "%22value%22%3A%20%22"+ valueVal + "%22%2C%20" +
                        "%22field%22%3A%20%22"+ fieldVal + "%22%2C%20" +
                        "%22operator%22%3A%20%22" + operatorVal + "%22%7D%5D")
                .prettyPeek()
                .then()
                .body("entities.siteName", hasItem("SearchLongitude3322b"))
                .body("entities.asr", hasItem(1000037))
                .body("entities.latitude", hasItem(88.0f))
                .body("entities.longitude", hasItem(33.22f))
                .body("entities.elevation", hasItem(12.12f))
                .body("entities.elevationUS", hasItem(39.76f));

        DeletePath_CleanUp(pathId);
    }

    @Test
    public void getSingleSiteSearch_SearchCriteriaCompanyIdLIKE1_ResultsetContainsCompanyId1(){
        Projects project = new Projects(32, 1, "SearchLIKECompanyId5 " + randomNumber, "PATH");

        projectId = given()
                .contentType(ContentType.JSON)
                .body(project)
                .when()
                .post("/projects")
                .then()
                .statusCode(201)
                .body("entity.projectName", is(project.getProjectName()))
                .body("entity.projectType", equalTo(project.getProjectType()))
                .extract()
                .path("entity.projectId");

        JSONObject pathDetailsJson = new JSONObject();
        JSONArray segmentsArray = new JSONArray();
        JSONArray segmentEndsArray = new JSONArray();
        JSONObject segmentEndJson1 = new JSONObject();
        JSONObject segmentEndJson2 = new JSONObject();
        JSONObject segment = new JSONObject();

        try {
            segmentEndJson1.put("companyId", "1");

            segmentEndJson1.put("callSign", "KA2071");
            segmentEndJson1.put("elevation", 11.11);
            segmentEndJson1.put("latitude", 88);
            segmentEndJson1.put("longitude", 22.22);
            segmentEndJson1.put("siteName", "SearchCompanyId1");

            segmentEndJson2.put("asr", "1000037");
            segmentEndJson2.put("elevation", 12.12);
            segmentEndJson2.put("latitude", 88);
            segmentEndJson2.put("longitude", 33.22);
            segmentEndJson2.put("siteName", "SearchCompanyId1b");

            pathDetailsJson.put("projectId", projectId);
            pathDetailsJson.put("bandId", 2);
            pathDetailsJson.put("pathName", "SearchCompanyId1 Path");

            segmentEndsArray.put(segmentEndJson1);
            segmentEndsArray.put(segmentEndJson2);

            segment.put("segmentEnds", segmentEndsArray);
            segmentsArray.put(segment);
            pathDetailsJson.put("segments", segmentsArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("jsonPath = " + pathDetailsJson);

        pathId = given()
                .contentType(ContentType.JSON)
                .body(pathDetailsJson.toString())
                .when()
                .post("/paths")
                .prettyPeek()
                .then()
                .extract()
                .path("entity.pathId");


        String valueVal = "1";
        String fieldVal = "companyId";
        String operatorVal = "LIKE";

        given().urlEncodingEnabled(false)
                .when()
                .log().all()
                .get("/sites?search=%5B%7B" +
                        "%22value%22%3A%20%22"+ valueVal + "%22%2C%20" +
                        "%22field%22%3A%20%22"+ fieldVal + "%22%2C%20" +
                        "%22operator%22%3A%20%22" + operatorVal + "%22%7D%5D")
                .prettyPeek()
                .then()
                .body("entities.siteName", hasItem("SearchCompanyId1"))
                .body("entities.company.companyId", hasItem(1))
                .body("entities.callSign", hasItem("KA2071"))
                .body("entities.latitude", hasItem(88.0f))
                .body("entities.longitude", hasItem(22.22f))
                .body("entities.elevation", hasItem(11.11f))
                .body("entities.elevationUS", hasItem(36.45f));;

        DeletePath_CleanUp(pathId);
    }

    @Test
    public void getSingleSiteSearch_SearchCriteriaElevationLIKE111_ResultsetContainsCallSignKA20(){
        Projects project = new Projects(32, 1, "SearchCriteriaLIKELongitude3322 " + randomNumber, "PATH");

        projectId = given()
                .contentType(ContentType.JSON)
                .body(project)
                .when()
                .post("/projects")
                .then()
                .statusCode(201)
                .body("entity.projectName", is(project.getProjectName()))
                .body("entity.projectType", equalTo(project.getProjectType()))
                .extract()
                .path("entity.projectId");

        JSONObject pathDetailsJson = new JSONObject();
        JSONArray segmentsArray = new JSONArray();
        JSONArray segmentEndsArray = new JSONArray();
        JSONObject segmentEndJson1 = new JSONObject();
        JSONObject segmentEndJson2 = new JSONObject();
        JSONObject segment = new JSONObject();

        try {
            segmentEndJson1.put("callSign", "KA2071");
            segmentEndJson1.put("elevation", 11.11);
            segmentEndJson1.put("latitude", 88);
            segmentEndJson1.put("longitude", 22.22);
            segmentEndJson1.put("siteName", "SearchLongitude3322");

            segmentEndJson2.put("asr", "1000037");
            segmentEndJson2.put("elevation", 12.12);
            segmentEndJson2.put("latitude", 88);
            segmentEndJson2.put("longitude", 33.22);
            segmentEndJson2.put("siteName", "SearchLongitude3322b");

            pathDetailsJson.put("projectId", projectId);
            pathDetailsJson.put("bandId", 2);
            pathDetailsJson.put("pathName", "SearchLongitude3322 Path");

            segmentEndsArray.put(segmentEndJson1);
            segmentEndsArray.put(segmentEndJson2);

            segment.put("segmentEnds", segmentEndsArray);
            segmentsArray.put(segment);
            pathDetailsJson.put("segments", segmentsArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("jsonPath = " + pathDetailsJson);

        pathId = given()
                .contentType(ContentType.JSON)
                .body(pathDetailsJson.toString())
                .when()
                .post("/paths")
                .prettyPeek()
                .then()
                .extract()
                .path("entity.pathId");

        String valVal_1 = "KA2071";
        String fieldVal_1 = "callsign";
        String opVal_1 = "LIKE";

        String valVal_2 = "8";
        String fieldVal_2 = "latitude";
        String opVal_2 = "LIKE";

        given().urlEncodingEnabled(false)
                .when()
                .log().all()
                .get("/sites?search=%5B%7B%22value%22%3A%20%22"+ valVal_1 +"%22%2C%20%22field%22%3A%20%22"+ fieldVal_1 +"%22%2C%20%22operator%22%3A%20%22"+ opVal_1 +"%22%7D%2C%20%7B%22" +
                        "value%22%3A%20%22"+ valVal_2 +"%22%2C%20%22field%22%3A%20%22"+ fieldVal_2 +"%22%2C%20%22operator%22%3A%20%22"+ opVal_2 +"%22%7D%5D")
                .prettyPeek()
                .then()
                .body("entities.siteName", hasItem("LIKECoIdSiteA"))
                .body("entities.company.companyId", hasItem(1))
                .body("entities.callSign", hasItem("KA2071"))
                .body("entities.latitude", hasItem(88.0f))
                .body("entities.longitude", hasItem(22.22f))
                .body("entities.elevation", hasItem(11.11f))
                .body("entities.elevationUS", hasItem(36.45f));

        DeletePath_CleanUp(pathId);
    }

    @Test
    public void getTwoSiteSearch_SearchCriteriaLog222LIKE5ANDLatLike_ResultsetContainsCompanyIdLogLIKE5ANDLatLike8(){
        Projects project = new Projects(32, 1, "LIKECoIdSite " + randomNumber, "PATH");

        projectId = given()
                .contentType(ContentType.JSON)
                .body(project)
                .when()
                .post("/projects")
                .then()
                .statusCode(201)
                .body("entity.projectName", is(project.getProjectName()))
                .body("entity.projectType", equalTo(project.getProjectType()))
                .extract()
                .path("entity.projectId");

        JSONObject pathDetailsJson = new JSONObject();
        JSONArray segmentsArray = new JSONArray();
        JSONArray segmentEndsArray = new JSONArray();
        JSONObject segmentEndJson1 = new JSONObject();
        JSONObject segmentEndJson2 = new JSONObject();
        JSONObject segment = new JSONObject();

        try {
            segmentEndJson1.put("companyId", "1");

            segmentEndJson1.put("callSign", "KA2071");
            segmentEndJson1.put("elevation", 11.11);
            segmentEndJson1.put("latitude", 88);
            segmentEndJson1.put("longitude", 22.22);
            segmentEndJson1.put("siteName", "LIKECoIdSiteA");

            segmentEndJson2.put("asr", "1000037");
            segmentEndJson2.put("elevation", 12.12);
            segmentEndJson2.put("latitude", 88);
            segmentEndJson2.put("longitude", 33.22);
            segmentEndJson2.put("siteName", "LIKECoIdSiteB");

            pathDetailsJson.put("projectId", projectId);
            pathDetailsJson.put("bandId", 2);
            pathDetailsJson.put("pathName", "LIKECoIdSite Path");

            segmentEndsArray.put(segmentEndJson1);
            segmentEndsArray.put(segmentEndJson2);

            segment.put("segmentEnds", segmentEndsArray);
            segmentsArray.put(segment);
            pathDetailsJson.put("segments", segmentsArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("jsonPath = " + pathDetailsJson);

        pathId = given()
                .contentType(ContentType.JSON)
                .body(pathDetailsJson.toString())
                .when()
                .post("/paths")
                .prettyPeek()
                .then()
                .extract()
                .path("entity.pathId");


        String valVal_1 = "22.2";
        String fieldVal_1 = "longitude";
        String opVal_1 = "LIKE";

        String valVal_2 = "8";
        String fieldVal_2 = "latitude";
        String opVal_2 = "LIKE";

        given().urlEncodingEnabled(false)
                .when()
                .log().all()
                .get("/sites?search=%5B%7B%22value%22%3A%20%22"+ valVal_1 +"%22%2C%20%22field%22%3A%20%22"+ fieldVal_1 +"%22%2C%20%22operator%22%3A%20%22"+ opVal_1 +"%22%7D%2C%20%7B%22" +
                        "value%22%3A%20%22"+ valVal_2 +"%22%2C%20%22field%22%3A%20%22"+ fieldVal_2 +"%22%2C%20%22operator%22%3A%20%22"+ opVal_2 +"%22%7D%5D")
                .prettyPeek()
                .then()
                .body("entities.siteName", hasItem("LIKECoIdSiteA"))
                .body("entities.company.companyId", hasItem(1))
                .body("entities.callSign", hasItem("KA2071"))
                .body("entities.latitude", hasItem(88.0f))
                .body("entities.longitude", hasItem(22.22f))
                .body("entities.elevation", hasItem(11.11f))
                .body("entities.elevationUS", hasItem(36.45f));

        DeletePath_CleanUp(pathId);
    }

    @Test
    public void getTwoSiteSearch_SearchCriteriaLongLIKE222ANDSiteeLIKELIKECoId_ResultsetContainsLongLIKE222ANDSiteeLIKELIKECoId(){
        Projects project = new Projects(32, 1, "LongLIKE222ANDSiteeLIKELIKECoId " + randomNumber, "PATH");

        projectId = given()
                .contentType(ContentType.JSON)
                .body(project)
                .when()
                .post("/projects")
                .then()
                .statusCode(201)
                .body("entity.projectName", is(project.getProjectName()))
                .body("entity.projectType", equalTo(project.getProjectType()))
                .extract()
                .path("entity.projectId");

        JSONObject pathDetailsJson = new JSONObject();
        JSONArray segmentsArray = new JSONArray();
        JSONArray segmentEndsArray = new JSONArray();
        JSONObject segmentEndJson1 = new JSONObject();
        JSONObject segmentEndJson2 = new JSONObject();
        JSONObject segment = new JSONObject();

        try {
            segmentEndJson1.put("companyId", "1");
            segmentEndJson1.put("callSign", "KA2071");
            segmentEndJson1.put("elevation", 11.11);
            segmentEndJson1.put("latitude", 88);
            segmentEndJson1.put("longitude", 22.22);
            segmentEndJson1.put("siteName", "LIKECoIdSiteA");

            segmentEndJson1.put("companyId", "1");
            segmentEndJson2.put("asr", "1000037");
            segmentEndJson2.put("elevation", 12.12);
            segmentEndJson2.put("latitude", 88);
            segmentEndJson2.put("longitude", 33.22);
            segmentEndJson2.put("siteName", "LIKECoIdSiteB");

            pathDetailsJson.put("projectId", projectId);
            pathDetailsJson.put("bandId", 2);
            pathDetailsJson.put("pathName", "LIKECoIdSite Path");

            segmentEndsArray.put(segmentEndJson1);
            segmentEndsArray.put(segmentEndJson2);

            segment.put("segmentEnds", segmentEndsArray);
            segmentsArray.put(segment);
            pathDetailsJson.put("segments", segmentsArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("jsonPath = " + pathDetailsJson);

        pathId = given()
                .contentType(ContentType.JSON)
                .body(pathDetailsJson.toString())
                .when()
                .post("/paths")
                .prettyPeek()
                .then()
                .extract()
                .path("entity.pathId");


        String valVal_1 = "22.2";
        String fieldVal_1 = "longitude";
        String opVal_1 = "LIKE";

        String valVal_2 = "LIKECoId";
        String fieldVal_2 = "siteName";
        String opVal_2 = "LIKE";

        given().urlEncodingEnabled(false)
                .when()
                .log().all()
                .get("/sites?search=%5B%7B%22value%22%3A%20%22"+ valVal_1 +"%22%2C%20%22field%22%3A%20%22"+ fieldVal_1 +"%22%2C%20%22operator%22%3A%20%22"+ opVal_1 +"%22%7D%2C%20%7B%22" +
                        "value%22%3A%20%22"+ valVal_2 +"%22%2C%20%22field%22%3A%20%22"+ fieldVal_2 +"%22%2C%20%22operator%22%3A%20%22"+ opVal_2 +"%22%7D%5D")
                .prettyPeek()
                .then()
                .body("entities.siteName", hasItem("LIKECoIdSiteA"))
                .body("entities.company.companyId", hasItem(5))
                .body("entities.callSign", hasItem("KA2071"))
                .body("entities.latitude", hasItem(88.0f))
                .body("entities.longitude", hasItem(22.22f))
                .body("entities.elevation", hasItem(11.11f))
                .body("entities.elevationUS", hasItem(36.45f));

        DeletePath_CleanUp(pathId);
    }

    @Test
    public void getTwoSiteSearch_SearchCriteriaLong222ANDSiteCoIdSiteA_ResultsetContainsLong222ANDSiteCoIdSiteA(){
        Projects project = new Projects(32, 1, "Long222ANDSiteeCoId " + randomNumber, "PATH");

        projectId = given()
                .contentType(ContentType.JSON)
                .body(project)
                .when()
                .post("/projects")
                .then()
                .statusCode(201)
                .body("entity.projectName", is(project.getProjectName()))
                .body("entity.projectType", equalTo(project.getProjectType()))
                .extract()
                .path("entity.projectId");

        JSONObject pathDetailsJson = new JSONObject();
        JSONArray segmentsArray = new JSONArray();
        JSONArray segmentEndsArray = new JSONArray();
        JSONObject segmentEndJson1 = new JSONObject();
        JSONObject segmentEndJson2 = new JSONObject();
        JSONObject segment = new JSONObject();

        try {
            segmentEndJson1.put("companyId", "1");
            segmentEndJson1.put("callSign", "KA2071");
            segmentEndJson1.put("elevation", 11.11);
            segmentEndJson1.put("latitude", 88);
            segmentEndJson1.put("longitude", 22.22);
            segmentEndJson1.put("siteName", "CoIdSiteA");

            segmentEndJson1.put("companyId", "1");
            segmentEndJson2.put("asr", "1000037");
            segmentEndJson2.put("elevation", 12.12);
            segmentEndJson2.put("latitude", 88);
            segmentEndJson2.put("longitude", 33.22);
            segmentEndJson2.put("siteName", "CoIdSiteB");

            pathDetailsJson.put("projectId", projectId);
            pathDetailsJson.put("bandId", 2);
            pathDetailsJson.put("pathName", "CoIdSite Path");

            segmentEndsArray.put(segmentEndJson1);
            segmentEndsArray.put(segmentEndJson2);

            segment.put("segmentEnds", segmentEndsArray);
            segmentsArray.put(segment);
            pathDetailsJson.put("segments", segmentsArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("jsonPath = " + pathDetailsJson);

        pathId = given()
                .contentType(ContentType.JSON)
                .body(pathDetailsJson.toString())
                .when()
                .post("/paths")
                .prettyPeek()
                .then()
                .extract()
                .path("entity.pathId");

        String valVal_1 = "22.22";
        String fieldVal_1 = "longitude";
        String opVal_1 = "EQ";

        String valVal_2 = "CoIdSiteA";
        String fieldVal_2 = "siteName";
        String opVal_2 = "EQ";

        given().urlEncodingEnabled(false)
                .when()
                .log().all()
                .get("/sites?search=%5B%7B%22value%22%3A%20%22"+ valVal_1 +"%22%2C%20%22field%22%3A%20%22"+ fieldVal_1 +"%22%2C%20%22operator%22%3A%20%22"+ opVal_1 +"%22%7D%2C%20%7B%22" +
                        "value%22%3A%20%22"+ valVal_2 +"%22%2C%20%22field%22%3A%20%22"+ fieldVal_2 +"%22%2C%20%22operator%22%3A%20%22"+ opVal_2 +"%22%7D%5D")
                .prettyPeek()
                .then()
                .body("entities.siteName", hasItem("CoIdSiteA"))
                .body("entities.company.companyId", hasItem(1))
                .body("entities.callSign", hasItem("KA2071"))
                .body("entities.latitude", hasItem(88.0f))
                .body("entities.longitude", hasItem(22.22f))
                .body("entities.elevation", hasItem(11.11f))
                .body("entities.elevationUS", hasItem(36.45f));

        DeletePath_CleanUp(pathId);
    }

    @Test
    public void getTwoSiteSearch_SearchCriteriaLong222ANDSiteCoIdCoIdSiteA_ResultsetContainsLong222ANDSiteCoId(){
        Projects project = new Projects(32, 1, "LongLIKE2222ANDSiteoIdCoIdSiteA " + randomNumber, "PATH");

        projectId = given()
                .contentType(ContentType.JSON)
                .body(project)
                .when()
                .post("/projects")
                .then()
                .statusCode(201)
                .body("entity.projectName", is(project.getProjectName()))
                .body("entity.projectType", equalTo(project.getProjectType()))
                .extract()
                .path("entity.projectId");

        JSONObject pathDetailsJson = new JSONObject();
        JSONArray segmentsArray = new JSONArray();
        JSONArray segmentEndsArray = new JSONArray();
        JSONObject segmentEndJson1 = new JSONObject();
        JSONObject segmentEndJson2 = new JSONObject();
        JSONObject segment = new JSONObject();

        try {
            segmentEndJson1.put("companyId", "1");
            segmentEndJson1.put("callSign", "KA2071");
            segmentEndJson1.put("elevation", 11.11);
            segmentEndJson1.put("latitude", 88);
            segmentEndJson1.put("longitude", 22.22);
            segmentEndJson1.put("siteName", "CoIdSiteA");

            segmentEndJson1.put("companyId", "1");
            segmentEndJson2.put("asr", "1000037");
            segmentEndJson2.put("elevation", 12.12);
            segmentEndJson2.put("latitude", 88);
            segmentEndJson2.put("longitude", 33.22);
            segmentEndJson2.put("siteName", "CoIdSiteB");

            pathDetailsJson.put("projectId", projectId);
            pathDetailsJson.put("bandId", 2);
            pathDetailsJson.put("pathName", "CoIdSite Path");

            segmentEndsArray.put(segmentEndJson1);
            segmentEndsArray.put(segmentEndJson2);

            segment.put("segmentEnds", segmentEndsArray);
            segmentsArray.put(segment);
            pathDetailsJson.put("segments", segmentsArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("jsonPath = " + pathDetailsJson);

        pathId = given()
                .contentType(ContentType.JSON)
                .body(pathDetailsJson.toString())
                .when()
                .post("/paths")
                .prettyPeek()
                .then()
                .extract()
                .path("entity.pathId");


        String valVal_1 = "22.22";
        String fieldVal_1 = "longitude";
        String opVal_1 = "EQ";

        String valVal_2 = "CoIdSiteA";
        String fieldVal_2 = "siteName";
        String opVal_2 = "EQ";

        given().urlEncodingEnabled(false)
                .when()
                .log().all()
                .get("/sites?search=%5B%7B%22value%22%3A%20%22"+ valVal_1 +"%22%2C%20%22field%22%3A%20%22"+ fieldVal_1 +"%22%2C%20%22operator%22%3A%20%22"+ opVal_1 +"%22%7D%2C%20%7B%22" +
                        "value%22%3A%20%22"+ valVal_2 +"%22%2C%20%22field%22%3A%20%22"+ fieldVal_2 +"%22%2C%20%22operator%22%3A%20%22"+ opVal_2 +"%22%7D%5D")
                .prettyPeek()
                .then()
                .body("entities.siteName", hasItem("CoIdSiteA"))
                .body("entities.company.companyId", hasItem(1))
                .body("entities.callSign", hasItem("KA2071"))
                .body("entities.latitude", hasItem(88.0f))
                .body("entities.longitude", hasItem(22.22f))
                .body("entities.elevation", hasItem(11.11f))
                .body("entities.elevationUS", hasItem(36.45f));

        DeletePath_CleanUp(pathId);
    }

    @Test
    public void getThreeSiteSearch_SearchLongLIKEAND332SiteeLIKELIKECoIdasr100003_ResultsetContainsLongLIKEAND332SiteeLIKELIKECoIdasr100003(){
        Projects project = new Projects(32, 1, "LIKEAND332SiteeLIKELIKECoIdasr100003 " + randomNumber, "PATH");

        projectId = given()
                .contentType(ContentType.JSON)
                .body(project)
                .when()
                .post("/projects")
                .then()
                .statusCode(201)
                .body("entity.projectName", is(project.getProjectName()))
                .body("entity.projectType", equalTo(project.getProjectType()))
                .extract()
                .path("entity.projectId");

        JSONObject pathDetailsJson = new JSONObject();
        JSONArray segmentsArray = new JSONArray();
        JSONArray segmentEndsArray = new JSONArray();
        JSONObject segmentEndJson1 = new JSONObject();
        JSONObject segmentEndJson2 = new JSONObject();
        JSONObject segment = new JSONObject();

        try {
            segmentEndJson1.put("companyId", "1");
            segmentEndJson1.put("callSign", "KA2071");
            segmentEndJson1.put("elevation", 11.11);
            segmentEndJson1.put("latitude", 88);
            segmentEndJson1.put("longitude", 22.22);
            segmentEndJson1.put("siteName", "LIKECoIdSiteA");

            segmentEndJson1.put("companyId", "1");
            segmentEndJson2.put("asr", "1000037");
            segmentEndJson2.put("elevation", 12.12);
            segmentEndJson2.put("latitude", 88);
            segmentEndJson2.put("longitude", 33.22);
            segmentEndJson2.put("siteName", "LIKECoIdSiteB");

            pathDetailsJson.put("projectId", projectId);
            pathDetailsJson.put("bandId", 2);
            pathDetailsJson.put("pathName", "LIKECoIdSite Path");

            segmentEndsArray.put(segmentEndJson1);
            segmentEndsArray.put(segmentEndJson2);

            segment.put("segmentEnds", segmentEndsArray);
            segmentsArray.put(segment);
            pathDetailsJson.put("segments", segmentsArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("jsonPath = " + pathDetailsJson);

        pathId = given()
                .contentType(ContentType.JSON)
                .body(pathDetailsJson.toString())
                .when()
                .post("/paths")
                .prettyPeek()
                .then()
                .extract()
                .path("entity.pathId");


        String valVal_1 = "33.2";
        String fieldVal_1 = "longitude";
        String opVal_1 = "LIKE";

        String valVal_2 = "LIKECoId";
        String fieldVal_2 = "siteName";
        String opVal_2 = "LIKE";

        String valVal_3 = "100003";
        String fieldVal_3 = "asr";
        String opVal_3 = "LIKE";

        given().urlEncodingEnabled(false)
                .when()
                .log().all()
                .get("/sites?search=%5B%7B%22value%22%3A%20%22"+ valVal_1 +"%22%2C%20%22field%22%3A%20%22"+ fieldVal_1 +"%22%2C%20%22operator%22%3A%20%22"+ opVal_1 +"%22%7D%2C%20%7B%22value%22" +
                        "%3A%20%22"+ valVal_2 +"%22%2C%20%22field%22%3A%20%22"+ fieldVal_2 +"%22%2C%20%22operator%22%3A%20%22"+ opVal_2 +"%22%7D%2C%7B%22value%22%3A%20%" +
                        "22"+ valVal_3 +"%22%2C%20%22field%22%3A%20%22"+ fieldVal_3 +"%22%2C%20%22operator%22%3A%20%22"+ opVal_3 +"%22%7D%5D")
                .prettyPeek()
                .then()
                .body("entities.siteName", hasItem("LIKECoIdSiteB"))
                .body("entities.asr", hasItem(1000037))
                .body("entities.latitude", hasItem(88.0f))
                .body("entities.longitude", hasItem(33.22f))
                .body("entities.elevation", hasItem(12.12f))
                .body("entities.elevationUS", hasItem(39.76f));

        DeletePath_CleanUp(pathId);
    }

    @Test
    public void getThreeSiteSearch_SearchLongAND332SiteeCoIdasr100003_ResultsetContainsLongAND3322SiteeCoIdasr1000037(){
        Projects project = new Projects(32, 1, "Long332SiteeCoIdasr100003 " + randomNumber, "PATH");

        projectId = given()
                .contentType(ContentType.JSON)
                .body(project)
                .when()
                .post("/projects")
                .then()
                .statusCode(201)
                .body("entity.projectName", is(project.getProjectName()))
                .body("entity.projectType", equalTo(project.getProjectType()))
                .extract()
                .path("entity.projectId");

        JSONObject pathDetailsJson = new JSONObject();
        JSONArray segmentsArray = new JSONArray();
        JSONArray segmentEndsArray = new JSONArray();
        JSONObject segmentEndJson1 = new JSONObject();
        JSONObject segmentEndJson2 = new JSONObject();
        JSONObject segment = new JSONObject();

        try {
            segmentEndJson1.put("companyId", "1");
            segmentEndJson1.put("callSign", "KA2071");
            segmentEndJson1.put("elevation", 11.11);
            segmentEndJson1.put("latitude", 88);
            segmentEndJson1.put("longitude", 22.22);
            segmentEndJson1.put("siteName", "CoIdSiteA");

            segmentEndJson1.put("companyId", "1");
            segmentEndJson2.put("asr", "1000037");
            segmentEndJson2.put("elevation", 12.12);
            segmentEndJson2.put("latitude", 88);
            segmentEndJson2.put("longitude", 33.22);
            segmentEndJson2.put("siteName", "CoIdSiteB");

            pathDetailsJson.put("projectId", projectId);
            pathDetailsJson.put("bandId", 2);
            pathDetailsJson.put("pathName", "CoIdSite Path");

            segmentEndsArray.put(segmentEndJson1);
            segmentEndsArray.put(segmentEndJson2);

            segment.put("segmentEnds", segmentEndsArray);
            segmentsArray.put(segment);
            pathDetailsJson.put("segments", segmentsArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("jsonPath = " + pathDetailsJson);

        pathId = given()
                .contentType(ContentType.JSON)
                .body(pathDetailsJson.toString())
                .when()
                .post("/paths")
                .prettyPeek()
                .then()
                .extract()
                .path("entity.pathId");


        String valVal_1 = "33.22";
        String fieldVal_1 = "longitude";
        String opVal_1 = "EQ";

        String valVal_2 = "CoIdSiteB";
        String fieldVal_2 = "siteName";
        String opVal_2 = "EQ";

        String valVal_3 = "1000037";
        String fieldVal_3 = "asr";
        String opVal_3 = "EQ";

        given().urlEncodingEnabled(false)
                .when()
                .log().all()
                .get("/sites?search=%5B%7B%22value%22%3A%20%22"+ valVal_1 +"%22%2C%20%22field%22%3A%20%22"+ fieldVal_1 +"%22%2C%20%22operator%22%3A%20%22"+ opVal_1 +"%22%7D%2C%20%7B%22value%22" +
                        "%3A%20%22"+ valVal_2 +"%22%2C%20%22field%22%3A%20%22"+ fieldVal_2 +"%22%2C%20%22operator%22%3A%20%22"+ opVal_2 +"%22%7D%2C%7B%22value%22%3A%20%" +
                        "22"+ valVal_3 +"%22%2C%20%22field%22%3A%20%22"+ fieldVal_3 +"%22%2C%20%22operator%22%3A%20%22"+ opVal_3 +"%22%7D%5D")
                .prettyPeek()
                .then()
                .body("entities.siteName", hasItem("CoIdSiteB"))
                .body("entities.asr", hasItem(1000037))
                .body("entities.latitude", hasItem(88.0f))
                .body("entities.longitude", hasItem(33.22f))
                .body("entities.elevation", hasItem(12.12f))
                .body("entities.elevationUS", hasItem(39.76f));

        DeletePath_CleanUp(pathId);
    }

    @Test
    public void getThreeSiteSearch_SearchLatLike8CallsignLikeKA2071companyLike1_ResultsetContainsLatLike8CallsignLikeKA2071companyLike1(){
        Projects project = new Projects(32, 1, "LongLIKEANDSiteLIKE " + randomNumber, "PATH");

        projectId = given()
                .contentType(ContentType.JSON)
                .body(project)
                .when()
                .post("/projects")
                .then()
                .statusCode(201)
                .body("entity.projectName", is(project.getProjectName()))
                .body("entity.projectType", equalTo(project.getProjectType()))
                .extract()
                .path("entity.projectId");

        JSONObject pathDetailsJson = new JSONObject();
        JSONArray segmentsArray = new JSONArray();
        JSONArray segmentEndsArray = new JSONArray();
        JSONObject segmentEndJson1 = new JSONObject();
        JSONObject segmentEndJson2 = new JSONObject();
        JSONObject segment = new JSONObject();

        try {
            segmentEndJson1.put("companyId", "1");
            segmentEndJson1.put("callSign", "KA2071");
            segmentEndJson1.put("elevation", 11.11);
            segmentEndJson1.put("latitude", 88);
            segmentEndJson1.put("longitude", 22.22);
            segmentEndJson1.put("siteName", "LIKECoIdSiteA");

            segmentEndJson1.put("companyId", "1");
            segmentEndJson2.put("asr", "1000037");
            segmentEndJson2.put("elevation", 12.12);
            segmentEndJson2.put("latitude", 88);
            segmentEndJson2.put("longitude", 33.22);
            segmentEndJson2.put("siteName", "LIKECoIdSiteB");

            pathDetailsJson.put("projectId", projectId);
            pathDetailsJson.put("bandId", 2);
            pathDetailsJson.put("pathName", "LIKECoIdSite Path");

            segmentEndsArray.put(segmentEndJson1);
            segmentEndsArray.put(segmentEndJson2);

            segment.put("segmentEnds", segmentEndsArray);
            segmentsArray.put(segment);
            pathDetailsJson.put("segments", segmentsArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("jsonPath = " + pathDetailsJson);

        pathId = given()
                .contentType(ContentType.JSON)
                .body(pathDetailsJson.toString())
                .when()
                .post("/paths")
                .prettyPeek()
                .then()
                .extract()
                .path("entity.pathId");

        String valVal_1 = "8";
        String fieldVal_1 = "latitude";
        String opVal_1 = "LIKE";

        String valVal_2 = "KA2071";
        String fieldVal_2 = "callsign";
        String opVal_2 = "LIKE";

        String valVal_3 = "1";
        String fieldVal_3 = "companyId";
        String opVal_3 = "LIKE";

        given().urlEncodingEnabled(false)
                .when()
                .log().all()
                .get("/sites?search=%5B%7B%22value%22%3A%20%22"+ valVal_1 +"%22%2C%20%22field%22%3A%20%22"+ fieldVal_1 +"%22%2C%20%22operator%22%3A%20%22"+ opVal_1 +"%22%7D%2C%20%7B%22value%22" +
                        "%3A%20%22"+ valVal_2 +"%22%2C%20%22field%22%3A%20%22"+ fieldVal_2 +"%22%2C%20%22operator%22%3A%20%22"+ opVal_2 +"%22%7D%2C%7B%22value%22%3A%20%" +
                        "22"+ valVal_3 +"%22%2C%20%22field%22%3A%20%22"+ fieldVal_3 +"%22%2C%20%22operator%22%3A%20%22"+ opVal_3 +"%22%7D%5D")
                .prettyPeek()
                .then()
                .body("entities.siteName", hasItem("LIKECoIdSiteA"))
                .body("entities.company.companyId", hasItem(1))
                .body("entities.callSign", hasItem("KA2071"))
                .body("entities.latitude", hasItem(88.0f))
                .body("entities.longitude", hasItem(22.22f))
                .body("entities.elevation", hasItem(11.11f))
                .body("entities.elevationUS", hasItem(36.45f));

        DeletePath_CleanUp(pathId);
    }

    @Test
    public void getThreeSiteSearch_SearchLat8CallsignKA2071company1_ResultsetContainsLat8CallsignKA2071company1(){
        Projects project = new Projects(32, 1, "SearchLat8CallsignKA2071company " + randomNumber, "PATH");

        projectId = given()
                .contentType(ContentType.JSON)
                .body(project)
                .when()
                .post("/projects")
                .then()
                .statusCode(201)
                .body("entity.projectName", is(project.getProjectName()))
                .body("entity.projectType", equalTo(project.getProjectType()))
                .extract()
                .path("entity.projectId");

        JSONObject pathDetailsJson = new JSONObject();
        JSONArray segmentsArray = new JSONArray();
        JSONArray segmentEndsArray = new JSONArray();
        JSONObject segmentEndJson1 = new JSONObject();
        JSONObject segmentEndJson2 = new JSONObject();
        JSONObject segment = new JSONObject();

        try {
            segmentEndJson1.put("companyId", "1");
            segmentEndJson1.put("callSign", "KA2071");
            segmentEndJson1.put("elevation", 11.11);
            segmentEndJson1.put("latitude", 88);
            segmentEndJson1.put("longitude", 22.22);
            segmentEndJson1.put("siteName", "LIKECoIdSiteA");

            segmentEndJson1.put("companyId", "1");
            segmentEndJson2.put("asr", "1000037");
            segmentEndJson2.put("elevation", 12.12);
            segmentEndJson2.put("latitude", 88);
            segmentEndJson2.put("longitude", 33.22);
            segmentEndJson2.put("siteName", "LIKECoIdSiteB");

            pathDetailsJson.put("projectId", projectId);
            pathDetailsJson.put("bandId", 2);
            pathDetailsJson.put("pathName", "LIKECoIdSite Path");

            segmentEndsArray.put(segmentEndJson1);
            segmentEndsArray.put(segmentEndJson2);

            segment.put("segmentEnds", segmentEndsArray);
            segmentsArray.put(segment);
            pathDetailsJson.put("segments", segmentsArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("jsonPath = " + pathDetailsJson);

        pathId = given()
                .contentType(ContentType.JSON)
                .body(pathDetailsJson.toString())
                .when()
                .post("/paths")
                .prettyPeek()
                .then()
                .extract()
                .path("entity.pathId");

        String valVal_1 = "88.0";
        String fieldVal_1 = "latitude";
        String opVal_1 = "EQ";

        String valVal_2 = "KA2071";
        String fieldVal_2 = "callsign";
        String opVal_2 = "EQ";

        String valVal_3 = "1";
        String fieldVal_3 = "companyId";
        String opVal_3 = "EQ";

        given().urlEncodingEnabled(false)
                .when()
                .log().all()
                .get("/sites?search=%5B%7B%22value%22%3A%20%22"+ valVal_1 +"%22%2C%20%22field%22%3A%20%22"+ fieldVal_1 +"%22%2C%20%22operator%22%3A%20%22"+ opVal_1 +"%22%7D%2C%20%7B%22value%22" +
                        "%3A%20%22"+ valVal_2 +"%22%2C%20%22field%22%3A%20%22"+ fieldVal_2 +"%22%2C%20%22operator%22%3A%20%22"+ opVal_2 +"%22%7D%2C%7B%22value%22%3A%20%" +
                        "22"+ valVal_3 +"%22%2C%20%22field%22%3A%20%22"+ fieldVal_3 +"%22%2C%20%22operator%22%3A%20%22"+ opVal_3 +"%22%7D%5D")
                .prettyPeek()
                .then()
                .body("entities.siteName", hasItem("LIKECoIdSiteA"))
                .body("entities.company.companyId", hasItem(1))
                .body("entities.callSign", hasItem("KA2071"))
                .body("entities.latitude", hasItem(88.0f))
                .body("entities.longitude", hasItem(22.22f))
                .body("entities.elevation", hasItem(11.11f))
                .body("entities.elevationUS", hasItem(36.45f));

        DeletePath_CleanUp(pathId);
    }

    @Test
    public void getThreeSiteSearch_SearchBADINPUT_ResultSuccessfullyretrieved0sites(){
        Projects project = new Projects(32, 1, "LongANDSite " + randomNumber, "PATH");

        projectId = given()
                .contentType(ContentType.JSON)
                .body(project)
                .when()
                .post("/projects")
                .then()
                .statusCode(201)
                .body("entity.projectName", is(project.getProjectName()))
                .body("entity.projectType", equalTo(project.getProjectType()))
                .extract()
                .path("entity.projectId");

        JSONObject pathDetailsJson = new JSONObject();
        JSONArray segmentsArray = new JSONArray();
        JSONArray segmentEndsArray = new JSONArray();
        JSONObject segmentEndJson1 = new JSONObject();
        JSONObject segmentEndJson2 = new JSONObject();
        JSONObject segment = new JSONObject();

        try {
            segmentEndJson1.put("companyId", "1");
            segmentEndJson1.put("callSign", "KA2071");
            segmentEndJson1.put("elevation", 11.11);
            segmentEndJson1.put("latitude", 88);
            segmentEndJson1.put("longitude", 22.22);
            segmentEndJson1.put("siteName", "LIKECoIdSiteA");

            segmentEndJson1.put("companyId", "1");
            segmentEndJson2.put("asr", "1000037");
            segmentEndJson2.put("elevation", 12.12);
            segmentEndJson2.put("latitude", 88);
            segmentEndJson2.put("longitude", 33.22);
            segmentEndJson2.put("siteName", "LIKECoIdSiteB");

            pathDetailsJson.put("projectId", projectId);
            pathDetailsJson.put("bandId", 2);
            pathDetailsJson.put("pathName", "LIKECoIdSite Path");

            segmentEndsArray.put(segmentEndJson1);
            segmentEndsArray.put(segmentEndJson2);

            segment.put("segmentEnds", segmentEndsArray);
            segmentsArray.put(segment);
            pathDetailsJson.put("segments", segmentsArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("jsonPath = " + pathDetailsJson);

        pathId = given()
                .contentType(ContentType.JSON)
                .body(pathDetailsJson.toString())
                .when()
                .post("/paths")
                .prettyPeek()
                .then()
                .extract()
                .path("entity.pathId");


        String valVal_1 = "88.0";
        String fieldVal_1 = "latitude";
        String opVal_1 = "EQ";

        String valVal_2 = "BADINPUT";
        String fieldVal_2 = "callsign";
        String opVal_2 = "EQ";

        String valVal_3 = "1";
        String fieldVal_3 = "companyId";
        String opVal_3 = "EQ";

        given().urlEncodingEnabled(false)
                .when()
                .log().all()
                .get("/sites?search=%5B%7B%22value%22%3A%20%22"+ valVal_1 +"%22%2C%20%22field%22%3A%20%22"+ fieldVal_1 +"%22%2C%20%22operator%22%3A%20%22"+ opVal_1 +"%22%7D%2C%20%7B%22value%22" +
                        "%3A%20%22"+ valVal_2 +"%22%2C%20%22field%22%3A%20%22"+ fieldVal_2 +"%22%2C%20%22operator%22%3A%20%22"+ opVal_2 +"%22%7D%2C%7B%22value%22%3A%20%" +
                        "22"+ valVal_3 +"%22%2C%20%22field%22%3A%20%22"+ fieldVal_3 +"%22%2C%20%22operator%22%3A%20%22"+ opVal_3 +"%22%7D%5D")
                .prettyPeek()
                .then()
                .body("message", equalTo("Successfully retrieved 0 sites"));

        DeletePath_CleanUp(pathId);
    }

    private void DeletePath_CleanUp(int pathNum) {

        ArrayList myList = new ArrayList();

        myList.add(pathNum);
        Map<String, ArrayList> mapOfList = new HashMap<String, ArrayList>();
        mapOfList.put("pathsToDelete", myList);

        given()
                .contentType(ContentType.JSON)
                .body(mapOfList)
                .when()
                .delete("/paths")
                .prettyPeek()
                .then()
                .statusCode(201);
    }
}



