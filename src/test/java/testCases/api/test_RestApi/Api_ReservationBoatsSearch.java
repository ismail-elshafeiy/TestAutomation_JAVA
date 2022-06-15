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
public class Api_ReservationBoatsSearch {
    private ApiActions apiObject;
    private PhptravelsApis phptravelsApis;
    private ExcelFileManager spreadSheet;

    @BeforeClass
    public void beforeClass() {
        spreadSheet = new ExcelFileManager(new File("src/test/resources/TestData/PhpTravels_ReservationBoatsSearch_TestData.xlsx"));
        spreadSheet.switchToSheet("API");

        apiObject = new ApiActions(PhptravelsApis.BASE_URL);
        phptravelsApis = new PhptravelsApis(apiObject);
    }

    @Test(description = "PHPTRAVELS - API - Validating the search function of the Boats")
    @Description("Given I'm on the PHPTravels home page; When I enter the data needed to search for Boats And click the search button; Then I should be navigated to the Boats search results page, Then I should get the search results related to the search value entered")
    public void testingBoatsSearch() {
        Response hotel = phptravelsApis.boatsSearch(spreadSheet.getCellData("Country Name", 2),
                spreadSheet.getCellData("City Name", 2),
                spreadSheet.getCellData("Boat Name", 2),
                spreadSheet.getCellData("Boat Date", 2),
                spreadSheet.getCellData("Adults Count", 2));

        Assert.assertTrue(hotel.getBody().asString().contains(spreadSheet.getCellData("Expected Boat Name", 2)),

                "No/Wrong Boat Name!; The Hotel Name should be: [" + spreadSheet.getCellData("Expected Boat Name", 2) + "]");

    }
}
