package api.graphQL;

import io.restassured.response.Response;
import utilities.actions.GraphQlApiActions;

public class GraphQlApiObject {
//    public static final String BASE_URI = "https://api.spacex.land/graphql/";
    /**
     * get past launches
     *
     * @return past launches response
     */
    public Response getPastLaunches() {
        return GraphQlApiActions.performGraphqlRequest(Resolvers.LAUNCHPAST_QUERY);
    }


}
