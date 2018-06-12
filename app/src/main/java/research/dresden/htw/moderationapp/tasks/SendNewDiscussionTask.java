package research.dresden.htw.moderationapp.tasks;

import android.os.AsyncTask;

import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import java.util.ArrayList;

import research.dresden.htw.moderationapp.model.Member;
import research.dresden.htw.moderationapp.utils.JSONUtils;

public class SendNewDiscussionTask extends AsyncTask<Void, Void, Void> {
    private final Socket socket;
    private final String title;
    private final int duration;
    private final ArrayList<Member> members;

    public SendNewDiscussionTask(Socket socket, String title, int duration, ArrayList<Member> members){
        this.socket = socket;
        this.title = title;
        this.duration = duration;
        this.members = members;
    }

    protected Void doInBackground(Void... voids) {
        JSONObject message = JSONUtils.createNewDiscussionJSONMessage(title, duration, members);
        socket.emit("message", message);
        return null;
    }
}
