package in.reqres.tests;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {

    public static final String BASE_URL = "https://reqres.in/api";

    @BeforeMethod
    public void setUp(){
        RestAssured.baseURI = BASE_URL;
        RestAssured.config = RestAssuredConfig.config()
                .logConfig(LogConfig.logConfig()
                        .enableLoggingOfRequestAndResponseIfValidationFails());
    }
}
