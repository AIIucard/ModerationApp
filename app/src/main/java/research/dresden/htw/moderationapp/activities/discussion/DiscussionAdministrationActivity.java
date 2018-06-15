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

import research.dresden.htw.moderationapp.R;
import research.dresden.htw.moderationapp.loader.DiscussionListViewAdapter;
import research.dresden.htw.moderationapp.manager.DiscussionManager;
import research.dresden.htw.moderationapp.model.AppDataViewModel;
import research.dresden.htw.moderationapp.model.Discussion;
import research.dresden.htw.moderationapp.model.IntentType;
import research.dresden.htw.moderationapp.model.Member;
import research.dresden.htw.moderationapp.model.RequestCode;

public class DiscussionAdministrationActivity extends AppCompatActivity {

    private Boolean isStartDiscussionActive = false;
    private Boolean isAddDiscussionActive = true;
    private Boolean isEditDiscussionActive = false;
    private Boolean isDeleteDiscussionActive = false;

    private FloatingActionButton startButton;
    private FloatingActionButton addButton;
    private FloatingActionButton editButton;
    private FloatingActionButton deleteButton;

    private DiscussionListViewAdapter discussionListAdapter;

    private AppDataViewModel dataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_administration);

        dataViewModel = AppDataViewModel.getInstance();

        final MutableLiveData<ArrayList<Discussion>> discussionListData = dataViewModel.getDiscussionList();
        ArrayList<Discussion> discussionList = discussionListData.getValue();
        if (discussionList != null) {
            discussionListAdapter = new DiscussionListViewAdapter(this, discussionList);
        }
        final ExpandableListView discussionListView = findViewById(R.id.discussion_expandable_list_view);
        discussionListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        discussionListView.setAdapter(discussionListAdapter);

        startButton = findViewById(R.id.fab_action_start);
        startButton.setEnabled(isStartDiscussionActive);
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
                    discussionListAdapter.handleSelectionDiscussion(groupPosition);
                    if (discussionListAdapter.getSelectedItemsList().get(groupPosition)) {
                        parent.expandGroup(groupPosition);
                    } else {
                        parent.collapseGroup(groupPosition);
                    }
                    if (discussionListAdapter.getSelectedItemsList().size() == 1) {
                        Discussion selectedDiscussion = (Discussion) discussionListAdapter.getGroup(groupPosition);
                        dataViewModel.setLastSelectedDiscussion(selectedDiscussion);
                    }
                    updateButtons();
                    discussionListAdapter.notifyDataSetChanged();
                    return true;
                }
        });

        startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isStartDiscussionActive) {
                    startActivityForResult(new Intent(getBaseContext(), DiscussionControlCenterActivity.class), RequestCode.DISCUSSION_ADMINISTRATION_CODE);
                }
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
                    if (discussionListAdapter.getSelectedItemsList().size() == 1) {
                        startActivityForResult(new Intent(getBaseContext(), EditDiscussionActivity.class), RequestCode.DISCUSSION_ADMINISTRATION_CODE);
                    }
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int size = discussionListAdapter.getSelectedItemsList().size();
                if (isDeleteDiscussionActive) {
                    if (size != 0) {
                        final int finalSize = size;
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        Discussion selectedDiscussion = null;
                                        for (int itemPosition = 0; itemPosition < discussionListAdapter.getSelectedItemsList().size(); ++itemPosition) {
                                            if (discussionListAdapter.getSelectedItemsList().get(itemPosition)) {
                                                boolean isDeleted = false;
                                                selectedDiscussion = (Discussion) discussionListAdapter.getGroup(itemPosition);
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
                                                    discussionListAdapter.handleRemoveDiscussion(itemPosition);
                                                    discussionListAdapter.notifyDataSetChanged();
                                                }
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
                } else if (resultCode == Activity.RESULT_OK) {
                    if (resultType.equals(IntentType.ADD_RESULT_TYPE)) {
                        discussionListAdapter.handleAddDiscussion();
                    }
                } else if (resultType.equals(IntentType.MANAGED_DISCUSSION_RESULT_TYPE)) {
                    Toast.makeText(DiscussionAdministrationActivity.this, getString(R.string.canceled_start_toast_discussion), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void updateButtons() {
        switch (discussionListAdapter.getNumberOfSelectedItems()) {
            case 0:
                isStartDiscussionActive = false;
                isAddDiscussionActive = true;
                isEditDiscussionActive = false;
                isDeleteDiscussionActive = false;
                break;

            case 1:
                isStartDiscussionActive = true;
                isEditDiscussionActive = true;
                isDeleteDiscussionActive = true;
                break;

            default:
                isStartDiscussionActive = false;
                isEditDiscussionActive = false;
                isDeleteDiscussionActive = true;
                break;
        }
        startButton = findViewById(R.id.fab_action_start);
        startButton.setEnabled(isStartDiscussionActive);
        addButton = findViewById(R.id.fab_action_add);
        addButton.setEnabled(isAddDiscussionActive);
        editButton = findViewById(R.id.fab_action_edit);
        editButton.setEnabled(isEditDiscussionActive);
        deleteButton = findViewById(R.id.fab_action_delete);
        deleteButton.setEnabled(isDeleteDiscussionActive);
    }
}
