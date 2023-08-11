package com.practice.api.rest.travels;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import engine.apiActions.RestApiActions;

import java.util.HashMap;
import java.util.Map;

public class BookingApis {

    private RestApiActions apiObject;
    private String token;
    private String booking_serviceName = "booking";

    public BookingApis(RestApiActions apiObject, String token) {
        this.apiObject = apiObject;
        this.token = token;
    }


    public Response createBooking(String firstName, String lastName, int totalPrice, boolean depositPaid, String checkIn, String checkOut, String additionalNeeds) {
        return apiObject.performRequest(RestApiActions.RequestType.POST,
                booking_serviceName,
                RestApiBase.StatusCode.SUCCESS.getCode(),
                null,
                ContentType.JSON,
                null,
                null,
                createBookingBody(firstName, lastName, totalPrice, depositPaid, checkIn, checkOut, additionalNeeds),
                null);
    }

    public Response getBooking(String bookingId) {

        return apiObject.performRequest(RestApiActions.RequestType.GET,
                booking_serviceName + "/" + bookingId,
                RestApiBase.StatusCode.SUCCESS.getCode(),
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public Response getBookingIds(String firstName, String lastName) {

        return apiObject.performRequest(RestApiActions.RequestType.GET,
                booking_serviceName + "?firstname=" + firstName + "&lastname=" + lastName,
                RestApiBase.StatusCode.SUCCESS.getCode(),
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public Response deleteBooking(String bookingId) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Cookie", "token= " + token);

        return apiObject.performRequest(RestApiActions.RequestType.DELETE,
                booking_serviceName + "/" + bookingId,
                RestApiBase.StatusCode.SUCCESS_DELETE.getCode(),
                headers,
                null,
                null,
                null,
                null,
                null);
    }


    ////////////////// Apis Objects ////////////////////////
    @SuppressWarnings("unchecked")
    private JSONObject createBookingBody(String firstName, String lastName, int totalPrice, boolean depositePaid,
                                         String checkIn, String checkOut, String additionalNeeds) {
        JSONObject createBookingBody = new JSONObject();
        createBookingBody.put("firstname", firstName);
        createBookingBody.put("lastname", lastName);
        createBookingBody.put("totalprice", totalPrice);
        createBookingBody.put("depositpaid", depositePaid);
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", checkIn);
        bookingDates.put("checkout", checkOut);
        createBookingBody.put("bookingdates", bookingDates);
        createBookingBody.put("additionalneeds", additionalNeeds);

        return createBookingBody;
    }


}

