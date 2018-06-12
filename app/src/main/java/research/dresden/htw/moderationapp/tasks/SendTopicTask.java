package research.dresden.htw.moderationapp.tasks;

import android.os.AsyncTask;

import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import research.dresden.htw.moderationapp.utils.JSONUtils;

public class SendTopicTask extends AsyncTask<Void, Void, Void> {
    private final Socket socket;
    private final String topic;

    public SendTopicTask(Socket socket, String topic){
        this.socket = socket;
        this.topic = topic;
    }

    protected Void doInBackground(Void... voids) {
        JSONObject message = JSONUtils.createTopicJSONMessage(topic);
        socket.emit("message", message);
        return null;
    }
}
