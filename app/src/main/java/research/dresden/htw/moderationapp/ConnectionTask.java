package research.dresden.htw.moderationapp;

import android.os.AsyncTask;
import android.util.Log;

import com.neovisionaries.ws.client.OpeningHandshakeException;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;

public class ConnectionTask extends AsyncTask<WebSocket, Void, Void> {
    private Exception exception;

    protected Void doInBackground(WebSocket... webSockets) {
        WebSocket webSocket = webSockets[0];
        try{
            if(webSocket != null) {
                webSocket.connect();
                Log.d("onCreate", "Connected!");
            } else{
                Log.e("onCreate", "No Websocket created!");
            }
        } catch (OpeningHandshakeException ohe) {
            Log.e("onCreate", "OpeningHandshakeException! " + ohe.getLocalizedMessage());
        } catch (WebSocketException we) {
            Log.e("onCreate", "WebSocketException! " + we.getLocalizedMessage());
        }
        return null;
    }
}
