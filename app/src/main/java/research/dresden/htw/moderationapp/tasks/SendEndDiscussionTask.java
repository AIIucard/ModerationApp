package research.dresden.htw.moderationapp.tasks;

import android.os.AsyncTask;

import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import research.dresden.htw.moderationapp.model.SocketSingleton;
import research.dresden.htw.moderationapp.utils.JSONUtils;

public class SendEndDiscussionTask extends AsyncTask<Void, Void, Void> {

    public SendEndDiscussionTask(){

    }

    protected Void doInBackground(Void... voids) {
        Socket webSocket = SocketSingleton.getSocket();
        JSONObject message = JSONUtils.createEndDiscussionJSONMessage();
        webSocket.emit("message", message);
        return null;
    }
}
