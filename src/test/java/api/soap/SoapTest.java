package api.soap;

import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SoapTest {

    @Test
    public void IsValidISBN13Number() {
        String phoneNumber = "978-1-4612-9090-2";
        // Define the SOAP endpoint URL
        String url = "http://webservices.daehosting.com/services/isbnservice.wso";
        // Define the SOAP XML request body
        String soapRequestBody =
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                        "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "  <soap:Body>" +
                        "    <IsValidISBN13 xmlns=\"http://webservices.daehosting.com/ISBN\">" +
                        "      <sISBN>" + phoneNumber + "</sISBN>" +
                        "    </IsValidISBN13>" +
                        "  </soap:Body>" +
                        "</soap:Envelope>";
        // Send the SOAP request
        Response response = RestAssured.given()
                .header("Content-Type", "text/xml; charset=utf-8")
                .header("SOAPAction", "http://webservices.daehosting.com/ISBN/IsValidISBN13") // SOAPAction header
                .body(soapRequestBody)
                .when()
                .post(url)
                .then()
                .statusCode(200) // Verify the response status code
                .extract().response();
        System.out.println("Response: " + response.asString());
        XmlPath xmlPath = new XmlPath(response.asString());
        // Extract the value of m:IsValidISBN13Result from the response
        String isValidISBN13Result = xmlPath.getString("soap:Envelope.soap:Body");
        // Print the extracted value
        System.out.println("IsValidISBN13Result: " + isValidISBN13Result);
        Assert.assertEquals(isValidISBN13Result, "true");
    }

    @Test
    public void ListOfLanguagesByName() {
        String url = "http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso";
        String soapRequestBody =
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                        "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "  <soap:Body>" +
                        "    <ListOfLanguagesByName xmlns=\"http://www.oorsprong.org/websamples.countryinfo\">" +
                        "    </ListOfLanguagesByName>" +
                        "  </soap:Body>" +
                        "</soap:Envelope>";
        // Send the SOAP request
        Response response = RestAssured.given()
                .header("Content-Type", "text/xml; charset=utf-8")
                .header("SOAPAction", "http://www.oorsprong.org/websamples.countryinfo/ListOfLanguagesByName") // SOAPAction header
                .body(soapRequestBody)
                .when()
                .post(url)
                .then()
                .statusCode(200) // Verify the response status code
                .extract().response();
        // Print the response
        System.out.println("Response: " + response.asString());
        // Convert the response body to XML Path to parse and extract values
        XmlPath xmlPath = new XmlPath(response.asString());
        // Extract specific tag value (ListOfLanguagesByName) from the response
        String tagValue = xmlPath.getString("soap:Envelope.soap:Body.ListOfLanguagesByName");
        // Print the extracted tag value
        System.out.println("Extracted Value: " + tagValue);
        // Validate the value (example: checking if the value is not null or empty)
        if (tagValue != null && !tagValue.isEmpty()) {
            System.out.println("Tag value is valid!");
        } else {
            System.out.println("Tag value is invalid.");
        }
    }
}
