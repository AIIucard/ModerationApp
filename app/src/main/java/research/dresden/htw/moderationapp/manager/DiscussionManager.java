package research.dresden.htw.moderationapp.manager;

import android.content.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

import research.dresden.htw.moderationapp.model.Discussion;
import research.dresden.htw.moderationapp.model.DiscussionList;

public class DiscussionManager {
    private static Object lock = new Object();
    private static DiscussionManager instance = null;
    private final String FILENAME = "discussions.json";

    private DiscussionManager() {
        // Use getInstance
    }

    @SuppressWarnings("SynchronizeOnNonFinalField")
    public static DiscussionManager getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new DiscussionManager();
                }
            }
        }
        return (instance);
    }
    public ArrayList<Discussion> readFromJSONFile(Context context) {
        String discussionsAsString = IOHelper.readStringFromFile(context, FILENAME);
        if(discussionsAsString != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                DiscussionList discussionList = objectMapper.readValue(discussionsAsString, DiscussionList.class);
                return discussionList.getDiscussionList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void writeToJSONFile(Context context, ArrayList<Discussion> discussionList) {
        try {
            DiscussionList discussions = new DiscussionList(discussionList);
            ObjectMapper objectMapper = new ObjectMapper();
            String discussionsAsString = objectMapper.writeValueAsString(discussions);
            IOHelper.writeStringToFile(context, FILENAME, discussionsAsString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
