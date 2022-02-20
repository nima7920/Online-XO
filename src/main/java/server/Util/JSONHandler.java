package server.Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import server.logic.Profile;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;

public class JSONHandler {

    private String profilesPath = "src//main//java//server//profiles";
    private String scoreBoardPath = "src//main//java//server//ScoreBoard.JSON";

    private GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
    private File profilesFolder;
    private FileReader fileReader;
    private FileWriter fileWriter;

    public JSONHandler() {
        profilesFolder = new File(profilesPath);

    }

    // methods for profiles
    public ArrayList<String> getProfilesName() {
        ArrayList<String> profileNames = new ArrayList<>();
        for (File file : profilesFolder.listFiles()) {
            profileNames.add(file.getName());
        }
        return profileNames;
    }

    public Profile getProfile(String name) {
        Gson gson = gsonBuilder.excludeFieldsWithModifiers().setPrettyPrinting().create();
        try {
            fileReader = new FileReader(profilesFolder + "\\" + name);
            Profile profile = gson.fromJson(fileReader, Profile.class);
            fileReader.close();
            return profile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveProfile(Profile profile) {
        Gson gson = gsonBuilder.excludeFieldsWithModifiers().setPrettyPrinting().create();
        try {
            fileWriter = new FileWriter(profilesFolder + "\\" + profile.getUsername() + ".JSON");
            gson.toJson(profile, fileWriter);
            fileWriter.close();
            saveScores();
        } catch (Exception e) {

        }

    }

    // methods for scoreboard
    public void saveScores() {
        Gson gson = gsonBuilder.excludeFieldsWithModifiers().setPrettyPrinting().create();
        ArrayList<String> scores = new ArrayList<>();
        for (File file : profilesFolder.listFiles()) {
            try {
                fileReader = new FileReader(file);
                Profile profile = gson.fromJson(fileReader, Profile.class);
                scores.add(profile.getScore() + "/" + profile.getUsername());
            }catch (Exception e){

            }
        }
        Collections.sort(scores);
        JSONArray array=new JSONArray();
        for(String s:scores){
            array.add(s);
        }
     try {
         fileWriter = new FileWriter(new File(scoreBoardPath));
         fileWriter.write(array.toJSONString());
         fileWriter.flush();
     }catch (Exception e){

     }
    }

    public ArrayList<String> getScores(){
        JSONParser jsonParser=new JSONParser();
        JSONArray jsonArray;
        ArrayList<String> scores=new ArrayList<>();
        try{
            fileReader=new FileReader(new File(scoreBoardPath));
            Object obj=jsonParser.parse(fileReader);
            jsonArray=(JSONArray) obj;
          jsonArray.forEach(s->scores.add((String) s));
        }catch (Exception e){
            e.printStackTrace();
        }
        Collections.sort(scores);
        return scores;
    }
}
