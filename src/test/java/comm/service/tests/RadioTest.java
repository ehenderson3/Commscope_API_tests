package comm.service.tests;

import com.jayway.restassured.http.ContentType;
import comm.service.model.RadioModel;
import comm.service.model.RestAssuredConfig;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
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


    //COM-540

    /**
     * To test RadioViewDto please do the following:
     Create Radio Favorite via the POST /radios/create-favorite
     {
     "radioId": 2,
     "radioModulations": [15, 16],
     "createUserId" : 1
     }
     */

    @Test
    public void postRadioFav_WhenFavoriteIsCreated16_AFavIdWillBeCreated(){
        List<Integer> myList = new ArrayList();
        myList.add(16);

        RadioModel radio = new RadioModel(2, myList, 1);

        given()
                .contentType(ContentType.JSON)
                .body(radio)
                .when()
                .post("/radios/create-favorite")
                .prettyPeek()
                .then()
                .statusCode(201)
                .body("entity.radioCode",equalTo("X06356"))
                .body("entity.radioFavorite.radioFavoriteId",equalTo(5))
                .body("entity.radioFavorite.favoriteRadioCode",equalTo("X06356-1"));
    }

    @Test
    public void postRadioFav_WhenFavoriteIsCreated17_AFavIdWillBeCreated(){
        List<Integer> myList = new ArrayList();
        myList.add(17);

        RadioModel radio = new RadioModel(2, myList, 1);

        given()
                .contentType(ContentType.JSON)
                .body(radio)
                .when()
                .post("/radios/create-favorite")
                .prettyPeek()
                .then()
                .statusCode(201)
                .body("entity.radioCode",equalTo("X06356"))
                .body("entity.radioFavorite.radioFavoriteId",equalTo(6))
                .body("entity.radioFavorite.favoriteRadioCode",equalTo("X06356-2"));
    }

    /**COM-540
     * Another item this ticket provides is with validating when a RadioFavorite is used that the modulations added to the segment end belong to the RadioFavorite set.
     In the example below, we are creating a path that has a segment end using radioFavoriteId of 12, the newly created radioFavoriteId in the example from the comment above.
     We are setting the radioModulationConfiguration object with radioModulationId of 14. Note that the radioFavorite we created uses radioModulationId 15 and 16, not 14.
     */
    @Test
    public void postRadioFav_WhenModBelongToAFavSet_RadioModulationsSelectedDoNotBelongRadioFavorite(){
        List<Integer> myList = new ArrayList();
        myList.add(14);

        RadioModel radio = new RadioModel(1, myList, 1);

        given()
                .contentType(ContentType.JSON)
                .body(radio)
                .when()
                .post("/radios/create-favorite")
                .prettyPeek()
                .then()
                .statusCode(422)
                .body("message",equalTo("Radio Modulations in the Favorite need to belong to the Radio Code: X11A22"));

    }


    //TODO Sprint 14
    /**
     * This ticket also add the ability to create a path with it's segment ends using a radioFavorite. Example below shows the create json object to create a path with segment ends using radioFavorites.
     NOTE: There is no radioId but instead a radioFavoriteId. The application will use the radio associated with the radioFavorite.
     */
    @Test
    public void GetRadios_ValidRadioId_ReturnRadioDataRelatedToSpecificID() {

        given()
                .pathParam("radioId","1")
                .when()
                .get("/radios/{radioId}")
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("message", equalTo("Successfully retrieved Radio"))
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
