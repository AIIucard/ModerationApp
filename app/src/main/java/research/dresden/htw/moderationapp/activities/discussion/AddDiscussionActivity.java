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
import research.dresden.htw.moderationapp.model.Member;
import research.dresden.htw.moderationapp.model.RequestCode;
import research.dresden.htw.moderationapp.utils.AppUtils;

public class AddDiscussionActivity extends AppCompatActivity {

    private Button continueToAddMemberButton;

    private Boolean isTitleSet = false;
    private Boolean isTimeSet = false;

    private EditText titleEditText;
    private EditText timeEditText;

    private AppDataViewModel dataViewModel;
    private ArrayList<Discussion> discussionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_discussion);

        dataViewModel = AppDataViewModel.getInstance();

        final MutableLiveData<ArrayList<Discussion>> discussionListData = dataViewModel.getDiscussionList();
        discussionList = discussionListData.getValue();

        continueToAddMemberButton = findViewById(R.id.button_continue_add_member);
        continueToAddMemberButton.setEnabled(false);

        titleEditText = findViewById(R.id.title_edit_text_view);
        timeEditText = findViewById(R.id.time_edit_text_view);

        titleEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                isTitleSet = s.toString().trim().length() != 0;
                updateContinueAddMemberButton();
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

                isTimeSet = s.toString().trim().length() != 0;
                updateContinueAddMemberButton();
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

        continueToAddMemberButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getBaseContext(), MemberAdministrationDiscussionActivity.class), RequestCode.MEMBER_ADMINISTRATION_DISCUSSION_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null && data.getExtras() != null && data.getExtras().getString("type") != null) {
            String resultType = data.getExtras().getString("type");
            if (resultType != null && requestCode == RequestCode.MEMBER_ADMINISTRATION_DISCUSSION_CODE) {
                if (resultCode == Activity.RESULT_CANCELED) {
                    if (resultType.equals(IntentType.ADD_RESULT_TYPE)) {
                        Toast.makeText(AddDiscussionActivity.this, getString(R.string.canceled_add_member_toast_discussion), Toast.LENGTH_LONG).show();
                    }
                } else if (resultCode == Activity.RESULT_OK) {
                    if (resultType.equals(IntentType.ADD_RESULT_TYPE)) {
                        ArrayList<Member> selectedMembersForDiscussion = dataViewModel.getSelectedMembersForDiscussionList().getValue();
                        if (selectedMembersForDiscussion != null && discussionList != null) {
                            titleEditText = findViewById(R.id.title_edit_text_view);
                            timeEditText = findViewById(R.id.time_edit_text_view);

                            // TODO: Check if discussion exists already
                            Discussion newDiscussion = new Discussion(AppUtils.getNextDiscussionId(discussionList), titleEditText.getText().toString(), Integer.parseInt(timeEditText.getText().toString()), selectedMembersForDiscussion);
                            discussionList.add(newDiscussion);
                            dataViewModel.setDiscussionList(discussionList);
                            DiscussionManager discussionManager = DiscussionManager.getInstance();
                            discussionManager.writeToJSONFile(getApplicationContext(), dataViewModel.getDiscussionList().getValue());
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("type", IntentType.ADD_RESULT_TYPE);
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();
                            Toast.makeText(AddDiscussionActivity.this, getString(R.string.add_toast_discussion, newDiscussion.getTitle(), newDiscussion.getId()), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        }
    }

    private void updateContinueAddMemberButton() {
        continueToAddMemberButton = findViewById(R.id.button_continue_add_member);
        if (isTitleSet && isTimeSet) {
            continueToAddMemberButton.setEnabled(true);
        } else {
            continueToAddMemberButton.setEnabled(false);
        }
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("type", IntentType.ADD_RESULT_TYPE);
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
}
