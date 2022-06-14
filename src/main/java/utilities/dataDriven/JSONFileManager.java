package utilities.dataDriven;

import io.restassured.path.json.JsonPath;
import io.restassured.path.json.exception.JsonPathException;
import org.testng.Assert;
import utilities.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class JSONFileManager {
    private final String jsonFilePath;
    private FileReader reader = null;

    /**
     * Enter yot JSONFile Path
     *
     * @param jsonFilePath Enter the file path of your Json file
     */

    public JSONFileManager(String jsonFilePath) {
        this.jsonFilePath = jsonFilePath;
        this.initializeReader();

    }

    public static String getAbsolutePath(String relativePath) {
        String filePath = "";

        try {
            filePath = (new File(relativePath)).getAbsolutePath();
        } catch (Exception var3) {
            Logger.logMessage(String.valueOf(var3));
        }

        return filePath;
    }

    private void initializeReader() {
        this.reader = null;

        try {
            this.reader = new FileReader(getAbsolutePath(this.jsonFilePath));
        } catch (FileNotFoundException var2) {
            Logger.logMessage(String.valueOf(var2));
            Logger.logMessage("Couldn't read the file. [" + this.jsonFilePath + "].");
            Assert.fail("Couldn't find the file. [" + this.jsonFilePath + "].");
        }

    }

    /**
     * Get the value of the JSON key
     *
     * @param jsonKey Enter the Key of the JSON Data you want to read
     */

    public String getTestData(String jsonKey) {
        Object testData = getTestData(jsonKey, DataType.STRING);
        if (testData != null) {
            return String.valueOf(testData);
        } else {
            return null;
        }
    }


    private Object getTestData(String jsonPath, JSONFileManager.DataType dataType) {
        Object testData = null;
        initializeReader();
        try {
            switch (dataType) {
                case STRING:
                    testData = JsonPath.from(this.reader).getString(jsonPath);
                    break;
            }
        } catch (ClassCastException var5) {
            Logger.logMessage(String.valueOf(var5));
            Logger.logMessage("Incorrect jsonPath. [" + jsonPath + "].");
            Assert.fail("Incorrect jsonPath. [" + jsonPath + "].");
        } catch (IllegalArgumentException | JsonPathException var6) {
            Logger.logMessage(String.valueOf(var6));
            Logger.logMessage("Couldn't read the file. [" + this.jsonFilePath + "].");
            Assert.fail("Couldn't read the  file. [" + this.jsonFilePath + "].");
        }

        return testData;
    }

    public static enum DataType {
        STRING;

        private DataType() {
        }
    }


}
