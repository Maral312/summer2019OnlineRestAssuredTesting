package com.automation.tests.day8;
import com.automation.pojos.Spartan;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import com.automation.pojos.Job;
import com.automation.pojos.Location;
import com.automation.utilities.ConfigurationReader;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;


public class OMDBTestsAPIKey {
    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("omdb.uri");
    }

    @Test
    @DisplayName("Verify that unauthorized user cannot get info about movies from OMDB api")
    public void test1(){
        given().
                contentType(ContentType.JSON).
                queryParam("t", "Home Alone").
        when().
                get().prettyPeek().
         then().
                assertThat().statusCode(401).body("Error", is("No API key provided."));
        //401 Unauthorized - you are not allowed to access this web service

    }
    @Test
    @DisplayName("Verify that Macaulay Culkin appears in actors list for Home Alone movie")
    public void test2() {
        Response response =
                given().
                        contentType(ContentType.JSON).
                        queryParam("t", "Home Alone").
                        queryParam("apikey", "9f94d4d0").
                        when().
                        get().prettyPeek();

        response.then().assertThat().statusCode(200).body("Actors", containsString("Macaulay Culkin"));

        Map<String, Object> payload = response.getBody().as(Map.class);

        System.out.println(payload);

        //entry - key=value pair
        //map it's a collection of entries
        //how to iterate a map?
        for (Map.Entry<String, Object> entry : payload.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", value: " + entry.getValue());
        }
    }
    @Test
    @DisplayName("Verify that rate id \"PG\" ")
    public void step3(){
        Response response =
                given().
                        contentType(ContentType.JSON).
                        queryParam("t", "Home Alone").
                        queryParam("apikey", "9f94d4d0").
                when().
                        get().prettyPeek();
        response.then().assertThat().statusCode(200).body("Rated", containsString("PG"));
        Map<String, Object> payload = response.getBody().as(Map.class);

        System.out.println(payload);

        for(Map.Entry<String,Object> entry: payload.entrySet()){
            System.out.println("Key: "+entry.getKey()+", value: "+entry.getValue());
        }

    }



}
