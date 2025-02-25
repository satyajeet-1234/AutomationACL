package alAssistance;



public class TestCaseGenerator {
	public static void main(String[] args) {
        AIService aiService = new AIService();

        String prompt = """
            Generate Selenium TestNG test cases for a login functionality with:
            1. Valid login
            2. Invalid username
            3. Invalid password
            4. Empty username and password fields
            The test should follow the Page Object Model (POM) structure.
        """;

        try {
            aiService.generateTestClass(prompt, "GeneratedLoginTest", "com.workforceScheduler.tests");
            System.out.println("🎉 Login test class generated successfully! You can now run it using TestNG.");
        } catch (Exception e) {
            System.err.println("❌ Failed to generate test class: " + e.getMessage());
        }
    }
}
