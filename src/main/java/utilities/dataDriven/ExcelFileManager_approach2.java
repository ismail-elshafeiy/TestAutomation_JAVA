package utilities.dataDriven;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileManager_approach2 {
    FileInputStream spreadSheet;


    public ExcelFileManager_approach2(FileInputStream file) {
        spreadSheet = file;
    }

    public void readData(int sheetName, int row1, int cell1) throws IOException {

        //Creating a workbook
        XSSFWorkbook workbook = new XSSFWorkbook(spreadSheet);
        XSSFSheet sheet = workbook.getSheetAt(sheetName);

        Row row = sheet.getRow(row1);
        Cell cell = row.getCell(cell1);

        System.out.println(sheet.getRow(row1).getCell(cell1));


//String cellval = cell.getStringCellValue();
//System.out.println(cellval);


    }
}