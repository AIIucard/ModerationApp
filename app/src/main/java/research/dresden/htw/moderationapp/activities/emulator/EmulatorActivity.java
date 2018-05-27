package research.dresden.htw.moderationapp.activities.emulator;

import android.annotation.SuppressLint;
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
import research.dresden.htw.moderationapp.tasks.SendEndDiscussionTask;
import research.dresden.htw.moderationapp.tasks.SendEndPauseTask;
import research.dresden.htw.moderationapp.tasks.SendNewDiscussionTask;
import research.dresden.htw.moderationapp.tasks.SendRemainingTimeTask;
import research.dresden.htw.moderationapp.tasks.SendSilenceTask;
import research.dresden.htw.moderationapp.tasks.SendStartDiscussionTask;
import research.dresden.htw.moderationapp.tasks.SendStartPauseTask;
import research.dresden.htw.moderationapp.tasks.SendTopicTask;
import research.dresden.htw.moderationapp.utils.JSONUtils;

public class EmulatorActivity extends AppCompatActivity {

    EditText inputTextField;
    private static PrintWriter printWriter;
    static String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emulator);
        inputTextField = findViewById(R.id.inputTextField);
    }

    public void send_new_discussion(View v) {
        Member member = new Member(1, Title.DIPLOMA_OF_ARTS, "Simon" ,"HTW ", "Helper", 2);
        ArrayList<Member> members = new ArrayList<Member>();
        members.add(member);
        new SendNewDiscussionTask("RunderTisch", 360, members).execute();
        Toast.makeText(getApplicationContext(), "New Disscusion sended...", Toast.LENGTH_LONG).show();
    }

    public void send_start_discussion(View v) {
        new SendStartDiscussionTask().execute();
        Toast.makeText(getApplicationContext(), "Start Disscusion sended...", Toast.LENGTH_LONG).show();
    }

    public void send_end_discussion(View v) {
        new SendEndDiscussionTask().execute();
        Toast.makeText(getApplicationContext(), "End Disscusion sended...", Toast.LENGTH_LONG).show();
    }

    public void send_remaining_time(View v) {
        new SendRemainingTimeTask("Noch 10 Minuten!").execute();
        Toast.makeText(getApplicationContext(), "Remaining Time sended...", Toast.LENGTH_LONG).show();
    }

    public void send_start_pause(View v) {
        new SendStartPauseTask().execute();
        Toast.makeText(getApplicationContext(), "Start Pause sended...", Toast.LENGTH_LONG).show();
    }

    public void send_end_pause(View v) {
        new SendEndPauseTask().execute();
        Toast.makeText(getApplicationContext(), "End Pause sended...", Toast.LENGTH_LONG).show();
    }

    public void send_topic(View v) {
        message = inputTextField.getText().toString();
        new SendTopicTask(message).execute();
        Toast.makeText(getApplicationContext(), "Topic with Message: " + message + " sendet...", Toast.LENGTH_LONG).show();
    }

    public void send_silence(View v) {
        new SendSilenceTask().execute();
        Toast.makeText(getApplicationContext(), "Silence sended...", Toast.LENGTH_LONG).show();
    }
}
