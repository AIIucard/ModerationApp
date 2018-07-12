package research.dresden.htw.moderationapp.activities.discussion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import research.dresden.htw.moderationapp.R;
import research.dresden.htw.moderationapp.model.AppDataViewModel;
import research.dresden.htw.moderationapp.model.Discussion;
import research.dresden.htw.moderationapp.model.IntentType;
import research.dresden.htw.moderationapp.tasks.SendEndDiscussionTask;
import research.dresden.htw.moderationapp.tasks.SendEndPauseTask;
import research.dresden.htw.moderationapp.tasks.SendNewDiscussionTask;
import research.dresden.htw.moderationapp.tasks.SendRemainingTimeTask;
import research.dresden.htw.moderationapp.tasks.SendSilenceTask;
import research.dresden.htw.moderationapp.tasks.SendStartPauseTask;
import research.dresden.htw.moderationapp.tasks.SendTopicTask;

public class DiscussionControlCenterActivity extends AppCompatActivity {

    private EditText topicInput;
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
    private TextView remainingTimeText;

    private TextView labelRemainingTime;

    CountDownTimer countRemainingTime = null;
    private Discussion lastSelectedDscussion;
    private int remainingTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_control_center);
        viewModel = AppDataViewModel.getInstance();
        AppDataViewModel dataViewModel = AppDataViewModel.getInstance();

        lastSelectedDscussion = dataViewModel.getLastSelectedDiscussion().getValue();
        if (lastSelectedDscussion != null) {
            remainingTime = lastSelectedDscussion.getTime();
        }

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
        labelRemainingTime = findViewById(R.id.label_remaining_time);
        labelRemainingTime.setVisibility(View.GONE);
        remainingTimeText = findViewById(R.id.remaining_time_text_view);
        remainingTimeText.setVisibility(View.GONE);

        pauseText = findViewById(R.id.textview_pause);

        topicInput = findViewById(R.id.topicInput);
        //Dynamisch eingeblendeter SendButton on Textchange
        topicInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                textChanged = s.toString().trim().length() != 0;
                if(textChanged){
                    displaySendButton();
                }else {
                    goneSendButton();
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not Used
            }
        });

    }

    public void sendStartDiscussion(View v) {
        isStartActive = false;
        startDisButton.setEnabled(false);
        isEndActive = true;
        endDisButton.setEnabled(true);
        silenceButton.setEnabled(true);
        timeUpdateButton.setEnabled(true);
        sendTopicButton.setEnabled(true);
        pauseButton.setEnabled(true);

        startDisButton.setVisibility(View.GONE);
        endDisButton.setVisibility(View.VISIBLE);

        labelRemainingTime.setVisibility(View.VISIBLE);
        remainingTimeText.setVisibility(View.VISIBLE);

        new SendNewDiscussionTask(viewModel.getSocket(), lastSelectedDscussion.getTitle(), lastSelectedDscussion.getTime(), lastSelectedDscussion.getMemberList()).execute();
        Toast.makeText(getApplicationContext(), "Start Disscusion sended...", Toast.LENGTH_LONG).show();

        countRemainingTime = new CountDownTimer(remainingTime * 60000, 60000) {

            public void onTick(long millisUntilFinished) {
                remainingTime = ((int) (millisUntilFinished) / 60000);
                remainingTimeText.setText(""+ remainingTime + " Minuten");
            }

            public void onFinish() {
                remainingTime = 0;
            }
        }.start();
    }

    public void sendEndDiscussion(View v) {
        new SendEndDiscussionTask(viewModel.getSocket()).execute();
        Toast.makeText(getApplicationContext(), "End Disscusion sended...", Toast.LENGTH_LONG).show();

        countRemainingTime.cancel();

        Intent returnIntent = new Intent();
        returnIntent.putExtra("type", IntentType.MANAGED_DISCUSSION_RESULT_TYPE);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void sendRemainingTime(View v) {
        String remainingTimeString = "";
        if (remainingTime == 0) {
            remainingTimeString = "Keine Zeit mehr vorhanden.";
        } else if (remainingTime == 1) {
            remainingTimeString = "Noch 1 Minute verbleibend!";
        } else {
            remainingTimeString = "Noch " + remainingTime + " Minuten verbleibend!";
        }
        new SendRemainingTimeTask(viewModel.getSocket(), remainingTimeString).execute();
        Toast.makeText(getApplicationContext(), "Remaining Time sended " + remainingTimeString + "...", Toast.LENGTH_LONG).show();
    }

    public void sendStartPause(View v) {
        if (!isPauseActive) {
            isPauseActive = true;
            pauseButton.setImageResource(R.mipmap.endpause_ic);
            pauseText.setText(getString(R.string.end_pause_discussion));

            new SendStartPauseTask(viewModel.getSocket()).execute();
            Toast.makeText(getApplicationContext(), "Start Pause sended...", Toast.LENGTH_LONG).show();
        } else {
            new SendEndPauseTask(viewModel.getSocket()).execute();
            Toast.makeText(getApplicationContext(), "End Pause sended...", Toast.LENGTH_LONG).show();
            pauseButton.setImageResource(R.mipmap.pause_ic);
            isPauseActive = false;
            pauseText.setText(getString(R.string.start_pause_discussion));
        }
    }

    public void sendTopic(View v) {
        String message = topicInput.getText().toString();

        if(! message.equals(""))
        {
            new SendTopicTask(viewModel.getSocket(), message).execute();
            Toast.makeText(getApplicationContext(), "Topic with Message: " + message + " sendet...", Toast.LENGTH_LONG).show();
            topicInput.setText("");
        } else {
            Toast.makeText(getApplicationContext(), "Bitte geben Sie ein Thema ", Toast.LENGTH_LONG).show();
        }


    }

    public void sendSilence(View v) {
        new SendSilenceTask(viewModel.getSocket()).execute();
        Toast.makeText(getApplicationContext(), "Silence sended...", Toast.LENGTH_LONG).show();
    }

    public void displaySendButton() {
        sendTopicButton.setVisibility(View.VISIBLE);
        sendTopicButton.setEnabled(true);
    }

    public void goneSendButton() {
        sendTopicButton.setVisibility(View.GONE);
        sendTopicButton.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("type", IntentType.MANAGED_DISCUSSION_RESULT_TYPE);
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
}
