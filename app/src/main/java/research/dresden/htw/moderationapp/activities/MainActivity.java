package research.dresden.htw.moderationapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

import research.dresden.htw.moderationapp.R;
import research.dresden.htw.moderationapp.activities.discussion.AddDiscussionActivity;
import research.dresden.htw.moderationapp.activities.discussion.DiscussionAdministrationActivity;
import research.dresden.htw.moderationapp.activities.emulator.EmulatorActivity;
import research.dresden.htw.moderationapp.activities.members.MemberAdministratonActivity;
import research.dresden.htw.moderationapp.manager.MemberManager;
import research.dresden.htw.moderationapp.model.SocketSingleton;
import research.dresden.htw.moderationapp.tasks.ConnectionTask;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: Test XMLblablabla
        // MemberManager memberManager = MemberManager.getInstance();
        // memberManager.writeToAddressBookXml(getBaseContext().getApplicationContext());

        createWebSocket();

        findViewById(R.id.button_emulator_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_start_emulator_activity();
            }
        });

        findViewById(R.id.button_new_discussion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_start_new_disussion_activity();
            }
        });

        findViewById(R.id.button_member_administration).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_start_member_administration_activity();
            }
        });


        findViewById(R.id.button_discussion_administration).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_start_discussion_administration_activity();
            }
        });

        // Burger Menu
        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });
        new ConnectionTask().execute(SocketSingleton.getSocket());
    }
    private void button_start_new_disussion_activity() {
        startActivity(new Intent(getBaseContext(), AddDiscussionActivity.class));
    }

    private void button_start_emulator_activity() {
        startActivity(new Intent(getBaseContext(), EmulatorActivity.class));
    }

    private void button_start_member_administration_activity() {
        startActivity(new Intent(getBaseContext(), MemberAdministratonActivity.class));
    }

    private void button_start_discussion_administration_activity() {
        startActivity(new Intent(getBaseContext(), DiscussionAdministrationActivity.class));
    }

    private void createWebSocket() {
        try {
            Socket mSocket = IO.socket("http://141.56.224.27:8989/");

            SocketSingleton.setSocket(mSocket);
            if(mSocket.connected()){
                Log.d("onCreate", "Connection detected!");
            }
        } catch (URISyntaxException ue) {
            Log.e("onCreate", "URISyntaxException! " + ue.getLocalizedMessage());
        }
    }

   /* @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }*/
}