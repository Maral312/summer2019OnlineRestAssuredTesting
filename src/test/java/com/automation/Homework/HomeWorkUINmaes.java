package com.automation.Homework;

import com.automation.pojos.*;
import com.automation.utilities.APIUtilities;
import com.automation.utilities.ExcelUtil;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;

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

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class HomeWorkUINmaes {

    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("ui.names.uri");

    }
    @Test
    @DisplayName("Verify that name, surname, gender, region fields have value")
    public void test1(){
        Response response =
                given().
                        header("Accept", "application/json").header("Accept","charset=utf-8").
               when().
                        get().prettyPeek();
        response.then().assertThat().statusCode(200).
                body("name", not(empty())).
                body("surname", not(empty())).
                body("gender",not(empty())).
                body("region",not(empty()));
    }
    @Test
    @DisplayName("Gender test")
    public void test2(){
        String gender = "female";
        Response response = given().
                header("Accept", "application/json").header("Accept","charset=utf-8").
                queryParam("gender",gender).
                when().
                get().prettyPeek();

        response.then().assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                body("gender", is(gender));
    }
    @Test
    @DisplayName("2 params test ")
    public void test3(){
        String gender = "female";
        String region = "Kyrgyz Republic";

        Response response = given().
                header("Accept", "application/json").header("Accept","charset=utf-8").
                queryParam("gender",gender).
                queryParam("region",region).
                when().
                get().prettyPeek();

        response.then().assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                body("gender", is(gender)).
                body("region",is(region));
//
//        String actualGender = response.jsonPath().getString("gender");
//        String actualRegion = response.jsonPath().getString("region");
//        assertEquals(gender, actualGender, "Gender is wrong!");
//        assertEquals(region, actualRegion, "Region is wrong!");

    }
    @Test
    @DisplayName("Invalid gender test ")
    public void test4(){

        String gender ="invalid";
        Response response = given().
                header("Accept", "application/json").
                queryParam("gender",gender).

                when().
                get().prettyPeek();
        response.then().assertThat().
                statusCode(400).
                contentType(ContentType.JSON).
                body("error",containsString("Invalid gender"));
    }
    @Test
    @DisplayName("Invalid region test")
    public void test5(){
        String region = "invalid";

        Response response = given().
                header("Accept", "application/json").
                queryParam("region",region).

                when().
                get().prettyPeek();
        response.then().assertThat().
                statusCode(400).
                contentType(ContentType.JSON).
                body("error",containsString("Region or language not found"));
    }
    @Test
    @DisplayName("Amount and regions test ")
    public void test6(){

        Response response = given().
                header("Accept", "application/json").header("Accept","charset=utf-8").
                queryParam("region","Italy").
                queryParam("amount",10).
                when().
                get().prettyPeek();

        response.then().assertThat().
                statusCode(200).
                contentType(ContentType.JSON);

        List<User> people = response.jsonPath().getList("",User.class);

        for(User each : people){
            each.printFullName();
        }
        boolean hasDuplicates = APIUtilities.hasDuplicates(people);
        assertFalse(hasDuplicates, "It has duplicates");

    }
    @Test
    @DisplayName("3 params test")
    public void test7(){

        Response response =
                given().
                header("Accept", "application/json").header("Accept","charset=utf-8").
                        queryParam("region","Italy").
                        queryParam("gender","female").
                        queryParam("amount",10).
                when().
                        get().prettyPeek();

        response.then().assertThat().
                          statusCode(200).
                          contentType(ContentType.JSON).
                body("gender", everyItem(is("female"))).
                body("region", everyItem(is("Italy")));
                //body("amount", everyItem(is(10)));
    }
    @Test
    @DisplayName("Amount count test ")
    public void test8(){
        int count =10;

        Response response =
                given().
                        header("Accept", "application/json").header("Accept","charset=utf-8").
                        queryParam("amount",10).
                when().get().prettyPeek();
        response.then().assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                body("size()", is(count));



    }
}
