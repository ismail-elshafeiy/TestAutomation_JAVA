package engine.dataDriven;


public class DataProvider {
	@org.testng.annotations.DataProvider
	public Object[][] getExcelData () {
		int numberOfRows = ExcelFileManager.getLastRowNumber();
		int numberOfColumns = ExcelFileManager.getLastColumnNumber();
		Object[][] data = new Object[numberOfRows][numberOfColumns];
		for ( int row = 0; row < numberOfRows; row++ ) {
			data[row][0] = ExcelFileManager.getCellData("email", row + 1);
			data[row][1] = ExcelFileManager.getCellData("password", row + 1);
			data[row][2] = ExcelFileManager.getCellData("expectedResult_successMessage", row + 1);
		}
		return data;
	}

	public static String getColumnHeader (String columnHeader) {
		return ExcelFileManager.getCellData(columnHeader);
	}

}
