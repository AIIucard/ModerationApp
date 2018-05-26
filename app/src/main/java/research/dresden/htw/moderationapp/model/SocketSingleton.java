package research.dresden.htw.moderationapp.model;

import com.github.nkzawa.socketio.client.Socket;

public class SocketSingleton {


    private static Socket socket;

    public static void setSocket(Socket socketpass){
        SocketSingleton.socket=socketpass;
    }

    public static Socket getSocket(){
        return SocketSingleton.socket;
    }
}