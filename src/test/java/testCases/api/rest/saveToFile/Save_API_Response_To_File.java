package testCases.api.rest.saveToFile;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.get;


public class Save_API_Response_To_File {

    File fileName;

    @Test( groups = {"group1", "gruop2"} )
    public void Producers_API_Response_Check() throws IOException {

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String today = dateFormat.format(date).toString();

        RestAssured.baseURI = "https://vandebron.nl/api/generators/searchPage";

        PrintWriter writer = new PrintWriter("src/test/resources/Files/Producers_API" + today + ".txt", "UTF-8");
        String fileContent = get().getBody().prettyPrint();
        writer.println(fileContent);
        writer.close();

        fileName = new File("Files/producersAPI-" + today + ".txt");
    }

}
