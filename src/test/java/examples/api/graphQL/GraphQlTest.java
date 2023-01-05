package examples.api.graphQL;
import static io.restassured.RestAssured.baseURI;
import com.practice.api.graphQL.GraphQlApiObject;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import engine.api.actions.GraphQlActions;

public class GraphQlTest {
    private GraphQlApiObject graphQlApiObject;

    @BeforeClass
    public void BeforeClass() {
       baseURI = GraphQlApiObject.BASE_URI;
        graphQlApiObject = new GraphQlApiObject();
    }


    @Test
    public void testPatLaunches_checkRocketName_shouldBeFalcon9() {
        Response response = graphQlApiObject.getPastLaunches();
        GraphQlActions.assertGraphQlResponse(response,
                "data.launchesPast[0].rocket.rocket_name", "Falcon 9");
        GraphQlActions.assertGraphQlResponse(response,
                "data.launchesPast[0].ships[0].name", "GO Ms Tree");
    }

    @Test
    public void insertUser_checkuserName_shouldBeTheSame() {

        Response response = graphQlApiObject.insertUser("ismail elshafeiy", "ismail's rocket");
        GraphQlActions.assertGraphQlResponse(response, "data.insert_users.returning[0].name", "ismail elshafeiy");
        GraphQlActions.assertGraphQlResponse(response, "data.insert_users.returning[0].rocket", "ismail's rocket");

    }

}
