package research.dresden.htw.moderationapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;
import java.util.ArrayList;

import research.dresden.htw.moderationapp.R;
import research.dresden.htw.moderationapp.activities.discussion.AddDiscussionActivity;
import research.dresden.htw.moderationapp.activities.discussion.DiscussionAdministrationActivity;
import research.dresden.htw.moderationapp.activities.members.MemberAdministrationActivity;
import research.dresden.htw.moderationapp.activities.settings.SettingsActivity;
import research.dresden.htw.moderationapp.manager.CfgManager;
import research.dresden.htw.moderationapp.manager.DiscussionManager;
import research.dresden.htw.moderationapp.manager.MemberManager;
import research.dresden.htw.moderationapp.model.AppConfig;
import research.dresden.htw.moderationapp.model.AppDataViewModel;
import research.dresden.htw.moderationapp.model.Discussion;
import research.dresden.htw.moderationapp.model.IntentType;
import research.dresden.htw.moderationapp.model.Member;
import research.dresden.htw.moderationapp.model.RequestCode;
import research.dresden.htw.moderationapp.model.Title;
import research.dresden.htw.moderationapp.tasks.ConnectionTask;

public class MainActivity extends AppCompatActivity {

    private static AppDataViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = AppDataViewModel.getInstance();

        initializeDataViewModelFromJSON();

        createWebSocket();


        findViewById(R.id.button_new_discussion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonStartNewDisussionActivityClicked();
            }
        });

        findViewById(R.id.button_member_administration).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonStartMemberAdministrationActivityClicked();
            }
        });

        findViewById(R.id.button_discussion_administration).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonStartDiscussionAdministrationActivityClicked();
            }
        });

        findViewById(R.id.button_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonStartSettingsActivityClicked();
            }
        });

        new ConnectionTask().execute(viewModel.getSocket());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null && data.getExtras() != null && data.getExtras().getString("type") != null) {
            String resultType = data.getExtras().getString("type");
            if (resultType != null && requestCode == RequestCode.ADD_NEW_DISCUSSION_CODE) {
                if (resultCode == Activity.RESULT_CANCELED) {
                    if (resultType.equals(IntentType.ADD_RESULT_TYPE)) {
                        Toast.makeText(MainActivity.this, getString(R.string.canceled_add_toast_discussion), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    private void initializeDataViewModelFromJSON(){
        ArrayList<Member> members;

        // TODO: Remove this later
        // Member Dummys
        ArrayList<Member> memberArrayListTemp = new ArrayList<>();
        memberArrayListTemp.add(new Member(1, Title.MASTER, "Hans Wurst", "HTW Dresden", "Der Sklave"));
        memberArrayListTemp.add(new Member(2, Title.BACHELOR, "Brett Pid", "Baumschule", "Der geile Stecher"));
        memberArrayListTemp.add(new Member(3, Title.WITHOUT, "Knecht Ruprecht", "Harz 4 AG", "Der Chiller"));
        memberArrayListTemp.add(new Member(4, Title.WITHOUT, "Darth Vader", "Imperium", "Lichtschwert Schwinger"));
        memberArrayListTemp.add(new Member(5, Title.DOCTOR, "Error 404", "Internet", "Shiiiiit"));
        memberArrayListTemp.add(new Member(6, Title.DOCTOR, "Krooooooßartig", "DFB", "Fussballgott"));
        memberArrayListTemp.add(new Member(7, Title.DIPLOM, "Thorsten Brotkahst", "Hacker", "Der Brotkahst Reziewer"));
//        memberArrayListTemp.add(new Member(1, Title.BACHELOR, "Charlie", "HTW Dresden", "Creates Fancy Stuff"));
//        memberArrayListTemp.add(new Member(2, Title.DOCTOR, "Simon", "HTW Dresden", "Neue Inspiration"));
//        memberArrayListTemp.add(new Member(3, Title.DOCTOR, "Georg", "HTW Dresden", "Ruhepol"));
//        memberArrayListTemp.add(new Member(4, Title.BACHELOR, "Stefan", "HTW Dresden", "Hackerman"));
//        memberArrayListTemp.add(new Member(5, Title.BACHELOR, "Fabian", "HTW Dresden", "Hackerman"));
//        memberArrayListTemp.add(new Member(6, Title.BACHELOR, "Max", "HTW Dresden", "Handler Creator hat Ahnung"));
//        memberArrayListTemp.add(new Member(7, Title.BACHELOR, "Shuqi", "HTW Dresden", "Quality AppDesigner"));
//        memberArrayListTemp.add(new Member(8, Title.BACHELOR, "Kay", "HTW Dresden", "Hackerman"));
//        memberArrayListTemp.add(new Member(9, Title.BACHELOR, "Paul", "HTW Dresden", "Crafter"));
//        memberArrayListTemp.add(new Member(10, Title.MASTER, "Denise", "HTW Dresden", "Fuck google Ask him"));

        MemberManager memberManagerTemp = MemberManager.getInstance();
        memberManagerTemp.writeToJSONFile(getApplicationContext(), memberArrayListTemp);

        // TODO: Remove this later
        // Discussion Dumys
        ArrayList<Discussion> discussionArrayListTemp = new ArrayList<>();
        ArrayList<Member> memberArrayListTemp2 = new ArrayList<>();
        /* memberArrayListTemp2.add(new Member(1, Title.DIPLOMA_OF_LANGUAGE_STUDIES, "Hans Wurst", "HTW Dresden", "Der Sklave", 2));
        memberArrayListTemp2.add(new Member(2, Title.DIPLOMA_OF_ARTS, "Brett Pid", "Baumschule", "Der geile Stecher", 4));
        memberArrayListTemp2.add(new Member(3, Title.DIPLOMA_OF_MUSIC, "Thorsten Brotkahst", "Hacker", "Der Brotkahst Reziewer", 5));

        discussionArrayListTemp.add(new Discussion(1, "Runder Tisch Beispiel", 370, memberArrayListTemp2));
        */
        memberArrayListTemp2.add(new Member(1, Title.MASTER, "Hans Wurst", "HTW Dresden", "Der Sklave", 1));
        memberArrayListTemp2.add(new Member(2, Title.BACHELOR, "Brett Pid", "Baumschule", "Der geile Stecher", 2));
        memberArrayListTemp2.add(new Member(3, Title.WITHOUT, "Knecht Ruprecht", "Harz 4 AG", "Der Chiller", 3));
        memberArrayListTemp2.add(new Member(4, Title.WITHOUT, "Darth Vader", "Imperium", "Lichtschwert Schwinger", 4));
        memberArrayListTemp2.add(new Member(5, Title.DOCTOR, "Error 404", "Internet", "Shiiiiit", 5));
        discussionArrayListTemp.add(new Discussion(1, "Selbsthilfe Gruppe", 10, memberArrayListTemp2));


        DiscussionManager discussionManagerTemp = DiscussionManager.getInstance();
        discussionManagerTemp.writeToJSONFile(getApplicationContext(), discussionArrayListTemp);

        // TODO: Remove this later
        // Websocket Dummys
        AppConfig config = new AppConfig("http://141.56.227.41:8989/");
        CfgManager cfgManagerTemp = CfgManager.getInstance();
        cfgManagerTemp.writeToJSONFile(getApplicationContext(), config);

        //Load Members from JSON
        MemberManager memberManager = MemberManager.getInstance();
        members = memberManager.readFromJSONFile(getApplicationContext());

        //Load Discussion from JSON
        DiscussionManager discussionManager = DiscussionManager.getInstance();
        ArrayList<Discussion> discussion = discussionManager.readFromJSONFile(getApplicationContext());

        //Load Socket URI from JSON
        CfgManager cfgManager= CfgManager.getInstance();
        AppConfig cfg = cfgManager.readFromJSONFile(getApplicationContext());
        String webSocketURI = cfg.getWebSocketURI();

        viewModel.setMemberList(members);
        viewModel.setDiscussionList(discussion);
        viewModel.setWebSocketURI(webSocketURI);
    }

    private void handleButtonStartNewDisussionActivityClicked() {
        AppDataViewModel.getInstance().setSelectedMembersForDiscussionList(new ArrayList<Member>());
        startActivityForResult(new Intent(getBaseContext(), AddDiscussionActivity.class), RequestCode.ADD_NEW_DISCUSSION_CODE);
    }


    private void handleButtonStartMemberAdministrationActivityClicked() {
        startActivity(new Intent(getBaseContext(), MemberAdministrationActivity.class));
    }

    private void handleButtonStartDiscussionAdministrationActivityClicked() {
        startActivity(new Intent(getBaseContext(), DiscussionAdministrationActivity.class));
    }

    private void buttonStartSettingsActivityClicked() {
        startActivity(new Intent(getBaseContext(), SettingsActivity.class));
    }

    private void createWebSocket() {
        try {
            String uri = viewModel.getWebSocketURI().getValue();
            Socket mSocket = IO.socket(uri);
            viewModel.setSocket(mSocket);
            if(mSocket.connected()){
                Log.d("onCreate", "Connection detected!");
            }
        } catch (URISyntaxException ue) {
            Log.e("onCreate", "URISyntaxException! " + ue.getLocalizedMessage());
        }
    }
}