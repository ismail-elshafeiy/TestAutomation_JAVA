package engine.dataDriven;

import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.*;
import engine.listeners.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import java.io.*;
import java.util.*;
import static org.testng.Assert.fail;

public class ExcelFileManager {

	private FileInputStream fis;
	private static XSSFWorkbook workbook;
	private static XSSFSheet currentSheet;
	private XSSFRow row;
	private static XSSFCell cell;
	private static File spreadSheet;
	// private static Sheet currentSheet;
	private static Map<String, Integer> columns;
	private static final String errorMessageException = " Error Message Exception: --> ";
	private static final String testMethodName = " Test Method Name:  ";

	/**
	 * Constructor to load the Excel file from the root directory of the project
	 *
	 * @param filePathFromRoot Enter the path of the file from the root directory of the project
	 */
	public ExcelFileManager (File filePathFromRoot) {
		String fileErrorMessage = testMethodName + "Couldn't find the desired file. [ " + filePathFromRoot + " ]. ";
		try {
			spreadSheet = filePathFromRoot;
			columns = new HashMap<String, Integer>();
			this.fis = new FileInputStream(filePathFromRoot);
			this.workbook = new XSSFWorkbook(this.fis);
			this.fis.close();
			Logger.logStep("File has been loaded successfully." + System.lineSeparator() + "File Path: " + filePathFromRoot.getAbsolutePath());
		} catch ( IOException | OutOfMemoryError e ) {
			Logger.logMessage(fileErrorMessage + errorMessageException + e.getMessage());
			Assert.fail(fileErrorMessage + errorMessageException + e.getMessage());
		} catch ( EmptyFileException e ) {
			Logger.logMessage(testMethodName + "Please check the target file, as it may be corrupted. [ " + filePathFromRoot + " ]." + errorMessageException + e.getMessage());
			Assert.fail(testMethodName + "Please check the target file, as it may be corrupted. [ " + filePathFromRoot + " ]." + errorMessageException + e.getMessage());
		}
	}

	// TODO: 1/29/2020  set Excel File
	public void setExcelFile (String excelFilePath, String sheetName) throws IOException {
		try {
			//Create an object of File class to open xls file
			File file = new File(excelFilePath);
			//Create an object of FileInputStream class to read excel file
			FileInputStream inputStream = new FileInputStream(file);
			//creating workbook instance that refers to .xls file
			workbook = new XSSFWorkbook(inputStream);
			//creating a Sheet object
			currentSheet = workbook.getSheet(sheetName);
		} catch ( Exception e ) {
			Logger.logMessage("Error in reading data from excel file... Message --> " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Switch to your Sheet in case you have multiple sheets in your Excel file
	 *
	 * @param sheetName Enter the sheetName of the Sheet
	 */
	public static void switchToSheet (String sheetName) {
		try ( Workbook workbooks = WorkbookFactory.create(spreadSheet) ) {
			currentSheet = (XSSFSheet) workbooks.getSheet(sheetName);
			Logger.logStep("Switched to sheet: [ " + sheetName + " ] from file: [ " + spreadSheet + " ].");
			getRowCountInSheet();
			currentSheet.getRow(0).forEach(cell -> {
				columns.put(cell.getStringCellValue(), cell.getColumnIndex());
				Logger.logStep("Column sheetName: [ " + cell.getStringCellValue() + " ], Column index: [ " + cell.getColumnIndex() + " ].");
			});
		} catch ( Exception e ) {
			Logger.logMessage(testMethodName + "Couldn't find the desired sheet. [ " + sheetName + " ]." + errorMessageException + e.getMessage());
			Assert.fail(testMethodName + "Couldn't find the desired sheet. [ " + sheetName + " ]." + errorMessageException + e.getMessage());
		}
	}

	/**
	 * Get the cell by Column name and Row number from your Excel file
	 *
	 * @param columnName enter the column Name name
	 * @param rowNumber  Enter the row  of the Sheet index start from 1
	 */
	public static String getCellData (String columnName, int rowNumber) {
		String columnErrorMessage = testMethodName + "Can't find the column name [ " + columnName + " ] from the sheet [ " + currentSheet.getSheetName() + " ]  ";
		try {
			Row dataRow = currentSheet.getRow(rowNumber - 1);
			if ( dataRow == null ) {
				Logger.logStep(testMethodName + "Can't find the row number [ " + rowNumber + " ] from the sheet [ " + currentSheet.getSheetName() + " ] ");
				fail(testMethodName + "Can't find the row number [ " + rowNumber + " ] from the sheet [ " + currentSheet.getSheetName() + " ]");
			}
			return getCellDataAsString(dataRow.getCell(columns.get(columnName)));
		} catch ( Exception e ) {
			Logger.logMessage(columnErrorMessage + errorMessageException + e.getMessage());
			fail(columnErrorMessage + errorMessageException + e.getMessage());
		}
		return null;
	}

	public static String getCellData (String sheetName, String columnName, int rowNumber) {
		switchToSheet(sheetName);
		return getCellData(columnName, rowNumber);
	}

	//TODO: Refactor set cell data
	public static void setCellData (String value, int columnNumber, int rowNumber) {
		String columnErrorMessage = testMethodName + "Can't find the column name [ " + columnNumber + " ] from the sheet [ " + currentSheet.getSheetName() + " ]  ";
		try {
//			Row dataRow = currentSheet.getRow(rowNumber - 1);
//			if ( dataRow == null ) {
//				Logger.logMessage(testMethodName + "Can't find the row number [ " + rowNumber + " ] from the sheet [ " + currentSheet.getSheetName() + " ] ");
//				fail(testMethodName + "Can't find the row number [ " + rowNumber + " ] from the sheet [ " + currentSheet.getSheetName() + " ]");
//			}
//			Cell cell = dataRow.getCell(columns.get(columnName));
//			if ( cell == null ) {
//				cell = dataRow.createCell(columns.get(columnName));
//			}
			//create a new cell in the row at index 6
			cell = currentSheet.createRow(rowNumber).createCell(columnNumber);
			cell.setCellValue(value);
			FileOutputStream fos = new FileOutputStream(spreadSheet);
			workbook.write(fos);
			fos.close();
		} catch ( Exception e ) {
			Logger.logMessage(columnErrorMessage + errorMessageException + e.getMessage());
			fail(columnErrorMessage + errorMessageException + e.getMessage());
		}
	}

	/**
	 * Get the cell by Column and Row number from your Excel file
	 *
	 * @param rowNumber    Enter the row  of the Sheet index start from 1
	 * @param columnNumber Enter the column number of the Sheet index start from 1
	 *
	 * @return String cell data as String
	 */
	public static String getCellData (String sheetName, int rowNumber, int columnNumber) {
		switchToSheet(sheetName);
		Row dataRow = currentSheet.getRow(rowNumber - 1);
		if ( dataRow == null ) {
			Logger.logMessage(testMethodName + "Can't find the row number [" + rowNumber + "] from the sheet [" + currentSheet.getSheetName() + "] ");
			fail(testMethodName + "Can't find the row number [" + rowNumber + "] from the sheet [" + currentSheet.getSheetName() + "]");
		}
		return getCellDataAsString(dataRow.getCell(columnNumber));
	}

	// TODO: 11/12/2019  add the method to get the cell data as string but need to refactor the code
	public void setCellValue (int rowNum, int cellNum, String cellValue, String excelFilePath) throws IOException {
		//creating a new cell in row and setting value to it
		currentSheet.getRow(rowNum).createCell(cellNum).setCellValue(cellValue);
		FileOutputStream outputStream = new FileOutputStream(excelFilePath);
		workbook.write(outputStream);
	}

	/**
	 * Get the cell by Column Name from your Excel fil
	 *
	 * @param columnName Enter the column Name
	 *
	 * @return String value of the cell
	 */
	public static String getCellData (String columnName) {
		try {
			Logger.logStep("Getting the cell data from the column name: [ " + columnName + " ] from the sheet [ " + currentSheet.getSheetName() + " ] ");
			return getCellData(columnName, 2);
		} catch ( NullPointerException e ) {
			Logger.logMessage("Can't find the columnName name [" + columnName + "] from the sheet [" + currentSheet.getSheetName() + "]  " + errorMessageException + e.getMessage() + testMethodName);
			fail("Can't find the columnName name [" + columnName + "]..Null Pointer Exception --> " + errorMessageException + e.getMessage() + testMethodName);
		} catch ( Exception e ) {
			Logger.logMessage("Can't find the columnName Name name [" + columnName + "]........" + errorMessageException + e.getMessage() + testMethodName);
			fail("Can't find the columnName Name name [" + columnName + "]........" + errorMessageException + e.getMessage() + testMethodName);
		}
		return columnName;
	}


	private static int getRowCountInSheet () {
		int rowCount = 0;
		try {
			rowCount = getLastRowNumber() - getFirstRowNumber();
			Logger.logStep("Row count from the sheet [" + currentSheet.getSheetName() + "] is [ " + rowCount + " ]");
		} catch ( Exception e ) {
			Logger.logMessage("Can't find the row count from the sheet [" + currentSheet.getSheetName() + "]  " + errorMessageException + e.getMessage() + testMethodName);
			fail("Can't find the row count from the sheet [" + currentSheet.getSheetName() + "]  " + errorMessageException + e.getMessage() + testMethodName);
		}
		return rowCount;
	}

	/**
	 * Get the last row number from your Excel file
	 */
	public static void getData () {
		//get all rows in the sheet
		int rowCount = getRowCountInSheet();
		//iterate over all the row to print the data present in each cell.
		for ( int i = 0; i <= rowCount; i++ ) {
			//get cell count in a row
			int cellCount = currentSheet.getRow(i).getLastCellNum();
			//iterate over each cell to print its value
			Logger.logStep("Row " + i + " data is :");
			for ( int j = 0; j < cellCount; j++ ) {
				Logger.logStep(currentSheet.getRow(i).getCell(j).getStringCellValue() + ",");
			}
			System.out.println();
		}
	}

	public static int getFirstRowNumber () {
		return currentSheet.getFirstRowNum();
	}

	public static int getLastRowNumber () {
		return currentSheet.getLastRowNum();
	}

	public static int getFirstColumnNumber () {
		return currentSheet.getRow(0).getFirstCellNum();
	}

	public static int getLastColumnNumber () {
		return currentSheet.getRow(0).getLastCellNum();
	}

	/**
	 * @param cell Enter the cell  of the Sheet
	 */
	private static String getCellDataAsString (Cell cell) {
		return switch ( cell.getCellType() ) {
			case STRING -> cell.getStringCellValue();
			case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());
			case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
			case FORMULA -> cell.getCellFormula();
			case BLANK -> "";
			default -> "";
		};
	}

	private String getDefaultSheetName () {
		return this.workbook.getSheetName(0);
	}


}