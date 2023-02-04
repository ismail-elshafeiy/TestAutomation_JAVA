package engine.tools.io;

import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.*;
import engine.tools.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static org.testng.Assert.fail;

public class ExcelFileManager {

	private FileInputStream fis;
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private XSSFRow row;
	private XSSFCell cell;
	private static File spreadSheet;
	private static Sheet currentSheet;
	private static Map<String, Integer> columns;
	private static final String errorMessageException = " Error Message Exception: --> ";
	private static final String testMethodName = " Test Method Name:  ";

	public ExcelFileManager (File filePathFromRoot) {
		String fileErrorMessage = testMethodName + "Couldn't find the desired file. [ " + filePathFromRoot + " ]. ";
		try {
			spreadSheet = filePathFromRoot;
			columns = new HashMap<String, Integer>();
			this.fis = new FileInputStream(filePathFromRoot);
			this.workbook = new XSSFWorkbook(this.fis);
			this.fis.close();
			Logger.logMessage("File has been loaded successfully." + System.lineSeparator() + "File Path: " + filePathFromRoot.getAbsolutePath());
		} catch ( IOException | OutOfMemoryError e ) {
			Logger.logMessage(fileErrorMessage + errorMessageException + e.getMessage());
			Assert.fail(fileErrorMessage + errorMessageException + e.getMessage());
		} catch ( EmptyFileException e ) {
			Logger.logMessage(testMethodName + "Please check the target file, as it may be corrupted. [ " + filePathFromRoot + " ]." + errorMessageException + e.getMessage());
			Assert.fail(testMethodName + "Please check the target file, as it may be corrupted. [ " + filePathFromRoot + " ]." + errorMessageException + e.getMessage());
		}
	}

	/**
	 * Switch to your Sheet in case you have multiple sheets in your Excel file
	 *
	 * @param name Enter the name of the Sheet
	 */
	public static void switchToSheet (String name) {
		try ( Workbook workbooks = WorkbookFactory.create(spreadSheet) ) {
			currentSheet = workbooks.getSheet(name);
			Logger.logMessage("Switched to sheet: [ " + name + " ] from file: [ " + spreadSheet + " ].");
			getRowCountInSheet();
			currentSheet.getRow(0).forEach(cell -> {
				columns.put(cell.getStringCellValue(), cell.getColumnIndex());
				Logger.logMessage("Column name: [ " + cell.getStringCellValue() + " ], Column index: [ " + cell.getColumnIndex() + " ].");
			});
		} catch ( Exception e ) {
			Logger.logMessage(testMethodName + "Couldn't find the desired sheet. [ " + name + " ]." + errorMessageException + e.getMessage());
			Assert.fail(testMethodName + "Couldn't find the desired sheet. [ " + name + " ]." + errorMessageException + e.getMessage());
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
		} catch ( NullPointerException e ) {

			Logger.logStep(columnErrorMessage + errorMessageException + e.getMessage());
			fail(columnErrorMessage + errorMessageException + e.getMessage());
		} catch ( Exception e ) {
			Logger.logMessage(columnErrorMessage + errorMessageException + e.getMessage());
			fail(columnErrorMessage + errorMessageException + e.getMessage());
		}
		return null;
	}

	/**
	 * Get the cell by Column Name from your Excel fil
	 *
	 * @param columnName
	 *
	 * @return String value of the cell
	 */
	public static String getCellData (String columnName) {
		try {
			return getCellData(columnName, 2);
		} catch ( NullPointerException e ) {
			Logger.logStep("Can't find the columnName name [" + columnName + "] from the sheet [" + currentSheet.getSheetName() + "]  " + errorMessageException + e.getMessage() + testMethodName);
			fail("Can't find the columnName name [" + columnName + "]..Null Pointer Exception --> " + errorMessageException + e.getMessage() + testMethodName);
		} catch ( Exception e ) {
			Logger.logMessage("Can't find the columnName Name name [" + columnName + "]........" + errorMessageException + e.getMessage() + testMethodName);
			fail("Can't find the columnName Name name [" + columnName + "]........" + errorMessageException + e.getMessage() + testMethodName);
		}
		return columnName;
	}

	/**
	 * Get the cell by Column and Row number from your Excel file
	 *
	 * @param rowNumber
	 * @param columnNumber
	 *
	 * @return String cell data as String
	 */
	public static String getCellData (int rowNumber, int columnNumber) {
		Row dataRow = currentSheet.getRow(rowNumber - 1);
		if ( dataRow == null ) {
			Logger.logStep(testMethodName + "Can't find the row number [" + rowNumber + "] from the sheet [" + currentSheet.getSheetName() + "] ");
			fail(testMethodName + "Can't find the row number [" + rowNumber + "] from the sheet [" + currentSheet.getSheetName() + "]");
		}
		return getCellDataAsString(dataRow.getCell(columnNumber));
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

	public static int getRowCountInSheet () {
		int rowCount = 0;
		try {
			rowCount = getLastRowNumber() - getFirstRowNumber();
			Logger.logMessage("Row count from the sheet [" + currentSheet.getSheetName() + "] is [ " + rowCount + " ]");
		} catch ( Exception e ) {
			Logger.logMessage("Can't find the row count from the sheet [" + currentSheet.getSheetName() + "]  " + errorMessageException + e.getMessage() + testMethodName);
			fail("Can't find the row count from the sheet [" + currentSheet.getSheetName() + "]  " + errorMessageException + e.getMessage() + testMethodName);
		}
		return rowCount;
	}

	/**
	 * @param cell Enter the cell  of the Sheet
	 */
	private static String getCellDataAsString (Cell cell) {
		return switch ( cell.getCellType() ) {
			case STRING -> cell.getStringCellValue();
			case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());
			default -> "";
		};
	}

	private String getDefaultSheetName () {
		return this.workbook.getSheetName(0);
	}
}