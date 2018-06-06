package research.dresden.htw.moderationapp.manager;

import android.content.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import research.dresden.htw.moderationapp.model.AppConfig;

public class CfgManager {
    private static Object lock = new Object();
    private static CfgManager instance = null;
    private final String FILENAME = "cfg.json";

    private CfgManager() {
        // Use getInstance
    }

    @SuppressWarnings("SynchronizeOnNonFinalField")
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
                return objectMapper.readValue(configAsString, AppConfig.class);
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
