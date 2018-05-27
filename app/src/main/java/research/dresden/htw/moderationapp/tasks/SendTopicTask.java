package research.dresden.htw.moderationapp.tasks;

import android.os.AsyncTask;

import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import research.dresden.htw.moderationapp.model.SocketSingleton;
import research.dresden.htw.moderationapp.utils.JSONUtils;

public class SendTopicTask extends AsyncTask<Void, Void, Void> {

    private static String topic;

    public SendTopicTask(String topic){
        this.topic = topic;
    }

    protected Void doInBackground(Void... voids) {
        Socket webSocket = SocketSingleton.getSocket();
        JSONObject message = JSONUtils.createTopicJSONMessage(topic);
        webSocket.emit("message", message);
        return null;
    }
}
