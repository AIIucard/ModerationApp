package research.dresden.htw.moderationapp.activities.emulator;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import java.io.PrintWriter;
import java.util.ArrayList;

import research.dresden.htw.moderationapp.R;
import research.dresden.htw.moderationapp.model.Member;
import research.dresden.htw.moderationapp.model.SocketSingleton;
import research.dresden.htw.moderationapp.model.Title;
import research.dresden.htw.moderationapp.utils.JSONUtils;

public class EmulatorActivity extends AppCompatActivity {

    EditText e1;
    private static PrintWriter printWriter;
    String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emulator);

        e1 = findViewById(R.id.editText);
    }

    public void send_text(View v) {
        message = e1.getText().toString();
        EmulatorActivity.myTask mt = new EmulatorActivity.myTask();
        mt.execute();

        Toast.makeText(getApplicationContext(), "Data send", Toast.LENGTH_LONG).show();
    }

    class myTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Socket webSocket = SocketSingleton.getSocket();
            Member member = new Member(Title.DIPLOMA_OF_ARTS, "Simon" ,"HTW ", "Helper");
            ArrayList<Member> members = new ArrayList<Member>();
            members.add(member);
            JSONObject message = JSONUtils.createNewDiscussion("RunderTisch", 360, members);
            webSocket.emit("message", message);
            return null;
        }
    }

    /*@Override
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
