package utilities.dataDriven;

import org.apache.poi.ss.usermodel.*;
import utilities.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.fail;

public class ExcelFileManager {

    private File spreadSheet;
    private Sheet currentSheet;
    private Map<String, Integer> columns;

    // Contractor
    public ExcelFileManager(File file) {
        spreadSheet = file;
        columns = new HashMap<String, Integer>();
    }


    /**
     * Switch to your Sheet in case you have multiple sheets in your Excel file
     *
     * @param name Enter the name of the Sheet
     */
    public void switchToSheet(String name) {
        try (Workbook workbooks = WorkbookFactory.create(spreadSheet)) {
            currentSheet = workbooks.getSheet(name);
            currentSheet.getRow(0).
                    forEach(cell -> {
                        columns.put(cell.getStringCellValue(), cell.getColumnIndex());
                    });

        } catch (Exception e) {
            Logger.logStep(e.getMessage());
            fail(e.getMessage());
        }
    }


    /**
     * Get the cell by Column and Row number from your Excel file
     *
     * @param column enter the column name
     * @param row    Enter the row  of the Sheet
     */
    public String getCellData(String column, int row) {
        try {
            Row dataRow = currentSheet.getRow(row - 1);
            return getCellDataAsString(dataRow.getCell(columns.get(column)));

        } catch (NullPointerException e) {
            Logger.logStep("Can't find the column name [" + column + "]........" + e.getMessage());
            fail("Can't find the column name [" + column + "]........" + e.getMessage());
        } catch (Exception e) {
            Logger.logStep(e.getMessage());
            fail(e.getMessage());
        }
        return null;
    }

    /**
     * Get the cell by Column only from your Excel file
     *
     * @param column enter the column name
     */
    public String getCellData(String column) {
        return getCellData(column, 2);
    }


    /**
     * @param cell Enter the cell  of the Sheet
     */
    private String getCellDataAsString(Cell cell) {
        return switch (cell
                .getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());

            default -> "";
        };
    }


}