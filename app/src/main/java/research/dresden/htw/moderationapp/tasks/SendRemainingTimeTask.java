package research.dresden.htw.moderationapp.tasks;

import android.os.AsyncTask;

import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import java.util.ArrayList;

import research.dresden.htw.moderationapp.model.Member;
import research.dresden.htw.moderationapp.model.SocketSingleton;
import research.dresden.htw.moderationapp.utils.JSONUtils;

public class SendRemainingTimeTask extends AsyncTask<Void, Void, Void> {

    private static String timeMessage;

    public SendRemainingTimeTask(String timeMessage){
        this.timeMessage = timeMessage;
    }

    protected Void doInBackground(Void... voids) {
        Socket webSocket = SocketSingleton.getSocket();
        JSONObject message = JSONUtils.createRemainingTimeJSONMessage(timeMessage);
        webSocket.emit("message", message);
        return null;
    }
}
