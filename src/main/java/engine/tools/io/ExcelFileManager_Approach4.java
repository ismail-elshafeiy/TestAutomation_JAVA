package engine.tools.io;


import java.io.FileInputStream;
import java.io.IOException;


import engine.tools.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileManager_Approach4 {
	FileInputStream spreadSheet;

	public ExcelFileManager_Approach4 (FileInputStream file) {
		spreadSheet = file;
	}

	public String readData (int sheetName, int row1, int cell1) throws IOException {
		//Creating a workbook
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(spreadSheet);
			XSSFSheet sheet = workbook.getSheetAt(sheetName);
			Row row = sheet.getRow(row1);
			Cell cell = row.getCell(cell1);

			System.out.println(sheet.getRow(row1).getCell(cell1));
			return sheet.getRow(row1).getCell(cell1).toString();
		} catch ( Exception e ) {
			Logger.logMessage("Error in reading data from excel file... Message --> " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}