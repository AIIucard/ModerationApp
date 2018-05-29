package research.dresden.htw.moderationapp.manager;

import android.content.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

import research.dresden.htw.moderationapp.model.AppConfig;
import research.dresden.htw.moderationapp.model.Discussion;
import research.dresden.htw.moderationapp.model.DiscussionList;

public class CfgManager {
    private static Object lock = new Object();
    private static CfgManager instance = null;
    private final String FILENAME = "cfg.json";

    private CfgManager() {
        // Use getInstance
    }

    public static CfgManager getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new CfgManager();
                }
            }
        }
        return (instance);
    }
    public AppConfig readFromJSONFile(Context context) {
        String configAsString = IOHelper.readStringFromFile(context, FILENAME);
        if(configAsString != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                AppConfig appConfig = objectMapper.readValue(configAsString, AppConfig.class);
                return appConfig;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void writeToJSONFile(Context context, AppConfig cfg) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String configAsString = objectMapper.writeValueAsString(cfg);
            IOHelper.writeStringToFile(context, FILENAME, configAsString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
