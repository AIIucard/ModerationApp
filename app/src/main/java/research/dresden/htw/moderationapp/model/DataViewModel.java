package research.dresden.htw.moderationapp.model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.github.nkzawa.socketio.client.Socket;

import java.util.ArrayList;

public class DataViewModel extends ViewModel {
    private Socket socket;
    private MutableLiveData<String> webSocketURI = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Discussion>> discussionList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Member>> memberList = new MutableLiveData<>();

    public MutableLiveData<String> getWebSocketURI() {
        return webSocketURI;
    }

    public void setWebSocketURI(String webSocketURI) {
        this.webSocketURI.postValue(webSocketURI);
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public MutableLiveData<ArrayList<Discussion>> getDiscussionList() {
        return discussionList;
    }

    public void setDiscussionList(ArrayList<Discussion> discussionList) {
        this.discussionList.postValue(discussionList);
    }

    public MutableLiveData<ArrayList<Member>> getMemberList() {
        return memberList;
    }

    public void setMemberList(ArrayList<Member> memberList) {
        this.memberList.postValue(memberList);
    }
}
