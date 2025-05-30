package com.kocaelitest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
// import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import static org.hamcrest.Matchers.*;
// import static org.junit.Assert.assertEquals;
import static io.restassured.RestAssured.*;
import org.json.JSONObject;


public class AppTest {

    private static final long MAX_RESPONSE_TIME_MS = 3000;

    @Before
    public void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }


    // ✅ Valid GET Request
    @Test
    public void testGetRequest() {
        Response response = given().
        when().
            get("/posts/1");

        response.
        then().
            statusCode(200).
            time(lessThan(MAX_RESPONSE_TIME_MS)).
            body("userId", equalTo(1)).
            body("id", equalTo(1)).
            body("title", notNullValue());

            System.out.println("✅ GET Testi /posts/1 isteği beklenen yanıtı döndürdü: userId 1 ve boş olmayan başlık.");    
        }
        

    // ❌ Invalid GET Request (Negative Test)
    @Test
    public void testGetInvalidPost() {
        given().
        when().
            get("/posts/0").
        then().
            statusCode(404);

            System.out.println("✅ GET Negatif testi /posts/0 isteği doğru şekilde 404 Not Found döndürdü.");
        }

    // ✅ Valid POST Request
    @Test
    public void testPostRequest() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("title", "kocaeliPost");
        requestParams.put("body", "test body");
        requestParams.put("userId", 1);

        Response res = given().
            header("Content-Type", "application/json").
            body(requestParams.toString()).
        when().
            post("/posts");

        int postId = res.then().
            statusCode(201).
            time(lessThan(MAX_RESPONSE_TIME_MS)).
            body("title", equalTo("kocaeliPost")).
            body("userId", equalTo(1)).
            extract().
            path("id");

            System.out.println("✅ POST Testi /posts başarılı bir şekilde oluşturuldu. Oluşturulan ID: " + postId);
        }

}

