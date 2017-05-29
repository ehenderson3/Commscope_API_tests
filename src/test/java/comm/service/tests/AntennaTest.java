package comm.service.tests;

import comm.service.model.RestAssuredConfig;
import org.testng.annotations.Test;

import java.util.Random;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class AntennaTest extends RestAssuredConfig {
    Random rndNum = new Random();
    int randomNumber = rndNum.nextInt(1000);
    int pathId = 0;
    String siteName1 = null;



    int projectId = 0;

    @Test
    public void getAntennaSpecsSearch_valueV1234FieldantennaCodeOpLike_ResultsetContainsV1234FieldantennaCode(){




        given().urlEncodingEnabled(false)
                .when()
                .pathParam("antId","1")
                .get("/antenna-specs/{antId}")
                .prettyPeek()
                .then()
                .body("message", equalTo("Successfully retrieved Antenna"))
                .body("entity.antennaSpecId", equalTo(1))
                .body("entity.antenna.antennaId", equalTo(1))
                .body("entity.antenna.modelNumber", equalTo("P8F-9"))
                .body("entity.antenna.wideBandInd", equalTo(true))
                .body("entity.antenna.diameter", equalTo(2.44f))
                .body("entity.antenna.classification.classificationId", equalTo(8))
                .body("entity.antenna.classification.classification", equalTo("Solid Parabolic"))
                .body("entity.antenna.classification.classification", equalTo("Solid Parabolic"))
                .body("entity.antenna.polarization.polarizationId", equalTo(1))
                .body("entity.antenna.polarization.polarization", equalTo("D ­ Dual"))
                .body("entity.antenna.antennaFamily.antennaFamilyId", equalTo(3))
                .body("entity.antenna.antennaFamily.antennaFamily", equalTo("Commscope Family"))
                .body("entity.antenna.manufacturer.manufacturerId", equalTo(1))
                .body("entity.antenna.manufacturer.manufacturerName", equalTo("Commscope"))
                .body("entity.antenna.segmentEndAntennasByAntennaId", equalTo(null))
                .body("entity.antenna.antennaApplications.antennaApplicationId[0]", equalTo(1))
                .body("entity.antenna.antennaApplications.antennaApplication[0]", equalTo("MW PtP ­ Microwave Point­to­Point"))
                .body("entity.frequencyRange.loFrequency", equalTo(790.0f))
                .body("entity.frequencyRange.hiFrequency", equalTo(860.0f))
                .body("entity.gain", equalTo(24.9f))
                .body("entity.beamWidth", equalTo(8.1f))
                .body("entity.fbRatio", equalTo(20.0f))
                .body("entity.status.statusId", equalTo(1))
                .body("entity.status.status", equalTo("Active"))
                .body("entity.polVersion", equalTo(null))
                .body("entity.compliantInd", equalTo(null))
                .body("entity.modifiedAntennaSpec", equalTo(0))
                .body("entity.feedDir.feedDirId", equalTo(1))
                .body("entity.feedDir.feedDir", equalTo("LF ­ Left Feed"))

                .body("entity.createUser.userId", equalTo(1))
                .body("entity.createUser.userName", equalTo("Lego Admin"))

                .body("entity.lastModifiedUser.userId", equalTo(1))
                .body("entity.createUser.userName", equalTo("Lego Admin"))

                .body("entity.modifiedAntennaSpecs", hasItem(2))

        ;

    }


    @Test
    public void getAntennaSpecsSearch_valueP8F9FiveldmodelNumberOpEQ_ResultsetContainsP8F9FieldantennaCode(){
        String valueVal = "P8F-9";
        String fieldVal = "modelNumber";
        String operatorVal = "EQ";
        Boolean revisions = true;
    given().urlEncodingEnabled(false)
            .queryParameter("includeRevisions", true)
            .when()
            .log().all()
            .get("/antenna-specs?search=%5B%5B%7B%22value%22%3A%22"+ valueVal +"%22%2C%20" +
                    "%22field%22%3A%22"+ fieldVal +"%22%2C%20%" +
                    "22operator%22%3A%22"+ operatorVal +"%22%7D%5D%" +
                    "5D&includeRevisions="+ revisions +"")
            .prettyPeek()
            .then()
            .body("message", equalTo("Successfully retrieved 7 antennaSpecs"))
            .body("count", equalTo(7))
            .body("entities.antennaSpecId", hasItem(1))
            .body("entities.antenna.antennaId", hasItem(1))
            .body("entities.antenna.modelNumber", hasItem("P8F-9"))
            .body("entities.antenna.wideBandInd", hasItem(true))
            .body("entities.antenna.diameter", hasItem(2.44f))
            .body("entities.antenna.classification.classificationId", hasItem(8))
            .body("entities.antenna.classification.classification", hasItem("Solid Parabolic"))
            .body("entities.antenna.polarization.polarizationId", hasItem(1))
            .body("entities.antenna.polarization.polarization", hasItem("D ­ Dual"))
            .body("entities.antenna.antennaFamily.antennaFamilyId", hasItem(3))
            .body("entities.antenna.antennaFamily.antennaFamily", hasItem("Commscope Family"))
            .body("entities.antenna.manufacturer.manufacturerId", hasItem(1))
            .body("entities.antenna.manufacturer.manufacturerName", hasItem("Commscope"))
            .body("entities.antenna.segmentEndAntennasByAntennaId[0]", equalTo(null))
            .body("entities.antenna.antennaApplications[0].antennaApplicationId[0]", equalTo(1))
            .body("entities.antenna.antennaApplications.antennaApplication[0]", hasItem("MW PtP ­ Microwave Point­to­Point"))
            .body("entities.antennaCode[0]", equalTo("02306AZ"))
            .body("entities.frequencyRange.loFrequency", hasItem(790.0f))
            .body("entities.frequencyRange.hiFrequency", hasItem(860.0f))
            .body("entities.gain", hasItem(24.9f))
            .body("entities.beamWidth", hasItem(8.1f))
            .body("entities.fbRatio", hasItem(20.0f))
            .body("entities.status.statusId", hasItem(1))
            .body("entities.status.status", hasItem("Active"))
            .body("entities.polVersion[0]", equalTo(null))
            .body("entities.compliantInd[0]", equalTo(null))
            .body("entities.modifiedAntennaSpec[0]", equalTo(0))
            .body("entities.feedDir.feedDirId", hasItem(1))
            .body("entities.feedDir.feedDir", hasItem("LF ­ Left Feed"))
            .body("entities.singlePolarization[0]", equalTo(null))
            .body("entities.createUser.userId[0]", equalTo(1))
            .body("entities.createUser.userName[0]", equalTo("Lego Admin"))
            .body("entities.modifiedAntennaSpecs[0]", hasItem(2));
   }

    @Test
    public void getAntennaSpecsSearch_valueP8F9modelNumberOpLike_ResultsetContainsP8F9FieldantennaCode(){
        String valueVal = "P8F";
        String fieldVal = "modelNumber";
        String operatorVal = "LIKE";
        Boolean revisions = true;

        given().urlEncodingEnabled(false)
                .queryParameter("includeRevisions", true)
                .when()
                .log().all()
                .get("/antenna-specs?search=%5B%5B%7B%22value%22%3A%22"+ valueVal +"%22%2C%20" +
                        "%22field%22%3A%22"+ fieldVal +"%22%2C%20%" +
                        "22operator%22%3A%22"+ operatorVal +"%22%7D%5D%" +
                        "5D&includeRevisions="+ revisions +"")
                .prettyPeek()
                .then()
                .body("message", equalTo("Successfully retrieved 7 antennaSpecs"))
                .body("count", equalTo(7))
                .body("entities.antenna[0].modelNumber", equalTo("P8F-9"))
                .body("entities.antenna[1].modelNumber", equalTo("P8F-9"))
                .body("entities.antenna[2].modelNumber", equalTo("P8F-9"))
                .body("entities.antenna[3].modelNumber", equalTo("P8F-9"))
                .body("entities.antenna[4].modelNumber", equalTo("P8F-9"))
                .body("entities.antenna[5].modelNumber", equalTo("P8F-9"))
                .body("entities.antenna[6].modelNumber", equalTo("P8F-9"));

    }


    @Test
    public void getAntennaSpecsSearch_valueP8F9FiveldmodelNumberOpNot_ResultsetContainsNotP8F9FieldantennaCode(){
        String valueVal = "P8F-9";
        String fieldVal = "modelNumber";
        String operatorVal = "NOT";
        Boolean revisions = true;

        given().urlEncodingEnabled(false)
                .queryParameter("includeRevisions", true)
                .when()
                .log().all()
                .get("/antenna-specs?search=%5B%5B%7B%22value%22%3A%22"+ valueVal +"%22%2C%20" +
                        "%22field%22%3A%22"+ fieldVal +"%22%2C%20%" +
                        "22operator%22%3A%22"+ operatorVal +"%22%7D%5D%" +
                        "5D&includeRevisions="+ revisions +"")
                .prettyPeek()
                .then()
                .body("message", equalTo("Successfully retrieved 3 antennaSpecs"))
                .body("count", equalTo(3))
                .body("entities.antennaSpecId", hasItem(6))
                .body("entities.antenna[0].modelNumber", equalTo("VHLPX2-11"))
                .body("entities.antennaSpecId", hasItem(7))
                .body("entities.antenna[1].modelNumber", equalTo("VHLPX2-11"))
                .body("entities.antennaSpecId", hasItem(8))
                .body("entities.antenna[2].modelNumber", equalTo("VHLPX2-11"));

    }

    @Test
    public void getAntennaSpecsSearch_value02306AZantennaCodeOpEQ_ResultsetContains02306AZantennaCode(){
        String valueVal = "02306AZ";
        String fieldVal = "antennaCode";
        String operatorVal = "EQ";
        Boolean revisions = true;

        given().urlEncodingEnabled(false)
                .queryParameter("includeRevisions", true)
                .when()
                .log().all()
                .get("/antenna-specs?search=%5B%5B%7B%22value%22%3A%22"+ valueVal +"%22%2C%20" +
                        "%22field%22%3A%22"+ fieldVal +"%22%2C%20%" +
                        "22operator%22%3A%22"+ operatorVal +"%22%7D%5D%" +
                        "5D&includeRevisions="+ revisions +"")
                .prettyPeek()
                .then()
                .body("message", equalTo("Successfully retrieved 2 antennaSpecs"))
                .body("count", equalTo(2))
                .body("entities.antennaSpecId[0]", equalTo(1))
                .body("entities.antennaCode[0]", equalTo("02306AZ"))
                .body("entities.antennaSpecId[1]", equalTo(2))
                .body("entities.antennaCode[1]", equalTo("02306AY"));

    }

    //TODO to make sure it is ok that 02306AY was returned
    @Test
    public void getAntennaSpecsSsearch_value02306AYantennaCodeOpNOT_ResultsetContains02306AZantennaCode(){
        String valueVal = "02306AY";
        String fieldVal = "antennaCode";
        String operatorVal = "NOT";
        Boolean revisions = true;

        given().urlEncodingEnabled(false)
                .queryParameter("includeRevisions", true)
                .when()
                .log().all()
                .get("/antenna-specs?search=%5B%5B%7B%22value%22%3A%22"+ valueVal +"%22%2C%20" +
                        "%22field%22%3A%22"+ fieldVal +"%22%2C%20%" +
                        "22operator%22%3A%22"+ operatorVal +"%22%7D%5D%" +
                        "5D&includeRevisions="+ revisions +"")
                .prettyPeek()
                .then()
                .body("message", equalTo("Successfully retrieved 8 antennaSpecs"))
                .body("count", equalTo(8))
                .body("entities.antennaCode[0]", equalTo("02306AZ"))
                .body("entities.antennaCode[1]", equalTo("02306AY"))
                .body("entities.antennaCode[2]", equalTo("02306AX"))
                .body("entities.antennaCode[3]", equalTo("02306A"))
                .body("entities.antennaCode[4]", equalTo("02306A"))
                .body("entities.antennaCode[5]", equalTo("77100A"))
                .body("entities.antennaCode[6]", equalTo("XXXXXX"))
                .body("entities.antennaCode[7]", equalTo("YYYYYY"));

    }

    @Test
    public void getAntennaSpecsSsearch_value02306antennaCodeOpLIKE_ResultsetContains02306antennaCode(){
        String valueVal = "02306";
        String fieldVal = "antennaCode";
        String operatorVal = "LIKE";
        Boolean revisions = true;

        given().urlEncodingEnabled(false)
                .queryParameter("includeRevisions", true)
                .when()
                .log().all()
                .get("/antenna-specs?search=%5B%5B%7B%22value%22%3A%22"+ valueVal +"%22%2C%20" +
                        "%22field%22%3A%22"+ fieldVal +"%22%2C%20%" +
                        "22operator%22%3A%22"+ operatorVal +"%22%7D%5D%" +
                        "5D&includeRevisions="+ revisions +"")
                .prettyPeek()
                .then()
                .body("message", equalTo("Successfully retrieved 7 antennaSpecs"))
                .body("count", equalTo(7))
                .body("entities.antennaCode[0]", equalTo("02306AZ"))
                .body("entities.antennaCode[1]", equalTo("02306AY"))
                .body("entities.antennaCode[2]", equalTo("02306AY"))
                .body("entities.antennaCode[3]", equalTo("02306AX"))
                .body("entities.antennaCode[4]", equalTo("02306AX"))
                .body("entities.antennaCode[5]", equalTo("02306A"))
                .body("entities.antennaCode[6]", equalTo("02306A"));
    }

    @Test
    public void getAntennaSpecsSearch_valueCommscopemanufacturerNameOpNOT_ResultsetNOTContains02306Commscope(){
        String valueVal = "Commscope";
        String fieldVal = "manufacturerName";
        String operatorVal = "NOT";
        Boolean revisions = true;

        given().urlEncodingEnabled(false)
                .queryParameter("includeRevisions", true)
                .when()
                .log().all()
                .get("/antenna-specs?search=%5B%5B%7B%22value%22%3A%22"+ valueVal +"%22%2C%20" +
                        "%22field%22%3A%22"+ fieldVal +"%22%2C%20%" +
                        "22operator%22%3A%22"+ operatorVal +"%22%7D%5D%" +
                        "5D&includeRevisions="+ revisions +"")
                .prettyPeek()
                .then()
                .body("message", equalTo("Successfully retrieved 0 antennaSpecs"))
                .body("count", equalTo(0));
    }

    @Test
    public void getAntennaSpecsSearch_value02306antennaCodeOpEQ_ResultsetContainsCommscopemanufacturerName(){
        String valueVal = "Commscope";
        String fieldVal = "manufacturerName";
        String operatorVal = "EQ";
        Boolean revisions = true;
        given().urlEncodingEnabled(false)
                .queryParameter("includeRevisions", true)
                .when()
                .log().all()
                .get("/antenna-specs?search=%5B%5B%7B%22value%22%3A%22"+ valueVal +"%22%2C%20" +
                        "%22field%22%3A%22"+ fieldVal +"%22%2C%20%" +
                        "22operator%22%3A%22"+ operatorVal +"%22%7D%5D%" +
                        "5D&includeRevisions="+ revisions +"")
                .prettyPeek()
                .then()
                .body("message", equalTo("Successfully retrieved 10 antennaSpecs"))
                .body("count", equalTo(10))
                .body("entities.antenna.manufacturer[0].manufacturerName", equalTo("Commscope"))
                .body("entities.antenna.manufacturer[1].manufacturerName", equalTo("Commscope"))
                .body("entities.antenna.manufacturer[2].manufacturerName", equalTo("Commscope"))
                .body("entities.antenna.manufacturer[3].manufacturerName", equalTo("Commscope"))
                .body("entities.antenna.manufacturer[4].manufacturerName", equalTo("Commscope"))
                .body("entities.antenna.manufacturer[5].manufacturerName", equalTo("Commscope"))
                .body("entities.antenna.manufacturer[6].manufacturerName", equalTo("Commscope"))
                .body("entities.antenna.manufacturer[7].manufacturerName", equalTo("Commscope"))
                .body("entities.antenna.manufacturer[8].manufacturerName", equalTo("Commscope"))
                .body("entities.antenna.manufacturer[9].manufacturerName", equalTo("Commscope"));

    }

    @Test
    public void getAntennaSpecsSearch_value02306classificationOpEQ_ResultsetContainsSolidParabolicclassification(){
        String valueVal = "Solid%20Parabolic";
        String fieldVal = "classification";
        String operatorVal = "EQ";
        Boolean revisions = true;
        given().urlEncodingEnabled(false)
                .queryParameter("includeRevisions", true)
                .when()
                .log().all()
                .get("/antenna-specs?search=%5B%5B%7B%22value%22%3A%22"+ valueVal +"%22%2C%20" +
                        "%22field%22%3A%22"+ fieldVal +"%22%2C%20%" +
                        "22operator%22%3A%22"+ operatorVal +"%22%7D%5D%" +
                        "5D&includeRevisions="+ revisions +"")
                .prettyPeek()
                .then()
                .body("message", equalTo("Successfully retrieved 10 antennaSpecs"))
                .body("count", equalTo(10))
                .body("entities.antenna.classification[0].classification", equalTo("Solid Parabolic"))
                .body("entities.antenna.classification[1].classification", equalTo("Solid Parabolic"))
                .body("entities.antenna.classification[2].classification", equalTo("Solid Parabolic"))
                .body("entities.antenna.classification[3].classification", equalTo("Solid Parabolic"))
                .body("entities.antenna.classification[4].classification", equalTo("Solid Parabolic"))
                .body("entities.antenna.classification[5].classification", equalTo("Solid Parabolic"))
                .body("entities.antenna.classification[6].classification", equalTo("Solid Parabolic"))
                .body("entities.antenna.classification[7].classification", equalTo("Solid Parabolic"))
                .body("entities.antenna.classification[8].classification", equalTo("Solid Parabolic"))
                .body("entities.antenna.classification[9].classification", equalTo("Solid Parabolic"));


    }

    @Test
    public void getAntennaSpecsSearch_valueActiveStatusOpEQ_ResultsetContainsActiveStatus(){
        String valueVal = "active";
        String fieldVal = "Status";
        String operatorVal = "EQ";
        Boolean revisions = true;
        given().urlEncodingEnabled(false)
                .queryParameter("includeRevisions", true)
                .when()
                .log().all()
                .get("/antenna-specs?search=%5B%5B%7B%22value%22%3A%22"+ valueVal +"%22%2C%20" +
                        "%22field%22%3A%22"+ fieldVal +"%22%2C%20%" +
                        "22operator%22%3A%22"+ operatorVal +"%22%7D%5D%" +
                        "5D&includeRevisions="+ revisions +"")
                .prettyPeek()
                .then()
                .body("message", equalTo("Successfully retrieved 8 antennaSpecs"))
                .body("count", equalTo(8))
                .body("entities.status[1].status", equalTo("Active"))
                .body("entities.status[2].status", equalTo("Active"))
                .body("entities.status[3].status", equalTo("Active"))
                .body("entities.status[4].status", equalTo("Active"))
                .body("entities.status[5].status", equalTo("Active"))
                .body("entities.status[6].status", equalTo("Active"))
                .body("entities.status[7].status", equalTo("Active"))

;


    }
    @Test
    public void getAntennaSpecsSearch_valueObsoleteStatusOpEQ_ResultsetContainsObsoleteStatus(){
        String valueVal = "obsolete";
        String fieldVal = "Status";
        String operatorVal = "EQ";
        Boolean revisions = true;
        given().urlEncodingEnabled(false)
                .queryParameter("includeRevisions", true)
                .when()
                .log().all()
                .get("/antenna-specs?search=%5B%5B%7B%22value%22%3A%22"+ valueVal +"%22%2C%20" +
                        "%22field%22%3A%22"+ fieldVal +"%22%2C%20%" +
                        "22operator%22%3A%22"+ operatorVal +"%22%7D%5D%" +
                        "5D&includeRevisions="+ revisions +"")
                .prettyPeek()
                .then()
                .body("message", equalTo("Successfully retrieved 1 antennaSpecs"))
                .body("count", equalTo(1))
                .body("entities.status[0].status", equalTo("Obsolete"))
        ;


    }
    @Test
    public void getAntennaSpecsSearch_valueInactiveStatusOpEQ_ResultsetContainsInactiveStatus(){
        String valueVal = "inactive";
        String fieldVal = "Status";
        String operatorVal = "EQ";
        Boolean revisions = true;
        given().urlEncodingEnabled(false)
                .queryParameter("includeRevisions", true)
                .when()
                .log().all()
                .get("/antenna-specs?search=%5B%5B%7B%22value%22%3A%22"+ valueVal +"%22%2C%20" +
                        "%22field%22%3A%22"+ fieldVal +"%22%2C%20%" +
                        "22operator%22%3A%22"+ operatorVal +"%22%7D%5D%" +
                        "5D&includeRevisions="+ revisions +"")
                .prettyPeek()
                .then()
                .body("message", equalTo("Successfully retrieved 0 antennaSpecs"))
                .body("count", equalTo(0))
        ;


    }

}



