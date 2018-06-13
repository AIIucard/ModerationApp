package research.dresden.htw.moderationapp.activities.discussion;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import research.dresden.htw.moderationapp.R;
import research.dresden.htw.moderationapp.model.AppDataViewModel;
import research.dresden.htw.moderationapp.model.Discussion;
import research.dresden.htw.moderationapp.model.IntentType;
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

public class DiscussionControlCenterActivity extends AppCompatActivity {



    private EditText topicInput;
    private static String message = "";
    private static AppDataViewModel viewModel;
    private Boolean isStartActive = true;
    private Boolean isEndActive = false;
    private Boolean isPauseActive = false;
    private Boolean textChanged = false;

    private Button startDisButton;
    private Button endDisButton;

    private ImageButton sendTopicButton;
    private ImageButton pauseButton;
    private ImageButton timeUpdateButton;
    private ImageButton silenceButton;

    private TextView pauseText;

    private AppDataViewModel dataViewModel;

    private Discussion discussion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_control_center);
        viewModel = AppDataViewModel.getInstance();
        dataViewModel = AppDataViewModel.getInstance();
        discussion = dataViewModel.getLastSelectedDiscussion().getValue();

        startDisButton = findViewById(R.id.startDiscussionButton);
        startDisButton.setEnabled(isStartActive);
        endDisButton = findViewById(R.id.endDiscussionButton);
        endDisButton.setVisibility(View.GONE);
        endDisButton.setEnabled(isEndActive);
        pauseButton = findViewById(R.id.pauseButton);
        pauseButton.setEnabled(false);
        silenceButton = findViewById(R.id.silenceButton);
        silenceButton.setEnabled(false);
        timeUpdateButton = findViewById(R.id.timeupdateButton);
        timeUpdateButton.setEnabled(false);
        sendTopicButton = findViewById(R.id.sendButton);
        sendTopicButton.setVisibility(View.GONE);
        sendTopicButton.setEnabled(false);


        pauseText = findViewById(R.id.textview_pause);

        topicInput = findViewById(R.id.topicInput);

        topicInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                textChanged = s.toString().trim().length() != 0;
                if(textChanged){
                    displaySendButton();
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not used
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("type", IntentType.MANAGED_DISCUSSION_RESULT_TYPE);
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

            public void sendStartDiscussion(View v) {
                isStartActive = false;
                startDisButton.setEnabled(isStartActive);
                isEndActive = true;
                endDisButton.setEnabled(isEndActive);
                silenceButton.setEnabled(true);
                timeUpdateButton.setEnabled(true);
                sendTopicButton.setEnabled(true);
                pauseButton.setEnabled(true);

                startDisButton.setVisibility(View.GONE);
                endDisButton.setVisibility(View.VISIBLE);

                new SendNewDiscussionTask(viewModel.getSocket(), discussion.getTitle(), discussion.getTime(), discussion.getMemberList()).execute();
                Toast.makeText(getApplicationContext(), "Start Disscusion sended...", Toast.LENGTH_LONG).show();

            }

            public void sendEndDiscussion(View v) {
                new SendEndDiscussionTask(viewModel.getSocket()).execute();
                Toast.makeText(getApplicationContext(), "End Disscusion sended...", Toast.LENGTH_LONG).show();

                Intent returnIntent = new Intent();
                returnIntent.putExtra("type", IntentType.MANAGED_DISCUSSION_RESULT_TYPE);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }

            public void sendRemainingTime(View v) {
                new SendRemainingTimeTask(viewModel.getSocket(),"Noch 10 Minuten!").execute();
                Toast.makeText(getApplicationContext(), "Remaining Time sended...", Toast.LENGTH_LONG).show();
            }

            public void sendStartPause(View v) {
            if(isPauseActive == false)
            {
                isPauseActive = true;
                pauseButton.setImageResource(R.mipmap.endpauseic);
                pauseText.setText("Pause beenden");

                new SendStartPauseTask(viewModel.getSocket()).execute();
                Toast.makeText(getApplicationContext(), "Start Pause sended...", Toast.LENGTH_LONG).show();
            }else
                {
                    new SendEndPauseTask(viewModel.getSocket()).execute();
                    Toast.makeText(getApplicationContext(), "End Pause sended...", Toast.LENGTH_LONG).show();
                    pauseButton.setImageResource(R.mipmap.pause_ic);
                    isPauseActive = false;
                    pauseText.setText("Pause beginnen");
            }
            }


            public void sendTopic(View v) {
                message = topicInput.getText().toString();
                new SendTopicTask(viewModel.getSocket(),message).execute();
                Toast.makeText(getApplicationContext(), "Topic with Message: " + message + " sendet...", Toast.LENGTH_LONG).show();
            }

            public void sendSilence(View v) {
                new SendSilenceTask(viewModel.getSocket()).execute();
                Toast.makeText(getApplicationContext(), "Silence sended...", Toast.LENGTH_LONG).show();
            }

            public void  displaySendButton() {
                sendTopicButton.setVisibility(View.VISIBLE);
                sendTopicButton.setEnabled(true);
            }



    }
