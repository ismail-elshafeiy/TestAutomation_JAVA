package com.engine.actions;

import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.engine.reports.Attachments;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.cookie.CookieFilter;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.restassured.specification.SpecificationQuerier;
import com.engine.reports.ExtentReport;
import com.engine.reports.CustomReporter;

import java.util.List;
import java.util.Map;

import static org.testng.Assert.fail;


public class RestApiActions {
    private RequestSpecification request;
    private Response response;
    private QueryableRequestSpecification queryableRequestSpecs;
    private SessionFilter sessionFilter;
    private CookieFilter cookieFilter;
    private String baseUrl;

    // HTTP Request Methods
    public enum RequestType {
        POST("POST"), GET("GET"), PUT("PUT"), DELETE("DELETE"), PATCH("PATCH");

        private String value;

        RequestType(String type) {
            this.value = type;
        }

        protected String getValue() {
            return value;
        }
    }

    // Status Code

    // Constructor to initialize the base URL
    public RestApiActions(String baseUrl) {
        this.baseUrl = baseUrl;
        sessionFilter = new SessionFilter();
        cookieFilter = new CookieFilter();
    }

    // Log the request and response
    private RequestSpecification requestSpec = new RequestSpecBuilder()
//	    .setBaseUri(baseURI)
            .log(LogDetail.ALL).build();
    private ResponseSpecification responseSpec = new ResponseSpecBuilder()
//	    .expectStatusCode(200)
            .log(LogDetail.BODY).build();


    // Create API request
    @Step("Perform API Request for Service Name: [{serviceName}]")
    /**
     *
     * @param requestType
     * @param serviceName
     * @param expectedStatusCode
     * @param headers
     * @param contentType
     * @param formParams
     * @param queryParams
     * @param body
     * @param cookies
     * @return Response
     */
    public Response performRequest(RequestType requestType, String serviceName, int expectedStatusCode,
                                   Map<String, Object> headers, ContentType contentType,
                                   Map<String, Object> formParams,
                                   Map<String, Object> queryParams,
                                   Object body, Map<String, String> cookies) {
        String requestUrl = baseUrl + serviceName;
        request = RestAssured.given().spec(requestSpec);
        queryableRequestSpecs = SpecificationQuerier.query(request);
        CustomReporter.logInfoStep("Request URL: [" + requestUrl + "] | Request Method: [" + requestType.getValue() + "] | Expected Status Code: [" + expectedStatusCode + "]");
        try {
            if (headers != null) {
                request.headers(headers);
                String qHeaders = queryableRequestSpecs.getHeaders().toString();
                Attachments.attachApiRequestToAllureReport("Headers", qHeaders.getBytes());
                ExtentReport.info(MarkupHelper.createCodeBlock("Request Headers: " + "\n" + qHeaders));
            }
            if (contentType != null) {
                request.contentType(contentType);
                String qContentType = queryableRequestSpecs.getContentType();
                Attachments.attachApiRequestToAllureReport("Content Type", qContentType.getBytes());
                ExtentReport.info(MarkupHelper.createCodeBlock("Request Content Type: " + qContentType));
            }
            if (formParams != null) {
                request.formParams(formParams);
                String qFormParams = queryableRequestSpecs.getFormParams().toString();
                Attachments.attachApiRequestToAllureReport("Form params", qFormParams.getBytes());
                ExtentReport.info(MarkupHelper.createCodeBlock("Request Form params: " + "\n" + qFormParams));
            }
            if (queryParams != null) {
                request.queryParams(queryParams);
                String qQueryParams = queryableRequestSpecs.getQueryParams().toString();
                Attachments.attachApiRequestToAllureReport("Query params", qQueryParams.getBytes());
                ExtentReport.info(MarkupHelper.createCodeBlock("Request Query params: " + "\n" + qQueryParams));
            }
            if (body != null) {
                request.body(body);
                String qBody = queryableRequestSpecs.getBody().toString();
                Attachments.attachApiRequestToAllureReport("Body", qBody.getBytes());
                ExtentReport.info(MarkupHelper.createCodeBlock("Request Body: " + "\n" + qBody));
            }
            if (cookies != null) {
                request.cookies(cookies);
                String qCookies = queryableRequestSpecs.getCookies().toString();
                Attachments.attachApiRequestToAllureReport("Cookies", qCookies.getBytes());
                ExtentReport.info(MarkupHelper.createCodeBlock("Request Cookies: " + "\n" + qCookies));
            }
            request.filter(sessionFilter);
            request.filter(cookieFilter);
            switch (requestType) {
                case POST -> response = request.post(requestUrl);
                case GET -> response = request.get(requestUrl);
                case PUT -> response = request.put(requestUrl);
                case DELETE -> response = request.delete(requestUrl);
                case PATCH -> response = request.patch(requestUrl);
            }
            response.then().spec(responseSpec).statusCode(expectedStatusCode);
        } catch (Exception e) {
            CustomReporter.logInfoStep(e.getMessage());
            fail(e.getMessage());
        }
        Attachments.attachApiResponseToAllureReport(response.asByteArray());
        ExtentReport.info(MarkupHelper.createCodeBlock("API Response: " + "\n" + response.asPrettyString()));
        return response;
    }

    public static String getResponseJsonValue(Response response, String jsonPath) {
        String value = "";
        try {
            value = response.jsonPath().getString(jsonPath);
        } catch (ClassCastException | JsonPathException | IllegalArgumentException e) {
            CustomReporter.logInfoStep(e.getMessage());
            fail(e.getMessage());
        }
        if (value != null) {
            return value;
        } else {
            return null;
        }
    }

    public static List<Object> getResponseJsonValueAsList(Response response, String jsonPath) {
        List<Object> listValue = null;
        try {
            listValue = response.jsonPath().getList(jsonPath);
        } catch (ClassCastException | JsonPathException | IllegalArgumentException e) {
            CustomReporter.logInfoStep(e.getMessage());
            fail(e.getMessage());
        }
        if (listValue != null) {
            return listValue;
        } else {
            return null;
        }
    }

    public static String getResponseBody(Response response) {
        return response.getBody().asString();
    }

}
