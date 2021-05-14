package com.test.api.user;

import com.google.gson.Gson;
import com.test.api.dto.CategoryDTO;
import com.test.api.dto.User;
import com.test.api.execution.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserTest extends BaseTest {
    @Test
    public void addUser() {
        User user = new User("passitplease", null, "frgvsrgb", "fergv@mail.ru",
                "grsbgtb", "gretgrvb", "vgergtrhb",
                "https://i.pinimg.com/originals/d1/5f/fc/d15ffc218562b3368712f2e15a6efa04.jpg",
                null, null);

        Response response = given().spec(defaultRequestSpec())
                .body(user)
                .post(getTestEnvironment().getUserPath())
                .then()
                .extract()
                .response();

        assertThat(response.getStatusCode(), equalTo(201));
        assertThat(response.getBody().as(User.class).getLoginName(), equalTo(user.getLoginName()));
        assertThat(response.getBody().as(User.class).getPassword(), equalTo(user.getPassword()));
        assertThat(response.getBody().as(User.class).getEmail(), equalTo(user.getEmail()));
        assertThat(response.getBody().as(User.class).getFirstName(), equalTo(user.getFirstName()));
        assertThat(response.getBody().as(User.class).getSurname(), equalTo(user.getSurname()));
        assertThat(response.getBody().as(User.class).getMiddleName(), equalTo(user.getMiddleName()));
        assertThat(response.getBody().as(User.class).getId(), instanceOf(Integer.TYPE));


    }

    @Test
    public void editSurnameLastAddedUser() {
        User[] users = given().spec(defaultRequestSpec())
                .when()
                .get(getTestEnvironment().getUsersPath())
                .then()
                .spec(defaultResponseSpec())
                .extract()
                .as(User[].class);

        List<User> userList = Arrays.asList(users);
        User user = userList.get(userList.size() - 1);
        user.setSurname("change");


        Response response = given().spec(defaultRequestSpec())
                .body(user)
                .post(getTestEnvironment().getUserPath())
                .then()
                .extract()
                .response();

        assertThat(response.getStatusCode(), equalTo(201));
        assertThat(response.getBody().as(User.class), equalTo(user));
    }

    @Test
    public void changeSurname() {
        User[] users = given().spec(defaultRequestSpec())
                .when()
                .get(getTestEnvironment().getUsersPath())
                .then()
                .spec(defaultResponseSpec())
                .extract()
                .as(User[].class);

        for (User user : users) {
            user.setSurname("rere");
            Response response = given().spec(defaultRequestSpec())
                    .body(user)
                    .post(getTestEnvironment().getUserPath())
                    .then()
                    .extract()
                    .response();
            assertThat(response.getStatusCode(), equalTo(201));
            assertThat(response.getBody().as(User.class), equalTo(user));
        }

    }

    @Test
    public void deleteUser() {
        given().spec(defaultRequestSpec())
                .delete(getTestEnvironment().getUserPath() + "?id=62")
                .then()
                .extract()
                .response();
    }

    private User getExistingUserById(int id) {
        final String idQueryParam = "id";

        return given().spec(defaultRequestSpec())
                .when().queryParam(idQueryParam, id)
                .get(getTestEnvironment().getUserPath())
                .then()
                .spec(defaultResponseSpec())
                .extract()
                .as(User.class);
    }
}
