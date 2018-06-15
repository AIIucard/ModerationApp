package research.dresden.htw.moderationapp.activities.discussion;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import research.dresden.htw.moderationapp.R;
import research.dresden.htw.moderationapp.loader.MemberListViewAdapter;
import research.dresden.htw.moderationapp.model.AppDataViewModel;
import research.dresden.htw.moderationapp.model.IntentType;
import research.dresden.htw.moderationapp.model.ItemPosition;
import research.dresden.htw.moderationapp.model.Member;

public class AddExistingMemberActivity extends AppCompatActivity {

    private final List<ItemPosition> selectedItemPositionList = new ArrayList<>();
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
                        adjustRowItemRowColor(parent, view, position);
                        updateButtons();
                    }
                }
        );

        addMemberButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isAddMemberActive) {
                    if (selectedItemPositionList.size() > 0) {
                        for (ItemPosition itemPosition : selectedItemPositionList) {
                            Member selectedMember = (Member) existingMemberListAdapter.getItem(itemPosition.getPosition());
                            selectedMembersForDiscussionList.add(selectedMember);
                            dataViewModel.setSelectedMembersForDiscussionList(selectedMembersForDiscussionList);
                        }
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("type", IntentType.ADD_RESULT_TYPE);
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                        Toast.makeText(AddExistingMemberActivity.this, getString(R.string.add_existing_member_toast_discussion, selectedItemPositionList.size()), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void updateButtons() {
        switch (selectedItemPositionList.size()) {
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

    private void adjustRowItemRowColor(AdapterView<?> parent, View view, int position) {
        TextView idTextView = view.findViewById(R.id.id_text_view_member);
        TextView titleTextView = view.findViewById(R.id.title_text_view_member);
        TextView nameTextView = view.findViewById(R.id.name_text_view_member);
        TextView organisationTextView = view.findViewById(R.id.organisation_text_view_member);
        TextView roleTextView = view.findViewById(R.id.role_text_view_member);
        if (!selectedItemPositionList.contains(new ItemPosition(position))) {
            selectedItemPositionList.add(new ItemPosition(position));
            parent.getChildAt(position).setBackgroundColor(Color.parseColor("#4285f4"));
            idTextView.setTextColor(Color.parseColor("#FFFFFF"));
            titleTextView.setTextColor(Color.parseColor("#FFFFFF"));
            nameTextView.setTextColor(Color.parseColor("#FFFFFF"));
            organisationTextView.setTextColor(Color.parseColor("#FFFFFF"));
            roleTextView.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            selectedItemPositionList.remove(new ItemPosition(position));
            parent.getChildAt(position).setBackgroundColor(Color.parseColor("#FFFFFF"));
            idTextView.setTextColor(Color.parseColor("#ff000000"));
            titleTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.darker_gray));
            nameTextView.setTextColor(Color.parseColor("#ff000000"));
            organisationTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.darker_gray));
            roleTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.darker_gray));
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
