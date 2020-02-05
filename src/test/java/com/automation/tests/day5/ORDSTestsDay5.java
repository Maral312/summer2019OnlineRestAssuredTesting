package com.automation.tests.day5;

    import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;

import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

    public class ORDSTestsDay5 {
    @BeforeAll
    public static void setup(){
        baseURI=ConfigurationReader.getProperty("ords.uri");
    }

    @Test
    @DisplayName("\"Verify that average salary is grater than $5000\"")
        public void test1(){
        Response response= given().accept(ContentType.JSON).
                            when().
                                    get("/employees");

        JsonPath jsonPath = response.jsonPath();

        List<Integer> salaries = jsonPath.getList("items.salary");

        int sum = 0;
        //we are finding a sum of all salaries
        for (int salary : salaries) {
            sum += salary;
        }
        //we are calculating average: salary sum/count
        int avg = sum / salaries.size();

        //we are asserting that average salary is grater than 5000
        assertTrue(avg > 5000, "ERROR: actually average salary is lower than 5000: "+avg);

    }

}
