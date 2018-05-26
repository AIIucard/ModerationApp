package research.dresden.htw.moderationapp.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import research.dresden.htw.moderationapp.model.Member;

public class JSONUtils {

    public static JSONObject createNewDiscussion(String title, int duration, ArrayList<Member> memberList){
        JSONObject message = new JSONObject();
        try {
            message.put("topic", Topic.DISCUSSION);
            JSONObject discussion = new JSONObject();
            discussion.put("title",title);
            discussion.put("duration",duration);
            JSONArray members = new JSONArray();
            for (Member currentMember: memberList) {
                JSONObject memberTitle = new JSONObject();
                memberTitle.put("title", currentMember.getTitle().toString());
                members.put(memberTitle);
                JSONObject memberName = new JSONObject();
                memberName.put("name", currentMember.getName());
                members.put(memberName);
                JSONObject memberOrganisation = new JSONObject();
                memberOrganisation.put("organisation", currentMember.getOrganisation());
                members.put(memberOrganisation);
                JSONObject memberDescription = new JSONObject();
                memberDescription.put("description", currentMember.getDescription());
                members.put(memberDescription);
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
}
