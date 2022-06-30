package testCases.api.rest.run_script_jsn;

import static org.hamcrest.Matchers.equalTo;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;


public class TestCase_json
{
  //Smoke test
    @Test
    public void validate_response_code_testcase1 (){
        given().get("https://reqres.in/api/users?page=2").then().assertThat().statusCode(200);
    }

    //positive test case
    @Test
    public void validate_response_data_testcase2 (){
        given().get("https://reqres.in/api/users?page=2").then().assertThat()
                .body("data[0].'email'",equalTo("michael.lawson@reqres.in")).and()
                .body("data[0].'first_name'",equalTo("ismail"));

    }

}
