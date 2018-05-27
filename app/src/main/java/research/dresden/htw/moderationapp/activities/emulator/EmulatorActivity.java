package research.dresden.htw.moderationapp.activities.emulator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.PrintWriter;
import java.util.ArrayList;

import research.dresden.htw.moderationapp.R;
import research.dresden.htw.moderationapp.model.AppDataViewModel;
import research.dresden.htw.moderationapp.model.Member;
import research.dresden.htw.moderationapp.model.Title;
import research.dresden.htw.moderationapp.tasks.SendEndDiscussionTask;
import research.dresden.htw.moderationapp.tasks.SendEndPauseTask;
import research.dresden.htw.moderationapp.tasks.SendNewDiscussionTask;
import research.dresden.htw.moderationapp.tasks.SendRemainingTimeTask;
import research.dresden.htw.moderationapp.tasks.SendSilenceTask;
import research.dresden.htw.moderationapp.tasks.SendStartDiscussionTask;
import research.dresden.htw.moderationapp.tasks.SendStartPauseTask;
import research.dresden.htw.moderationapp.tasks.SendTopicTask;

public class EmulatorActivity extends AppCompatActivity {

    EditText inputTextField;
    private static PrintWriter printWriter;
    static String message = "";
    private static AppDataViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emulator);
        viewModel = AppDataViewModel.getInstance();
        inputTextField = findViewById(R.id.inputTextField);
    }

    public void sendNewDiscussion(View v) {
        Member member1 = new Member(1, Title.DIPLOMA_OF_ARTS, "Simon" ,"HTW ", "Helper", 2);
        Member member2 = new Member(2, Title.DIPLOMA_OF_MUSIC, "Georg" ,"HTW ", "Master", 4);
        ArrayList<Member> members = new ArrayList<Member>();
        members.add(member1);
        members.add(member2);
        if(viewModel.getSocket()== null){
            Log.d("setSocket", "No Socket found!");
        } else {
            new SendNewDiscussionTask(viewModel.getSocket(), "RunderTisch", 360, members).execute();
            Toast.makeText(getApplicationContext(), "New Disscusion sended...", Toast.LENGTH_LONG).show();
        }
    }

    public void sendStartDiscussion(View v) {

        new SendStartDiscussionTask(viewModel.getSocket()).execute();
        Toast.makeText(getApplicationContext(), "Start Disscusion sended...", Toast.LENGTH_LONG).show();
    }

    public void sendEndDiscussion(View v) {
        new SendEndDiscussionTask(viewModel.getSocket()).execute();
        Toast.makeText(getApplicationContext(), "End Disscusion sended...", Toast.LENGTH_LONG).show();
    }

    public void sendRemainingTime(View v) {
        new SendRemainingTimeTask(viewModel.getSocket(),"Noch 10 Minuten!").execute();
        Toast.makeText(getApplicationContext(), "Remaining Time sended...", Toast.LENGTH_LONG).show();
    }

    public void sendStartPause(View v) {
        new SendStartPauseTask(viewModel.getSocket()).execute();
        Toast.makeText(getApplicationContext(), "Start Pause sended...", Toast.LENGTH_LONG).show();
    }

    public void sendEndPause(View v) {
        new SendEndPauseTask(viewModel.getSocket()).execute();
        Toast.makeText(getApplicationContext(), "End Pause sended...", Toast.LENGTH_LONG).show();
    }

    public void sendTopic(View v) {
        message = inputTextField.getText().toString();
        new SendTopicTask(viewModel.getSocket(),message).execute();
        Toast.makeText(getApplicationContext(), "Topic with Message: " + message + " sendet...", Toast.LENGTH_LONG).show();
    }

    public void sendSilence(View v) {
        new SendSilenceTask(viewModel.getSocket()).execute();
        Toast.makeText(getApplicationContext(), "Silence sended...", Toast.LENGTH_LONG).show();
    }
}
