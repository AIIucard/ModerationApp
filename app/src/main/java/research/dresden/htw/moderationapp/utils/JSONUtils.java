package research.dresden.htw.moderationapp.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import research.dresden.htw.moderationapp.model.Member;

public class JSONUtils {

    public static JSONObject createNewDiscussionJSONMessage(String title, int duration, ArrayList<Member> memberList){
        JSONObject message = new JSONObject();
        try {
            message.put("topic", Topic.NEW_DISCUSSION);
            JSONObject discussion = new JSONObject();
            discussion.put("title",title);
            discussion.put("duration",duration);
            JSONArray members = new JSONArray();

            //TODO: Sort members by place but not here
            for (Member currentMember: memberList) {
                JSONArray member = new JSONArray();
                JSONObject memberTitle = new JSONObject();
                memberTitle.put("title", currentMember.getTitle().toString());
                member.put(memberTitle);
                JSONObject memberName = new JSONObject();
                memberName.put("name", currentMember.getName());
                member.put(memberName);
                JSONObject memberOrganisation = new JSONObject();
                memberOrganisation.put("organisation", currentMember.getOrganisation());
                member.put(memberOrganisation);
                JSONObject memberRole = new JSONObject();
                memberRole.put("role", currentMember.getRole());
                member.put(memberRole);
                JSONObject memberPlaceNumber = new JSONObject();
                memberPlaceNumber.put("placeNumber", currentMember.getPlaceNumber());
                member.put(memberPlaceNumber);
                members.put(member);
            }
            discussion.put("members",members);
            message.put("payload", discussion);
        } catch (JSONException je) {
            Log.e("createNewDiscussion", "JSONException! " + je.getLocalizedMessage());
            return null;
        }
        Log.d("createNewDiscussion", "JSON Message: " + message.toString());
        return message;
    }

    public static JSONObject createStartDiscussionJSONMessage(){
        JSONObject message = new JSONObject();
        try {
            message.put("topic", Topic.START_DISCUSSION);
        } catch (JSONException je) {
            Log.e("createStartDiscussion", "JSONException! " + je.getLocalizedMessage());
            return null;
        }
        Log.d("createStartDiscussion", "JSON Message: " + message.toString());
        return message;
    }

    public static JSONObject createEndDiscussionJSONMessage(){
        JSONObject message = new JSONObject();
        try {
            message.put("topic", Topic.END_DISCUSSION);
        } catch (JSONException je) {
            Log.e("createEndDiscussion", "JSONException! " + je.getLocalizedMessage());
            return null;
        }
        Log.d("createEndDiscussion", "JSON Message: " + message.toString());
        return message;
    }

    public static JSONObject createRemainingTimeJSONMessage(String timeMessage){
        JSONObject message = new JSONObject();
        try {
            message.put("topic", Topic.REMAINING_TIME);
            JSONObject remaining_time = new JSONObject();
            remaining_time.put("remaining_time",timeMessage);
            message.put("payload", remaining_time);
        } catch (JSONException je) {
            Log.e("createRemainingTime", "JSONException! " + je.getLocalizedMessage());
            return null;
        }
        Log.d("createRemainingTime", "JSON Message: " + message.toString());
        return message;
    }

    public static JSONObject createStartPauseJSONMessage(){
        JSONObject message = new JSONObject();
        try {
            message.put("topic", Topic.START_PAUSE);
        } catch (JSONException je) {
            Log.e("createStartPause", "JSONException! " + je.getLocalizedMessage());
            return null;
        }
        Log.d("createStartPause", "JSON Message: " + message.toString());
        return message;
    }

    public static JSONObject createEndPauseJSONMessage(){
        JSONObject message = new JSONObject();
        try {
            message.put("topic", Topic.END_PAUSE);
        } catch (JSONException je) {
            Log.e("createEndPause", "JSONException! " + je.getLocalizedMessage());
            return null;
        }
        Log.d("createEndPause", "JSON Message: " + message.toString());
        return message;
    }

    public static JSONObject createTopicJSONMessage(String topicString){
        JSONObject message = new JSONObject();
        try {
            message.put("topic", Topic.TOPIC);
            JSONObject topic = new JSONObject();
            topic.put("topic",topicString);
            message.put("payload", topic);
        } catch (JSONException je) {
            Log.e("createTopic", "JSONException! " + je.getLocalizedMessage());
            return null;
        }
        Log.d("createTopic", "JSON Message: " + message.toString());
        return message;
    }

    public static JSONObject createSilenceJSONMessage(){
        JSONObject message = new JSONObject();
        try {
            message.put("topic", Topic.SILENCE);
        } catch (JSONException je) {
            Log.e("createSilence", "JSONException! " + je.getLocalizedMessage());
            return null;
        }
        Log.d("createSilence", "JSON Message: " + message.toString());
        return message;
    }
}
