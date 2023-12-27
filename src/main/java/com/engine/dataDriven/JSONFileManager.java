package com.engine.dataDriven;

import com.engine.reports.CustomReporter;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.exception.JsonPathException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class JSONFileManager {
    private String jsonFilePath;
    private static FileReader reader = null;


    /**
     * Enter yot JSONFile Path
     *
     * @param jsonFilePath Enter the file path of your Json file
     */

    public JSONFileManager(String jsonFilePath) {
        this.jsonFilePath = jsonFilePath;
        this.initializeReader();
    }

    public JSONFileManager() {
    }

    public static JSONObject getTestData(String jsonFilePath, String dataFileName) {
        JSONObject jsonObject = null;
        try {
            reader = new FileReader(jsonFilePath + dataFileName);
            JSONParser jsonParser = new JSONParser();
            jsonObject = (JSONObject) jsonParser.parse(reader);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return jsonObject;
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
                    testData = JsonPath.from(reader).getString(jsonPath);
                    break;
            }
        } catch (ClassCastException var5) {
            CustomReporter.logError(String.valueOf(var5));
            CustomReporter.logError("Incorrect jsonPath. [" + jsonPath + "].");
            Assert.fail("Incorrect jsonPath. [" + jsonPath + "].");
        } catch (IllegalArgumentException | JsonPathException var6) {
            CustomReporter.logError(String.valueOf(var6));
            CustomReporter.logError("Couldn't read the file. [" + this.jsonFilePath + "].");
            Assert.fail("Couldn't read the  file. [" + this.jsonFilePath + "].");
        }
        return testData;
    }


    private void initializeReader() {
        this.reader = null;
        try {
            reader = new FileReader(getAbsolutePath(this.jsonFilePath));
        } catch (FileNotFoundException var2) {
            CustomReporter.logError(String.valueOf(var2));
            CustomReporter.logError("Couldn't read the file. [" + this.jsonFilePath + "].");
            Assert.fail("Couldn't find the file. [" + this.jsonFilePath + "]. + \n" + var2.getMessage());
        }
    }

    public static String getAbsolutePath(String relativePath) {
        String filePath = "";
        try {
            filePath = (new File(relativePath)).getAbsolutePath();
        } catch (Exception var3) {
            CustomReporter.logError(String.valueOf(var3));
        }
        return filePath;
    }

    public enum DataType {
        STRING;

        private DataType() {
        }
    }
}
