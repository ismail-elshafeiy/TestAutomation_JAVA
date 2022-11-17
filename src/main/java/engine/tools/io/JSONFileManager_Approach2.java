package engine.tools.io;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class JSONFileManager_Approach2 {

    public JSONObject getDataFile(String dataFileName) {
        String dataFilePath = "src/test/resources/TestData/TestData.json";
        JSONObject testObject = null;

        try {
            FileReader reader = new FileReader(dataFilePath + dataFileName);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            testObject = (JSONObject) jsonObject;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return testObject;
    }



}
