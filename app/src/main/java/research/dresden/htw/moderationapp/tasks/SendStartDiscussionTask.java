package research.dresden.htw.moderationapp.tasks;

import android.os.AsyncTask;

import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import research.dresden.htw.moderationapp.utils.JSONUtils;

public class SendStartDiscussionTask extends AsyncTask<Void, Void, Void> {
    private Socket socket;
    public SendStartDiscussionTask(Socket socket){
        this.socket = socket;
    }

    protected Void doInBackground(Void... voids) {
        JSONObject message = JSONUtils.createStartDiscussionJSONMessage();
        socket.emit("message", message);
        return null;
    }
}
