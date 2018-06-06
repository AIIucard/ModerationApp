package research.dresden.htw.moderationapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;
import java.util.ArrayList;

import research.dresden.htw.moderationapp.R;
import research.dresden.htw.moderationapp.activities.discussion.AddDiscussionActivity;
import research.dresden.htw.moderationapp.activities.discussion.DiscussionAdministrationActivity;
import research.dresden.htw.moderationapp.activities.emulator.EmulatorActivity;
import research.dresden.htw.moderationapp.activities.members.MemberAdministrationActivity;
import research.dresden.htw.moderationapp.activities.settings.SettingsActivity;
import research.dresden.htw.moderationapp.manager.CfgManager;
import research.dresden.htw.moderationapp.manager.DiscussionManager;
import research.dresden.htw.moderationapp.manager.MemberManager;
import research.dresden.htw.moderationapp.model.AppConfig;
import research.dresden.htw.moderationapp.model.AppDataViewModel;
import research.dresden.htw.moderationapp.model.Discussion;
import research.dresden.htw.moderationapp.model.Member;
import research.dresden.htw.moderationapp.model.Title;
import research.dresden.htw.moderationapp.tasks.ConnectionTask;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private static AppDataViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = AppDataViewModel.getInstance();

        initializeDataViewModelFromJSON();

        createWebSocket();

        findViewById(R.id.button_emulator_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonStartEmulatorActivity();
            }
        });

        findViewById(R.id.button_new_discussion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonStartNewDisussionActivity();
            }
        });

        findViewById(R.id.button_member_administration).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonStartMemberAdministrationActivity();
            }
        });

        findViewById(R.id.button_discussion_administration).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonStartDiscussionAdministrationActivity();
            }
        });
        findViewById(R.id.button_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonStartSettingsActivity();
            }
        });


        // Burger Menu
        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });
        new ConnectionTask().execute(viewModel.getSocket());
    }

    private void initializeDataViewModelFromJSON(){
        ArrayList<Member> members;

        // TODO: Remove this later
        // Member Dummys
        ArrayList<Member> memberArrayListTemp = new ArrayList<>();
        memberArrayListTemp.add(new Member(1, Title.DIPLOMA_OF_LANGUAGE_STUDIES, "Hans Wurst", "HTW Dresden", "Der Sklave"));
        memberArrayListTemp.add(new Member(2, Title.DIPLOMA_OF_ARTS, "Brett Pid", "Baumschule", "Der geile Stecher"));
        memberArrayListTemp.add(new Member(3, Title.DIPLOMA_OF_MUSIC, "Thorsten Brotkahst", "Hacker", "Der Brotkahst Reziewer"));
        MemberManager memberManagerTemp = MemberManager.getInstance();
        memberManagerTemp.writeToJSONFile(getApplicationContext(), memberArrayListTemp);

        // TODO: Remove this later
        // Discussion Dumys
        ArrayList<Discussion> discussionArrayListTemp = new ArrayList<>();
        ArrayList<Member> memberArrayListTemp2 = new ArrayList<>();
        memberArrayListTemp2.add(new Member(1, Title.DIPLOMA_OF_ARTS, "Karl", "HTW", "Hat Ahnung", 2));
        memberArrayListTemp2.add(new Member(2, Title.DIPLOMA_OF_ARTS, "Simon", "HTW", "Hat Ahnung", 4));
        discussionArrayListTemp.add(new Discussion(1, "Runder Tisch", 360, memberArrayListTemp2));
        DiscussionManager discussionManagerTemp = DiscussionManager.getInstance();
        discussionManagerTemp.writeToJSONFile(getApplicationContext(), discussionArrayListTemp);

        // TODO: Remove this later
        // Websocket Dummys
        AppConfig config = new AppConfig("http://141.56.224.27:8989/");
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

    private void buttonStartNewDisussionActivity() {
        startActivity(new Intent(getBaseContext(), AddDiscussionActivity.class));
    }

    private void buttonStartEmulatorActivity() {
        startActivity(new Intent(getBaseContext(), EmulatorActivity.class));
    }

    private void buttonStartMemberAdministrationActivity() {
        startActivity(new Intent(getBaseContext(), MemberAdministrationActivity.class));
    }

    private void buttonStartDiscussionAdministrationActivity() {
        startActivity(new Intent(getBaseContext(), DiscussionAdministrationActivity.class));
    }

    private void buttonStartSettingsActivity() {
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