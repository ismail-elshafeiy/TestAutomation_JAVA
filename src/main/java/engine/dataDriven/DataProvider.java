package engine.dataDriven;


public class DataProvider {
	@org.testng.annotations.DataProvider
	public Object[][] getExcelData () {
		int numberOfRows = ExcelFileManager.getLastRowNumber();
		int numberOfColumns = ExcelFileManager.getLastColumnNumber();
		Object[][] data = new Object[numberOfRows][numberOfColumns];
		for ( int row = 0; row < numberOfRows; row++ ) {
			data[row][0] = ExcelFileManager.getCellData(row + 1, "email");
			data[row][1] = ExcelFileManager.getCellData(row + 1, "password");
			data[row][2] = ExcelFileManager.getCellData(row + 1, "expectedResult_successMessage");
		}
		return data;
	}

	public static String getColumnHeader (String columnHeader) {
		return ExcelFileManager.getCellData(columnHeader);
	}

}
