package comm.service.tests;

import comm.service.model.RestAssuredConfig;
import org.testng.annotations.Test;

import java.util.Random;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

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

                .body("entity.company.companyId", equalTo(9))
                .body("entity.company.companyName", equalTo("Exalt Communications Inc."))
                .body("entity.modulationType.modulationTypeId", equalTo(2))
                .body("entity.modulationType.modulationType", equalTo("A ­ Adaptive"))

                .body("entity.modulationLimit", equalTo(null))
                .body("entity.architecture.architectureId", equalTo(2))
                .body("entity.architecture.architecture", equalTo("O ­ Outdoor"))
                .body("entity.loFrequency", equalTo(10700.0f))
                .body("entity.hiFrequency", equalTo(11700.0f))
                .body("entity.powerType", equalTo(null))
                .body("entity.stability", equalTo(7.0E-4f))
                .body("entity.dynamicPowerFlag", equalTo(false))
                .body("entity.atpcFlag", equalTo(false))
                .body("entity.atpcType", equalTo(null))
                .body("entity.atpcAlarmFlag", equalTo(false))
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
                .body("entity.modifiedRadio", equalTo(null))
                .body("entity.radioModulations.radioModulationId", hasItem(1))
                .body("entity.radioModulations.modelNumber", hasItem("E11E732-490X1_1"))
                .body("entity.radioModulations[0].throughput", equalTo(262.0f))
                .body("entity.radioModulations[0].coChannelTToI", equalTo(null))
                .body("entity.radioModulations[0].emissionDesignator", equalTo("40M0D7W"))
                .body("entity.radioModulations[0].bandwidth", equalTo(40.0f))
                .body("entity.radioModulations[0].modulationScheme.modulationSchemeId", equalTo(1))
                .body("entity.radioModulations.modulationScheme.modulationSchemeDescription.modulationSchemeDescriptionId[0]", equalTo(13))
                .body("entity.radioModulations.modulationScheme.modulationSchemeDescription.modulationSchemeDescription[0]", equalTo("256 QAM"))
                .body("entity.radioModulations.modulationScheme.modulationSchemeDescription.modulationType[0]", equalTo("Digital"))
                .body("entity.radioModulations.modulationScheme.modulationSchemeType.modulationSchemeTypeId[0]", equalTo(2))
                .body("entity.radioModulations.modulationScheme.modulationSchemeType.modulationSchemeType[0]", equalTo("D ­ Digital"))
                .body("entity.radioModulations.modulationScheme.dfltCoChannelTToI[0]", equalTo(null))


                .body("entity.radioModulations.modulationScheme.numAnalogChannels[0]", equalTo(null))
                .body("entity.radioModulations.modulationScheme.analogBandwidth[0]", equalTo(null))
                .body("entity.radioModulations.modulationScheme.analogPerChannelLoad[0]", equalTo(null))
                .body("entity.radioModulations.modulationScheme.analogPerChannelRmsDev[0]", equalTo(null))
                .body("entity.radioModulations.modulationScheme.analogMinBasebandFreq[0]", equalTo(null))
                .body("entity.radioModulations.modulationScheme.analogMaxBasebandFreq[0]", equalTo(null))

                .body("entity.radioModulations.downThreshold[0]", equalTo(-61.5f))
                .body("entity.radioModulations.rxThreshold[0]", equalTo(-64.5f))
                .body("entity.radioModulations.upThreshold[0]", equalTo(-59.5f))
                .body("entity.radioModulations.dispersiveFm[0]", equalTo(null))
                .body("entity.radioModulations.txPowerMax[0]", equalTo(20.0f))
                .body("entity.radioModulations.txPowerMin[0]", equalTo(-20.0f))
                .body("entity.radioModulations.maxRxSignalDbm[0]", equalTo(-25.0f))
                .body("entity.radioModulations.receiverNoiseFigure[0]", equalTo(null))
                .body("entity.radioModulations.modulationScheme.minAtpcTrigger[0]", equalTo(null))
                .body("entity.radioModulations.modulationScheme.maxAtpcTrigger[0]", equalTo(null))
                .body("entity.radioModulations.modulationScheme.xpif[0]", equalTo(null))
                .body("entity.radioModulations.modulationScheme[0].minSignatureDepth", equalTo(null))
                .body("entity.radioModulations.modulationScheme.maxSignatureDepth[0]", equalTo(null))
                .body("entity.radioModulations.modulationScheme.minSignatureWidth[0]", equalTo(null))
                .body("entity.radioModulations.modulationScheme.maxSignatureWidth[0]", equalTo(null))
                .body("entity.radioModulations.modulationScheme.minDelay[0]", equalTo(null))
                .body("entity.radioModulations.modulationScheme.radioCurveSet[0]", equalTo(null))
                .body("entity.radioModulations.modulationScheme.associatedFixModulation[0]", equalTo(null))
                .body("entity.radioModulations.modulationScheme.radioByRadioId[0]", equalTo(null))
                .body("entity.radioModulations.modulationScheme.enabled[0]", equalTo(null))













        ;

    }


}
