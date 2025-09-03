package com.engine.dataDriven;



import com.engine.reports.CustomReporter;
import org.apache.logging.log4j.Level;
import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.testng.Assert.fail;

public class ExcelFileManager {

    private FileInputStream fis;
    private FileOutputStream fos;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private XSSFRow row;
    private XSSFCell cell;
    private String excelFilePath;
    private int columnNumber = 0, rowNumber = 0;
    public final String EXCEPTION_ERROR_MESSAGE = " Exception Error Message: >>>>  ";

    public ExcelFileManager() {
        initializeVariables();
    }

    public ExcelFileManager(String excelFilePath) {
        initializeVariables();
        this.excelFilePath = excelFilePath;
        loadWorkbook(excelFilePath);
    }

    public ExcelFileManager(String excelFilePath, String sheetName) {
        this(excelFilePath);
        switchToSheet(sheetName);
    }

    public FileInputStream getFileInputStream(String filePath) {
        excelFilePath = String.valueOf(new File(filePath));
        try {
            fis = new FileInputStream(excelFilePath);
            return fis;
        } catch (FileNotFoundException e) {
            CustomReporter.getInstance().logError("There is no file exist in the path: [ " + filePath + " ] , please check the path and try again." + EXCEPTION_ERROR_MESSAGE + e.getMessage());
            Assert.fail("There is no file exist in the path: [ " + filePath + " ] , please check the path and try again. " + EXCEPTION_ERROR_MESSAGE + e.getMessage());
        }
        return fis;
    }


    /**
     * Switch to your Sheet in case you have multiple sheets in your Excel file
     *
     * @param sheetName enter the name of the sheet you want to switch to
     */
    public void switchToSheet(String sheetName) {
        try {
            sheet = workbook.getSheet(sheetName);
            CustomReporter.getInstance().logConsole("Switched to sheet: [ " + sheetName + " ] from file: [ " + excelFilePath + " ].");
        } catch (Exception e) {
            CustomReporter.getInstance().logError("Couldn't find the desired sheet. [ " + sheetName + " ]." + EXCEPTION_ERROR_MESSAGE + e.getMessage());
            Assert.fail("Couldn't find the desired sheet. [ " + sheetName + " ]." + EXCEPTION_ERROR_MESSAGE + e.getMessage());
        }
    }

    /**
     * This method will return the data from the excel sheet as array
     *
     * @param filePath  the path of the excel file
     * @param sheetName the name of the sheet you want to switch to
     * @return the data from the excel sheet as array
     */
    public String[][] getDataAsArray(String filePath, String sheetName) {
        CustomReporter.getInstance().logConsole("Reading data from Excel file: [ " + filePath + " ] | Sheet: [ " + sheetName + " ]");
        try {
            fis = getFileInputStream(filePath);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);
            int rowLength = 0, columnLength = 0, rowIndex = 0, columnIndex = 0;
            String[][] excelDataAsArray = new String[rowLength][columnLength];
            try {
                rowLength = sheet.getLastRowNum() + 1;
                columnLength = getLastColumnNumber();
                excelDataAsArray = new String[rowLength][columnLength];
                for (rowIndex = 0; rowIndex < rowLength; rowIndex++) {
                    for (columnIndex = 0; columnIndex < columnLength; columnIndex++) {
                        row = sheet.getRow(rowIndex);
                        if (row.getCell(columnIndex) == null || (row.getCell(columnIndex).toString()).equalsIgnoreCase("null") || row.getCell(columnIndex).getCellType() == CellType.BLANK || row.getCell(columnIndex).getCellType() == CellType._NONE) {
                            excelDataAsArray[rowIndex][columnIndex] = "";
                        } else {
                            String cellValue = row.getCell(columnIndex).toString();
                            if (cellValue.endsWith(".0")) {
                                cellValue = cellValue.split("\\.")[0];
                            }
                            excelDataAsArray[rowIndex][columnIndex] = cellValue;
                        }
                    }
                }
                // CustomReporter.logConsole("Excel Data as Array row length: [ " + rowLength + " ], column length: [ " + columnLength + " ], data: [ " + Arrays.deepToString(excelDataAsArray) + " ]");
            } catch (Exception e) {

                CustomReporter.getInstance().logError("Can't read data from File Path [ " + filePath + " ], Sheet: [ " + sheetName + " ]," + " Row: [" + rowIndex + "], Column: [ " + columnIndex + " ], Array length [ " + rowLength + " ][ " + columnLength + " ] data :" + Arrays.toString(excelDataAsArray) + EXCEPTION_ERROR_MESSAGE + e.getMessage() + " ]");
                Assert.fail("Can't read data from File Path [ " + filePath + " ], Sheet: [ " + sheetName + " ]," + " Row: [" + rowIndex + "], Column: [ " + columnIndex + " ], Array length [ " + rowLength + " ][ " + columnLength + " ] data :" + Arrays.toString(excelDataAsArray) + EXCEPTION_ERROR_MESSAGE + e.getMessage() + " ]");
            }
            workbook.close();
            fis.close();
//           // debuggingExcelDataAsArray(excelDataAsArray);
//            for (int _rowIndex = 0; _rowIndex < excelDataAsArray.length; _rowIndex++) {
//                for (int _columnIndex = 0; _columnIndex < excelDataAsArray[_rowIndex].length; _columnIndex++) {
//                }
//                CustomReporter
//                .logConsole("Data from row: [ " + _rowIndex + " ] is array: [ " + Arrays.toString(excelDataAsArray[_rowIndex]) + " ]");
//            }
            return excelDataAsArray;
        } catch (FileNotFoundException e) {
            CustomReporter.getInstance().logError("There is no file exist in the path: [ " + filePath + " ] , please check the path and try again. " + e.getMessage());
            Assert.fail("There is no file exist in the path: [ " + filePath + " ] , please check the path and try again. " + e.getMessage());
        } catch (Exception e) {
            CustomReporter.getInstance().logError("There is no file exist in the path: [ " + filePath + " ] , please check the path and try again. " + e.getMessage());
        }
        return new String[0][0];
    }

    Object[][] data = null;

    public Object[][] getDataAsLinkedHashMap(String filePath, String sheetName) {
        CustomReporter.getInstance().logConsole("Reading data from Excel file: [ " + filePath + " ] | Sheet: [ " + sheetName + " ]");
        Object[][] data = null;
        LinkedHashMap<String, String> linkedHashMap = null;
        String key, value;
        int startRowIndex = 1, numberOfTestCases = 0, dataArrayIndex = 0;
        try {
            fis = getFileInputStream(filePath);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);
            int lastRowIndex = getLastRowNumber(), lastColumnIndex = getLastColumnNumber();
            try {
                for (int rowIndex = startRowIndex; rowIndex <= lastRowIndex; rowIndex++) {
                    numberOfTestCases++;
                }
                CustomReporter.getInstance().logConsole("====================================================================+++++++++=" + "\nStart Row index: [ " + startRowIndex + " ] ,\nLast Row index [ " + lastRowIndex + " ], Last column index: [ " + lastColumnIndex + " ] in the sheet [ " + sheetName + " ]");
                CustomReporter.getInstance().logConsole("====================================================================+++++++++=");
                data = new Object[numberOfTestCases][1];
                for (int rowIndex = startRowIndex; rowIndex <= lastRowIndex; rowIndex++) {
                    this.rowNumber = rowIndex + 1;
                    linkedHashMap = new LinkedHashMap<>();
                    dataArrayIndex = dataArrayIndex + 1;
                    linkedHashMap.put("runid", String.valueOf(dataArrayIndex));
                    for (int colIndex = 0; colIndex < lastColumnIndex; colIndex++) {
                        this.columnNumber = colIndex + 1;
                        key = Helper.replaceAllSpaces(getCellData(1, columnNumber), false).toLowerCase();
                        value = getCellData(rowNumber, columnNumber);
                        linkedHashMap.put(key, value);
                        //CustomReporter.getInstance().logConsole("Get cell Data from row [" + rowNumber + "], column : [ " + columnNumber + " ] - Key : [ " + key + " ], Value : [ " + value + " ] "); //debugging
                    }
                    CustomReporter.getInstance().logConsole("Record [ " + dataArrayIndex + " ] from row [ " + rowNumber + " ]: [ " + linkedHashMap + " ]");
                    data[dataArrayIndex - 1][0] = linkedHashMap;
                }
            } catch (Exception e) {
                CustomReporter.getInstance().logError("Can't read data from File Path [ " + filePath + " ], Sheet: [ " + sheetName + " ]," + " Row: [ " + rowNumber + " ], Column: [ " + columnNumber + " ], data length [ " + data.length + " ] :" + Arrays.toString(data) + EXCEPTION_ERROR_MESSAGE + e.getMessage() + " ]");

            }
        } catch (FileNotFoundException e) {
            CustomReporter.getInstance().logError("There is no file exist in the path: [ " + filePath + " ] , please check the path and try again. " + e.getMessage());
            Assert.fail("There is no file exist in the path: [ " + filePath + " ] , please check the path and try again. " + e.getMessage());
        } catch (Exception e) {
            CustomReporter.getInstance().logError("There is no file exist in the path: [ " + filePath + " ] , please check the path and try again. " + e.getMessage());
        }
        return data;
    }

    public Object[][] getDataAsLinkedHashMap(String filePath, String sheetName, String columnNameCheck, String condition) {
        CustomReporter.getInstance().logConsole("Reading data from Excel file: [ " + filePath + " ] | Sheet: [ " + sheetName + " ]");
        Object[][] data = null;
        LinkedHashMap<String, String> linkedHashMap;
        String key, value;
        int startRowIndex = 1, numberOfTestCases = 0, dataArrayIndex = 0;
        try {
            fis = getFileInputStream(filePath);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);
            int lastRowIndex = getLastRowNumber(), lastColumnIndex = getLastColumnNumber();
            try {
                for (int rowIndex = startRowIndex; rowIndex <= lastRowIndex; rowIndex++) {
                    if (getCellData(rowIndex + 1, columnNameCheck).equalsIgnoreCase(condition)) {
                        numberOfTestCases++;
                    }
                }
                CustomReporter.getInstance().logConsole("=================================================================================" + "\nStart Row index: [ " + startRowIndex + " ] ,\nLast Row index [ " + lastRowIndex + " ], Last column index: [ " + lastColumnIndex + " ]\n" + "Number of records match the condition [ " + condition + " ] in check column name [ " + columnNameCheck + " ] are [ " + numberOfTestCases + " records] in the sheet [ " + sheetName + " ]");
                CustomReporter.getInstance().logConsole("=================================================================================");
                data = new Object[numberOfTestCases][1];
                for (int rowIndex = startRowIndex; rowIndex <= lastRowIndex; rowIndex++) {
                    this.rowNumber = rowIndex + 1;
                    if (getCellData(rowNumber, columnNameCheck).equalsIgnoreCase(condition)) {
                        linkedHashMap = new LinkedHashMap<>();
                        dataArrayIndex = dataArrayIndex + 1;
                        linkedHashMap.put("runid", String.valueOf(dataArrayIndex));
                        for (int colIndex = 0; colIndex < lastColumnIndex; colIndex++) {
                            this.columnNumber = colIndex + 1;
                            key = Helper.replaceAllSpaces(getCellData(1, columnNumber), false).toLowerCase();
                            value = getCellData(rowNumber, columnNumber);
                            linkedHashMap.put(key, value);
                            //CustomReporter.getInstance().logConsole("Get cell Data from row [" + rowNumber + "], column : [ " + columnNumber + " ] - Key : [ " + key + " ], Value : [ " + value + " ] "); //debugging
                        }
                        linkedHashMap.remove("chatlog");
                        CustomReporter.getInstance().logConsole("Record [ " + dataArrayIndex + " ] from row [ " + rowNumber + " ]: [ " + linkedHashMap + " ]");
                        data[dataArrayIndex - 1][0] = linkedHashMap;
                    }
                }
            } catch (Exception e) {
                CustomReporter.getInstance().logError("Can't read data from File Path [ " + filePath + " ], Sheet: [ " + sheetName + " ]," + " Row: [ " + rowNumber + " ], Column: [ " + columnNumber + " ], data length [ " + data.length + " ] :" + Arrays.toString(data) + EXCEPTION_ERROR_MESSAGE + e.getMessage() + " ]");
            }
        } catch (FileNotFoundException e) {
            CustomReporter.getInstance().logError("There is no file exist in the path: [ " + filePath + " ] , please check the path and try again. " + e.getMessage());
            Assert.fail("There is no file exist in the path: [ " + filePath + " ] , please check the path and try again. " + e.getMessage());
        } catch (Exception e) {
            CustomReporter.getInstance().logError("There is no file exist in the path: [ " + filePath + " ] , please check the path and try again. " + e.getMessage());
        }
        return data;
    }


    public Object[][] getDataAsLinkedHashMap(String filePath, String sheetName, String columnNameCheck, String condition, LinkedHashMap<String, String> testCasesDataMap) {
        CustomReporter.getInstance().logConsole("Reading data from Excel file: [ " + filePath + " ] | Sheet: [ " + sheetName + " ]");
        LinkedHashMap<String, String> linkedHashMap;
        String key, value;
        int startRowIndex = 1, numberOfTestCases = 0, dataArrayIndex = 0;
        int testCasesDataMapSize = testCasesDataMap.size();
        try {
            fis = getFileInputStream(filePath);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);
            int lastRowIndex = getLastRowNumber(), lastColumnIndex = getLastColumnNumber();
            try {
                for (int rowIndex = startRowIndex; rowIndex <= lastRowIndex; rowIndex++) {
                    if (getCellData(rowIndex + 1, columnNameCheck).equalsIgnoreCase(condition)) {
                        numberOfTestCases++;
                    }
                }
                CustomReporter.getInstance().logConsole("====================================================================+++++++++=" + "\nStart Row index: [ " + startRowIndex + " ] ,\nLast Row index [ " + lastRowIndex + " ], Last column index: [ " + lastColumnIndex + " ]\n" + "Number of records match the condition [ " + condition + " ] in check column name [ " + columnNameCheck + " ] are [ " + numberOfTestCases + " records] in the sheet [ " + sheetName + " ]");
                CustomReporter.getInstance().logConsole("====================================================================+++++++++=");
                linkedHashMap = new LinkedHashMap<>();
                data = new Object[numberOfTestCases][1];
                for (int rowIndex = startRowIndex; rowIndex <= lastRowIndex; rowIndex++) {
                    if (testCasesDataMapSize > 0) {
                        for (Map.Entry<String, String> entry : testCasesDataMap.entrySet()) {
                            key = entry.getKey();
                            value = entry.getValue();
                            linkedHashMap.put(key, value);
                        }
                    }
                    this.rowNumber = rowIndex + 1;
                    if (getCellData(rowNumber, columnNameCheck).equalsIgnoreCase(condition)) {
                        dataArrayIndex = dataArrayIndex + 1;
                        linkedHashMap.put("runid", String.valueOf(dataArrayIndex));
                        for (int colIndex = 0; colIndex < lastColumnIndex; colIndex++) {
                            this.columnNumber = colIndex + 1;
                            key = Helper.replaceAllSpaces(getCellData(1, columnNumber), false).toLowerCase();
                            value = getCellData(rowNumber, columnNumber);
                            linkedHashMap.put(key, value);
                            //           CustomReporter.getInstance().logConsole("Get cell Data from row [" + rowNumber + "], column : [ " + columnNumber + " ] - Key : [ " + key + " ], Value : [ " + value + " ] "); //debugging
                        }
                        linkedHashMap.remove("chatlog");
                        CustomReporter.getInstance().logConsole("Record [ " + dataArrayIndex + " ] from row [ " + rowNumber + " ]: [ " + linkedHashMap + " ]");
                        data[dataArrayIndex - 1][0] = linkedHashMap;
                    }
                }
                CustomReporter.getInstance().logConsole("Number of records match the condition [ " + condition + " ] in check column name [ " + columnNameCheck + " ] are [ " + numberOfTestCases + " records]\n" + "Data from Excel file: [ " + filePath + " ] | Sheet: [ " + sheetName + " ]", Level.INFO, "32");
            } catch (Exception e) {
                CustomReporter.getInstance().logError("Can't read data from File Path [ " + filePath + " ], Sheet: [ " + sheetName + " ]," + " Row: [ " + rowNumber + " ], Column: [ " + columnNumber + " ], data length [ " + data.length + " ] :" + Arrays.toString(data) + EXCEPTION_ERROR_MESSAGE + e.getMessage() + " ]");
            }
        } catch (FileNotFoundException e) {
            CustomReporter.getInstance().logError("There is no file exist in the path: [ " + filePath + " ] , please check the path and try again. " + e.getMessage());
            Assert.fail("There is no file exist in the path: [ " + filePath + " ] , please check the path and try again. " + e.getMessage());
        } catch (Exception e) {
            CustomReporter.getInstance().logError("There is no file exist in the path: [ " + filePath + " ] , please check the path and try again. " + e.getMessage());
        }
        return data;
    }


    /**
     * Reads cell data from a specific sheet name inside the Excel file Reads cell
     * data using row name (1st column) and column name
     * the name of the target Excel sheet
     *
     * @param rowName    Enter the row name of the Sheet
     * @param columnName the value of the first cell of the target column
     * @return the value of the target cell within the target row and column within
     * the target sheet
     */
    //TODO: Test impact of LogWarningMessage
    public String getCellData(String rowName, String columnName) {
        try {
            int rowNum = getRowNumberFromRowName(rowName);
            int colNum = getColumnNumberFromColumnName(columnName);
            // get the desired row
            row = sheet.getRow(rowNum); // why use -1 here?
            // get the desired cell
            cell = row.getCell(colNum);
            // return cell value given the different cell types
            CustomReporter.getInstance().logConsole("Reading data [ " + getCellData() + " ] from row [ " + rowName + " ] and column [ " + columnName + " ]");
            return getCellData();
        } catch (Exception e) {
            CustomReporter.getInstance().logWarning("Failed to read data from row [" + rowName + "] and column [" + columnName + "] in the Test Data Sheet [" + sheet + "], under the following path [" + excelFilePath + "]. " + e.getMessage());
            return "";
        }
    }

    /**
     * Get the cell by Column name and Row number from your Excel file
     *
     * @param columnName Enter the column Name name
     * @param rowNumber  Enter the row  of the Sheet index start from 1
     */
    public String getCellData(int rowNumber, String columnName) {
        try {
            int colNum = getColumnNumberFromColumnName(columnName);
            row = sheet.getRow(rowNumber - 1);
            cell = row.getCell(colNum);
            return getCellData();
        } catch (Exception e) {
            CustomReporter.getInstance().logError("Failed to read data from row [" + rowNumber + "] and column [" + columnName + "] in the Test Data Sheet [" + sheet + "], under the following path [" + excelFilePath + "]." + e.getMessage());
            return "";
        }
    }

    public String getCellData(String rowName, int columnNumber) {
        try {
            int rowNum = getRowNumberFromRowName(rowName);
//            int colNum = getColumnNumberFromColumnName(columnName);
            // get the desired row
            row = sheet.getRow(rowNum); // why use -1 here?
            // get the desired cell
            cell = row.getCell(columnNumber - 1);
            // return cell value given the different cell types
            CustomReporter.getInstance().logConsole("Reading data [ " + getCellData() + " ] from row [ " + rowName + " ] and column [ " + columnNumber + " ]");
            return getCellData();
        } catch (Exception e) {
            CustomReporter.getInstance().logWarning("Failed to read data from row [" + rowName + "] and column [" + columnNumber + "] in the Test Data Sheet [" + sheet + "], under the following path [" + excelFilePath + "]. " + e.getMessage());
            return "";
        }
    }

    /**
     * Get the cell by Column and Row number from your Excel file
     *
     * @param rowNumber    Enter the row  of the Sheet index start from 1
     * @param columnNumber Enter the column number of the Sheet index start from 1
     * @return String cell data as String
     */
    public String getCellData(int rowNumber, int columnNumber) {
        try {
            row = sheet.getRow(rowNumber - 1);
            cell = row.getCell(columnNumber - 1);
            return getCellData();
        } catch (Exception e) {
            fail("Failed to read data from row [" + rowNumber + "] and column [" + columnNumber + "] in the Test Data Sheet [" + sheet + "], under the following path [" + excelFilePath + "].", e);
            return "";
        }
    }

    //TODO: Remove console log to check the error [ Failed to get the row number that corresponds to Row Name ]
    public int getRowNumberFromRowName(String rowName) {
        // CustomReporter.getInstance().logConsole("Getting row number that corresponds to Row Name [" + rowName + "] in the Test Data Sheet [" + sheet + "], under the following path [" + excelFilePath + "].");
        try {
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                row = sheet.getRow(i);
                // get the first cell of each row, and compare it to rowName
                // if they match then that's the row we want
                if (row != null && row.getCell(0).getStringCellValue().equals(rowName)) {
                    return i;
                }
                // in certain cases if the row is empty, its value is set to null, and hence a
                // null pointer exception is thrown when
                // you try to get the cell from it.
                // we can skip this exception by checking if row != null.
            }
            // in case you provided valid data type, no exceptions were thrown, and yet the
            // rowName you mentioned was not present in this sheet
            CustomReporter.getInstance().logError("Failed to get row Name [" + rowName + "] in the Test Data Sheet, under the following path [" + excelFilePath + "].");
            return -1; // in case of failure this line is unreachable
        } catch (Exception e) {

            return -1; // in case of failure this line is unreachable
        }
    }


    public int getRowNumberFromRowName(String rowName, int column) {
        try {
            for (int i = column; i <= sheet.getLastRowNum(); i++) {
                row = sheet.getRow(i);
                // get the first cell of each row, and compare it to rowName
                // if they match then that's the row we want
                if (row != null && row.getCell(0).getStringCellValue().equals(rowName)) {
                    return i;
                }
                // in certain cases if the row is empty, its value is set to null, and hence a
                // null pointer exception is thrown when
                // you try to get the cell from it.
                // we can skip this exception by checking if row != null.
            }
            // in case you provided valid data type, no exceptions were thrown, and yet the
            // rowName you mentioned was not present in this sheet
            CustomReporter.getInstance().logError("Failed to get row Name [" + rowName + "] in the Test Data Sheet, under the following path [" + excelFilePath + "].");
            return -1; // in case of failure this line is unreachable
        } catch (Exception e) {
            CustomReporter.getInstance().logError("Failed to get the row number that corresponds to Row Name [" + rowName + "] , in the Test Data Sheet [" + sheet + "], under the following path [" + excelFilePath + "]." + e.getMessage());
            return -1; // in case of failure this line is unreachable
        }
    }
    // ************ Get Cell Data By index  ************//

    public int getColumnNumberFromColumnName(String columnName) {
        try {
            if (!columnName.isEmpty()) {
                row = sheet.getRow(0);
                for (int i = 0; i < row.getLastCellNum(); i++) {
                    // get the first cell of each column, and compare it to columnName
                    // if they match then that's the column we want
                    if (row.getCell(i).getStringCellValue().equals(columnName)) {
                        return i;
                    }
                }
            } else {
                return 1;
            }
            // in case you provided valid data type, no exceptions were thrown, and yet the
            // columnName you mentioned was not present in this sheet
            CustomReporter.getInstance().logError("Failed to get the column number that corresponds to columnName [" + columnName + "] in the Test Data Sheet [" + sheet + "], under the following path [" + excelFilePath + "].");
            return -1; // in case of failure this line is unreachable
        } catch (Exception e) {
            CustomReporter.getInstance().logError("Failed to get the column number that corresponds to columnName [" + columnName + "] in the Test Data Sheet [" + sheet + "], under the following path [" + excelFilePath + "]." + e.getMessage());

            return -1; // in case of failure this line is unreachable
        }
    }

    private String getCellData() {
        try {
            CellType cellType = cell.getCellType();
            if (cellType == CellType.STRING) {
                return cell.getStringCellValue();
            } else if (cellType == CellType.NUMERIC || cellType == CellType.FORMULA) {
                String cellValue = String.valueOf(cell.getNumericCellValue());
                if (cellValue.contains(".0")) {
                    cellValue = cellValue.split("\\.")[0];
                }
                if (DateUtil.isCellDateFormatted(cell)) {
                    DateFormat df = new SimpleDateFormat("dd/MM/yy");
                    Date date = cell.getDateCellValue();
                    cellValue = df.format(date);
                }
                return cellValue;
            } else if (cellType == CellType.BOOLEAN) {
                return String.valueOf(cell.getBooleanCellValue());
            } else if (cellType == CellType.BLANK || cellType == null) {
                return "";
            } else {
                return "";
            }
        } catch (Exception e) {
            CustomReporter.getInstance().logConsole("Failed to get the cell data from the Test Data Sheet [" + sheet.getSheetName() + "]" + ", under the following path [" + excelFilePath + "]." + e.getMessage());
            return "";
        }
    }


    /**
     * This method will set the data in the cell by row name and column name from your Excel file using file path and sheet name as parameter to switch to the sheet first
     *
     * @param filePath     Enter the file path of the Excel file
     * @param sheetName    Enter the sheet name to switch to
     * @param rowNumber    Enter the row number of the Sheet index start from 1
     * @param columnNumber Enter the column number of the Sheet index start from 1
     * @param data         Enter the data you want to set in the cell
     */

    public ExcelFileManager writeDataInCell(String filePath, String sheetName, int rowNumber, int columnNumber, String data) {
        initializeVariables();
        CustomReporter.getInstance().logConsole("Writing data in Excel file: [ " + filePath + " ] | Sheet: [ " + sheetName + " ] | Row: [ " + rowNumber + " ] | Column: [ " + columnNumber + " ] | Data: [ " + data + " ]");
        fis = getFileInputStream(filePath);
        try {
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);
            row = sheet.getRow(rowNumber);
            if (row == null) row = sheet.createRow(rowNumber);
            cell = row.getCell(columnNumber);
            if (cell == null) cell = row.createCell(columnNumber);
            cell.setCellValue(data);
            fos = new FileOutputStream(filePath);
            try {
                workbook.write(fos);
            } catch (Exception e) {

                CustomReporter.getInstance().logError("There is no file exist in the path: [ " + filePath + " ] , please check the path and try again. " + e.getMessage());
            }
            fos.close();
            //  workbook.close();
        } catch (Exception e) {
            CustomReporter.getInstance().logConsole("There is no file exist in the path: [ " + filePath + " ] , please check the path and try again. " + e.getMessage(), Level.ERROR);
        }
        return this;
    }

    public ExcelFileManager writeDataInCell(String filePath, String sheetName, String cellData0, String cellData1, String cellData2, String cellData3, String cellData4, String cellData5, String cellData6) {
        initializeVariables();
        CustomReporter.getInstance().logConsole("Writing data in Excel file: [ " + filePath + " ] | Sheet: [ " + sheetName + " ] , cell 1: [ " + cellData0 + " ] , cell 2: [ " + cellData1 + " ] , cell 3: [ " + cellData2 + " ] , cell 4: [ " + cellData3 + " ] , cell 5: [ " + cellData4 + " ] , cell 6: [ " + cellData5 + " ] , cell 7: [ " + cellData6 + " ]");
        fis = getFileInputStream(filePath);
        try {
            workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet(sheetName);
            int lastRowIndex = sheet.getLastRowNum();
            row = sheet.createRow(lastRowIndex + 1);
            int lastCellIndex = row.getLastCellNum();
            cell = row.createCell(lastCellIndex + 1, CellType.STRING);
            cell.setCellValue(cellData0);
            Cell cell1 = row.createCell(1);
            cell1.setCellValue(cellData1);
            Cell cell2 = row.createCell(2);
            cell2.setCellValue(cellData2);
            Cell cell3 = row.createCell(3);
            cell3.setCellValue(cellData3);
            Cell cell4 = row.createCell(4);
            cell4.setCellValue(cellData4);
            Cell cell7 = row.createCell(5);
            cell7.setCellValue(cellData5);
            Cell cell8 = row.createCell(6);
            cell8.setCellValue(cellData6);
            fos = new FileOutputStream(filePath);
            workbook.write(fos);
            fos.close();
        } catch (Exception e) {
            CustomReporter.getInstance().logConsole("There is no file exist in the file Path: [ " + filePath + " ] , please check the file Path and try again. Exception Error :->" + e.getMessage(), Level.ERROR);
        }
        return this;
    }

    /**
     * This method will set the data in the cell by row index and column index from your Excel file
     * <p>
     * You should read the file and sheet first before using this method using the following methods:
     * <p>
     * {@link #getDataAsArray(String, String)} or {@link #ExcelFileManager(String, String)}
     * <p>
     * If you want to get row number by name you should also use the following method:
     * <p>
     * {@link #getRowNumberFromRowName(String)}
     * <p>
     *
     * @param rowIndex    Enter the row  of the Sheet index start from 1
     * @param columnIndex Enter the column number of the Sheet index start from 1
     * @param data        Enter the data you want to set in the cell
     */
    public ExcelFileManager writeDataInCell(int rowIndex, int columnIndex, String data) {
        CustomReporter.getInstance().logConsole("Writing data in Excel file: [ " + excelFilePath + " ] | Sheet: [ " + sheet + " ] | Row index: [ " + rowIndex + " ] | Column index: [ " + columnIndex + " ] | Data: [ " + data + " ]");
        try {
            row = sheet.getRow(rowIndex);
            if (row == null) row = sheet.createRow(rowIndex);
            cell = row.getCell(columnIndex);
            if (cell == null) cell = row.createCell(columnIndex);
            cell.setCellValue(data);
            fos = new FileOutputStream(excelFilePath);
            workbook.write(fos);
            fos.close();
        } catch (Exception e) {
            CustomReporter.getInstance().logConsole("There is no file exist in the path: [ " + excelFilePath + " ] , please check the path and try again. " + e.getMessage(), Level.ERROR);
        }
        return this;
    }

    // TODO : this method needs enhancement to write data in specific cell by name also instead of index only
    public ExcelFileManager writeDataInCell(String filePath, String sheetName, int rowIndex, int columnIndex, String data, String updatedAt, String updatedBy) {
        initializeVariables();
        CustomReporter.getInstance().logConsole("Writing data in Excel file: [ " + filePath + " ] | Sheet: [ " + sheetName + " ] | Row index: [ " + rowIndex + " ] | Column index: [ " + columnIndex + " ] | Data: [ " + data + " ]");
        fis = getFileInputStream(filePath);
        try {
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);
            row = sheet.getRow(rowIndex);
            if (row == null) row = sheet.createRow(rowIndex);
            cell = row.getCell(columnIndex);
            if (cell == null) cell = row.createCell(columnIndex);
            cell.setCellValue(data);
            Cell cell5 = row.createCell(7);
            cell5.setCellValue(updatedAt);
            Cell cell6 = row.createCell(8);
            cell6.setCellValue(updatedBy);
            fos = new FileOutputStream(filePath);
            workbook.write(fos);
            fos.close();
        } catch (Exception e) {
            CustomReporter.getInstance().logConsole("There is no file exist in the path: [ " + filePath + " ] , please check the path and try again. " + e.getMessage(), Level.ERROR);
        }
        return this;
    }

    public ExcelFileManager writeDataInCell(String filePath, String sheetName, int rowIndex, int columnIndex, String data, String updatedAt, int updateAtColumnIndex, String updatedBy, int updatedByColumnIndex) {
        CustomReporter.getInstance().logConsole("Writing data in Excel file: [ " + filePath + " ] | Sheet: [ " + sheetName + " ] | Row index: [ " + rowIndex + " ] | Column index: [ " + columnIndex + " ] | Data: [ " + data + " ]");
        initializeVariables();
        fis = getFileInputStream(filePath);
        try {
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);
            row = sheet.getRow(rowIndex);
            if (row == null) row = sheet.createRow(rowIndex);
            cell = row.getCell(columnIndex);
            if (cell == null) cell = row.createCell(columnIndex);
            cell.setCellValue(data);
            Cell cell5 = row.createCell(updateAtColumnIndex);
            cell5.setCellValue(updatedAt);
            Cell cell6 = row.createCell(updatedByColumnIndex);
            cell6.setCellValue(updatedBy);
            fos = new FileOutputStream(filePath);
            workbook.write(fos);
            fos.close();
        } catch (Exception e) {
            CustomReporter.getInstance().logConsole("There is no file exist in the path: [ " + filePath + " ] , please check the path and try again. " + e.getMessage(), Level.ERROR);
        }
        return this;
    }

    private void initializeVariables() {
        fis = null;
        fos = null;
        workbook = null;
        sheet = null;
        row = null;
        cell = null;
        excelFilePath = "";
    }

    private void loadWorkbook(String excelFilePath) {
        try {
            fis = new FileInputStream(excelFilePath);
            workbook = new XSSFWorkbook(fis);
            fis.close();
            CustomReporter.getInstance().logConsole("Reading test data from the following file [" + excelFilePath + "].");
        } catch (IOException | OutOfMemoryError | EmptyFileException e) {
            handleException(e, "There is no file exist in the path: [ " + excelFilePath + " ], please check the path and try again.");
        }
    }

    private void handleException(Throwable e, String message) {
        CustomReporter.getInstance().logError(message + EXCEPTION_ERROR_MESSAGE + e.getMessage());
        Assert.fail(message, e);
    }

    public int getLastRowNumber() {
        return sheet.getLastRowNum();
    }

    private String getDefaultSheetName() {
        return workbook.getSheetName(0);
    }

    public int getFirstRowNumber() {
        return sheet.getFirstRowNum();
    }

    public int getFirstColumnNumber() {
        return sheet.getRow(0).getFirstCellNum();
    }

    public int getLastColumnNumber() {
        return sheet.getRow(0).getLastCellNum();
    }

    /**
     * Get the last row number from your Excel file
     */
    public void getAllDataInSheet(String sheetName) {
        switchToSheet(sheetName);
        //get all rows in the sheet
        int rowCount = getRowCountInSheet();
        //iterate over all the row to print the data present in each cell.
        for (int i = 0; i <= rowCount; i++) {
            //get cell count in a row
            int cellCount = sheet.getRow(i).getLastCellNum();
            //iterate over each cell to print its value
            CustomReporter.getInstance().logInfoStep("Row " + i + " data is :");
            for (int j = 0; j < cellCount; j++) {
                CustomReporter.getInstance().logInfoStep(sheet.getRow(i).getCell(j).getStringCellValue() + ",");
            }
            System.out.println();
        }
    }

    private int getRowCountInSheet() {
        int rowCount = 0;
        try {
            rowCount = getLastRowNumber() - getFirstRowNumber();
            CustomReporter.getInstance().logInfoStep("Row count from the sheet [" + sheet.getSheetName() + "] is [ " + rowCount + " ]");
        } catch (Exception e) {
            CustomReporter.getInstance().logError("Can't find the row count from the sheet [" + sheet.getSheetName() + "]  " + EXCEPTION_ERROR_MESSAGE + e.getMessage());
            fail("Can't find the row count from the sheet [" + sheet.getSheetName() + "]  " + EXCEPTION_ERROR_MESSAGE + e.getMessage());
        }
        return rowCount;
    }
}
