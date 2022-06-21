package api.graphQL;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import utilities.actions.GraphqlActions;

public class GraphQlApiObject {
//    public static final String BASE_URI = "https://api.spacex.land/graphql/";
    /**
     * get past launches
     *
     * @return past launches response
     */
    public Response getPastLaunches() {
        return GraphqlActions.performGraphqlRequest(Resolvers.LAUNCHPAST_QUERY);
    }


}
