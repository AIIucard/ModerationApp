package research.dresden.htw.moderationapp.activities.discussion;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import research.dresden.htw.moderationapp.R;
import research.dresden.htw.moderationapp.loader.MemberListViewAdapter;
import research.dresden.htw.moderationapp.model.AppDataViewModel;
import research.dresden.htw.moderationapp.model.IntentType;
import research.dresden.htw.moderationapp.model.Member;

public class AddExistingMemberActivity extends AppCompatActivity {

    private Boolean isAddMemberActive = false;
    private Button addMemberButton;
    private MemberListViewAdapter existingMemberListAdapter;
    private AppDataViewModel dataViewModel;
    private ArrayList<Member> selectedMembersForDiscussionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exsisting_member);

        dataViewModel = AppDataViewModel.getInstance();

        final MutableLiveData<ArrayList<Member>> selectedMembersForDiscussionListData = dataViewModel.getSelectedMembersForDiscussionList();
        selectedMembersForDiscussionList = selectedMembersForDiscussionListData.getValue();
        final MutableLiveData<ArrayList<Member>> membersListData = dataViewModel.getMemberList();
        ArrayList<Member> membersList = membersListData.getValue();

        ArrayList<Member> existingMemberList = new ArrayList<>(membersList != null ? membersList : new ArrayList<Member>());
        if (selectedMembersForDiscussionList != null) {
            for (Member selectedMember : selectedMembersForDiscussionList) {
                existingMemberList.remove(selectedMember);
            }
        }
        existingMemberListAdapter = new MemberListViewAdapter(this, existingMemberList);
        ListView existingMemberListView = findViewById(R.id.existing_member_list_view);
        existingMemberListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        existingMemberListView.setAdapter(existingMemberListAdapter);

        addMemberButton = findViewById(R.id.button_add_existing_member);
        addMemberButton.setEnabled(isAddMemberActive);

        existingMemberListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        existingMemberListAdapter.handleSelectionMember(position);
                        existingMemberListAdapter.notifyDataSetChanged();
                        updateButtons();
                    }
                }
        );

        addMemberButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isAddMemberActive) {
                    if (existingMemberListAdapter.getNumberOfSelectedItems() > 0) {
                        for (int itemPosition = 0; itemPosition < existingMemberListAdapter.getNumberOfSelectedItems(); ++itemPosition) {
                            if (existingMemberListAdapter.getSelectedItemsList().get(itemPosition)) {
                                Member selectedMember = existingMemberListAdapter.getItem(itemPosition);
                                selectedMembersForDiscussionList.add(selectedMember);
                                dataViewModel.setSelectedMembersForDiscussionList(selectedMembersForDiscussionList);
                            }
                        }
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("type", IntentType.ADD_RESULT_TYPE);
                        returnIntent.putExtra("number", existingMemberListAdapter.getNumberOfSelectedItems());
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                        Toast.makeText(AddExistingMemberActivity.this, getString(R.string.add_existing_member_toast_discussion, existingMemberListAdapter.getNumberOfSelectedItems()), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void updateButtons() {
        switch (existingMemberListAdapter.getNumberOfSelectedItems()) {
            case 0:
                isAddMemberActive = false;
                break;

            default:
                isAddMemberActive = true;
                break;
        }
        addMemberButton = findViewById(R.id.button_add_existing_member);
        addMemberButton.setEnabled(isAddMemberActive);
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("type", IntentType.ADD_RESULT_TYPE);
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
}
