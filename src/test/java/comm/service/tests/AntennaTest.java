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



}



