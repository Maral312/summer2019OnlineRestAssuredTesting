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

public class BearerTokenTestsWithBookit {
    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("bookit.qa1");
    }
    @Test
    @DisplayName("Get list of rooms")
    public void test1(){
        Response response = given().
                header("Authorization", getToken()).
                when().
                get("/api/rooms").prettyPeek();
    }
    /**
     * Method that generates access token
     * @return bearer token
     */
    public String getToken(){
//https://cybertek-reservation-api-qa.herokuapp.com/sign?email=vasyl@cybertekschool.com&password=cybertek2020

        Response response =
                given().
                    queryParam("email", ConfigurationReader.getProperty("team.leader.email")).
                    queryParam("password",ConfigurationReader.getProperty("team.leader.password")).
                when().get("/sign").prettyPeek();

        return response.jsonPath().getString("accessToken");
    }

}

