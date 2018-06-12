package research.dresden.htw.moderationapp.activities.discussion;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import research.dresden.htw.moderationapp.R;
import research.dresden.htw.moderationapp.loader.DiscussionListViewAdapter;
import research.dresden.htw.moderationapp.manager.DiscussionManager;
import research.dresden.htw.moderationapp.model.AppDataViewModel;
import research.dresden.htw.moderationapp.model.Discussion;
import research.dresden.htw.moderationapp.model.IntentType;
import research.dresden.htw.moderationapp.model.ItemPosition;
import research.dresden.htw.moderationapp.model.Member;
import research.dresden.htw.moderationapp.model.RequestCode;

public class DiscussionAdministrationActivity extends AppCompatActivity {

    private Boolean isAddDiscussionActive = true;
    private Boolean isEditDiscussionActive = false;
    private Boolean isDeleteDiscussionActive = false;

    private FloatingActionButton addButton;
    private FloatingActionButton editButton;
    private FloatingActionButton deleteButton;

    private DiscussionListViewAdapter discussionListAdapter;

    private AppDataViewModel dataViewModel;
    private final List<ItemPosition> selectedItemPositionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_administration);

        dataViewModel = AppDataViewModel.getInstance();

        final MutableLiveData<ArrayList<Discussion>> discussionListData = dataViewModel.getDiscussionList();
        ArrayList<Discussion> discussionList = discussionListData.getValue();
        discussionListAdapter = new DiscussionListViewAdapter(this, discussionList);
        ExpandableListView discussionListView = findViewById(R.id.discussion_expandable_list_view);
        discussionListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        discussionListView.setAdapter(discussionListAdapter);

        addButton = findViewById(R.id.fab_action_add);
        addButton.setEnabled(isAddDiscussionActive);
        editButton = findViewById(R.id.fab_action_edit);
        editButton.setEnabled(isEditDiscussionActive);
        deleteButton = findViewById(R.id.fab_action_delete);
        deleteButton.setEnabled(isDeleteDiscussionActive);

        discussionListView.setOnGroupClickListener(
            new ExpandableListView.OnGroupClickListener() {

                @Override
                public boolean onGroupClick(ExpandableListView parent, View view, int groupPosition, long id) {
                    if (!selectedItemPositionList.contains(new ItemPosition(groupPosition))) {
                        selectedItemPositionList.add(new ItemPosition(groupPosition));
                        parent.expandGroup(groupPosition);
                    } else {
                        selectedItemPositionList.remove(new ItemPosition(groupPosition));
                        parent.collapseGroup(groupPosition);
                    }
                    if(selectedItemPositionList.size() == 1){
                        Discussion selectedDiscussion = (Discussion) discussionListAdapter.getGroup(groupPosition);
                        dataViewModel.setLastSelectedDiscussion(selectedDiscussion);
                    }
                    updateButtons();
                    return true;
                }
        });

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isAddDiscussionActive) {
                    dataViewModel.setSelectedMembersForDiscussionList(new ArrayList<Member>());
                    startActivityForResult(new Intent(getBaseContext(), AddDiscussionActivity.class), RequestCode.DISCUSSION_ADMINISTRATION_CODE);
                }
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isEditDiscussionActive) {
                    if (selectedItemPositionList.size() == 1) {
                        startActivityForResult(new Intent(getBaseContext(), EditDiscussionActivity.class), RequestCode.DISCUSSION_ADMINISTRATION_CODE);
                    }
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int size = selectedItemPositionList.size();
                if (isDeleteDiscussionActive) {
                    if (!selectedItemPositionList.isEmpty()) {
                        final int finalSize = size;
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        Discussion selectedDiscussion = null;
                                        for (ItemPosition selectedItemPosition : selectedItemPositionList) {
                                            boolean isDeleted = false;
                                            selectedDiscussion = (Discussion) discussionListAdapter.getGroup(selectedItemPosition.getPosition());
                                            ArrayList<Discussion> discussionList = dataViewModel.getDiscussionList().getValue();
                                            if (discussionList != null) {
                                                for (int i = 0; i < discussionList.size(); i++) {
                                                    if (discussionList.get(i).getId().equals(selectedDiscussion.getId())) {
                                                        discussionList.remove(i);
                                                        isDeleted = true;
                                                    }
                                                }
                                                dataViewModel.setDiscussionList(discussionList);
                                                DiscussionManager discussionManager = DiscussionManager.getInstance();
                                                discussionManager.writeToJSONFile(getApplicationContext(), discussionList);
                                            }
                                            if (isDeleted) {
                                                selectedItemPositionList.remove(selectedItemPosition);
                                            }
                                        }
                                        if (finalSize > 1)
                                            Toast.makeText(DiscussionAdministrationActivity.this, getString(R.string.delete_toast_discussions), Toast.LENGTH_LONG).show();
                                        else if (selectedDiscussion != null && selectedDiscussion.getTitle() != null) {
                                            Toast.makeText(DiscussionAdministrationActivity.this, getString(R.string.delete_toast_discussion, selectedDiscussion.getTitle()), Toast.LENGTH_LONG).show();
                                        }
                                        updateButtons();
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //No button clicked
                                        break;
                                }
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(DiscussionAdministrationActivity.this);
                        if (finalSize > 1) {
                            builder.setMessage(getString(R.string.delete_question_discussions)).setPositiveButton("Ja", dialogClickListener)
                                    .setNegativeButton("Nein", dialogClickListener).show();
                        } else {
                            builder.setMessage(getString(R.string.delete_question_discussion)).setPositiveButton("Ja", dialogClickListener)
                                    .setNegativeButton("Nein", dialogClickListener).show();
                        }
                    }
                }
            }
        });

        dataViewModel.getDiscussionList().observe(this, new Observer<ArrayList<Discussion>>() {

            @Override
            public void onChanged(@Nullable ArrayList<Discussion> discussions) {
                discussionListAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null && data.getExtras() != null && data.getExtras().getString("type") != null) {
            String resultType = data.getExtras().getString("type");
            if (resultType != null && requestCode == RequestCode.DISCUSSION_ADMINISTRATION_CODE) {
                if (resultCode == Activity.RESULT_CANCELED) {
                    if (resultType.equals(IntentType.ADD_RESULT_TYPE)) {
                        Toast.makeText(DiscussionAdministrationActivity.this, getString(R.string.canceled_add_toast_discussion), Toast.LENGTH_LONG).show();
                    } else if (resultType.equals(IntentType.EDIT_RESULT_TYPE)) {
                        Toast.makeText(DiscussionAdministrationActivity.this, getString(R.string.canceled_edit_toast_discussion), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    private void updateButtons() {
        switch (selectedItemPositionList.size()) {
            case 0:
                isAddDiscussionActive = true;
                isEditDiscussionActive = false;
                isDeleteDiscussionActive = false;
                break;

            case 1:
                isEditDiscussionActive = true;
                isDeleteDiscussionActive = true;
                break;

            default:
                isEditDiscussionActive = false;
                isDeleteDiscussionActive = true;
                break;
        }
        addButton = findViewById(R.id.fab_action_add);
        addButton.setEnabled(isAddDiscussionActive);
        editButton = findViewById(R.id.fab_action_edit);
        editButton.setEnabled(isEditDiscussionActive);
        deleteButton = findViewById(R.id.fab_action_delete);
        deleteButton.setEnabled(isDeleteDiscussionActive);
    }
}
