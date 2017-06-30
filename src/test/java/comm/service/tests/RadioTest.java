package comm.service.tests;

import comm.service.model.RestAssuredConfig;
import org.testng.annotations.Test;

import java.util.Random;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RadioTest extends RestAssuredConfig {
    private Random rndNum = new Random();
    private int randomNumber = rndNum.nextInt(1000);


    /*
    MethodName_StateUnderTest_ExpectedBehavior:
    There are arguments against this strategy that if method
    names change as part of code refactoring than test name like this should also change or it becomes
    difficult to comprehend at a later stage. Following are some of the example:
        isAdult_AgeLessThan18_False
        withdrawMoney_InvalidAccount_ExceptionThrown
        admitStudent_MissingMandatoryFields_FailToAdmit
     */

    private int domain = 1;


    @Test
    public void GetRadios_ValidRadioId_ReturnRadioDataRelatedToSpecificID() {

        given()
                .pathParam("radioId","1")
                .when()
                .get("/radios/{radioId}")
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("message", equalTo("Successfully retrieved Antenna"))
                .body("entity.radioCode", equalTo("X11A22"))
                .body("entity.radioId", equalTo(1))
                .body("entity.radioFamily.radioFamilyId", equalTo(24))
                .body("entity.radioFamily.radioFamilyName", equalTo("ExtendAir"))
                .body("entity.radioType.radioTypeId", equalTo(2))
                .body("entity.radioType.radioType", equalTo("D ­ Digital"))

                .body("entity.companyViewDto.companyId", equalTo(9))
                .body("entity.companyViewDto.companyName", equalTo("Exalt Communications Inc."))
                .body("entity.modulationType.modulationTypeId", equalTo(2))
                .body("entity.modulationType.modulationType", equalTo("A ­ Adaptive"))

                .body("entity.modulationLimit", equalTo(null))
                .body("entity.architecture.architectureId", equalTo(2))
                .body("entity.architecture.architecture", equalTo("O ­ Outdoor"))
                .body("entity.loFrequency", equalTo(10700.0f))
                .body("entity.hiFrequency", equalTo(11700.0f))
                .body("entity.powerType", equalTo(null))
                .body("entity.stability", equalTo(7.0E-4f))
                .body("entity.dynamicPowerFlag", equalTo(null))
                .body("entity.atpcFlag", equalTo(false))
                .body("entity.atpcType", equalTo(null))
                .body("entity.atpcAlarmFlag", equalTo(null))
                .body("entity.status.statusId", equalTo(1))
                .body("entity.status.status", equalTo("Active"))
                .body("entity.xpic", equalTo(null))
                .body("entity.accp", equalTo(null))
                .body("entity.acap", equalTo(null))
                .body("entity.duplexMode.duplexModeId", equalTo(1))
                .body("entity.duplexMode.duplexMode", equalTo("F ­ Frequency division"))
                .body("entity.extNarrowBwFilter", equalTo(null))
                .body("entity.specDate", equalTo(null))
                .body("entity.createTimestamp", equalTo(null))
                .body("entity.createUser.userId", equalTo(1))
                .body("entity.createUser.userName", equalTo("Lego Admin"))
                .body("entity.lastModifiedUser.userId", equalTo(1))
                .body("entity.lastModifiedUser.userName", equalTo("Lego Admin"))
                .body("entity.modifiedRadio", equalTo(0))
                .body("entity.radioModulations", equalTo(null))

        ;

    }


}
