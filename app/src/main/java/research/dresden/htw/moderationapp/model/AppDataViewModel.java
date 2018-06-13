package research.dresden.htw.moderationapp.model;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.github.nkzawa.socketio.client.Socket;

import java.util.ArrayList;

public class AppDataViewModel {
    private Socket socket;
    private static final Object lock = new Object();
    private final MutableLiveData<String> webSocketURI = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Discussion>> discussionList = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Member>> memberList = new MutableLiveData<>();
    private static AppDataViewModel instance = null;
    private final MutableLiveData<Member> lastSelectedMember = new MutableLiveData<>();
    private final MutableLiveData<Discussion> lastSelectedDiscussion = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Member>> selectedMembersForDiscussionList = new MutableLiveData<>();
    private final MutableLiveData<Discussion> discussionToEditTempObj = new MutableLiveData<>();

    private AppDataViewModel() {
        // Use getInstance
    }

    @SuppressWarnings("SynchronizeOnNonFinalField")
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

    public MutableLiveData<Member> getLastSelectedMember() {
        return lastSelectedMember;
    }

    public void setLastSelectedMember(Member lastSelectedMember) {
        this.lastSelectedMember.setValue(lastSelectedMember);
    }

    public MutableLiveData<Discussion> getLastSelectedDiscussion() {
        return lastSelectedDiscussion;
    }

    public void setLastSelectedDiscussion(Discussion lastSelectedDiscussion) {
        this.lastSelectedDiscussion.setValue(lastSelectedDiscussion);
    }

    public MutableLiveData<ArrayList<Member>> getSelectedMembersForDiscussionList() {
        if (selectedMembersForDiscussionList.getValue() == null) {
            selectedMembersForDiscussionList.setValue(new ArrayList<Member>());
        }
        return selectedMembersForDiscussionList;
    }

    public void setSelectedMembersForDiscussionList(ArrayList<Member> selectedMembersForDiscussionList) {
        this.selectedMembersForDiscussionList.setValue(selectedMembersForDiscussionList);
    }

    public MutableLiveData<Discussion> getDiscussionToEditTempObj() {
        return discussionToEditTempObj;
    }

    public void setDiscussionToEditTempObj(Discussion discussionToEditTempObj) {
        this.discussionToEditTempObj.setValue(discussionToEditTempObj);
    }
}
