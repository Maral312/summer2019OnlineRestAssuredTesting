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


public class CalendarificTestAPIKey {

    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("calendarific.uri");
    }
/**
 * API key is a secret that the API generates and gives to the developer.
 * API key looks like long string: ebe88078e981c84bedeb9e8a34ad927560e545f2
 * API key can go as query parameter or inside a header,
 * it depends on web service how you must pass API key (as header parameter or query parameter)
 * How it get's created? You go to web site, register, and service gives you API key
 * Then you have to pass API key alongside with every request
 * API key is easy to implement for developer and client
 * But, non-technical people have no idea about this
 * So it's mostly used by developers only
 */
    /**
     * ###### TASK ###########
     * Given accept content type as JSON
     * When user sends GET request to "/countries"
     * Then user verifies that status code is 401
     * And user verifies that status line contains "Unauthorized" message
     * And user verifies that meta.error_detail contains "Missing or invalid api credentials." message
     */

    @Test
    @DisplayName("Verify that user cannot access web service without valid API key")
    public void test1() {
        given().
                accept(ContentType.JSON).
                when().
                get("/countries").prettyPeek().
                then().assertThat().
                statusCode(401).
                statusLine(containsString("Unauthorized")).
                body("meta.error_detail", containsString("Missing or invalid api credentials."));
    }

    @Test
    @DisplayName("user verifies that status line contains \"OK\" message")
    public void test2() {

        given().
                accept(ContentType.JSON).
                queryParam("api_key", "ebe88078e981c84bedeb9e8a34ad927560e545f2").
                when().
                get("/countries").prettyPeek().
                then().assertThat().statusCode(200).
                statusLine(containsString("OK")).
                body("response.countries", not(empty()));
    }


/**
 * #######TASK########
 * Given accept content type as JSON
 * And query parameter api_key with valid API key
 * And query parameter country is equals to PL
 * And query parameter type is equals to national
 * And query parameter year is equals to 2019
 * When user sends GET request to "/holidays"
 * Then user verifies that number of national holidays in Poland is equals to 13
 */
    /**
     * Given accept content type as JSON
     * And query parameter api_key with valid API key
     * And query parameter country is equals to US
     * And query parameter type is equals to national
     * And query parameter year is equals to 2019
     * When user sends GET request to "/holidays"
     * Then user verifies that number of national holidays in US is equals to 11
     */
    @Test
    @DisplayName("user verifies that number of national holidays in US is equals to 11")
    public void test3() {
        Response response = given().
                accept(ContentType.JSON).
                queryParam("api_key", "f59f0f225c3539871f305c71b4508fb83a732230").
                queryParam("country", "US").
                queryParam("type", " national").
                queryParam("year", 2019).
                when().
                get("/holidays").prettyPeek();

        //shorter way with syntax sugar (hamcrest matcher)
        response.then().assertThat().statusCode(200).body("response.holidays", hasSize(11));


        //List<Map<String, ?>> - list of objects, since there are nested objects, we cannot specify some value type
        List<Map<String, ?>> holidays = response.jsonPath().get("response.holidays");

        //regular way
        assertEquals(11, holidays.size(), "Wrong number of holidays!");
        assertEquals(200, response.getStatusCode());


    }

    /**
     * ####### WARM-UP TASK ########
     * Given accept content type as JSON
     * And query parameter api_key with valid API key
     * When user sends GET request to "/countries"
     * Then user verifies that total number of holidays in United Kingdom is equals to 95
     * <p>
     * website: https://calendarific.com/
     */
    @Test
    @DisplayName("user verifies that number of national holidays in UK is equals to 95")
    public void test4() {

        Response response =
                given().
                        contentType(ContentType.JSON).
                         queryParam("api_key", "f59f0f225c3539871f305c71b4508fb83a732230").
                         queryParam("country", "UK").
                        queryParam("year",2019).
                when().
                        get("/holidays").prettyPeek();
        response.then().assertThat().statusCode(200).body("response.holidays", hasSize(95));

        List<Map<String, ?>> holidays = response.jsonPath().get("response.holidays");

        //regular way
        assertEquals(95, holidays.size(), "Wrong number of holidays!");
        assertEquals(200, response.getStatusCode());

    }
}