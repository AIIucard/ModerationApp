package research.dresden.htw.moderationapp.model;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.github.nkzawa.socketio.client.Socket;

import java.util.ArrayList;

public class AppDataViewModel {
    private Socket socket;
    private MutableLiveData<String> webSocketURI = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Discussion>> discussionList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Member>> memberList = new MutableLiveData<>();

    private static Object lock = new Object();
    private static AppDataViewModel instance = null;

    private AppDataViewModel() {
        // Use getInstance
    }

    public static AppDataViewModel getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new AppDataViewModel();
                }
            }
        }

        return (instance);
    }

    public MutableLiveData<String> getWebSocketURI() {
        return webSocketURI;
    }

    public void setWebSocketURI(String webSocketURI) {
        this.webSocketURI.setValue(webSocketURI);
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
        Log.d("setSocket", "Socket set! " + socket);
    }

    public MutableLiveData<ArrayList<Discussion>> getDiscussionList() {
        return discussionList;
    }

    public void setDiscussionList(ArrayList<Discussion> discussionList) {
        this.discussionList.setValue(discussionList);
    }

    public MutableLiveData<ArrayList<Member>> getMemberList() {
        return memberList;
    }

    public void setMemberList(ArrayList<Member> memberList) {
        this.memberList.setValue(memberList);
    }
}
