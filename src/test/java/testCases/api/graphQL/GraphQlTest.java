package testCases.api.graphQL;

import api.graphQL.GraphQlApiObject;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.actions.GraphQlApiActions;

public class GraphQlTest {
    private GraphQlApiObject graphQlApiObject;

    @BeforeClass
    public void BeforeClass() {
        graphQlApiObject = new GraphQlApiObject();
    }

    @Test
    public void testPatLaunches_checkRocketName_shouldBeFalcon9() {
        Response response = graphQlApiObject.getPastLaunches();
        GraphQlApiActions.assertGraphqlResponse(response,
                "data.launchesPast[0].rocket.rocket_name", "Falcon 9");
        GraphQlApiActions.assertGraphqlResponse(response,
                "data.launchesPast[0].ships[0].name", "GO Ms Tree");
    }


}
