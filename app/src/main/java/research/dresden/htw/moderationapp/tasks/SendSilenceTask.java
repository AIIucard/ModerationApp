package research.dresden.htw.moderationapp.tasks;

import android.os.AsyncTask;

import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import research.dresden.htw.moderationapp.utils.JSONUtils;

public class SendSilenceTask extends AsyncTask<Void, Void, Void> {
    private Socket socket;
    public SendSilenceTask(Socket socket){
        this.socket = socket;
    }

    protected Void doInBackground(Void... voids) {
        JSONObject message = JSONUtils.createSilenceJSONMessage();
        socket.emit("message", message);
        return null;
    }
}
