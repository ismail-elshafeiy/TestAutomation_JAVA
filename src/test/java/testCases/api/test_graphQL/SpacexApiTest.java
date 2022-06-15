package testCases.api.test_graphQL;

import api.graphQL.SpacexApiObjectModel;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.actions.GraphqlActions;


import static io.restassured.RestAssured.baseURI;

public class SpacexApiTest {

	SpacexApiObjectModel spacexApiObjectModel;

	@BeforeClass
	public void beforeClass() {
		baseURI= GraphqlActions.BASE_URI;
		spacexApiObjectModel = new SpacexApiObjectModel();

	}

	@Test
	public void testPatLaunches_checkRocketName_shouldBeFalcon9() {

		Response response = spacexApiObjectModel.getPastLaunches();
		GraphqlActions.assertGraphqlResponse(
				response,"data.launchesPast[0].rocket.rocket_name", "Falcon 9");
	}
	
	@Test
	public void insertUser_checkuserName_shouldBeTheSame() {

		Response response = spacexApiObjectModel.insertUser("ismail elshafeiy","ismail's rocket");
		GraphqlActions.assertGraphqlResponse(
				response,"data.insert_users.returning[0].name", "ismail elshafeiy");
		GraphqlActions.assertGraphqlResponse(
				response,"data.insert_users.returning[0].rocket", "ismail's rocket");

	}
	

	
}
