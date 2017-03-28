package comm.service.tests;

import com.google.gson.Gson;
import com.jayway.restassured.http.ContentType;
import comm.service.model.Licensee;
import comm.service.model.Projects;
import comm.service.model.RestAssuredConfig;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PathTest extends RestAssuredConfig {
    Random rndNum = new Random();
    int randomNumber6 = rndNum.nextInt(1000);

    Gson gson = new Gson();

    int pathId = 0;
    int projectId = 0;
    int licenseeId = 0;


    @Test
    public void PostPath_ValidPaths_PathSaves() {
        Licensee licensee = new Licensee("stPath_ValidPa", "stPath_ValidPa@uKnow.com", "stPath_ValidPa Co", "PATH");
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


        Projects project = new Projects(licenseeId, licenseeId, "stPath_ValidPa " + randomNumber6, "PATH");

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
            segmentEndJson1.put("elevation", 11.11);
            segmentEndJson1.put("latitude", 71);
            segmentEndJson1.put("longitude", 22.22);
            segmentEndJson1.put("siteName", "Test API Site 1");

            segmentEndJson2.put("elevation", 12.12);
            segmentEndJson2.put("latitude", 80);
            segmentEndJson2.put("longitude", 23.23);
            segmentEndJson2.put("siteName", "Test API Site 2");

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
                .then()
                .extract()
                .path("entity.pathId");
    }

    @Test
    public void GetPath_ValidPaths_PathSaves() {
        Licensee licensee = new Licensee("ValidPaths", "ValidPaths@uKnow.com", "ValidPaths Co", "PATH");
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


        Projects project = new Projects(licenseeId, licenseeId, "RequiredProjectInput " + randomNumber6, "PATH");

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
            segmentEndJson1.put("elevation", 11.11);
            segmentEndJson1.put("latitude", 71);
            segmentEndJson1.put("longitude", 22.22);
            segmentEndJson1.put("siteName", "Test API Site 1");

            segmentEndJson2.put("elevation", 12.12);
            segmentEndJson2.put("latitude", 80);
            segmentEndJson2.put("longitude", 23.23);
            segmentEndJson2.put("siteName", "Test API Site 2");

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
                .then()
                .extract()
                .path("entity.pathId");

        given()
                .pathParam("pathId", pathId)
                //.queryParam("unitType", "US")
                .when()
                .get("/paths/{pathId}")
                .prettyPeek()
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 ")
                .body("entity.pathId", equalTo(pathId))
                .body("entity.pathName", equalTo("Test Path 1"))
                .body("entity.band.bandId", equalTo(2))
                .body("pathLength", isEmptyOrNullString())
                .body("matchingRadios", isEmptyOrNullString())
                .body("entity.segments.segmentEnds[0].elevation[0]", is(11.11f))
                .body("entity.segments.segmentEnds[0].elevation[1]", is(12.12f))
                .body("entity.segments.segmentEnds[0].siteName[0]", is("Test API Site 1"))
                .body("entity.segments.segmentEnds[0].siteName[1]", is("Test API Site 2"))
                .body("entity.segments.segmentEnds[0].latitude[0]", is(71.0f))
                .body("entity.segments.segmentEnds[0].latitude[1]", is(80.0f))
                .body("entity.segments.segmentEnds[0].longitude[0]", is(22.22f))
                .body("entity.segments.segmentEnds[0].longitude[1]", is(23.23f))
                .body("entity.segments.segmentEnds[0].elevationUS[0]", is(36.45f))
                .body("entity.segments.segmentEnds[0].elevationUS[1]", is(39.76f))
                .body("entity.segments.segmentEnds[0].passiveFlag[0]", is(false))
                .body("entity.segments.segmentEnds[0].passiveFlag[1]", is(false));
    }


    @Test
    public void PostPath_NoElevationSegOne_GroundElevationCannotBeNull() {
        Licensee licensee = new Licensee("_NoElevationSegOne", "_NoElevationSegOne@uKnow.com", "_NoElevationSegOne Co", "PATH");
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


        Projects project = new Projects(licenseeId, licenseeId, "_NoElevationSegOne " + randomNumber6, "PATH");

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

            segmentEndJson1.put("latitude", 71);
            segmentEndJson1.put("longitude", 22.22);
            segmentEndJson1.put("siteName", "Test API Site 1");

            segmentEndJson2.put("elevation", 12.12);
            segmentEndJson2.put("latitude", 80);
            segmentEndJson2.put("longitude", 23.23);
            segmentEndJson2.put("siteName", "Test API Site 2");

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

        given()
                .contentType(ContentType.JSON)
                .body(pathDetailsJson.toString())
                .when()
                .post("/paths")
                .prettyPeek()
                .then()
                .statusLine("HTTP/1.1 422 ")
                .statusCode(422)
                .body("status", is("Error"))
                .body("message", is("Validation failed for classes [com.commscope.comsearch.entity.main.SegmentEnd] during persist time for groups [javax.validation.groups.Default, ]\nList of constraint violations:[\n\tConstraintViolationImpl{interpolatedMessage='Ground Elevation cannot be null', propertyPath=groundElevation, rootBeanClass=class com.commscope.comsearch.entity.main.SegmentEnd, messageTemplate='Ground Elevation cannot be null'}\n]"));
    }

    @Test
    public void PostPath_NoElevationSegTwo_GroundElevationCannotBeNull() {
        Licensee licensee = new Licensee("NoElevationSegTwo_", "NoElevationSegTwo_@uKnow.com", "NoElevationSegTwo_ Co", "PATH");
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


        Projects project = new Projects(licenseeId, licenseeId, "NoElevationSegTwo_ " + randomNumber6, "PATH");

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
            segmentEndJson1.put("elevation", 12.12);
            segmentEndJson1.put("latitude", 71);
            segmentEndJson1.put("longitude", 22.22);
            segmentEndJson1.put("siteName", "Test API Site 1");

            segmentEndJson2.put("latitude", 80);
            segmentEndJson2.put("longitude", 23.23);
            segmentEndJson2.put("siteName", "Test API Site 2");


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

        given()
                .contentType(ContentType.JSON)
                .body(pathDetailsJson.toString())
                .when()
                .post("/paths")
                .prettyPeek()
                .then()
                .statusCode(422)
                .statusLine("HTTP/1.1 422 ")
                .body("status", is("Error"))
                .body("message", is("Validation failed for classes [com.commscope.comsearch.entity.main.SegmentEnd] during persist time for groups [javax.validation.groups.Default, ]\nList of constraint violations:[\n\tConstraintViolationImpl{interpolatedMessage='Ground Elevation cannot be null', propertyPath=groundElevation, rootBeanClass=class com.commscope.comsearch.entity.main.SegmentEnd, messageTemplate='Ground Elevation cannot be null'}\n]"));
    }

    @Test
    public void PostPath_NoLatitudeSegOne_LatitudeCannotBeNull() {
        Licensee licensee = new Licensee("_NoLatitudeSegOne", "_NoLatitudeSegOne@uKnow.com", "_NoLatitudeSegOne Co", "PATH");
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


        Projects project = new Projects(licenseeId, licenseeId, "_NoLatitudeSegOne " + randomNumber6, "PATH");

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
            segmentEndJson1.put("elevation", 12.12);
            segmentEndJson1.put("longitude", 22.22);
            segmentEndJson1.put("siteName", "Test API Site 1");

            segmentEndJson2.put("elevation", 12.12);
            segmentEndJson2.put("latitude", 80);
            segmentEndJson2.put("longitude", 23.23);
            segmentEndJson2.put("siteName", "Test API Site 2");


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

        given()
                .contentType(ContentType.JSON)
                .body(pathDetailsJson.toString())
                .when()
                .post("/paths")
                .prettyPeek()
                .then()
                .statusCode(422)
                .statusLine("HTTP/1.1 422 ")
                .body("status", is("Error"))
                .body("message", is("Validation failed for classes [com.commscope.comsearch.entity.main.SegmentEnd] during persist time for groups [javax.validation.groups.Default, ]\nList of constraint violations:[\n\tConstraintViolationImpl{interpolatedMessage='Latitude cannot be null', propertyPath=latitude, rootBeanClass=class com.commscope.comsearch.entity.main.SegmentEnd, messageTemplate='Latitude cannot be null'}\n]"));
    }

    @Test
    public void PostPath_NoLatitudeSegTwo_LatitudeCannotBeNull() {
        Licensee licensee = new Licensee("NoLatitudeSegTwo", "NoLatitudeSegTwo@uKnow.com", "NoLatitudeSegTwo Co", "PATH");
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


        Projects project = new Projects(licenseeId, licenseeId, "NoLatitudeSegTwo " + randomNumber6, "PATH");

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
            segmentEndJson1.put("elevation", 12.12);
            segmentEndJson1.put("latitude", 80);
            segmentEndJson1.put("longitude", 22.22);
            segmentEndJson1.put("siteName", "Test API Site 1");

            segmentEndJson2.put("elevation", 12.12);
            segmentEndJson2.put("longitude", 23.23);
            segmentEndJson2.put("siteName", "Test API Site 2");

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

        given()
                .contentType(ContentType.JSON)
                .body(pathDetailsJson.toString())
                .when()
                .post("/paths")
                .prettyPeek()
                .then()
                .statusCode(422)
                .statusLine("HTTP/1.1 422 ")
                .body("status", is("Error"))
                .body("message", is("Validation failed for classes [com.commscope.comsearch.entity.main.SegmentEnd] during persist time for groups [javax.validation.groups.Default, ]\nList of constraint violations:[\n\tConstraintViolationImpl{interpolatedMessage='Latitude cannot be null', propertyPath=latitude, rootBeanClass=class com.commscope.comsearch.entity.main.SegmentEnd, messageTemplate='Latitude cannot be null'}\n]"));
    }

    @Test
    public void PostPath_NoLongitudeSegOne_LongitudeCannotBeNull() {
        Licensee licensee = new Licensee("NoLongitudeSegOne_", "NoLongitudeSegOne_@uKnow.com", "NoLongitudeSegOne_ Co", "PATH");
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


        Projects project = new Projects(licenseeId, licenseeId, "NoLongitudeSegOne_ " + randomNumber6, "PATH");

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
            segmentEndJson1.put("elevation", 12.12);
            segmentEndJson1.put("latitude", 80);
            segmentEndJson1.put("longitude", 23.23);
            segmentEndJson1.put("siteName", "Test API Site 1");

            segmentEndJson2.put("elevation", 12.12);
            segmentEndJson2.put("latitude", 80);
            segmentEndJson2.put("siteName", "Test API Site 2");

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

        given()
                .contentType(ContentType.JSON)
                .body(pathDetailsJson.toString())
                .when()
                .post("/paths")
                .prettyPeek()
                .then()
                .statusCode(422)
                .statusLine("HTTP/1.1 422 ")
                .body("status", is("Error"))
                .body("message", is("Validation failed for classes [com.commscope.comsearch.entity.main.SegmentEnd] during persist time for groups [javax.validation.groups.Default, ]\nList of constraint violations:[\n\tConstraintViolationImpl{interpolatedMessage='Longitude cannot be null', propertyPath=longitude, rootBeanClass=class com.commscope.comsearch.entity.main.SegmentEnd, messageTemplate='Longitude cannot be null'}\n]"));
        ;
    }

    @Test
    public void PostPath_NoLongitudeSegTwo_LongitudeCannotBeNull() {
        Licensee licensee = new Licensee("NoLongitudeSegTwo_", "NoLongitudeSegTwo_@uKnow.com", "NoLongitudeSegTwo_ Co", "PATH");
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


        Projects project = new Projects(licenseeId, licenseeId, "NoLongitudeSegTwo_ " + randomNumber6, "PATH");

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
            segmentEndJson1.put("elevation", 12.12);
            segmentEndJson1.put("latitude", 80);
            segmentEndJson1.put("longitude", 23.23);
            segmentEndJson1.put("siteName", "Test API Site 1");

            segmentEndJson2.put("elevation", 12.12);
            segmentEndJson2.put("latitude", 80);
            segmentEndJson2.put("siteName", "Test API Site 2");


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

        given()
                .contentType(ContentType.JSON)
                .body(pathDetailsJson.toString())
                .when()
                .post("/paths")
                .prettyPeek()
                .then()
                .statusCode(422)
                .statusLine("HTTP/1.1 422 ")
                .body("status", is("Error"))
                .body("message", is("Validation failed for classes [com.commscope.comsearch.entity.main.SegmentEnd] during persist time for groups [javax.validation.groups.Default, ]\nList of constraint violations:[\n\tConstraintViolationImpl{interpolatedMessage='Longitude cannot be null', propertyPath=longitude, rootBeanClass=class com.commscope.comsearch.entity.main.SegmentEnd, messageTemplate='Longitude cannot be null'}\n]"));
    }

    @Test
    public void PostPath_NoSiteNameSegOne_SiteNameCannotBeNull() {
        Licensee licensee = new Licensee("NoSiteNameSegOne", "NoSiteNameSegOne@uKnow.com", "NoSiteNameSegOne Co", "PATH");
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


        Projects project = new Projects(licenseeId, licenseeId, "NoSiteNameSegOne " + randomNumber6, "PATH");

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
            segmentEndJson1.put("elevation", 12.12);
            segmentEndJson1.put("latitude", 80);
            segmentEndJson1.put("longitude", 23.23);

            segmentEndJson2.put("elevation", 12.12);
            segmentEndJson2.put("latitude", 80);
            segmentEndJson2.put("longitude", 23.23);
            segmentEndJson2.put("siteName", "Test API Site 2");


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

        given()
                .contentType(ContentType.JSON)
                .body(pathDetailsJson.toString())
                .when()
                .post("/paths")
                .prettyPeek()
                .then()
                .statusCode(422)
                .statusLine("HTTP/1.1 422 ")
                .body("status", is("Error"))
                .body("message", is("Validation failed for classes [com.commscope.comsearch.entity.main.SegmentEnd] during persist time for groups [javax.validation.groups.Default, ]\nList of constraint violations:[\n\tConstraintViolationImpl{interpolatedMessage='Site Name cannot be null', propertyPath=siteName, rootBeanClass=class com.commscope.comsearch.entity.main.SegmentEnd, messageTemplate='Site Name cannot be null'}\n]"));
    }

    @Test
    public void PostPath_NoSiteNameSegTwo_SiteNameCannotBeNull() {
        Licensee licensee = new Licensee("_NoSiteNameSegTwo", "_NoSiteNameSegTwo@uKnow.com", "_NoSiteNameSegTwo Co", "PATH");
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


        Projects project = new Projects(licenseeId, licenseeId, "_NoSiteNameSegTwo " + randomNumber6, "PATH");

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
            segmentEndJson1.put("elevation", 12.12);
            segmentEndJson1.put("latitude", 80);
            segmentEndJson1.put("longitude", 23.23);
            segmentEndJson1.put("siteName", "Test API Site 2");


            segmentEndJson2.put("elevation", 12.12);
            segmentEndJson2.put("latitude", 80);
            segmentEndJson2.put("longitude", 23.23);


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

        given()
                .contentType(ContentType.JSON)
                .body(pathDetailsJson.toString())
                .when()
                .post("/paths")
                .prettyPeek()
                .then()
                .statusCode(422)
                .statusLine("HTTP/1.1 422 ")
                .body("status", is("Error"))
                .body("message", is("Validation failed for classes [com.commscope.comsearch.entity.main.SegmentEnd] during persist time for groups [javax.validation.groups.Default, ]\nList of constraint violations:[\n\tConstraintViolationImpl{interpolatedMessage='Site Name cannot be null', propertyPath=siteName, rootBeanClass=class com.commscope.comsearch.entity.main.SegmentEnd, messageTemplate='Site Name cannot be null'}\n]"));
    }

    @Test
    public void DeletePath_ValidPaths_DeletePath() {
        Licensee licensee = new Licensee("DeletePath_", "DeletePath_@uKnow.com", "DeletePath_ Co", "PATH");
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


        Projects project = new Projects(licenseeId, licenseeId, "DeletePath_ " + randomNumber6, "PATH");

        projectId = given()
                .contentType(ContentType.JSON)
                .body(project)
                .when()
                .post("/projects")
                .prettyPeek()
                .then()
                .statusCode(201)
                .body("entity.projectName", is(project.getProjectName()))
                .body("entity.projectType", equalTo(project.getProjectType()))
                .extract()
                .path("entity.projectId");

        JSONObject pathDetailsJson = new JSONObject();
        JSONObject segmentEndJson1 = new JSONObject();
        JSONObject segmentEndJson2 = new JSONObject();
        JSONArray segmentsArray = new JSONArray();
        JSONArray segmentEndsArray = new JSONArray();
        JSONObject segment = new JSONObject();

        try {
            segmentEndJson1.put("elevation", 11.11);
            segmentEndJson1.put("latitude", 71);
            segmentEndJson1.put("longitude", 22.22);
            segmentEndJson1.put("siteName", "Test API Site 1");


            segmentEndJson2.put("elevation", 12.12);
            segmentEndJson2.put("latitude", 80);
            segmentEndJson2.put("longitude", 23.23);
            segmentEndJson2.put("siteName", "Test API Site 2");


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

        pathId = given()
                .contentType(ContentType.JSON)
                .body(pathDetailsJson.toString())
                .when()
                .post("/paths")
                .then()
                .extract()
                .path("entity.pathId");

        given()
                .pathParam("pathId", pathId)
                .when()
                .get("/paths/{pathId}")
                .prettyPeek()
                .then()
                .statusCode(200);

        ArrayList myList = new ArrayList();

        myList.add(pathId);
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

    @Test
    public void DeletePath_ValidPaths_DeletePathValFail() {

        ArrayList myList = new ArrayList();

        myList.add(12);
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
