package com.workforceScheduler.dataProvider;

import java.lang.reflect.Method;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.DataProvider;

public class CustomDataProvider {

	private static final String EXCEL_PATH = System.getProperty("user.dir") + "/src/test/resources/TestData1.xlsx";
	private static final String SHEET_NAME = "EmployeeData";

	// Data provided using excel
	@DataProvider(name = "loginDetails")
	public static Object[][] getData() {
		Object[][] arr = ExcelReader.getDataFromSheet("Login");
		return arr;
	}

	// data provided using json
	@DataProvider(name = "loginData")
	public Object[][] loginData() {
		// Construct the file path (adjust as necessary)
		String filePath = System.getProperty("user.dir") + "/src/test/resources/login.json";
		return JSONDataReader.getTestData(filePath);
	}

	// data provided using the reference of testCaseName

	@DataProvider(name = "testLoginData")
	public static Object[][] getData(Method method) {
		JSONArray jsonArray = JSONDataReader.readJSONFile();

		// Iterate through JSON data and find the matching test case
		for (Object jsonObj : jsonArray) {
			JSONObject testCaseData = (JSONObject) jsonObj;
			String testCaseName = (String) testCaseData.get("testCaseName");

			// Check if the test case name in JSON matches the current method name
			if (testCaseName.equalsIgnoreCase(method.getName())) {
				String username = (String) testCaseData.get("username");
				String password = (String) testCaseData.get("password");
				return new Object[][] { { username, password } };
			}
		}

		// Return empty if no matching data is found
		return new Object[][] {};
	}

	@DataProvider(name = "employeeData")
	public static Object[][] employeeDataProvider() {
		return ExcelReader1.readSheetAsMap(EXCEL_PATH, SHEET_NAME);
	}

}
