package testCases.gui.web.dataDriven.excelSheet;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class writeToExcel {
	public static void main (String[] args) throws IOException {
		File file = new File("Data.xlsx");
		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook data = new XSSFWorkbook(inputStream);
		Sheet usernames = data.getSheet("Checklist");

		if (usernames.getRow(2) == null) {
			usernames.createRow(1);
		}
		if (usernames.getRow(2).getCell(1) == null) {
			usernames.createRow(1).createCell(1);
		}
		usernames.getRow(2).getCell(1).setCellValue("Pass");
		inputStream.close();
		FileOutputStream outputStream = new FileOutputStream(file);
		data.write(outputStream);
		outputStream.close();
	}
}
