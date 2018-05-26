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
import research.dresden.htw.moderationapp.model.SocketSingleton;
import research.dresden.htw.moderationapp.tasks.ConnectionTask;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createWebSocket();

        findViewById(R.id.button_start_add_keyword_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_start_add_keyword_activity();
            }
        });

        findViewById(R.id.button_new_discussion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_start_new_disussion_activity();
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

    private void button_start_add_keyword_activity() {
        startActivity(new Intent(getBaseContext(), AddKeywordActivity.class));
    }

    private void button_start_new_disussion_activity() {
        startActivity(new Intent(getBaseContext(), NewDiscussionActivity.class));
    }

    private void createWebSocket() {
        try {
            Socket mSocket = IO.socket("http://141.56.224.171:8989/");

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