package research.dresden.htw.moderationapp.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import java.util.ArrayList;

import research.dresden.htw.moderationapp.model.Member;
import research.dresden.htw.moderationapp.model.SocketSingleton;
import research.dresden.htw.moderationapp.model.Title;
import research.dresden.htw.moderationapp.utils.JSONUtils;

public class SendNewDiscussionTask extends AsyncTask<Void, Void, Void> {

    private static String title;
    private static int duration;
    private static ArrayList<Member> members;

    public SendNewDiscussionTask(String title, int duration, ArrayList<Member> members){
        this.title = title;
        this.duration = duration;
        this.members = members;
    }

    protected Void doInBackground(Void... voids) {
        Socket webSocket = SocketSingleton.getSocket();
        JSONObject message = JSONUtils.createNewDiscussionJSONMessage(title, duration, members);
        webSocket.emit("message", message);
        return null;
    }
}
