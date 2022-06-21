package testCases.api.rest;

import api.rest.travels.RestApiBase;
import api.rest.travels.BookingApis;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.actions.ApiActions;

import static org.testng.Assert.assertEquals;

public class BookingTestApi {

    private ApiActions apiObject;
    private RestApiBase apiBase;
    private BookingApis bookingApis;

    @BeforeClass
    public void beforeClass() {
        apiObject = new ApiActions(RestApiBase.BASE_URL);
        apiBase = new RestApiBase(apiObject);
        String token = apiBase.getAccessToken("admin", "password123");
        bookingApis = new BookingApis(apiObject, token);
    }

    @Test
    public void createBooking() {
        String firstName = "ismail", lastName = "elshafeiy", checkIn = "2022-07-01", checkOut = "2022-07-01",
                additionalNeeds = "additional needs - 12345";
        int totalPrice = 100;
        boolean depositPaid = true;
        Response createBooking = bookingApis.createBooking(
                firstName,
                lastName,
                totalPrice,
                depositPaid,
                checkIn,
                checkOut,
                additionalNeeds);
        String bookingId = createBooking.jsonPath().getString("bookingid");
        Response getBooking = bookingApis.getBooking(bookingId);
        Assert.assertEquals(ApiActions.getResponseJsonValue(getBooking, "firstname"), firstName);
        Assert.assertEquals(ApiActions.getResponseJsonValue(getBooking, "lastname"), lastName);
        Assert.assertEquals(ApiActions.getResponseJsonValue(getBooking, "bookingdates.checkin"), checkIn);
        Assert.assertEquals(ApiActions.getResponseJsonValue(getBooking, "bookingdates.checkout"), checkOut);
        Assert.assertEquals(ApiActions.getResponseJsonValue(getBooking, "additionalneeds"), additionalNeeds);

    }

    @Test(dependsOnMethods = "createBooking")
    public void deleteBooking() {
        Response bookingIds = bookingApis.getBookingIds("ismail", "elshafeiy");
        String firstBookingId = ApiActions.getResponseJsonValue(bookingIds, "bookingid[0]");
        Response deleteBooking = bookingApis.deleteBooking(firstBookingId);
        assertEquals(ApiActions.getResponseBody(deleteBooking), "Created");
    }


}
