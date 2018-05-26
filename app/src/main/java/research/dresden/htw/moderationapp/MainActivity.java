package research.dresden.htw.moderationapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.neovisionaries.ws.client.OpeningHandshakeException;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private WebSocket webSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_start_add_keyword_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_start_add_keyword_activity();
            }
        });

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
        findViewById(R.id.button_new_discussion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_start_new_disussion_activity();
            }
        });
        createWebSocket();
        new ConnectionTask().execute(webSocket);
    }

    private void button_start_add_keyword_activity() {
        startActivity(new Intent(getBaseContext(), AddKeywordActivity.class));
    }

    private void button_start_new_disussion_activity() {
        startActivity(new Intent(getBaseContext(), NewDiscussionActivity.class));
    }

    private void createWebSocket() {
        try{
            WebSocketFactory factory = new WebSocketFactory().setConnectionTimeout(5000);
            webSocket = factory.createSocket("ws://141.56.224.171:8989/socket.io/?EIO=4&transport=websocket");
            //ws://141.56.224.171:8989/socket.io/?EIO=4&transport=websocket

            webSocket.addListener(new WebSocketAdapter() {
                @Override
                public void onTextMessage(WebSocket websocket, String message) throws Exception {
                    // Received a text message
                    Log.d("onCreate", "Got Message: " + message);
                }
            });
        } catch (IOException ioe) {
            Log.e("onCreate", "IOException! " + ioe.getLocalizedMessage());
        }
    }

    private void startWebSocket() {

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