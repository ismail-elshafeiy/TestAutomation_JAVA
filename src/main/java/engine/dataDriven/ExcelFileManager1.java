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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.testng.Assert.fail;

public class ExcelFileManager1 {
	private String excelFilePath;
	//	private String sheetName;
	private FileInputStream fis;
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private XSSFRow row;
	private XSSFCell cell;

	private static final String errorMessageException = " Error Message Exception: --> ";
	private static final String testMethodName = " Test Method Name:  ";

	public ExcelFileManager1 (String excelFilePath) {
		this.initializeVariables();
		this.excelFilePath = excelFilePath;
		try {
			this.fis = new FileInputStream(excelFilePath);
			this.workbook = new XSSFWorkbook(this.fis);
			this.fis.close();
		} catch ( IOException var6 ) {
			Logger.logMessage(var6.getMessage());
			Logger.logMessage("Couldn't find the desired file. [" + excelFilePath + "].");
			Assert.fail("Couldn't find the desired file. [" + excelFilePath + "].");
		} catch ( OutOfMemoryError var7 ) {
			Logger.logMessage("Couldn't open the desired file. [" + excelFilePath + "].");
			Assert.fail("Couldn't open the desired file. [" + excelFilePath + "].");
		} catch ( EmptyFileException var8 ) {
			Logger.logMessage(var8.getMessage());
			Logger.logMessage("Please check the target file, as it may be corrupted. [" + excelFilePath + "].");
			Assert.fail("Please check the target file, as it may be corrupted. [" + excelFilePath + "].");
		}
	}

	public void switchToSheet (String sheetName) {
		try {
			this.sheet = this.workbook.getSheet(sheetName);
			Logger.logStep("Switched to sheet: [ " + sheetName + " ] from file: [ " + sheetName + " ].");
		} catch ( Exception e ) {
			Logger.logMessage(testMethodName + "Couldn't find the desired sheet. [ " + sheetName + " ]." + errorMessageException + e.getMessage());
			Assert.fail(testMethodName + "Couldn't find the desired sheet. [ " + sheetName + " ]." + errorMessageException + e.getMessage());
		}
	}

	public String getCellData1 (String rowName, String columnName) {
		try {
			int rowNum = this.getRowNumberFromRowName(rowName);
			int colNum = this.getColumnNumberFromColumnName(columnName);
			this.row = this.sheet.getRow(rowNum);
			this.cell = this.row.getCell(colNum);
			return this.getCellData();
		} catch ( Exception var6 ) {
			Logger.logMessage(var6.getMessage());
			Logger.logMessage("Failed to read data from row [" + rowName + "] and column [" + columnName + "] " + this.excelFilePath + "].");
			Assert.fail("Failed to read data from row [" + rowName + "] and column [" + columnName + "] " + this.excelFilePath + "].");
			return "";
		}
	}

	private void initializeVariables () {
		this.fis = null;
		this.workbook = null;
		this.sheet = null;
		this.row = null;
		this.cell = null;
		this.excelFilePath = "";
	}

	private int getRowNumberFromRowName (String rowName) {
		try {
			for ( int i = 0; i <= this.sheet.getLastRowNum(); ++ i ) {
				this.row = this.sheet.getRow(i);
				if ( this.row != null && this.row.getCell(0).getStringCellValue().equals(rowName) ) {
					return i;
				}
			}
			Logger.logMessage("Failed to get the row number that coresponds to rowName [" + rowName + "] ");
			Assert.fail("Failed to get the row number that coresponds to rowName [" + rowName + "] ");
			return - 1;
		} catch ( Exception var4 ) {
			Logger.logMessage(var4.getMessage());
			Logger.logMessage("Failed to get the row number that coresponds to rowName [" + rowName + "] ");
			Assert.fail("Failed to get the row number that coresponds to rowName [" + rowName + "] ");
			return - 1;
		}
	}

	private int getColumnNumberFromColumnName (String columnName) {
		try {
			if ( ! columnName.equals("") ) {
				this.row = this.sheet.getRow(0);
				for ( int i = 0; i < this.row.getLastCellNum(); ++ i ) {
					if ( this.row.getCell(i).getStringCellValue().equals(columnName) ) {
						return i;
					}
				}
				Logger.logMessage("Failed to get the column number that coresponds to columnName [" + columnName + "] ");
				Assert.fail("Failed to get the column number that coresponds to columnName [" + columnName + "] ");
				return - 1;
			} else {
				return 1;
			}
		} catch ( Exception var4 ) {
			Logger.logMessage(var4.getMessage());
			Logger.logMessage("Failed to get the column number that coresponds to columnName [" + columnName + "] ");
			Assert.fail("Failed to get the column number that coresponds to columnName [" + columnName + "] ");
			return - 1;
		}
	}


	private String getCellData () {
		try {
			if ( this.cell.getCellType() == CellType.STRING ) {
				return this.cell.getStringCellValue();
			} else if ( this.cell.getCellType() != CellType.NUMERIC && this.cell.getCellType() != CellType.FORMULA ) {
				return this.cell.getCellType() == CellType.BOOLEAN ? String.valueOf(this.cell.getBooleanCellValue()):"";
			} else {
				String cellValue = String.valueOf(this.cell.getNumericCellValue());
				if ( cellValue.contains(".0") ) {
					cellValue = cellValue.split("\\.")[0];
				}
				if ( DateUtil.isCellDateFormatted(this.cell) ) {
					DateFormat df = new SimpleDateFormat("dd/MM/yy");
					Date date = this.cell.getDateCellValue();
					cellValue = df.format(date);
				}
				return cellValue;
			}
		} catch ( Exception var4 ) {
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