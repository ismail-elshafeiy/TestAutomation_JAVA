package com.engine.dataDriven;


import com.engine.reports.Logger;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import com.opencsv.CSVReaderBuilder;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class CSVFileManager {
    public CSVFileManager(String filePath) {

        String line = "";
        String splitBy = ",";
        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            while ((line = br.readLine()) != null) {
                String[] employee = line.split(splitBy);    // use comma as separator
                System.out.println("Employee [First Name=" + employee[0] + ", Last Name=" + employee[1] + ", Designation=" + employee[2] + ", Contact=" + employee[3] + ", Salary= " + employee[4] + ", City= " + employee[5] + "]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String[][] getAllData(String filePath) throws IOException, CsvException {
        // Create an object of file reader class with CSV file as a parameter.
        FileReader filereader = new FileReader(filePath);
        // create csvParser object with
        // custom separator semi-colon
        CSVParser parser = new CSVParserBuilder().withSeparator(',').build();

        // create csvReader object with parameter
        // filereader and parser
        CSVReader csvReader = new CSVReaderBuilder(filereader)
                .withCSVParser(parser)
                .build();

        // Read all data at once
        return csvReader.readAll().toArray(new String[0][]);
    }

// CSV file line by line
    public static void readDataLineByLine(String file) {
        try {
            // Create an object of filereader
            // class with CSV file as a parameter.
            FileReader filereader = new FileReader(file);
            // create csvReader object passing
            // file reader as a parameter
            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;
            // we are going to read data line by line
            while ((nextRecord = csvReader.readNext()) != null) {
                for (String cell : nextRecord) {
                    System.out.print("Cell " + cell + "\t");
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void compareTwoCSVFilesByLine(String file1, String file2) throws IOException {
        BufferedReader reader1 = new BufferedReader(new FileReader(file1));
        BufferedReader reader2 = new BufferedReader(new FileReader(file2));
        String line1 = reader1.readLine();
        Logger.logConsole("line 1: [ " + line1 + " ]");
        String line2 = reader2.readLine();
        Logger.logConsole("line 2: [ " + line2 + " ]");
        SoftAssert softAssert = new SoftAssert();
        int lineNum = 1;
        while (line1 != null || line2 != null) {
            line1 = reader1.readLine();
            Logger.logConsole("line " + lineNum + ": [ " + line1 + " ]");
            line2 = reader2.readLine();
            Logger.logConsole("line " + lineNum + ": [ " + line2 + " ]");
            if (line1.equalsIgnoreCase(line2)) {
                Logger.logPassed("line " + lineNum + ": [ " + line1 + " ] is equal to line " + lineNum + ": [ " + line2 + " ]");
            } else {
                Logger.logError("line " + lineNum + ": [ " + line1 + " ] is not equal to line " + lineNum + ": [ " + line2 + " ]");
            }
            softAssert.assertEquals(line1, line2);
            lineNum++;
        }
        reader1.close();
        reader2.close();
    }

    public static void compareTwoCSVFilesByValue(String expectedCSVFilePath, String actualCSVFilePath) throws IOException {
        List<CSVRecord> expectedFile = readCSVFileAsList(expectedCSVFilePath);
        List<CSVRecord> actualFile = readCSVFileAsList(actualCSVFilePath);
        SoftAssert softAssert = new SoftAssert();
        // Compare the size of both files
        Assert.assertEquals(expectedFile.size(), actualFile.size());
        // Compare each row and column
        for (int row = 1; row < expectedFile.size(); row++) {
            Logger.logConsole("Row [ " + (row + 1) + " ] ############################################################################################");
            CSVRecord record1 = expectedFile.get(row);
            CSVRecord record2 = actualFile.get(row);
            // Assuming the number of columns is the same in both files
            Assert.assertEquals(record1.size(), record2.size(), "Number of columns is different in row " + (row + 1));
            // Compare each value in the row
            for (int column = 0; column < record1.size(); column++) {
                String expectedValue = record1.get(column).toLowerCase().trim();
                String actualValue = record2.get(column).toLowerCase().trim();
                String actualValueAfterReplacement = actualValue.toLowerCase().replace("testing", "test");
                int rowNumber = row + 1;
                int columnNumber = column + 1;
                if (expectedValue.equalsIgnoreCase(actualValueAfterReplacement) && !expectedValue.isEmpty() && !expectedValue.equals("") && !expectedValue.equals("null")) {
                    Logger.logPassed("Row [" + rowNumber + "], Column [" + columnNumber + "] - Expected value: [ " + expectedValue + " ] is equal to actual value: [ " + actualValueAfterReplacement + " ]");
                } else {
                    Logger.logError("Row [" + rowNumber + "], Column [" + columnNumber + "] -  Expected value: [ " + expectedValue + " ] is not equal to actual value: [ " + actualValueAfterReplacement + " ]");
                }
                softAssert.assertEquals(expectedValue, actualValueAfterReplacement, "Data mismatch in row " + rowNumber + ", column " + columnNumber);
            }
        }
        softAssert.assertAll("Data mismatch between the two files");
    }



    // Java code to illustrate
// Reading CSV File with different separator
    public static void readDataFromCustomSeparator(String file, String file2) {
        try {
            // Create an object of file reader class with CSV file as a parameter.
            FileReader fileReader = new FileReader(file);
            FileReader fileReader2 = new FileReader(file2);

            // create csvParser object with
            // custom separator semi-colon
            CSVParser parser = new CSVParserBuilder().withSeparator(',').build();
            CSVParser parser2 = new CSVParserBuilder().withSeparator(',').build();

            // create csvReader object with parameter
            // fileReader and parser
            CSVReader csvReader = new CSVReaderBuilder(fileReader)
                    .withCSVParser(parser)
                    .build();
            CSVReader csvReader2 = new CSVReaderBuilder(fileReader2)
                    .withCSVParser(parser2)
                    .build();
            // Read all data at once
            List<String[]> allData = csvReader.readAll();
            List<String[]> allData2 = csvReader2.readAll();
            String[] row1;
            String[] row2;
            // Print Data.
            for (String[] row : allData) {
                row1 = row;
                Logger.logConsole("Row from file 1:[ " + row1.toString() + " ]");
            }
            for (String[] row : allData2) {
                row2 = row;
                Logger.logConsole("Row from file 2 :[ " + row2.toString() + " ]");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<CSVRecord> readCSVFileAsList(String filePath) {
        try {
            Reader reader = new FileReader(filePath);
            org.apache.commons.csv.CSVParser csvParser = CSVFormat.DEFAULT.parse(reader);
            return csvParser.getRecords();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.logError("Error reading CSV file: " + filePath);
            return null;
        }
    }

    public static String readCSVFile(String filePath) {
        try {
            Path path = new File(filePath).toPath();
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
            Logger.logError("Error reading CSV file: " + filePath);
            return null;
        }
    }
}
