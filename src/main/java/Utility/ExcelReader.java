package Utility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelReader {

    private static final String EXCEL_FILE_PATH = "src/test/resources/todos_testdata.xlsx"; // Update the path to your Excel file

    // Method to fetch data from Excel based on action and test case
    public static Object[][] getDataFromExcel(String sheetName, String action, String testCase) throws IOException {
        // Open the Excel file
        FileInputStream fis = new FileInputStream(new File(EXCEL_FILE_PATH));
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(sheetName);
        
        List<Object[]> data = new ArrayList<>();

        // Iterate through rows and filter based on action and testCase
        Iterator<Row> rowIterator = sheet.iterator();
        boolean isHeaderRow = true; // Assuming the first row is the header
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            if (isHeaderRow) {
                isHeaderRow = false;  // Skip the header row
                continue;
            }

            String excelAction = getCellValue(row, 1);  
            String excelTestCase = getCellValue(row, 2);
            String newText = getCellValue(row, 3); 

            if (excelAction.equalsIgnoreCase(action) && excelTestCase.equalsIgnoreCase(testCase)) {
                String taskName = getCellValue(row, 0);
                data.add(new Object[] { taskName, excelAction, excelTestCase, newText });
            }
            
        }

        workbook.close();  // Close the workbook
        return data.toArray(new Object[0][0]);  // Convert List to 2D array for DataProvider
    }

    // Helper method to fetch cell value as String
    private static String getCellValue(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue();
                case NUMERIC:
                    return String.valueOf(cell.getNumericCellValue());
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                default:
                    return "";
            }
        }
        return "";
    }
}
