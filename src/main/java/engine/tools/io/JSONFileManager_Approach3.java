package engine.tools.io;

import io.restassured.path.json.JsonPath;
import java.io.FileNotFoundException;
import java.io.FileReader;
import static org.testng.Assert.fail;

public class JSONFileManager_Approach3 {
	private final String jsonFilePath;
	private FileReader reader;

	public JSONFileManager_Approach3 (String jsonFilePath) {
		this.jsonFilePath = jsonFilePath;
		try {
			reader = new FileReader(jsonFilePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public String getTestData (String jsonPath) {
		Object testData = null;
		try {
			reader = new FileReader(jsonFilePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		testData = JsonPath.from(reader).getString(jsonPath);
		return String.valueOf(testData);
	}
}
