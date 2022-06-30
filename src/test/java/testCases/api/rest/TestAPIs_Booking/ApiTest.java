package testCases.api.rest.TestAPIs_Booking;

import static org.hamcrest.Matchers.equalTo;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
public class ApiTest
{

    @Test
    public void validate_response_code_testcase1 (){
        given().get("http://parabank.parasoft.com/parabank/services/bank/customers/12212/").then().assertThat().statusCode(200);

    }

    @Test
    public void validate_response_code_testcase2 (){
        Response resp_body = RestAssured.get("http://parabank.parasoft.com/parabank/services/bank/customers/12212/");
        Assert.assertEquals(resp_body.statusCode(),200);
    }
    @Test
    public void validate_response_data_code_TC3 (){
        given().get("http://parabank.parasoft.com/parabank/services/bank/customers/12212/").then()
                .assertThat().body("customer.id",equalTo("12212"));

    }
}
