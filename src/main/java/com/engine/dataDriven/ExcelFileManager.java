package com.engine.dataDriven;

import com.engine.reports.CustomReporter;
import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.testng.Assert;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.testng.Assert.fail;
public class ExcelFileManager {
	private static final String EXCEPTION_ERROR_MESSAGE = " Exception Error Message: --> ";
	private static FileInputStream fis;
	private static FileOutputStream fos;
	private static XSSFWorkbook workbook;
	private static XSSFSheet sheet;
	private static XSSFRow row;
	private static XSSFCell cell;
	private static XSSFCellStyle style;
	private static String excelFilePath;


	private static void initializeVariables() {
		fis = null;
		fos = null;
		workbook = null;
		sheet = null;
		row = null;
		cell = null;
		style = null;
		excelFilePath = "";
	}

	public ExcelFileManager() {
		initializeVariables();
	}

	/**
	 * Creates a new instance of the test data Excel reader using the target Excel
	 * file path
	 *
	 * @param excelFilePath target test data Excel file path
	 */
	public ExcelFileManager(String excelFilePath) {
		initializeVariables();
		ExcelFileManager.excelFilePath = excelFilePath;
		try {
			fis = new FileInputStream(excelFilePath);
			workbook = new XSSFWorkbook(fis);
			fis.close();
			CustomReporter.logConsole("Reading test data from the following file [" + excelFilePath + "].");
		} catch (IOException | OutOfMemoryError e) {
			Assert.fail("Couldn't find the desired file. [" + excelFilePath + "].", e);
		} catch (EmptyFileException e) {
			Assert.fail("Please check the target file, as it may be corrupted. [" + excelFilePath + "].", e);
		}
		List<List<Object>> attachments = new ArrayList<>();
		List<Object> testDataFileAttachment = null;
		try {
			testDataFileAttachment = Arrays.asList("Test Data", "Excel", new FileInputStream(excelFilePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			//unreachable code because if the file was not found then the reader would have failed at a previous step
		}
		attachments.add(testDataFileAttachment);
	}

	public ExcelFileManager(String excelFilePath, String sheetName) {
		initializeVariables();
		ExcelFileManager.excelFilePath = excelFilePath;
		try {
			fis = new FileInputStream(excelFilePath);
			workbook = new XSSFWorkbook(fis);
			switchToSheet(sheetName);
			fis.close();
			CustomReporter.logConsole("Reading test data from the following file [" + excelFilePath + "],and sheet [" + sheetName + "].");
		} catch (IOException | OutOfMemoryError e) {
			Assert.fail("Couldn't find the desired file. [" + excelFilePath + "]. " + EXCEPTION_ERROR_MESSAGE + e.getMessage(), e);
		} catch (EmptyFileException e) {
			Assert.fail("Please check the target file, as it may be corrupted. [" + excelFilePath + "]. " + EXCEPTION_ERROR_MESSAGE + e.getMessage(), e);
		}
		List<List<Object>> attachments = new ArrayList<>();
		List<Object> testDataFileAttachment = null;
		try {
			testDataFileAttachment = Arrays.asList("Test Data", "Excel", new FileInputStream(excelFilePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			//unreachable code because if the file was not found then the reader would have failed at a previous step
		}
		attachments.add(testDataFileAttachment);
	}

	public static FileInputStream getFileInputStream(String filePath) {
		excelFilePath = String.valueOf(new File(filePath));
		try {
			fis = new FileInputStream(excelFilePath);
			return fis;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			CustomReporter.logError("There is no file exist in the path: [ " + filePath + " ] , please check the path and try again." + EXCEPTION_ERROR_MESSAGE + e.getMessage());
			Assert.fail("There is no file exist in the path: [ " + filePath + " ] , please check the path and try again. " + EXCEPTION_ERROR_MESSAGE + e.getMessage());
		}
		return fis;
	}

	public static void closeFile() {
		CustomReporter.logConsole("Closing the Excel file: [ " + excelFilePath + " ]");
		try {
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			CustomReporter.logError("There is no file exist in the path: [ " + excelFilePath + " ] , please check the path and try again." + EXCEPTION_ERROR_MESSAGE + e.getMessage());
			Assert.fail("There is no file exist in the path: [ " + excelFilePath + " ] , please check the path and try again. " + EXCEPTION_ERROR_MESSAGE + e.getMessage());
		}
	}

	/**
	 * Switch to your Sheet in case you have multiple sheets in your Excel file
	 *
	 * @param sheetName enter the name of the sheet you want to switch to
	 */
	public static void switchToSheet(String sheetName) {
		try {
			sheet = workbook.getSheet(sheetName);
			CustomReporter.logConsole("Switched to sheet: [ " + sheetName + " ] from file: [ " + excelFilePath + " ].");
		} catch (Exception e) {
			e.printStackTrace();
			CustomReporter.logError("Couldn't find the desired sheet. [ " + sheetName + " ]." + EXCEPTION_ERROR_MESSAGE + e.getMessage());
			Assert.fail("Couldn't find the desired sheet. [ " + sheetName + " ]." + EXCEPTION_ERROR_MESSAGE + e.getMessage());
		}
	}

	/**
	 * This method will return the data from the excel sheet as array
	 *
	 * @param filePath  the path of the excel file
	 * @param sheetName the name of the sheet you want to switch to
	 * @return the data from the excel sheet as array
	 * @throws FileNotFoundException if the file is not found
	 */
	public String[][] getDataAsArray(String filePath, String sheetName) {
		CustomReporter.logConsole("Reading data from Excel file: [ " + filePath + " ] | Sheet: [ " + sheetName + " ]");
		fis = getFileInputStream(filePath);
		try {
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet(sheetName);
			String[][] arrayExcelData = new String[0][];
			int rowNumber = 0, columnNumber = 0;
			try {
				rowNumber = sheet.getLastRowNum() + 1;
				columnNumber = sheet.getRow(0).getLastCellNum();
				arrayExcelData = new String[rowNumber][columnNumber];
				for (int r = 0; r < rowNumber; r++) {
					for (int c = 0; c < columnNumber; c++) {
						row = sheet.getRow(r);
						if (row.getCell(c) == null || row.getCell(c).getCellType() == CellType.BLANK || row.getCell(c).getCellType() == CellType._NONE) {
							arrayExcelData[r][c] = "";
						} else {
							arrayExcelData[r][c] = row.getCell(c).toString();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				CustomReporter.logError("Can't read data from File Path [ " + filePath + " ], Sheet: [ " + sheetName + " ]," + " Row: [" + rowNumber + "], Column: [ " + columnNumber + " ]" + Arrays.toString(arrayExcelData) + EXCEPTION_ERROR_MESSAGE + e.getMessage() + " ]");
				Assert.fail("Can't read data from File Path [ " + filePath + " ], Sheet: [ " + sheetName + " ]," + " Row: [" + rowNumber + "], Column: [ " + columnNumber + " ]" + Arrays.toString(arrayExcelData) + EXCEPTION_ERROR_MESSAGE + e.getMessage() + " ]");
			}
			workbook.close();
			fis.close();
			// CustomReporter.logConsole("Data from Excel file: [ " + Arrays.deepToString(arrayExcelData) + " ] is read successfully.");
			return arrayExcelData;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			CustomReporter.logError("There is no file exist in the path: [ " + filePath + " ] , please check the path and try again. " + e.getMessage());
			Assert.fail("There is no file exist in the path: [ " + filePath + " ] , please check the path and try again. " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			CustomReporter.logError("There is no file exist in the path: [ " + filePath + " ] , please check the path and try again. " + e.getMessage());
		}
		return new String[0][0];
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
	public static String getCellData(String rowName, String columnName) {
		try {
			int rowNum = getRowNumberFromRowName(rowName);
			int colNum = getColumnNumberFromColumnName(columnName);
			// get the desired row
			row = sheet.getRow(rowNum); // why use -1 here?
			// get the desired cell
			cell = row.getCell(colNum);
			// return cell value given the different cell types
			CustomReporter.logConsole("Reading data [ " + getCellData() + " ] from row [ " + rowName + " ] and column [ " + columnName + " ]");
			return getCellData();

		} catch (Exception e) {
			e.printStackTrace();
			CustomReporter.logWarning("Failed to read data from row [" + rowName + "] and column [" + columnName
					+ "] in the Test Data Sheet [" + sheet + "], under the following path [" + excelFilePath
					+ "]. " + e.getMessage());

			return "";
		}
	}

	/**
	 * Get the cell by Column and Row name  from your Excel file using sheet name as parameter to switch to the sheet first
	 *
	 * @param sheetName  Enter the sheetName of the Sheet
	 * @param rowName    Enter the row name of the Sheet
	 * @param columnName Enter the column Name name
	 * @return the value of the target cell within the target row and column within the target sheet
	 */
	public static String getCellData(String sheetName, String rowName, String columnName) {
		return getCellData(sheetName, rowName, columnName);
	}

	public static String getCellData(String sheetName, int rowNumber, String columnName) {
		switchToSheet(sheetName);
		return getCellData(rowNumber, columnName);
	}


	/**
	 * Get the cell by Column name and Row number from your Excel file
	 *
	 * @param columnName Enter the column Name name
	 * @param rowNumber  Enter the row  of the Sheet index start from 1
	 */
	public static String getCellData(int rowNumber, String columnName) {
		try {
			int colNum = getColumnNumberFromColumnName(columnName);
			row = sheet.getRow(rowNumber - 1);
			cell = row.getCell(colNum);
			return getCellData();
		} catch (Exception e) {
			e.printStackTrace();
			CustomReporter.logError("Failed to read data from row [" + rowNumber + "] and column [" + columnName
					+ "] in the Test Data Sheet [" + sheet + "], under the following path [" + excelFilePath
					+ "]." + e.getMessage());

			return "";
		}
	}

	/**
	 * Get the cell by Column and Row number from your Excel file using sheet name as parameter to switch to the sheet first
	 *
	 * @param sheetName    Enter the sheet name to switch to
	 * @param rowNumber    Enter the row number of the Sheet index start from 1
	 * @param columnNumber Enter the column number of the Sheet index start from 1
	 * @return String cell data as String value of the target cell within the target row and column within the target sheet
	 */
	public static String getCellData(String sheetName, int rowNumber, int columnNumber) {
		switchToSheet(sheetName);
		return getCellData(rowNumber, columnNumber);
	}

	/**
	 * Get the cell by Column and Row number from your Excel file
	 *
	 * @param rowNumber    Enter the row  of the Sheet index start from 1
	 * @param columnNumber Enter the column number of the Sheet index start from 1
	 * @return String cell data as String
	 */
	public static String getCellData(int rowNumber, int columnNumber) {
		try {
			row = sheet.getRow(rowNumber - 1);
			cell = row.getCell(columnNumber - 1);
			return getCellData();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Failed to read data from row [" + rowNumber + "] and column [" + columnNumber
					+ "] in the Test Data Sheet [" + sheet + "], under the following path [" + excelFilePath
					+ "].", e);
			return "";
		}
	}

	private static String getCellData() {
		try {
			if (cell.getCellType() == CellType.STRING) {
				return cell.getStringCellValue();
			} else if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {
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
			} else if (cell.getCellType() == CellType.BOOLEAN) {
				return String.valueOf(cell.getBooleanCellValue());
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			CustomReporter.logConsole("Failed to get the cell data from the Test Data Sheet [" + sheet + "], under the following path [" + excelFilePath + "]." + e.getMessage());

			return "";
		}
	}
	//TODO: this method needs to be tested as it is not working properly

	/**
	 * @param cell Enter the cell  of the Sheet
	 */
	private static String getCellDataAsString(Cell cell) {
		return switch (cell.getCellType()) {
			case STRING -> cell.getStringCellValue();
			case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());
			case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
			case FORMULA -> cell.getCellFormula();
			case BLANK -> "";
			default -> "";
		};
	}

	public static int getRowNumberFromRowName(String rowName, int column) {
		try {
			// get the row number that corresponds to the desired rowName within the first
			// column [0]
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
			CustomReporter.logError("Failed to get row Name [" + rowName + "] in the Test Data Sheet, under the following path [" + excelFilePath + "].");
			return -1; // in case of failure this line is unreachable
		} catch (Exception e) {
			e.printStackTrace();
			CustomReporter.logError("Failed to get the row number that corresponds to Row Name [" + rowName + "] , in the Test Data Sheet ["
					+ sheet + "], under the following path [" + excelFilePath + "]." + e.getMessage());

			return -1; // in case of failure this line is unreachable
		}
	}

	public static int getColumnNumberFromColumnName(String columnName) {
		try {
			// get the column number that corresponds to the desired columnName within the
			// target row [row_Num]
			// if no column name is provided, retrieves data from the 2nd
			// column (1st Value in the test data file)
			if (!columnName.equals("")) {
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
			CustomReporter.logError("Failed to get the column number that corresponds to columnName [" + columnName
					+ "] in the Test Data Sheet [" + sheet + "], under the following path [" + excelFilePath
					+ "].");
			return -1; // in case of failure this line is unreachable
		} catch (Exception e) {
			e.printStackTrace();
			CustomReporter.logError("Failed to get the column number that corresponds to columnName [" + columnName
					+ "] in the Test Data Sheet [" + sheet + "], under the following path [" + excelFilePath
					+ "]." + e.getMessage());

			return -1; // in case of failure this line is unreachable
		}
	}

	public static int getRowNumberFromRowName(String rowName) {
		try {
			// get the row number that corresponds to the desired rowName within the first
			// column [0]
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
			CustomReporter.logError("Failed to get row Name [" + rowName + "] in the Test Data Sheet, under the following path [" + excelFilePath + "].");
			return -1; // in case of failure this line is unreachable
		} catch (Exception e) {
			e.printStackTrace();
			CustomReporter.logError("Failed to get the row number that corresponds to Row Name [" + rowName + "] , in the Test Data Sheet ["
					+ sheet + "], under the following path [" + excelFilePath + "]." + e.getMessage());

			return -1; // in case of failure this line is unreachable
		}
	}

	//************************************************************************************//
	//******************************  Set data cell  *************************************//
	//************************************************************************************//

	/**
	 * This method will set the data in the cell by row name and column name from your Excel file using file path and sheet name as parameter to switch to the sheet first
	 * <p>
	 *
	 * @param filePath     Enter the file path of the Excel file
	 * @param sheetName    Enter the sheet name to switch to
	 * @param rowNumber    Enter the row number of the Sheet index start from 1
	 * @param columnNumber Enter the column number of the Sheet index start from 1
	 * @param data         Enter the data you want to set in the cell
	 */

	public static void setCellData(String filePath, String sheetName, int rowNumber, int columnNumber, String data) {
		initializeVariables();
		CustomReporter.logConsole("Writing data in Excel file: [ " + filePath + " ] | Sheet: [ " + sheetName + " ] | Row: [ " + rowNumber + " ] | Column: [ " + columnNumber + " ] | Data: [ " + data + " ]");
		fis = getFileInputStream(filePath);
		try {
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(rowNumber);
			if (row == null)
				row = sheet.createRow(rowNumber);
			cell = row.getCell(columnNumber);
			if (cell == null)
				cell = row.createCell(columnNumber);
			cell.setCellValue(data);
			fos = new FileOutputStream(filePath);
			try {
				workbook.write(fos);
			} catch (Exception e) {
				e.printStackTrace();
				CustomReporter.logError("There is no file exist in the path: [ " + filePath + " ] , please check the path and try again. " + e.getMessage());
			}
			fos.close();
			//  workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
			CustomReporter.logWarning("There is no file exist in the path: [ " + filePath + " ] , please check the path and try again. " + e.getMessage());

		}
	}

	// TODO : this method needs to be customized to write data in specific cell
	public static void setCellData(String filePath, String sheetName, String cellData0, String cellData2, String cellData3, String cellData4, String cellData5, String cellData6) {
		initializeVariables();
		CustomReporter.logConsole("Writing data in Excel file: [ " + filePath + " ] | Sheet: [ " + sheetName + " ] ");
		fis = getFileInputStream(filePath);
		try {
			workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheet(sheetName);
			int lastRowIndex = sheet.getLastRowNum();
			row = sheet.createRow(lastRowIndex + 1);
			int lastCellIndex = row.getLastCellNum();
			cell = row.createCell(lastCellIndex + 1, CellType.STRING);
			cell.setCellValue(cellData0);
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
			e.printStackTrace();
			CustomReporter.logError("There is no file exist in the file Path: [ " + filePath + " ] , please check the file Path and try again. Exception Error :->" + e.getMessage());
		}
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
	 * @param rowNumber    Enter the row  of the Sheet index start from 1
	 * @param columnNumber Enter the column number of the Sheet index start from 1
	 * @param data         Enter the data you want to set in the cell
	 */
	public static void setCellData(int rowNumber, int columnNumber, String data) {
		CustomReporter.logConsole("Writing data in Excel file: [ " + excelFilePath + " ] | Sheet: [ " + sheet + " ] | Row: [ " + rowNumber + " ] | Column: [ " + columnNumber + " ] | Data: [ " + data + " ]");
		try {
			row = sheet.getRow(rowNumber);
			if (row == null)
				row = sheet.createRow(rowNumber);
			cell = row.getCell(columnNumber);
			if (cell == null)
				cell = row.createCell(columnNumber);
			cell.setCellValue(data);
			fos = new FileOutputStream(excelFilePath);
			workbook.write(fos);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			CustomReporter.logWarning("There is no file exist in the path: [ " + excelFilePath + " ] , please check the path and try again. " + e.getMessage());
		}
	}

	// TODO : this method needs enhancement to write data in specific cell by name also instead of index only
	public static void setCellData(String filePath, String sheetName, int rowNumber, int columnNumber, String data, String updatedAt, String updatedBy) {
		initializeVariables();
		CustomReporter.logConsole("Writing data in Excel file: [ " + filePath + " ] | Sheet: [ " + sheetName + " ] | Row: [ " + rowNumber + " ] | Column: [ " + columnNumber + " ] | Data: [ " + data + " ]");
		fis = getFileInputStream(filePath);
		try {
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(rowNumber);
			if (row == null)
				row = sheet.createRow(rowNumber);
			cell = row.getCell(columnNumber);
			if (cell == null)
				cell = row.createCell(columnNumber);
			cell.setCellValue(data);
			Cell cell5 = row.createCell(7);
			cell5.setCellValue(updatedAt);
			Cell cell6 = row.createCell(8);
			cell6.setCellValue(updatedBy);
			fos = new FileOutputStream(filePath);
			workbook.write(fos);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			CustomReporter.logError("There is no file exist in the path: [ " + filePath + " ] , please check the path and try again. " + e.getMessage());
		}
	}

	public static void setCellData(String filePath, String sheetName, int rowNumber, int columnNumber, String data, String updatedAt, int updateAtColumnNumber, String updatedBy, int updatedByColumnNumber) {
		CustomReporter.logConsole("Writing data in Excel file: [ " + filePath + " ] | Sheet: [ " + sheetName + " ] | Row: [ " + rowNumber + " ] | Column: [ " + columnNumber + " ] | Data: [ " + data + " ]");
		initializeVariables();
		fis = getFileInputStream(filePath);
		try {
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(rowNumber);
			if (row == null)
				row = sheet.createRow(rowNumber);
			cell = row.getCell(columnNumber);
			if (cell == null)
				cell = row.createCell(columnNumber);
			cell.setCellValue(data);
			Cell cell5 = row.createCell(updateAtColumnNumber);
			cell5.setCellValue(updatedAt);
			Cell cell6 = row.createCell(updatedByColumnNumber);
			cell6.setCellValue(updatedBy);
			fos = new FileOutputStream(filePath);
			workbook.write(fos);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			CustomReporter.logError("There is no file exist in the path: [ " + filePath + " ] , please check the path and try again. " + e.getMessage());

		}
	}

	//TODO: this method needs to be tested as it is not working properly
	public void setCellData(String text, int rowNumber, int colNumber) {
		try {
			row = sheet.getRow(rowNumber);
			if (row == null) {
				row = sheet.createRow(rowNumber);
			}
			cell = row.getCell(colNumber);

			if (cell == null) {
				cell = row.createCell(colNumber);
			}
			cell.setCellValue(text);

			XSSFCellStyle style = workbook.createCellStyle();
			text = text.trim().toLowerCase();
			if (text == "pass" || text == "passed" || text == "success") {
				style.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
			}
			if (text == "fail" || text == "failed" || text == "failure") {
				style.setFillForegroundColor(IndexedColors.RED.getIndex());
			}
			style.setFillPattern(FillPatternType.NO_FILL);
			style.setAlignment(HorizontalAlignment.CENTER);
			style.setVerticalAlignment(VerticalAlignment.CENTER);

			cell.setCellStyle(style);

			fos = new FileOutputStream(excelFilePath);
			workbook.write(fos);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	//************************************************************************************//
	// *****************************  Helper methods  ************************************//
	//************************************************************************************//
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
			CustomReporter.logInfoStep("Row " + i + " data is :");
			for (int j = 0; j < cellCount; j++) {
				CustomReporter.logInfoStep(sheet.getRow(i).getCell(j).getStringCellValue() + ",");
			}
			System.out.println();
		}
	}

	private int getRowCountInSheet() {
		int rowCount = 0;
		try {
			rowCount = getLastRowNumber() - getFirstRowNumber();
			CustomReporter.logInfoStep("Row count from the sheet [" + sheet.getSheetName() + "] is [ " + rowCount + " ]");
		} catch (Exception e) {
			e.printStackTrace();
			CustomReporter.logError("Can't find the row count from the sheet [" + sheet.getSheetName() + "]  " + EXCEPTION_ERROR_MESSAGE + e.getMessage());
			fail("Can't find the row count from the sheet [" + sheet.getSheetName() + "]  " + EXCEPTION_ERROR_MESSAGE + e.getMessage());
		}
		return rowCount;
	}

	public int getFirstRowNumber() {
		return sheet.getFirstRowNum();
	}

	public int getLastRowNumber() {
		return sheet.getLastRowNum();
	}

	public int getFirstColumnNumber() {
		return sheet.getRow(0).getFirstCellNum();
	}

	public int getLastColumnNumber() {
		return sheet.getRow(0).getLastCellNum();
	}

	/**
	 * Extracts the first sheet name from the desired workbook.
	 *
	 * @return the first sheet name for the current test data file
	 */
	private String getDefaultSheetName() {
		return workbook.getSheetName(0);
	}
}