package webPractice.dataDriven;

import com.engine.actions.FileActions;
import com.engine.reports.CustomReporter;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

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

    @Test
    public void renameFile() throws IOException {
        File destFile = new File("src/test/resources/data/TestDataRenamed.csv");
        String fileName = FileActions.getInstance().listFilesInDirectory("src/test/resources/csv/");
        CustomReporter.logInfoStep("listOfFiles: " + fileName);
        FileActions.getInstance().renameFile("src/test/resources/csv/" + fileName, String.valueOf(destFile));
    }
}