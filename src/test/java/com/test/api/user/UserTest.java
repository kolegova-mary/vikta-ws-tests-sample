package com.test.api.user;

import com.google.gson.Gson;
import com.test.api.dto.CategoryDTO;
import com.test.api.dto.User;
import com.test.api.execution.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class UserTest extends BaseTest {
    @Test
    public void addUserAndChangeDetails() {
        User user = new User("try6", null, "frgvsrgb", "fergv@mail.ru",
                "grsbgtb", "gretgrvb", "vgergtrhb",null,null,null);
        given().spec(defaultRequestSpec())
                .body(user)
                .post(getTestEnvironment().getUserPath());
    }

    @Test
    public void editUser(){
        // Give id of user which you want to edit
        User user = new User("try6", 64, "next", "next@mail.ru",
                "next", "next", "next",null,null,null);
        given().spec(defaultRequestSpec())
                .body(user)
                .post(getTestEnvironment().getUserPath())
                .then()
                .extract()
                .response();
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

        for (User user:users){
            user.setSurname("rere");
            given().spec(defaultRequestSpec())
                    .body(user)
                    .post(getTestEnvironment().getUserPath())
                    .then()
                    .extract()
                    .response();
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
