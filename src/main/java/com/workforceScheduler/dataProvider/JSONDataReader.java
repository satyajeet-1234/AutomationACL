package com.workforceScheduler.dataProvider;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONDataReader {

    /**
     * Reads test data from the specified JSON file and returns it as a 2D Object array.
     * Each row represents one set of test data.
     *
     * @param filePath The path to the JSON file.
     * @return A two-dimensional Object array with test data.
     
     */
	private static final String filePath = System.getProperty("user.dir") + "/src/test/resources/login.json";

	
    public static Object[][] getTestData(String filePath) {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(filePath)) {
            // Parse the JSON file
            Object obj = parser.parse(reader);
            JSONArray jsonArray = (JSONArray) obj;

            // Prepare the data array
            Object[][] data = new Object[jsonArray.size()][];

            // Loop through each JSON object in the array
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                // Extract values (customize keys as per your JSON structure)
                String username = (String) jsonObject.get("username");
                String password = (String) jsonObject.get("password");
                data[i] = new Object[] { username, password };
            }
            return data;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return new Object[][] {}; // Return an empty array in case of error
        }
    }
    
    
    /**
     * Reads the JSON file and returns the JSONArray containing test data.
     * 
     * @return JSONArray of test data
     */
    public static JSONArray readJSONFile() {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(filePath)) {
            // Parse and return JSON array
            return (JSONArray) parser.parse(reader);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return new JSONArray(); // Return empty JSON array if an error occurs
    }
}
