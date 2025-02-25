package com.workforceScheduler.dataProvider;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader1 {

	/**
	 * Reads an Excel sheet and returns its data as an Object[][]. Each element is a
	 * Map<String, String> where keys are column headers.
	 *
	 * @param filePath  the full path of the Excel file.
	 * @param sheetName the name of the sheet to read.
	 * @return Object[][] array for DataProvider.
	 */
	public static Object[][] readSheetAsMap(String filePath, String sheetName) {
		List<Map<String, String>> allRows = new ArrayList<>();

		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis)) {

			Sheet sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				throw new RuntimeException("Sheet '" + sheetName + "' not found in " + filePath);
			}

			// Read header row (assumed to be the first row)
			Row headerRow = sheet.getRow(0);
			int totalColumns = headerRow.getPhysicalNumberOfCells();
			List<String> columnNames = new ArrayList<>();
			for (int c = 0; c < totalColumns; c++) {
				Cell cell = headerRow.getCell(c);
				columnNames.add(getCellValueAsString(cell));
			}

			// Read each data row and create a map of column header -> cell value
			int totalRows = sheet.getPhysicalNumberOfRows();
			for (int r = 1; r < totalRows; r++) {
				Row row = sheet.getRow(r);
				if (row == null) {
					continue; // skip empty rows
				}
				Map<String, String> rowData = new LinkedHashMap<>();
				for (int c = 0; c < totalColumns; c++) {
					Cell cell = row.getCell(c);
					String cellValue = getCellValueAsString(cell);
					rowData.put(columnNames.get(c), cellValue);
				}
				allRows.add(rowData);
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to read Excel file: " + e.getMessage());
		}

		// Convert List<Map<String, String>> to Object[][] for TestNG DataProvider
		Object[][] dataArray = new Object[allRows.size()][1];
		for (int i = 0; i < allRows.size(); i++) {
			dataArray[i][0] = allRows.get(i);
		}
		return dataArray;
	}

	/**
	 * Helper method to convert a cell's value to a String.
	 *
	 * @param cell the Excel cell.
	 * @return String representation of the cell value.
	 */
	private static String getCellValueAsString(Cell cell) {
		if (cell == null) {
			return "";
		}
		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue().toString(); // Format date as needed
			} else {
				return String.valueOf(cell.getNumericCellValue());
			}
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case FORMULA:
			try {
				return cell.getStringCellValue();
			} catch (IllegalStateException e) {
				return String.valueOf(cell.getNumericCellValue());
			}
		case BLANK:
		case ERROR:
		default:
			return "";
		}
	}
}


