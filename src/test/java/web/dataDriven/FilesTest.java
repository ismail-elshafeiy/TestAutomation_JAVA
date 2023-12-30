package web.dataDriven;

import com.engine.actions.FileActions;
import com.engine.reports.Attachments;
import com.engine.reports.CustomReporter;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;


import static com.engine.dataDriven.CSVFileManager.compareTwoCSVFilesByValue;
import static com.engine.dataDriven.CSVFileManager.readCSVFile;

public class FilesTest {
    @Test
    public void createFolder() {
        String folderPath = "src/test/resources/newFolder/";
        FileActions.getInstance().createFolder(folderPath);
    }

    @Test
    public void copyFolder() {
        String sourceFolderPath = "src/test/resources/TestData/";
        String destinationFolderPath = "src/test/resources/newFolder/";
        FileActions.getInstance().deleteFolder(destinationFolderPath);
        FileActions.getInstance().copyFolder(sourceFolderPath, destinationFolderPath);
    }

    @Test
    public void createFile() {
        String filePath = "src/test/resources/newFolder/";
        FileActions.getInstance().createFile(filePath, "newFile.csv");
        FileActions.getInstance().doesFileExist(filePath, "newFile.csv", 2);
        FileActions.getInstance().writeToFile(filePath, "newFile.csv", "Hello World");
    }

    @Test
    public void downloadFile() {
        FileActions.getInstance().downloadFile("https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf", "dummy.pdf");
    }

    @Test
    public void compareTwoCSVFiles() throws IOException {
        String filePath = "src/test/resources/TestData/CSVFile.csv";
        String filePath2 = "src/test/resources/TestData/CSVFile2.csv";
        compareTwoCSVFilesByValue(filePath, filePath2);
    }

    @Test
    public void renameFile() throws IOException {
        File destFile = new File("src/test/resources/data/TestDataRenamed.csv");
        String fileName = FileActions.getInstance().listFilesInDirectory("src/test/resources/csv/");
        CustomReporter.logInfoStep("listOfFiles: " + fileName);
        FileActions.getInstance().renameFile("src/test/resources/csv/" + fileName, String.valueOf(destFile));
    }

    @Test
    public void convertFileToCSVFile() {
        String excelFilePath = "src/test/resources/TestData/ExcelFile.xlsx";
        String csvFilePath = "src/test/resources/csv/ExcelToCSV1.csv";
        FileActions.getInstance().convertFileToCSV(excelFilePath, csvFilePath);
        String csvContent = readCSVFile(csvFilePath);
        // Attach the CSV file to the Allure report
        assert csvContent != null;
        Attachments.attach(Attachments.AttachmentType.CSV.getValue(), "Test case - CSV File", csvContent);
        //Allure.addAttachment("Test case Attachment", "text/csv", csvContent, ".csv");
    }
}