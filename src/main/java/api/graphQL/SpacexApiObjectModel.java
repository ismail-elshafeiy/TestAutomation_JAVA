package api.graphQL;

import io.restassured.response.Response;
import utilities.actions.GraphqlActions;


public class SpacexApiObjectModel {

	// graphql sample request using query only without variables
	/**
	 * get past launches
	 * 
	 * @return past launches response
	 */
	public Response getPastLaunches() {

		return GraphqlActions.sendGraphqlRequest(SpacexResolvers.LAUNCHPAST_QUERY);
	}

	// graphql sample request using mutation with variables
	/**
	 * create user
	 * 
	 * @param name
	 * @param rocket
	 * @return insert user response
	 */
	public Response insertUser(String name, String rocket) {

		String variables = "{\"name\": \"" + name + "\",\"rocket\": \"" + rocket + "\"}";

		return GraphqlActions.sendGraphqlRequest(SpacexResolvers.INSERTUSER_MUTATION, variables);

	}
}
