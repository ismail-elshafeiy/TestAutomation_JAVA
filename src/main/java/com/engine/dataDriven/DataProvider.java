package com.engine.dataDriven;


import java.io.IOException;

public class DataProvider {
    ExcelFileManager excelFileManager;

    @org.testng.annotations.DataProvider(name = "getExcelData")
    public Object[][] getExcelData() {
        int numberOfRows = excelFileManager.getLastRowNumber();
        int numberOfColumns = excelFileManager.getLastColumnNumber();
        Object[][] data = new Object[numberOfRows][numberOfColumns];
        for (int row = 0; row < numberOfRows; row++) {
            data[row][0] = excelFileManager.getCellData(row + 1, "email");
            data[row][1] = excelFileManager.getCellData(row + 1, "password");
            data[row][2] = excelFileManager.getCellData(row + 1, "expectedResult_successMessage");
        }
        return data;
    }

    @org.testng.annotations.DataProvider(name = "getExcelData2")
    public Object[][] getExcelData2() throws IOException {
        excelFileManager = new ExcelFileManager();
        Object[][] data;
        data = excelFileManager.getDataHashTable2("src/test/resources/TestData/excel/LoginData.xlsx", "login Data", 2, 14);
        return data;
	}

}
