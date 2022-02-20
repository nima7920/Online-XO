package client.util;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONReader {

    public String[][] getScoreBoardFromJSON(String jsonString) {
        System.out.println("score board string:" + jsonString);
        String[][] result;
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = new JSONArray();
        try {

            Object obj = jsonParser.parse(jsonString);
            jsonArray = (JSONArray) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        result = new String[jsonArray.size()][3];
        for (int i = 0; i < jsonArray.size(); i++) {
            result[i] = ((String) jsonArray.get(i)).split("/");
        }
        return result;
    }
}
