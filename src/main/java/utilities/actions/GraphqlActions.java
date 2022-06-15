package utilities.actions;

import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class GraphqlActions {

    // ******* variables *******//
    private static org.json.JSONObject jsonObject = new org.json.JSONObject();
    private static RequestSpecification request = RestAssured.given();
    public static final String BASE_URI = "https://api.spacex.land/graphql/";

    // ******* request related methods *******//
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
        request.header("Content-Type", "application/json");
        request.given().log().all();
    }

    private static Response apiResponseHelper() {

        Response response = request.post(BASE_URI);
        System.out.println("<<< getting graphql response <<<");
        response.then().statusCode(200).log().body();
        return response;

    }

    /**
     * Send Graphql Request Using "Query or Mutation" only
     *
     * @param query or mutation
     * @return Graphql Response
     */
    public static Response sendGraphqlRequest(String query) {

        GraphqlActions.setQuery(query);
        GraphqlActions.apiRequestHelper();
        String requestBody = GraphqlActions.getRequestBody();
        request.body(requestBody);
        return GraphqlActions.apiResponseHelper();

    }

    /**
     * Send Graphql Request Using "Query or Mutation" and Variables
     * @param query or mutation
     * @param variables
     * @return Graphql Response
     */
    public static Response sendGraphqlRequest(String query, String variables) {

        GraphqlActions.setQuery(query);
        GraphqlActions.setVariables(variables);
        GraphqlActions.apiRequestHelper();
        String requestBody = GraphqlActions.getRequestBody();
        request.body(requestBody);
        return GraphqlActions.apiResponseHelper();

    }

    /**
     * Send Graphql Request Using "Query or Mutation", Variables, and Fragments.<br>
     *
     * @param query or mutation
     * @param variables
     * @param fragments
     * @return Graphql Response
     */
    public static Response sendGraphqlRequest(String query, String variables, String fragments) {

        GraphqlActions.setQuery(query);
        GraphqlActions.setVariables(variables);
        GraphqlActions.setFragments(fragments);
        GraphqlActions.apiRequestHelper();
        String requestBody = GraphqlActions.getRequestBody();
        request.body(requestBody);
        return GraphqlActions.apiResponseHelper();

    }
    // ******* response related methods *******//

    /**
     * assert that the actual results matches the expected results. the execution
     * will stop once this assertion failed
     *
     * @param response
     * @param actualResult_jsonPath
     * @param expectedResult
     */
    public static void assertGraphqlResponse(Response response, String actualResult_jsonPath, String expectedResult) {
        System.out.println("assert that, '" + actualResult_jsonPath + "' is equal to:" + expectedResult + "");
        response.then().body(actualResult_jsonPath, equalTo(expectedResult));
    }




}
