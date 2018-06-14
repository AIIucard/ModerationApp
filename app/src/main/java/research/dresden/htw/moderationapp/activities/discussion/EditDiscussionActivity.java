package research.dresden.htw.moderationapp.activities.discussion;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import research.dresden.htw.moderationapp.R;
import research.dresden.htw.moderationapp.manager.DiscussionManager;
import research.dresden.htw.moderationapp.model.AppDataViewModel;
import research.dresden.htw.moderationapp.model.Discussion;
import research.dresden.htw.moderationapp.model.IntentType;
import research.dresden.htw.moderationapp.model.RequestCode;

public class EditDiscussionActivity extends AppCompatActivity {

    private Boolean isTitleChanged = false;
    private Boolean isTimeChanged = false;
    private Boolean isMemberChanged = false;
    private Boolean isPlaceChanged = false;

    private EditText titleEditText;
    private String oldTitle = "";
    private EditText timeEditText;
    private int oldTime = 0;

    private Button editDiscussionButton;

    private AppDataViewModel dataViewModel;
    private ArrayList<Discussion> discussionList;
    private Discussion discussionToEditTempObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_discussion);

        dataViewModel = AppDataViewModel.getInstance();

        final MutableLiveData<ArrayList<Discussion>> discussionListData = AppDataViewModel.getInstance().getDiscussionList();
        discussionList = discussionListData.getValue();

        Discussion lastSelectedDiscussion = dataViewModel.getLastSelectedDiscussion().getValue();
        if (dataViewModel.getDiscussionToEditTempObj().getValue() == null) {
            dataViewModel.setDiscussionToEditTempObj(new Discussion(lastSelectedDiscussion.getId(), lastSelectedDiscussion.getTitle(), lastSelectedDiscussion.getTime(), lastSelectedDiscussion.getMemberList()));
        }
        discussionToEditTempObj = dataViewModel.getDiscussionToEditTempObj().getValue();

        Button continueEditMemberButton = findViewById(R.id.button_continue_edit_member);
        editDiscussionButton = findViewById(R.id.button_edit_discussion);
        editDiscussionButton.setEnabled(false);

        titleEditText = findViewById(R.id.title_edit_text_view);
        timeEditText = findViewById(R.id.time_edit_text_view);

        initValues();

        titleEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                isTitleChanged = s.toString().trim().length() != 0 && !s.toString().trim().equals(oldTitle);
                updateEditButton();
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

        timeEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                isTimeChanged = s.toString().trim().length() != 0 && Integer.parseInt(s.toString().trim()) != oldTime;
                updateEditButton();
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

        editDiscussionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (discussionToEditTempObj != null) {
                    titleEditText = findViewById(R.id.title_edit_text_view);
                    timeEditText = findViewById(R.id.time_edit_text_view);
                    discussionToEditTempObj.setTitle(titleEditText.getText().toString());
                    discussionToEditTempObj.setTime(Integer.parseInt(timeEditText.getText().toString()));

                    for (Discussion currentDiscussion : discussionList) {
                        if (currentDiscussion.getId().equals(discussionToEditTempObj.getId())) {
                            currentDiscussion.setTitle(discussionToEditTempObj.getTitle());
                            currentDiscussion.setTime(discussionToEditTempObj.getTime());
                            currentDiscussion.setMemberList(discussionToEditTempObj.getMemberList());
                        }
                    }
                    dataViewModel.setDiscussionList(discussionList);
                    dataViewModel.setLastSelectedDiscussion(discussionToEditTempObj);
                    DiscussionManager discussionManager = DiscussionManager.getInstance();
                    discussionManager.writeToJSONFile(getApplicationContext(), dataViewModel.getDiscussionList().getValue());
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("type", IntentType.ADD_RESULT_TYPE);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                    Toast.makeText(EditDiscussionActivity.this, getString(R.string.edit_toast_discussion, discussionToEditTempObj.getId()), Toast.LENGTH_LONG).show();
                }
            }
        });

        continueEditMemberButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getBaseContext(), MemberAdministrationEditDiscussionActivity.class), RequestCode.MEMBER_ADMINISTRATION_DISCUSSION_CODE);
            }
        });
    }

    private void updateEditButton() {
        editDiscussionButton = findViewById(R.id.button_edit_discussion);
        if (isTitleChanged || isTimeChanged || isMemberChanged || isPlaceChanged) {
            editDiscussionButton.setEnabled(true);
        } else {
            editDiscussionButton.setEnabled(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null && data.getExtras() != null && data.getExtras().getString("type") != null) {
            String resultType = data.getExtras().getString("type");
            if (resultType != null && requestCode == RequestCode.MEMBER_ADMINISTRATION_DISCUSSION_CODE) {
                if (resultCode == Activity.RESULT_CANCELED) {
                    if (resultType.equals(IntentType.EDIT_RESULT_TYPE)) {
                        Toast.makeText(EditDiscussionActivity.this, getString(R.string.canceled_edit_member_toast_discussion), Toast.LENGTH_LONG).show();
                    }
                } else if (resultCode == Activity.RESULT_OK) {
                    if (resultType.equals(IntentType.EDIT_RESULT_TYPE)) {
                        isMemberChanged = data.getExtras().getBoolean("isMemberChanged");
                        isPlaceChanged = data.getExtras().getBoolean("isPlaceChanged");
                        updateEditButton();
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("type", IntentType.EDIT_RESULT_TYPE);
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    private void initValues() {
        if (discussionToEditTempObj != null) {
            oldTitle = discussionToEditTempObj.getTitle();
            oldTime = discussionToEditTempObj.getTime();
            if (titleEditText != null) titleEditText.setText(oldTitle);
            if (timeEditText != null) timeEditText.setText("" + oldTime);
        }
    }
}
