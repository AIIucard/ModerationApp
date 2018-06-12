package research.dresden.htw.moderationapp.tasks;

import android.os.AsyncTask;

import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import research.dresden.htw.moderationapp.utils.JSONUtils;

public class SendRemainingTimeTask extends AsyncTask<Void, Void, Void> {
    private final Socket socket;
    private final String timeMessage;

    public SendRemainingTimeTask(Socket socket, String timeMessage){
        this.socket = socket;
        this.timeMessage = timeMessage;
    }

    protected Void doInBackground(Void... voids) {
        JSONObject message = JSONUtils.createRemainingTimeJSONMessage(timeMessage);
        socket.emit("message", message);
        return null;
    }
}
