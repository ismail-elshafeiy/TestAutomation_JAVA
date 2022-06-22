package testCases.api.restAssured;

import org.testng.annotations.Test;

import java.util.Date;

import static io.restassured.RestAssured.given;

public class RestAssuredPostRequestDemo {
    Date date = new Date();
    
    String firstname = "test firstname";
    String lastname = "test lastname";
    String phone = "01099999999";
    String email = "test" + date.getTime() + "@test.com";
    String password = "12345678";
    
    @Test
    public void restAssuredGherkinsSyntaxTest1() {
	System.out.println("The mail is: " + email);
	given().
	     formParam("firstname", firstname).
	     formParam("lastname", lastname).
	     formParam("phone", phone).
	     formParam("email", email).
	     formParam("password", password).
	     formParam("confirmpassword", password).
	     log().all().
	when().
	     post("https://www.phptravels.net/account/signup").
	then().
	     assertThat().statusCode(200).
	 and().
             log().body();
	
    }
 
}