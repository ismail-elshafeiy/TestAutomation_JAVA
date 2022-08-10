package com.practice.api.graphQL;

public class Schema {
    public static String LAUNCHPAST_QUERY = "{launchesPast(limit: 10) {rocket {rocket_name} ships {name home_port image}}}";
    public static String INSERTUSER_MUTATION = """
            mutation  ($name: String,$rocket:String) {
              insert_users(objects: {name: $name, rocket: $rocket, twitter: "test"}) {
                affected_rows
                returning {
                  id
                  name
                  rocket
                  timestamp
                  twitter
                }
              }
            }""";


}
