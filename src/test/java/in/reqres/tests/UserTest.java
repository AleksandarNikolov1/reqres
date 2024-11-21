package in.reqres.tests;

import in.reqres.models.pojo.UserData;
import in.reqres.models.request.RegisterUserRequest;
import in.reqres.models.response.SingleUserResponse;
import in.reqres.models.response.UserListResponse;
import in.reqres.utils.ApiUtil;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class UserTest extends BaseTest {
    private int userId;

    @Test
    public void testListAllAvailableUsers() {
        UserListResponse response =
                given()
                        .queryParam("page", 1)
                        .when()
                        .get("/users")
                        .then()
                        .statusCode(200)
                        .body("data", notNullValue())
                        .extract().as(UserListResponse.class);

        UserData firstUser = response.getData().get(0);
        System.out.println("Extracted user id: " + firstUser.getId());
        System.out.println("Extracted user email: " + firstUser.getEmail());

        List<String> sortedUsers = response.getData()
                .stream()
                .map(UserData::getFirstName)
                .sorted().collect(Collectors.toList());

        System.out.println(String.join(", ", sortedUsers));
    }

    @Test
    public void testGetExtractedUserDetails() {
        SingleUserResponse response =
                given()
                        .when()
                        .pathParam("userId", 2)
                        .when()
                        .get("/users/{userId}")
                        .then()
                        .statusCode(200)
                        .extract().as(SingleUserResponse.class);

        assertEquals(2, response.getData().getId());
        assertEquals("janet.weaver@reqres.in", response.getData().getEmail());

        assertNotNull(response.getSupport().getUrl());
    }

    @Test
    public void testGetNonExistingUser(){
        given()
                .spec(ApiUtil.jsonRequestSpec())
                .when()
                .pathParams("invalidUserId", -1)
                .get("/users/{invalidUserId}")
                .then()
                .statusCode(404)
                .body(equalTo("{}"));
    }

    @Test
    public void testCreateUniqueNewUser() {
        RegisterUserRequest registrationRequest =
                new RegisterUserRequest("eve.holt@reqres.in", "pistol");

        Response response =
                given()
                        .spec(ApiUtil.jsonRequestSpec())
                        .body(registrationRequest)
                        .when()
                        .post("/register")
                        .then()
                        .statusCode(200)
                        .body("id", notNullValue())
                        .body("token", notNullValue())
                        .extract().response();

        userId = response.jsonPath().getInt("id");

    }

    @Test(dependsOnMethods = "testCreateUniqueNewUser")
    public void testDeleteUser() {
        given()
                .spec(ApiUtil.jsonRequestSpec())
                .when()
                .delete("/users/" + userId)
                .then()
                .statusCode(204);
    }
}
