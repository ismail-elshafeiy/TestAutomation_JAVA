package engine.dataDriven;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ExcelReader {
	static FileInputStream fis = null;

	public FileInputStream getFileInputStream (String path) {
		File srcFile = new File(path);
		try {
			fis = new FileInputStream(srcFile);
		} catch ( FileNotFoundException e ) {
			e.printStackTrace();
		}
		return fis;
	}

	public String[][] getExcelData (String path, String sheetName) throws IOException {
		fis = getFileInputStream(path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet(sheetName);
		int rowCount = sheet.getLastRowNum() + 1;
		int columnCount = sheet.getRow(0).getLastCellNum();

		String[][] arrayExcelData = new String[rowCount][columnCount];

		for ( int i = 0; i < rowCount; i++ ) {
			for ( int j = 0; j < columnCount; j++ ) {
				XSSFRow row = sheet.getRow(i);
				if ( row.getCell(j) != null ) {
					arrayExcelData[i][j] = "";
				} else {
					arrayExcelData[i][j] = row.getCell(j).toString();
				}
			}
		}
		workbook.close();
		return arrayExcelData;
	}

	public void writeDataInCell (String path, String sheetName, int rowNumber, int columnNumber, String value) throws IOException {
		fis = getFileInputStream(path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet(sheetName);
		Row row = sheet.getRow(rowNumber);
		if ( row == null )
			row = sheet.createRow(rowNumber);
		Cell cell = row.createCell(columnNumber);
		if ( cell == null )
			cell = row.createCell(columnNumber);
		cell.setCellValue(value);
		FileOutputStream fos = new FileOutputStream(path);
		workbook.write(fos);
		fos.close();
	}

	public void writeDataInCell (String path, String sheetName, String value) throws IOException {
		fis = getFileInputStream(path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet(sheetName);
		int lastRowIndex = sheet.getLastRowNum();
		Row row = sheet.getRow(lastRowIndex);
		int lastCellIndex = row.getLastCellNum();
		Cell cell = row.createCell(lastCellIndex + 1, CellType.STRING);
		cell.setCellValue(value);
		FileOutputStream fos = new FileOutputStream(path);
		workbook.write(fos);
		fos.close();
	}


}