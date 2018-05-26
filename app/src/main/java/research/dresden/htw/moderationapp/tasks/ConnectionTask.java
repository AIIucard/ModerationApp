package research.dresden.htw.moderationapp.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.github.nkzawa.socketio.client.Socket;

public class ConnectionTask extends AsyncTask<Socket, Void, Void> {
    private Exception exception;

    protected Void doInBackground(Socket... webSockets) {
        Socket webSocket = webSockets[0];
        if(webSocket != null) {
            webSocket.connect();
            Log.d("onCreate", "Connected!");
        } else{
            Log.e("onCreate", "No Websocket created!");
        }
        return null;
    }
}
