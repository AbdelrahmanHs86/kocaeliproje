package com.kocaelitest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import org.json.JSONObject;

/**
 * Unit test for simple App.
 */


public class AppTest {

    @Before
    public void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void testGetRequest() {
        given().
        when().
            get("/posts/1").
        then().
            assertThat().
            statusCode(200).
            and().
            time(lessThan(3000L)).
            and().
            body("userId", equalTo(1));
    }

    @Test
    public void testPostRequest() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        JSONObject requestParams = new JSONObject();
        requestParams.put("title", "foo");
        requestParams.put("body", "bar");
        requestParams.put("userId", 1);

        given().
            header("Content-Type", "application/json").
            body(requestParams.toString()).
        when().
            post("/posts").
        then().
            assertThat().
            statusCode(201).
            and().
            time(lessThan(3000L)).
            and().
            body("title", equalTo("foo"));
    }
}

