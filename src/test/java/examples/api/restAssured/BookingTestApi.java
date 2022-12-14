package examples.api.restAssured;

import com.practice.api.rest.travels.RestApiBase;
import com.practice.api.rest.travels.BookingApis;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import engine.api.actions.RestApiActions;

import static org.testng.Assert.assertEquals;

public class BookingTestApi {

    private RestApiActions apiObject;
    private RestApiBase apiBase;
    private BookingApis bookingApis;

    @BeforeClass
    public void beforeClass() {
        apiObject = new RestApiActions(RestApiBase.BASE_URL);
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
        Response createBooking = bookingApis.createBooking(firstName, lastName, totalPrice, depositPaid, checkIn, checkOut, additionalNeeds);
        String bookingId = createBooking.jsonPath().getString("bookingid");
        Response getBooking = bookingApis.getBooking(bookingId);
        Assert.assertEquals(RestApiActions.getResponseJsonValue(getBooking, "firstname"), firstName);
        Assert.assertEquals(RestApiActions.getResponseJsonValue(getBooking, "lastname"), lastName);
        Assert.assertEquals(RestApiActions.getResponseJsonValue(getBooking, "bookingdates.checkin"), checkIn);
        Assert.assertEquals(RestApiActions.getResponseJsonValue(getBooking, "bookingdates.checkout"), checkOut);
        Assert.assertEquals(RestApiActions.getResponseJsonValue(getBooking, "additionalneeds"), additionalNeeds);

    }

    @Test(dependsOnMethods = "createBooking")
    public void deleteBooking() {
        Response bookingIds = bookingApis.getBookingIds("ismail", "elshafeiy");
        String firstBookingId = RestApiActions.getResponseJsonValue(bookingIds, "bookingid[0]");
        Response deleteBooking = bookingApis.deleteBooking(firstBookingId);
        assertEquals(RestApiActions.getResponseBody(deleteBooking), "Created");
    }


}
