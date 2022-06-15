package api.rest.travels;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import utilities.ExtentReport;
import utilities.PropertiesReader;
import utilities.actions.ApiActions;

public class RestfulBooker_Apis {
    private ApiActions apiObject;
    public static final String BASE_URL = PropertiesReader.getProperty("project.properties",
            "restfulbooker.baseUri");

    // Expected status codes
    public enum Status {
        SUCCESS(200), SUCCESS_DELETE(201);

        private int code;

        Status(int code) {
            this.code = code;
        }

        protected int getCode() {
            return code;
        }
    }

    // Service Names
    private String auth_serviceName = "auth";

    // Constructor
    public RestfulBooker_Apis(ApiActions apiObject) {
        this.apiObject = apiObject;
    }

    //////////////////////////////////////////////////////////////////
    //////////////////////////// Actions ////////////////////////////

    @SuppressWarnings("unchecked")
    @Step("Get Access Token with Data --> Username: [{username}] and Password: [{password}]")
    public String getAccessToken(String username, String password) {
        ExtentReport.info(MarkupHelper.createLabel("Get Access Token", ExtentColor.BLUE));

        JSONObject authentication = new JSONObject();
        authentication.put("username", username);
        authentication.put("password", password);

        Response getToken = apiObject
                .performRequest(ApiActions.RequestType.POST,
                        auth_serviceName,
                        Status.SUCCESS.getCode(),
                        null,
                        ContentType.JSON,
                        null, null,
                        authentication, null);
        return ApiActions.getResponseJsonValue(getToken, "token");
    }
}
