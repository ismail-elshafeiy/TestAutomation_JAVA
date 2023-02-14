package engine.dataDriven;

import engine.Helper;
import engine.listeners.Logger;
import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.testng.Assert.fail;

public class ExcelFileManager1 {
	private FileInputStream fis;
	private File file;
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private XSSFRow row;
	private static Map<String, Integer> columns;
	private XSSFCell cell;
	private String excelFilePath;
	private static final String errorMessageException = " Error Message Exception: --> ";

	/**
	 * Creates a new instance of the test data Excel reader using the target Excel
	 * file path
	 *
	 * @param excelFilePath target test data Excel file path
	 */
	public ExcelFileManager1 (String excelFilePath) {
		initializeVariables();
		this.excelFilePath = excelFilePath;
		try {
			fis = new FileInputStream(excelFilePath);
			workbook = new XSSFWorkbook(fis);
			fis.close();
			Logger.logMessage("Reading test data from the following file [" + excelFilePath + "].");
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
	 * @param sheetName Enter the sheetName of the Sheet
	 */
	public void switchToSheet (String sheetName) {
		try ( Workbook workbooks = WorkbookFactory.create(file) ) {
			this.sheet = (XSSFSheet) workbooks.getSheet(sheetName);
			Logger.logStep("Switched to sheet: [ " + sheetName + " ] from file: [ " + this.sheet + " ].");
			//getRowCountInSheet();
			this.sheet.getRow(0).forEach(cell -> {
				columns.put(cell.getStringCellValue(), cell.getColumnIndex());
				//Logger.logStep("Column sheetName: [ " + cell.getStringCellValue() + " ], Column index: [ " + cell.getColumnIndex() + " ].");
			});
		} catch ( Exception e ) {
			Logger.logMessage("Couldn't find the desired sheet. [ " + sheetName + " ]." + errorMessageException + e.getMessage());
			Assert.fail("Couldn't find the desired sheet. [ " + sheetName + " ]." + errorMessageException + e.getMessage());
		}
	}

	/**
	 * Reads cell data from a specific sheet name inside the Excel file Reads cell
	 * data using row name (1st column) and column name
	 *
	 * @param sheetName  the name of the target Excel sheet
	 * @param rowName    the value of the first cell of the target row
	 * @param columnName the value of the first cell of the target column
	 *
	 * @return the value of the target cell within the target row and column within
	 * the target sheet
	 */
	public String getCellData (String sheetName, String rowName, String columnName) {
		try {
			int rowNum = getRowNumberFromRowName(sheetName, rowName);
			int colNum = getColumnNumberFromColumnName(sheetName, columnName);
			// get the desired row
			row = sheet.getRow(rowNum); // why use -1 here?
			// get the desired cell
			cell = row.getCell(colNum);
			// return cell value given the different cell types
			return getCellData();
		} catch ( Exception e ) {
			Assert.fail("Failed to read data from row [" + rowName + "] and column [" + columnName
					+ "] in the Test Data Sheet [" + sheetName + "], under the following path [" + excelFilePath
					+ "].", e);
			return "";
		}
	}

	// ************ Get Cell Data By index  ************//

	/**
	 * Get the cell by Column name and Row number from your Excel file
	 *
	 * @param columnName enter the column Name name
	 * @param rowNumber  Enter the row  of the Sheet index start from 1
	 */
	public String getCellData (int rowNumber, String columnName) {
		String columnErrorMessage = "Can't find the column name [ " + columnName + " ] from the sheet [ " + this.sheet.getSheetName() + " ]  ";
		try {
			Row dataRow = this.sheet.getRow(rowNumber - 1);
			if ( dataRow == null ) {
				Logger.logStep("Can't find the row number [ " + rowNumber + " ] from the sheet [ " + this.sheet.getSheetName() + " ] ");
				fail("Can't find the row number [ " + rowNumber + " ] from the sheet [ " + this.sheet.getSheetName() + " ]");
			}
			Logger.logStep("Getting cell data from column [ " + columnName + " ] and row [ " + rowNumber + " ] from sheet [ " + this.sheet.getSheetName() + " ]");
			return getCellDataAsString(dataRow.getCell(columns.get(columnName)));
		} catch ( Exception e ) {
			Logger.logMessage(columnErrorMessage + errorMessageException + e.getMessage());
			fail(columnErrorMessage + errorMessageException + e.getMessage());
		}
		return null;
	}

	public String getCellData (String sheetName, int rowNumber, String columnName) {
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
	public String getCellData (String sheetName, int rowNumber, int columnNumber) {
		switchToSheet(sheetName);
		Row dataRow = this.sheet.getRow(rowNumber - 1);
		if ( dataRow == null ) {
			Logger.logMessage("Can't find the row number [" + rowNumber + "] from the sheet [" + this.sheet.getSheetName() + "] ");
			fail("Can't find the row number [" + rowNumber + "] from the sheet [" + this.sheet.getSheetName() + "]");
		}
		return getCellDataAsString(dataRow.getCell(columnNumber));
	}

	// ************ helper Methods  ************//
	public int getFirstRowNumber () {
		return this.sheet.getFirstRowNum();
	}

	public int getLastRowNumber () {
		return this.sheet.getLastRowNum();
	}

	public int getFirstColumnNumber () {
		return this.sheet.getRow(0).getFirstCellNum();
	}

	public int getLastColumnNumber () {
		return this.sheet.getRow(0).getLastCellNum();
	}

	/**
	 * Get the last row number from your Excel file
	 */
	public void getData () {
		//get all rows in the sheet
		int rowCount = getRowCountInSheet();
		//iterate over all the row to print the data present in each cell.
		for ( int i = 0; i <= rowCount; i++ ) {
			//get cell count in a row
			int cellCount = this.sheet.getRow(i).getLastCellNum();
			//iterate over each cell to print its value
			Logger.logStep("Row " + i + " data is :");
			for ( int j = 0; j < cellCount; j++ ) {
				Logger.logStep(this.sheet.getRow(i).getCell(j).getStringCellValue() + ",");
			}
			System.out.println();
		}
	}


	// ****************************** private methods ********************************//
	private void initializeVariables () {
		fis = null;
		workbook = null;
		sheet = null;
		row = null;
		cell = null;
		excelFilePath = "";
	}

	private int getRowNumberFromRowName (String sheetName, String rowName) {
		try {
			// get the row number that corresponds to the desired rowName within the first
			// column [0]
			sheet = workbook.getSheet(sheetName);
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
			Assert.fail("Failed to get the row number that corresponds to rowName [" + rowName + "] in the Test Data Sheet ["
					+ sheetName + "], under the following path [" + excelFilePath + "].");
			return - 1; // in case of failure this line is unreachable
		} catch ( Exception e ) {
			Assert.fail("Failed to get the row number that corresponds to rowName [" + rowName + "] in the Test Data Sheet ["
					+ sheetName + "], under the following path [" + excelFilePath + "].", e);
			return - 1; // in case of failure this line is unreachable
		}
	}

	private int getColumnNumberFromColumnName (String sheetName, String columnName) {
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
			Assert.fail("Failed to get the column number that corresponds to columnName [" + columnName
					+ "] in the Test Data Sheet [" + sheetName + "], under the following path [" + excelFilePath
					+ "].");
			return - 1; // in case of failure this line is unreachable
		} catch ( Exception e ) {
			Assert.fail("Failed to get the column number that corresponds to columnName [" + columnName
					+ "] in the Test Data Sheet [" + sheetName + "], under the following path [" + excelFilePath
					+ "].", e);
			return - 1; // in case of failure this line is unreachable
		}
	}

	private String getCellData () {
		try {
			if ( cell.getCellType() == CellType.STRING ) {
				return cell.getStringCellValue();
			} else if ( cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA ) {
				String cellValue = String.valueOf(cell.getNumericCellValue());
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
				return String.valueOf(cell.getBooleanCellValue());
			} else {
				return "";
			}
		} catch ( Exception e ) {
			return "";
		}
	}


	/**
	 * @param cell Enter the cell  of the Sheet
	 */
	private static String getCellDataAsString (Cell cell) {
		return switch ( cell.getCellType() ) {
			case STRING -> cell.getStringCellValue();
			case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());
			case BLANK -> "";
			default -> "";
		};
	}

	private int getRowCountInSheet () {
		int rowCount = 0;
		try {
			rowCount = getLastRowNumber() - getFirstRowNumber();
			Logger.logStep("Row count from the sheet [" + this.sheet.getSheetName() + "] is [ " + rowCount + " ]");
		} catch ( Exception e ) {
			Logger.logMessage("Can't find the row count from the sheet [" + this.sheet.getSheetName() + "]  " + errorMessageException + e.getMessage());
			fail("Can't find the row count from the sheet [" + this.sheet.getSheetName() + "]  " + errorMessageException + e.getMessage());
		}
		return rowCount;
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
