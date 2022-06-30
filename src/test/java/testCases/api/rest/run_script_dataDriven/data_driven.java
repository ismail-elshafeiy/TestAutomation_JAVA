package testCases.api.rest.run_script_dataDriven;

import static io.restassured.RestAssured.get;

import io.restassured.path.xml.XmlPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.dataDriven.ExcelFileManager;

public class data_driven {
    ExcelFileManager spreedSheet;
    // dataProvider = Function Name
    @Test(dataProvider = "test_data")
    public void dataDriven_testcase(String customer_id,
                                    String firstName,
                                    String lastName,
                                    String street,
                                    String city,
                                    String state,
                                    String phoneNumber,
                                    String ssn,
                                    String zipcode) {
        String xml = get("https://parabank.parasoft.com/parabank/services/bank/customers/" + customer_id + "/").andReturn().asString();
        // Recognize the main tag
        XmlPath xmlpath = new XmlPath(xml).setRoot("customer");
        // store the value of id
        String cus_id = xmlpath.getString("id");
        String f_name = xmlpath.getString("firstName");
        String l_name = xmlpath.getString("lastName");
        String trt = xmlpath.getString("address.street");
        String cty = xmlpath.getString("address.city");
        String stt = xmlpath.getString("address.state");
        String p_num = xmlpath.getString("address.phoneNumber");
        String s_sn = xmlpath.getString("address.ssn");
        String zip = xmlpath.getString("address.zipCode");
        Assert.assertEquals(cus_id,customer_id);

    }

/*    @DataProvider
    public void Object[][] test_data() throws IOException, InvalidFormatException {
      spreedSheet = new ExcelFileManager(new File("src/test/resources/TestData/Book1.xlsx"));
      return
    }*/
}
