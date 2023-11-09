package com.engine.dataDriven;


public class DataProvider {
	ExcelFileManager excelFileManager;
	@org.testng.annotations.DataProvider
	public Object[][] getExcelData () {
		int numberOfRows = excelFileManager.getLastRowNumber();
		int numberOfColumns = excelFileManager.getLastColumnNumber();
		Object[][] data = new Object[numberOfRows][numberOfColumns];
		for ( int row = 0; row < numberOfRows; row++ ) {
			data[row][0] = excelFileManager.getCellData(row + 1, "email");
			data[row][1] = excelFileManager.getCellData(row + 1, "password");
			data[row][2] = excelFileManager.getCellData(row + 1, "expectedResult_successMessage");
		}
		return data;
	}

}
