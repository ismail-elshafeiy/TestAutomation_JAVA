package com.practice.api.graphQL;

import io.restassured.response.Response;
import engine.api.actions.GraphQlActions;

public class GraphQlApiObject {
    public static final String BASE_URI = "https://api.spacex.land/graphql/";

    /**
     * get past launches
     *
     * @return past launches response
     */
    public Response getPastLaunches() {
        return GraphQlActions.sendGraphQlRequest(Resolvers.LAUNCHPAST_QUERY);
    }

// graphql sample request using mutation with variables

    /**
     * create user
     *
     * @param name
     * @param rocket
     *
     * @return insert user response
     */
    public Response insertUser(String name, String rocket) {

        String variables = "{\"name\": \"" + name + "\",\"rocket\": \"" + rocket + "\"}";
        return GraphQlActions.sendGraphQlRequest(Resolvers.INSERTUSER_MUTATION, variables);

    }
}
