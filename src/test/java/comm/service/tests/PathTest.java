package comm.service.tests;

import com.google.gson.Gson;
import com.jayway.restassured.http.ContentType;
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



        Projects project = new Projects(44, 1, "stPath_ValidPa " + randomNumber6, "PATH");

        projectId = given()
                .contentType(ContentType.JSON)
                .body(project)
                .when()
                .post("/projects")
                .then()
                .statusCode(201)
                .body("entity.projectName", is(project.getProjectName()))//getDefaultCompanyId
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
    public void CopyPath_ValidPaths_PathSaves() {

        Projects project = new Projects(44, 1, "CopyPath_ValidPaths " + randomNumber6, "PATH");

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
                .when()
                .post("/paths/copy/{pathId}")
                .prettyPeek()
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 ")
                .body("entity.pathName", equalTo("Test Path 1"))
                .body("entity.band.bandId", equalTo(2))
                .body("entity.band.band", equalTo(0.96f))
                .body("entity.band.bandDescription", equalTo("960 MHz"))
                .body("entity.segments[0].segmentNumber", equalTo(1))
                .body("entity.segments[0].segmentEnds[0].elevation", equalTo(11.11f))
                .body("entity.segments[0].segmentEnds[0].segmentEndNumber", equalTo(1))
                .body("entity.segments[0].segmentEnds[0].siteName", equalTo("Test API Site 1"))
                .body("entity.segments[0].segmentEnds[0].latitude", equalTo(71.0f))
                .body("entity.segments[0].segmentEnds[0].longitude", equalTo(22.22f))
                .body("entity.segments[0].segmentEnds[0].elevationUS", equalTo(36.45f))
                .body("entity.segments[0].segmentEnds[0].passiveFlag", equalTo(false))
                .body("entity.segments[0].segmentEnds[1].elevation", equalTo(12.12f))
                .body("entity.segments[0].segmentEnds[1].segmentEndNumber", equalTo(2))
                .body("entity.segments[0].segmentEnds[1].siteName", equalTo("Test API Site 2"))
                .body("entity.segments[0].segmentEnds[1].latitude", equalTo(80.0f))
                .body("entity.segments[0].segmentEnds[1].longitude", equalTo(23.23f))
                .body("entity.segments[0].segmentEnds[1].elevationUS", equalTo(39.76f))
                .body("entity.segments[0].segmentEnds[1].passiveFlag", equalTo(false))

        ;

    }






    @Test
    public void SplitPath_ValidPaths_PathSaves() {
        Projects project = new Projects(32, 5, "SplitPath_ValidPaths_PathSavesInput " + randomNumber6, "PATH");

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
                .when()
                .get("/paths/{pathId}")
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
                .body("entity.segments.segmentEnds[0].siteName[1]", is("Test API Site 2"))
                .body("entity.segments.segmentEnds[0].siteName[0]", is("Test API Site 1"))
                .body("entity.segments.segmentEnds[0].latitude[0]", is(71.0f))
                .body("entity.segments.segmentEnds[0].latitude[1]", is(80.0f))
                .body("entity.segments.segmentEnds[0].longitude[0]", is(22.22f))
                .body("entity.segments.segmentEnds[0].longitude[1]", is(23.23f))
                .body("entity.segments.segmentEnds[0].elevationUS[0]", is(36.45f))
                .body("entity.segments.segmentEnds[0].elevationUS[1]", is(39.76f))
                .body("entity.segments.segmentEnds[0].passiveFlag[1]", is(false))
                .body("entity.segments.segmentEnds[0].passiveFlag[0]", is(false));


        ArrayList myList = new ArrayList();
        myList.add(pathId);

        Projects project2 = new Projects(44, 2, "Split_Project " + randomNumber6, "PATH",myList);


        Map<String, ArrayList> mapOfList = new HashMap<String, ArrayList>();
        mapOfList.put("paths", myList);


        given()
                .contentType(ContentType.JSON)
                .body(pathDetailsJson.toString())
                .body(project2)
                .when()
                .post("/projects/split")
                .prettyPeek()
                .then()
                .statusCode(201)
                .statusLine("HTTP/1.1 201 ")
                .body("message", equalTo("Project Split_Project " + randomNumber6 + " created"))
                .body("status", equalTo("Ok"))
                .body("entity.projectName", equalTo("Split_Project "+ randomNumber6))
                .body("entity.createUser.userId", equalTo(1))
                .body("entity.createUser.userName", equalTo("Lego Admin"))
                .body("entity.unitType", equalTo("US"))
                .body("entity.paths.pathName", hasItem("Test Path 1"))
                .body("entity.paths[0].segments[0].segmentEnds.elevation[0]", is(11.11f))
                .body("entity.paths[0].segments[0].segmentEnds.segmentEndNumber[0]", is(1))
                .body("entity.paths[0].segments[0].segmentEnds.siteName[0]", is("Test API Site 1"))
                .body("entity.paths[0].segments[0].segmentEnds.latitude[0]", is(71.0f))
                .body("entity.paths[0].segments[0].segmentEnds.longitude[0]", is(22.22f))
                .body("entity.paths[0].segments[0].segmentEnds.elevation[0]", is(11.11f))
                .body("entity.paths[0].segments[0].segmentEnds.elevationUS[0]", is(36.45f))
                .body("entity.paths[0].segments[0].segmentEnds.passiveFlag[0]", is(false))
                .body("entity.paths[0].segments[0].segmentEnds.elevation[0]", is(11.11f))


                .body("entity.paths.pathName", hasItem("Test Path 1"))
                .body("entity.paths[0].segments[0].segmentEnds.elevation[0]", is(11.11f))
                .body("entity.paths[0].segments[0].segmentEnds.segmentEndNumber[0]", is(1))
                .body("entity.paths[0].segments[0].segmentEnds.siteName[0]", is("Test API Site 1"))
                .body("entity.paths[0].segments[0].segmentEnds.latitude[0]", is(71.0f))
                .body("entity.paths[0].segments[0].segmentEnds.longitude[0]", is(22.22f))
                .body("entity.paths[0].segments[0].segmentEnds.elevation[0]", is(11.11f))
                .body("entity.paths[0].segments[0].segmentEnds.elevationUS[0]", is(36.45f))
                .body("entity.paths[0].segments[0].segmentEnds.passiveFlag[0]", is(false))
                .body("entity.paths[0].segments[0].segmentEnds.elevation[0]", is(11.11f))


        ;

    }

    @Test
    public void GetPath_ValidPaths_PathSaves() {

        Projects project = new Projects(32, 5, "RequiredProjectInput " + randomNumber6, "PATH");

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
                .body("entity.segments.segmentEnds[0].elevation[1]", is(12.12f))
                .body("entity.segments.segmentEnds[0].elevation[0]", is(11.11f))
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

        Projects project = new Projects(14, 4, "_NoElevationSegOne " + randomNumber6, "PATH");

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

        Projects project = new Projects(14, 3, "NoElevationSegTwo_ " + randomNumber6, "PATH");

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

        Projects project = new Projects(12, 2, "_NoLatitudeSegOne " + randomNumber6, "PATH");

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

        Projects project = new Projects(21, 1, "NoLatitudeSegTwo " + randomNumber6, "PATH");

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

        Projects project = new Projects(32, 5, "NoLongitudeSegOne_ " + randomNumber6, "PATH");

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

        Projects project = new Projects(23, 4, "NoLongitudeSegTwo_ " + randomNumber6, "PATH");

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

        Projects project = new Projects(22, 3, "NoSiteNameSegOne " + randomNumber6, "PATH");

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

        Projects project = new Projects(18, 2, "_NoSiteNameSegTwo " + randomNumber6, "PATH");

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

        Projects project = new Projects(17, 1, "DeletePath_ " + randomNumber6, "PATH");

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
    public void DeletePath_PathAlreadyDeactivated_UnableToDeletePath() {

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
                .statusCode(404)
                .body("message", is("Unable to delete path with pathId 12 - Path Id is already deactivated"));
    }


}
