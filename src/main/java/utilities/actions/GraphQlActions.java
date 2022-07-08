package utilities.actions;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;


public class GraphQlActions {

    // Variables
    private static JSONObject jsonObject = new JSONObject();
    private static RequestSpecification requestSpec = RestAssured.given();
    public static final String BASE_URI = "https://api.spacex.land/graphql/";


    /**
     * Send Graphql Request Using "Query or Mutation" only
     *
     * @param query or mutation
     * @return Graphql Response
     */
    public static Response sendGraphQlRequest(String query) {
        GraphQlActions.setQuery(query);
        GraphQlActions.apiRequestHelper();
        String requestBody = GraphQlActions.getRequestBody();
        requestSpec.body(requestBody);
        return GraphQlActions.apiResponseHelper();
    }

    /**
     * Send GraphQl Request Using "Query or Mutation" only
     *
     * @param query or mutation
     * @return GraphQl Response
     */
    public static Response sendGraphQlRequest(String query, String variables) {
        GraphQlActions.setQuery(query);
        GraphQlActions.setVariables(variables);
        GraphQlActions.apiRequestHelper();
        String requestBody = GraphQlActions.getRequestBody();
        requestSpec.body(requestBody);
        return GraphQlActions.apiResponseHelper();
    }

    /**
     * Send GraphQl Request Using "Query or Mutation" and Variables
     *
     * @param query     or mutation
     * @param variables
     * @return GraphQl Response
     */
    public static Response sendGraphQlRequest(String query, String variables, String fragments) {
        GraphQlActions.setQuery(query);
        GraphQlActions.setVariables(variables);
        GraphQlActions.setFragments(fragments);
        GraphQlActions.apiRequestHelper();
        String requestBody = GraphQlActions.getRequestBody();
        requestSpec.body(requestBody);
        return GraphQlActions.apiResponseHelper();
    }

    public static void assertGraphQlResponse(Response response, String jsonPath, String expectedResponse) {
        response.then().statusCode(200).log().body();
        System.out.println("Assert that : [" + jsonPath + "] is equal to : [" + expectedResponse + "]");
        response.then().body(jsonPath, equalTo(expectedResponse));
    }

    /////////////////////   Methods for Request  /////////////////////////
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
  //TODO : Add method for getAPIDriver(baseClass.BASE_URI);
    private static void setQuery(String query) {
        jsonObject.put("query", query);
    }

    private static void setVariables(String variables) {
        jsonObject.put("variables", variables);
    }

    private static void setFragments(String fragments) {
        jsonObject.put("fragments", fragments);
    }

    private static String getRequestBody() {
        return jsonObject.toString();
    }

    private static void apiRequestHelper() {
        System.out.println(">>> sending graphql request >>>");
        requestSpec.header("Content-Type", "application/json");
        requestSpec.given().log().all();
    }

    private static Response apiResponseHelper() {
        Response response = requestSpec.post(BASE_URI);
        System.out.println("<<< getting graphql response <<<");
        response.then().statusCode(StatusCode.SUCCESS.getCode()).log().body();
        return response;
    }

    private static String getResponseBody(Response response) {
        return response.getBody().asString();
    }


}

