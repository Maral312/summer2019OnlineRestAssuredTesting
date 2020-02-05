package com.automation.tests.day2;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class ExchangeRatesAPITests {
    private String baseURI = "http://api.openrates.io/";

    @Test
    public void test1(){
        Response response = given().
                get(baseURI+"latest");
        assertEquals(200, response.getStatusCode());
        System.out.println(response.prettyPrint());
    }
    @Test
    public void test2(){
        Response response =  given().get(baseURI+"latest");
        //verify that content type is JSON
        assertEquals(200, response.getStatusCode());
        assertEquals("application/json", response.getHeader("Content-Type"));
        //or like this
        assertEquals("application/json", response.getContentType());
    }
    @Test
    public void test3(){
        //Task: get currency excchange rate for dollar. By default its euro
       // Response response = given().get(baseURI+"latest?base=USD");
        //or
        Response response= given().
                baseUri((baseURI+"latest")).
                queryParam("base", "USD").
                get();
        assertEquals(200,response.getStatusCode());
        System.out.println(response.prettyPrint());


        
        //    #TASK: verify that response body, for latest currency rates, contains today's date (2020-01-23 | yyyy-MM-dd)

    }
    @Test
    public void test4() {
        Response response = given().
                baseUri(baseURI + "latest").
                queryParam("base", "GBP").
                get();

        String todaysDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        System.out.println("Today's date: " + todaysDate);

        assertEquals(200, response.getStatusCode());
        assertTrue(response.getBody().asString().contains(todaysDate));
    }
    @Test
    public void test5(){
        Response response = given().
                baseUri(baseURI+ "history").
                queryParam("start_at", "2000-01-01").
                queryParam("end_at", "2000-12-31").
                queryParam("base", "USD").
                queryParam("symbols", "EUR", "TRL", "JPY").
                get();
        System.out.println(response.prettyPrint());

    }
    @Test
    public void test6(){
        /**
         * Given request parameter "base" is "USD"
         * When user sends request to "api.openrates.io"
         * Then response code should be 200
         * And response body must contain "base": "USD"
         */
        Response response = given().
                baseUri(baseURI + "latest").
                queryParam("base", "USD").
                get();
        String body = response.getBody().asString();
        assertEquals(200, response.getStatusCode());
        assertTrue(body.contains("\"base\":\"USD\""));
    }

}
