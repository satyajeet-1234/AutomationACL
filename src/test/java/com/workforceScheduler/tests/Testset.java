package com.workforceScheduler.tests;

import java.util.Map;

import org.testng.annotations.Test;

import com.workforceScheduler.dataProvider.CustomDataProvider;

public class Testset {



	@Test(dataProvider = "employeeData", dataProviderClass = CustomDataProvider.class)
	    public void testAddOrEditEmployee(Map<String, String> data) {
		System.out.println(data.get("FirstName"));

}
	
	}
