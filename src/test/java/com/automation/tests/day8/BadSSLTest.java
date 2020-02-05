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
public class BadSSLTest {
    @BeforeAll
    public static void before(){
        baseURI = "https://untrusted-root.badssl.com/";
    }
    @Test
    @DisplayName("Access web site with bad SSL")
    public void test1(){
        Response response = get().prettyPeek();
        System.out.println(response.statusCode());
    }
    @Test
    @DisplayName("Access web site with bad SSL (solution)")
    public void test2() {
        //.relaxedHTTPSValidation() - ignores SSL issues
        // * Use relaxed HTTP validation with SSLContext protocol SSL.
        // This means that you'll trust all hosts regardless if the SSL certificate is invalid.
        Response response = given().relaxedHTTPSValidation().get().prettyPeek();

        System.out.println(response.statusCode());

        assertEquals(200, response.getStatusCode());
    }
}

