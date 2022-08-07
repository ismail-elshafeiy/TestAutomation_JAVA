package com.practice.api.rest.travels;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import engine.PropertiesReader;
import engine.api.RestApiActions;

public class RestApiBase {
    private RestApiActions apiObject;
    public static final String BASE_URL = PropertiesReader.getProperty(
            "project.properties", "rest.baseUrl");

    public enum StatusCode {
        SUCCESS(200), SUCCESS_DELETE(201);
        private int code;

        StatusCode(int code) {
            this.code = code;
        }

        protected int getCode() {
            return code;
        }
    }


    public enum status {
        SUCCESS("Success"), FAIL("Fail");
        private String status;

        status(String status) {
            this.status = status;
        }

        protected String getStatus() {
            return status;
        }
    }

    // services names
    private String auth_serviceName = "auth";

    public RestApiBase(RestApiActions apiObject) {
        this.apiObject = apiObject;
    }
    //  Requests

    @Step("Get Access Token with Data --> Username: [{username}] and Password: [{password}]")
    public String getAccessToken(String username, String password) {
        JSONObject authentication = new JSONObject();
        authentication.put("username", username);
        authentication.put("password", password);
        Response response = apiObject.performRequest(
                RestApiActions.RequestType.POST,
                auth_serviceName,
                StatusCode.SUCCESS.getCode(),
                null,
                ContentType.JSON,
                null,
                null,
                authentication,
                null);

        return RestApiActions.getResponseJsonValue(response, "token");

    }


}
