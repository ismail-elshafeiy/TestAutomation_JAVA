package testCases.api.restAssured;

import api.RestfulBooker_Apis;
import api.RestfulBookerApisBooking;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.actions.ApiActions;

import static org.testng.Assert.assertEquals;

@Epic("Restful-Booker")
@Feature("API")
public class Restfulbooker_Tests {
    private ApiActions apiObject;
    private RestfulBooker_Apis restfulBookerApis;
    private RestfulBookerApisBooking restfulBookerApisBooking;

    @BeforeClass
    public void beforeClass() {
        apiObject = new ApiActions(RestfulBooker_Apis.BASE_URL);
        restfulBookerApis = new RestfulBooker_Apis(apiObject);
        String accessToken = restfulBookerApis.getAccessToken("admin", "password123");
        restfulBookerApisBooking = new RestfulBookerApisBooking(apiObject, accessToken);
    }

    @Test(description = "Restful-Booker - API - Create Booking")
    @Description("Creates a new booking in the API")
    public void createBooking() {
        Response createBooking =
                restfulBookerApisBooking
                        .createBooking("Mahmoud", "ElSharkawy", 2000, true,
                                "2021-01-01", "2022-01-01", "Cream Caramel");

        String bookingId = ApiActions.getResponseJsonValue(createBooking, "bookingid");
        Response getBooking = restfulBookerApisBooking.getBooking(bookingId);


        assertEquals(ApiActions.getResponseJsonValue(getBooking, "firstname"), "Mahmoud");
        assertEquals(ApiActions.getResponseJsonValue(getBooking, "lastname"), "ElSharkawy");
        assertEquals(ApiActions.getResponseJsonValue(getBooking, "bookingdates.checkout"), "2022-01-01");
        assertEquals(ApiActions.getResponseJsonValue(getBooking, "additionalneeds"), "Cream Caramel");
    }

    @Test(description = "Restful-Booker - API - Delete Booking", dependsOnMethods = {"createBooking"})
    public void deleteBooking() {
        Response bookingIds = restfulBookerApisBooking.getBookingIds("Mahmoud", "ElSharkawy");
        String firstBookingId = ApiActions.getResponseJsonValue(bookingIds, "bookingid[0]");

        Response deleteBooking = restfulBookerApisBooking.deleteBooking(firstBookingId);
        assertEquals(ApiActions.getResponseBody(deleteBooking), "Created");
    }
}
