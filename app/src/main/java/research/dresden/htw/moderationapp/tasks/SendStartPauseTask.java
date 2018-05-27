package research.dresden.htw.moderationapp.tasks;

import android.os.AsyncTask;

import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import research.dresden.htw.moderationapp.utils.JSONUtils;

public class SendStartPauseTask extends AsyncTask<Void, Void, Void> {
    Socket socket;
    public SendStartPauseTask(Socket socket){
        this.socket = socket;
    }

    protected Void doInBackground(Void... voids) {
        JSONObject message = JSONUtils.createStartPauseJSONMessage();
        socket.emit("message", message);
        return null;
    }
}
