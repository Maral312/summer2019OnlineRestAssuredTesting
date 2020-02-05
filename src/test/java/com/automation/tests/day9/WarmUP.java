package com.automation.tests.day9;

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

public class WarmUP {


/**
 *  ####### WARM-UP TASK ########
 * Given accept content type as JSON
 * And query parameter api_key with valid API key
 *      * When user sends GET request to "/countries"
 *      * Then user verifies that total number of holidays in United Kingdom is equals to 95
 *      *
 *      * website: https://calendarific.com/
 *      *
 *      *
 *      *
 *      */

    @BeforeAll


    public static void setup() {
 baseURI = ConfigurationReader.getProperty("calendarific.uri");
 }
    @Test
    @DisplayName("user verifies that total number of holidays in United Kingdom is equals to 95")
    public void TC1(){
        Response response=
                given().accept(ContentType.JSON).
                        queryParam("api_key","aaf312cd592b0c7b22d90dd82e805dd4cf182dd5")
                        .when().get("/countries").prettyPeek();
        response.then().assertThat().statusCode(200);
        //Gpath - it's like Xpath, stands for searching values in JSON file.
        //GPath: response.countries.find {it.country_name == 'United Kingdom'}.total_holidays
        //find - method to find some parameter
        //{it.parameter_name == value} find JSON object that is matching criteria
        //.parameter_that_you_want = return this parameter after filtering
        int numberHolidays = response.jsonPath().get("response.countries.find {it.country_name == 'United Kingdom'}.total_holidays");

        //get all countries where number supported_languages is = 4

        List<String> countries = response.jsonPath().get("response.countries.findAll {it.supported_languages == 4}.country_name");

        System.out.println(countries);

        assertEquals(95,numberHolidays);
        System.out.println("MY TESTS PASSED SUCCESSFULLY😍😍");
    }

    @Test
    @DisplayName("user verifies that total number of holidays in United Kingdom is equals to 95")
    public void TC1_2(){
        Response response=
                given().accept(ContentType.JSON).
                        queryParam("api_key","aaf312cd592b0c7b22d90dd82e805dd4cf182dd5")
                        .queryParam("country","GB")
                        .queryParam("year",2019)
                        .when().get("/holidays").prettyPeek();
        response.then().assertThat().statusCode(200);
        List<Map<String,?>> holidays=response.jsonPath().get("response.holidays");
        assertEquals(95,holidays.size());
        System.out.println("MY TESTS PASSED SUCCESSFULLY😍😍");
    }

}
