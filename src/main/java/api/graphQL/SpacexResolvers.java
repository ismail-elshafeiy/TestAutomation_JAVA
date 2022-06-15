package api.graphQL;

public class SpacexResolvers {
	
	public final static String LAUNCHPAST_QUERY = "{launchesPast(limit: 10) {rocket {rocket_name}\r\n"
			+ "ships {name home_port image}}}";
	
	public final static String INSERTUSER_MUTATION =
			"mutation  ($name: String,$rocket:String) {\r\n"
			+ "  insert_users(objects: " +
					"{name: $name, rocket: $rocket, twitter: \"test\"}) {\r\n"
			+ "    affected_rows\r\n"
			+ "    returning {\r\n"
			+ "      id\r\n"
			+ "      name\r\n"
			+ "      rocket\r\n"
			+ "      timestamp\r\n"
			+ "      twitter\r\n"
			+ "    }\r\n"
			+ "  }\r\n"
			+ "}";

}
