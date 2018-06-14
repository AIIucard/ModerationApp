package research.dresden.htw.moderationapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
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

    private DrawerLayout mDrawerLayout;
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
        /*
        memberArrayListTemp.add(new Member(1, Title.DIPLOMA_OF_LANGUAGE_STUDIES, "Hans Wurst", "HTW Dresden", "Der Sklave"));
        memberArrayListTemp.add(new Member(2, Title.DIPLOMA_OF_ARTS, "Brett Pid", "Baumschule", "Der geile Stecher"));
        memberArrayListTemp.add(new Member(3, Title.DIPLOMA_OF_MUSIC, "Thorsten Brotkahst", "Hacker", "Der Brotkahst Reziewer"));
        memberArrayListTemp.add(new Member(4, Title.DIPLOMA_OF_EDUCATION, "Knecht Ruprecht", "Harz 4 AG", "Der Chiller"));
        memberArrayListTemp.add(new Member(5, Title.DIPLOMA_OF_MUSIC, "Darth Vader", "Imperium", "Lichtschwert Schwinger"));
        memberArrayListTemp.add(new Member(6, Title.DIPLOMA_OF_SOCIAL_SCIENCES, "Der Pedobär", "Kindergarten", "Der Vertrauenswürdige"));
        memberArrayListTemp.add(new Member(7, Title.DIPLOMA_OF_MUSIC, "Pinzessin Lillifee", "Feenreich", "Die Matratze"));
        */
        memberArrayListTemp.add(new Member(1, Title.CLOSE_TO_MASTER_OF_ARTS, "Charlie", "HTW Dresden", "Creates Fancy Stuff"));
        memberArrayListTemp.add(new Member(2, Title.RPOF_OF_ARTS, "Simon", "HTW Dresden", "Neue Inspiration"));
        memberArrayListTemp.add(new Member(3, Title.RPOF_OF_SIENCE, "Georg", "HTW Dresden", "Ruhepol"));
        memberArrayListTemp.add(new Member(4, Title.CLOSE_TO_MASTER_OF_SIENCE, "Stefan", "HTW Dresden", "Hackerman"));
        memberArrayListTemp.add(new Member(5, Title.DIPLOMA_OF_MUSIC, "Fabian", "HTW Dresden", "Hackerman"));
        memberArrayListTemp.add(new Member(6, Title.CLOSE_TO_MASTER_OF_SIENCE, "Max", "HTW Dresden", "Handler Creator hat Ahnung"));
        memberArrayListTemp.add(new Member(7, Title.CLOSE_TO_MASTER_OF_ARTS, "Shuqi", "HTW Dresden", "Quality AppDesigner"));
        memberArrayListTemp.add(new Member(8, Title.CLOSE_TO_MASTER_OF_SIENCE, "Kay", "HTW Dresden", "Hackerman"));
        memberArrayListTemp.add(new Member(9, Title.CLOSE_TO_MASTER_OF_ARTS, "Paul", "HTW Dresden", "Crafter"));
        memberArrayListTemp.add(new Member(10, Title.RPOF_OF_SIENCE, "Denise", "HTW Dresden", "Fuck google Ask him"));

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
        memberArrayListTemp2.add(new Member(1, Title.CLOSE_TO_MASTER_OF_ARTS, "Charlie", "HTW Dresden", "Creates Fancy Stuff", 1));
        memberArrayListTemp2.add(new Member(3, Title.RPOF_OF_SIENCE, "Kay", "HTW Dresden", "Ruhepol", 2));
        memberArrayListTemp2.add(new Member(2, Title.RPOF_OF_ARTS, "Antje<3", "HTW Dresden", "Neue Inspiration",3));
        memberArrayListTemp2.add(new Member(6, Title.CLOSE_TO_MASTER_OF_SIENCE, "Max", "HTW Dresden", "Handler Creator hat Ahnung",4));
        memberArrayListTemp2.add(new Member(4, Title.CLOSE_TO_MASTER_OF_SIENCE, "Stefan", "HTW Dresden", "Hackerman",5));
        discussionArrayListTemp.add(new Discussion(1, "Runder Tisch Beispiel", 10, memberArrayListTemp2));


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
        AppDataViewModel.getInstance().setSelectedMembersForDiscussionList(new ArrayList<Member>());
        startActivityForResult(new Intent(getBaseContext(), AddDiscussionActivity.class), RequestCode.ADD_NEW_DISCUSSION_CODE);
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