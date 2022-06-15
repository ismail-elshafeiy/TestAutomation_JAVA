package testCases.api.test_RestApi;
import api.rest.travels.PhptravelsApis;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.actions.ApiActions;
import utilities.dataDriven.ExcelFileManager;
import java.io.File;

@Epic("PHPTRAVELS")
@Feature("API")
public class Api_ReservationHotelsSearch {
    private ApiActions apiObject;
    private PhptravelsApis phptravelsApis;
    private ExcelFileManager spreadSheet;

    @BeforeClass
    public void beforeClass() {
        spreadSheet = new ExcelFileManager(
                new File("src/test/resources/TestData/PhpTravels_ReservationHotelsSearch_TestData.xlsx"));
        spreadSheet.switchToSheet("API");

        apiObject = new ApiActions(PhptravelsApis.BASE_URL);
        phptravelsApis = new PhptravelsApis(apiObject);
    }

    @Test(description = "PHPTRAVELS - API - Validating the search function of the Hotels")
    @Description("Given I'm on the PHPTravels home page; When I Enter the data needed to search for hotels And click the search button; Then I should be navigated to the hotels search results page, Then I should get the search results related to the search value entered")
    @Story("Reservation Search")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("Test_case")
    @Issue("Software_bug")
    public void testingHotelsSearch() {
        Response hotel = phptravelsApis.hotelsSearch(spreadSheet.getCellData("City Name", 2),
                spreadSheet.getCellData("Hotel Name", 2), spreadSheet.getCellData("Check In Date", 2),
                spreadSheet.getCellData("Check Out Date", 2), spreadSheet.getCellData("Adults Count", 2),
                spreadSheet.getCellData("Child Count", 2));
        Assert.assertTrue(hotel.getBody().asString().contains(spreadSheet.getCellData("Expected Hotel Name", 2)),
                "No/Wrong Hotel Name!; The Hotel Name should be: [" + spreadSheet.getCellData("Expected Hotel Name", 2)
                        + "]");

    }
}
