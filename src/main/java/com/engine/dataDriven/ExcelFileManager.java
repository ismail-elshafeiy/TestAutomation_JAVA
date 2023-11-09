package com.engine.dataDriven;

import com.engine.listeners.CustomReporter;
import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.testng.Assert.fail;

public class ExcelFileManager {
	private static FileInputStream fis;
	private static XSSFWorkbook workbook;
	private static XSSFSheet sheet;
	private static XSSFRow row;
	private static XSSFCell cell;
	private static String excelFilePath;
	private static final String errorMessageException = " Error Message Exception: --> ";

	/**
	 * Creates a new instance of the test data Excel reader using the target Excel
	 * file path
	 *
	 * @param excelFilePath target test data Excel file path
	 */
	public ExcelFileManager (String excelFilePath) {
		initializeVariables();
		this.excelFilePath = excelFilePath;
		try {
			fis = new FileInputStream(excelFilePath);
			workbook = new XSSFWorkbook(fis);
			fis.close();
            CustomReporter.logStep("Reading test data from the following file [" + excelFilePath + "].");
		} catch ( IOException | OutOfMemoryError e ) {
			Assert.fail("Couldn't find the desired file. [" + excelFilePath + "].", e);
		} catch ( EmptyFileException e ) {
			Assert.fail("Please check the target file, as it may be corrupted. [" + excelFilePath + "].", e);
		}
		List<List<Object>> attachments = new ArrayList<>();
		List<Object> testDataFileAttachment = null;
		try {
			testDataFileAttachment = Arrays.asList("Test Data", "Excel",
					new FileInputStream(excelFilePath));
		} catch ( FileNotFoundException e ) {
			//unreachable code because if the file was not found then the reader would have failed at a previous step
		}
		attachments.add(testDataFileAttachment);
	}

	/**
	 * Switch to your Sheet in case you have multiple sheets in your Excel file
	 *
	 * @param sheetName enter the name of the sheet you want to switch to
	 */
	public static void switchToSheet (String sheetName) {
		try {
			sheet = workbook.getSheet(sheetName);
            CustomReporter.logStep("Switched to sheet: [ " + sheetName + " ] from file: [ " + excelFilePath + " ].");
		} catch ( Exception e ) {
            CustomReporter.logMessage("Couldn't find the desired sheet. [ " + sheetName + " ]." + errorMessageException + e.getMessage());
			fail("Couldn't find the desired sheet. [ " + sheetName + " ]." + errorMessageException + e.getMessage());
		}
	}


	/**
	 * Reads cell data from a specific sheet name inside the Excel file Reads cell
	 * data using row name (1st column) and column name
	 * <p>
	 * the name of the target Excel sheet
	 *
	 * @param rowName    Enter the row name of the Sheet
	 * @param columnName the value of the first cell of the target column
	 *
	 * @return the value of the target cell within the target row and column within
	 * the target sheet
	 */
	public static String getCellData (String rowName, String columnName) {
		try {
			int rowNum = getRowNumberFromRowName(rowName);
			int colNum = getColumnNumberFromColumnName(columnName);
			// get the desired row
			row = sheet.getRow(rowNum); // why use -1 here?
			// get the desired cell
			cell = row.getCell(colNum);
			// return cell value given the different cell types
			return getCellData();
		} catch ( Exception e ) {
			fail("Failed to read data from row [" + rowName + "] and column [" + columnName
					+ "] in the Test Data Sheet [" + sheet + "], under the following path [" + excelFilePath
					+ "].", e);
			return "";
		}
	}

	/**
	 * Get the cell by Column and Row name  from your Excel file using sheet name as parameter to switch to the sheet first
	 *
	 * @param sheetName  Enter the sheetName of the Sheet
	 * @param rowName    Enter the row name of the Sheet
	 * @param columnName Enter the column Name name
	 *
	 * @return the value of the target cell within the target row and column within the target sheet
	 */
	public static String getCellData (String sheetName, String rowName, String columnName) {
		return getCellData(sheetName, rowName, columnName);
	}

	// ************ Get Cell Data By index  ************//

	/**
	 * Get the cell by Column name and Row number from your Excel file
	 *
	 * @param columnName Enter the column Name name
	 * @param rowNumber  Enter the row  of the Sheet index start from 1
	 */
	public static String getCellData (int rowNumber, String columnName) {
		try {
			int colNum = getColumnNumberFromColumnName(columnName);
			row = sheet.getRow(rowNumber - 1);
			cell = row.getCell(colNum);
			return getCellData();
		} catch ( Exception e ) {
			fail("Failed to read data from row [" + rowNumber + "] and column [" + columnName
					+ "] in the Test Data Sheet [" + sheet + "], under the following path [" + excelFilePath
					+ "].", e);
			return "";
		}
	}

	public static String getCellData (String sheetName, int rowNumber, String columnName) {
		switchToSheet(sheetName);
		return getCellData(rowNumber, columnName);
	}

	/**
	 * Get the cell by Column and Row number from your Excel file
	 *
	 * @param rowNumber    Enter the row  of the Sheet index start from 1
	 * @param columnNumber Enter the column number of the Sheet index start from 1
	 *
	 * @return String cell data as String
	 */
	public static String getCellData (int rowNumber, int columnNumber) {
		try {
			row = sheet.getRow(rowNumber - 1);
			cell = row.getCell(columnNumber - 1);
			return getCellData();
		} catch ( Exception e ) {
			fail("Failed to read data from row [" + rowNumber + "] and column [" + columnNumber
					+ "] in the Test Data Sheet [" + sheet + "], under the following path [" + excelFilePath
					+ "].", e);
			return "";
		}
	}

	/**
	 * Get the cell by Column and Row number from your Excel file using sheet name as parameter to switch to the sheet first
	 *
	 * @param sheetName    Enter the sheet name to switch to
	 * @param rowNumber    Enter the row number of the Sheet index start from 1
	 * @param columnNumber Enter the column number of the Sheet index start from 1
	 *
	 * @return String cell data as String value of the target cell within the target row and column within the target sheet
	 */
	public static String getCellData (String sheetName, int rowNumber, int columnNumber) {
		switchToSheet(sheetName);
		return getCellData(rowNumber, columnNumber);
	}

	// ****************************** private methods ********************************//
	private static void initializeVariables () {
		fis = null;
		workbook = null;
		sheet = null;
		row = null;
		cell = null;
		excelFilePath = "";
	}

	private static int getRowNumberFromRowName (String rowName) {
		try {
			// get the row number that corresponds to the desired rowName within the first
			// column [0]
			for ( int i = 0; i <= sheet.getLastRowNum(); i++ ) {
				row = sheet.getRow(i);
				// get the first cell of each row, and compare it to rowName
				// if they match then that's the row we want
				if ( row != null && row.getCell(0).getStringCellValue().equals(rowName) ) {
					return i;
				}
				// in certain cases if the row is empty, its value is set to null, and hence a
				// null pointer exception is thrown when
				// you try to get the cell from it.
				// we can skip this exception by checking if row != null.
			}
			// in case you provided valid data type, no exceptions were thrown, and yet the
			// rowName you mentioned was not present in this sheet
			fail("Failed to get the row number that corresponds to rowName [" + rowName + "] in the Test Data Sheet ["
					+ sheet + "], under the following path [" + excelFilePath + "].");
			return - 1; // in case of failure this line is unreachable
		} catch ( Exception e ) {
			fail("Failed to get the row number that corresponds to rowName [" + rowName + "] in the Test Data Sheet ["
					+ sheet + "], under the following path [" + excelFilePath + "].", e);
			return - 1; // in case of failure this line is unreachable
		}
	}

	private static int getColumnNumberFromColumnName (String columnName) {
		try {
			// get the column number that corresponds to the desired columnName within the
			// target row [row_Num]
			// if no column name is provided, retrieves data from the 2nd
			// column (1st Value in the test data file)
			if ( ! columnName.equals("") ) {
				row = sheet.getRow(0);
				for ( int i = 0; i < row.getLastCellNum(); i++ ) {
					// get the first cell of each column, and compare it to columnName
					// if they match then that's the column we want
					if ( row.getCell(i).getStringCellValue().equals(columnName) ) {
						return i;
					}
				}
			} else {
				return 1;
			}
			// in case you provided valid data type, no exceptions were thrown, and yet the
			// columnName you mentioned was not present in this sheet
			fail("Failed to get the column number that corresponds to columnName [" + columnName
					+ "] in the Test Data Sheet [" + sheet + "], under the following path [" + excelFilePath
					+ "].");
			return - 1; // in case of failure this line is unreachable
		} catch ( Exception e ) {
			fail("Failed to get the column number that corresponds to columnName [" + columnName
					+ "] in the Test Data Sheet [" + sheet + "], under the following path [" + excelFilePath
					+ "].", e);
			return - 1; // in case of failure this line is unreachable
		}
	}

	private static String getCellData () {
		try {
			if ( cell.getCellType() == CellType.STRING ) {
				return cell.getStringCellValue();
			} else if ( cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA ) {
				String cellValue = java.lang.String.valueOf(cell.getNumericCellValue());
				if ( cellValue.contains(".0") ) {
					cellValue = cellValue.split("\\.")[0];
				}
				if ( DateUtil.isCellDateFormatted(cell) ) {
					DateFormat df = new SimpleDateFormat("dd/MM/yy");
					Date date = cell.getDateCellValue();
					cellValue = df.format(date);
				}
				return cellValue;
			} else if ( cell.getCellType() == CellType.BOOLEAN ) {
				return java.lang.String.valueOf(cell.getBooleanCellValue());
			} else {
				return "";
			}
		} catch ( Exception e ) {
			return "";
		}
	}


	private int getRowCountInSheet () {
		int rowCount = 0;
		try {
			rowCount = getLastRowNumber() - getFirstRowNumber();
            CustomReporter.logStep("Row count from the sheet [" + sheet.getSheetName() + "] is [ " + rowCount + " ]");
		} catch ( Exception e ) {
            CustomReporter.logMessage("Can't find the row count from the sheet [" + sheet.getSheetName() + "]  " + errorMessageException + e.getMessage());
			fail("Can't find the row count from the sheet [" + sheet.getSheetName() + "]  " + errorMessageException + e.getMessage());
		}
		return rowCount;
	}

	// ************ helper Methods  ************//
	public int getFirstRowNumber () {
		return sheet.getFirstRowNum();
	}

	public int getLastRowNumber () {
		return sheet.getLastRowNum();
	}

	public int getFirstColumnNumber () {
		return sheet.getRow(0).getFirstCellNum();
	}

	public int getLastColumnNumber () {
		return sheet.getRow(0).getLastCellNum();
	}

	/**
	 * Get the last row number from your Excel file
	 */
	public void getAllDataInSheet (String sheetName) {
		switchToSheet(sheetName);
		//get all rows in the sheet
		int rowCount = getRowCountInSheet();
		//iterate over all the row to print the data present in each cell.
		for ( int i = 0; i <= rowCount; i++ ) {
			//get cell count in a row
			int cellCount = sheet.getRow(i).getLastCellNum();
			//iterate over each cell to print its value
            CustomReporter.logStep("Row " + i + " data is :");
			for ( int j = 0; j < cellCount; j++ ) {
                CustomReporter.logStep(sheet.getRow(i).getCell(j).getStringCellValue() + ",");
			}
			System.out.println();
		}
	}


	/**
	 * Extracts the first sheet name from the desired workbook.
	 *
	 * @return the first sheet name for the current test data file
	 */
	private String getDefaultSheetName () {
		return workbook.getSheetName(0);
	}

}
