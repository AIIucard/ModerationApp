package research.dresden.htw.moderationapp.tasks;

import android.os.AsyncTask;

import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import research.dresden.htw.moderationapp.utils.JSONUtils;

public class SendEndPauseTask extends AsyncTask<Void, Void, Void> {
    private Socket socket;
    public SendEndPauseTask(Socket socket){
        this.socket = socket;
    }

    protected Void doInBackground(Void... voids) {
        JSONObject message = JSONUtils.createEndPauseJSONMessage();
        socket.emit("message", message);
        return null;
    }
}
