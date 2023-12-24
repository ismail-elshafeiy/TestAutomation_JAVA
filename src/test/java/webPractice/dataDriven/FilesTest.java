package webPractice.dataDriven;

import org.testng.annotations.Test;

import java.io.IOException;

import static com.engine.dataDriven.CSVFileManager.compareTwoCSVFiles2;

public class FilesTest {
    @Test
    public void testSuccessfulLogin2() throws IOException {
        String filePath = "src/test/resources/TestData/CSVFile.csv";
        String filePath2 = "src/test/resources/TestData/CSVFile2.csv";
        //new CSVFileManager("src/test/resources/TestData/CSVFile.csv");
//		String text = String.valueOf(excelFileTestDataReader.getCellData());
//		CustomReporter.logConsole("text: " + text);
        //readDataLineByLine("src/test/resources/TestData/CSVFile.csv");
        //compareTwoCSVFiles(filePath, filePath2);
        compareTwoCSVFiles2(filePath, filePath2);
    }
}
