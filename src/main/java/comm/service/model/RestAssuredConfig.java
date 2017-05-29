package comm.service.model;

import com.jayway.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;

/**
 * Created by ehend on 2/22/2017.
 */
public class RestAssuredConfig {

    @BeforeSuite(alwaysRun = true)
    public void configure() {
        RestAssured.baseURI = "https://comsearch.staging.surgeforward.com";
        RestAssured.port = 8443;

    }
}
