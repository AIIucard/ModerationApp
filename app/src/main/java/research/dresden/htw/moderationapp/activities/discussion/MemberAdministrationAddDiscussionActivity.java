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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import research.dresden.htw.moderationapp.R;
import research.dresden.htw.moderationapp.loader.MemberListViewAdapter;
import research.dresden.htw.moderationapp.model.AppDataViewModel;
import research.dresden.htw.moderationapp.model.IntentType;
import research.dresden.htw.moderationapp.model.Member;
import research.dresden.htw.moderationapp.model.RequestCode;

public class MemberAdministrationAddDiscussionActivity extends AppCompatActivity {

    private Boolean isAddNewActive = true;
    private Boolean isAddExistingActive = true;
    private Boolean isRemoveActive = false;
    private FloatingActionButton addNewButton;
    private FloatingActionButton addExistingButton;
    private FloatingActionButton removeButton;
    private Button setPlacesButton;
    private ListView selectedMemberListView;
    private MemberListViewAdapter selectedMemberListAdapter;
    private AppDataViewModel dataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_administration_add_discussion);

        dataViewModel = AppDataViewModel.getInstance();

        final MutableLiveData<ArrayList<Member>> selectedMembersListData = dataViewModel.getSelectedMembersForDiscussionList();
        ArrayList<Member> selectedMembersList = selectedMembersListData.getValue();
        selectedMemberListAdapter = new MemberListViewAdapter(this, selectedMembersList);
        selectedMemberListView = findViewById(R.id.member_list_list_view);
        selectedMemberListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        selectedMemberListView.setAdapter(selectedMemberListAdapter);

        addNewButton = findViewById(R.id.fab_action_add_new_member);
        addNewButton.setEnabled(isAddNewActive);
        addExistingButton = findViewById(R.id.fab_action_add_existing_member);
        addExistingButton.setEnabled(isAddExistingActive);
        removeButton = findViewById(R.id.fab_action_remove_member);
        removeButton.setEnabled(isRemoveActive);
        setPlacesButton = findViewById(R.id.button_set_places);
        setPlacesButton.setEnabled(false);

        selectedMemberListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedMemberListAdapter.handleSelectionMember(position);
                        selectedMemberListAdapter.notifyDataSetChanged();
                        updateButtons();
                    }
                }
        );

        addNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAddNewActive) {
                    startActivityForResult(new Intent(getBaseContext(), AddNewMemberActivity.class), RequestCode.ADD_NEW_MEMBER_DISCUSSION_CODE);
                }
            }
        });

        addExistingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAddExistingActive) {
                    startActivityForResult(new Intent(getBaseContext(), AddExistingMemberActivity.class), RequestCode.ADD_EXISTING_MEMBER_DISCUSSION_CODE);
                }
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int size = selectedMemberListAdapter.getNumberOfSelectedItems();
                if (isRemoveActive) {
                    if (size != 0) {
                        final int finalSize = size;
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        Member selectedMember = null;
                                        for (int itemPosition = 0; itemPosition < selectedMemberListAdapter.getSelectedItemsList().size(); ++itemPosition) {
                                            if (selectedMemberListAdapter.getSelectedItemsList().get(itemPosition)) {
                                                boolean isRemoved = false;
                                                selectedMember = selectedMemberListAdapter.getItem(itemPosition);
                                                ArrayList<Member> selectedMemberList = dataViewModel.getSelectedMembersForDiscussionList().getValue();
                                                if (selectedMemberList != null) {
                                                    for (int i = 0; i < selectedMemberList.size(); i++) {
                                                        if (selectedMember != null && selectedMemberList.get(i).getId() == selectedMember.getId()) {
                                                            selectedMemberList.remove(i);
                                                            isRemoved = true;
                                                        }
                                                    }
                                                    dataViewModel.setSelectedMembersForDiscussionList(selectedMemberList);
                                                    if (isRemoved) {
                                                        selectedMemberListAdapter.handleRemoveMember(itemPosition);
                                                        selectedMemberListAdapter.notifyDataSetChanged();
                                                    }
                                                }
                                            }
                                        }
                                        if (finalSize > 1)
                                            Toast.makeText(MemberAdministrationAddDiscussionActivity.this, getString(R.string.remove_toast_members), Toast.LENGTH_LONG).show();
                                        else if (selectedMember != null && selectedMember.getName() != null) {
                                            Toast.makeText(MemberAdministrationAddDiscussionActivity.this, getString(R.string.remove_toast_member, selectedMember.getName()), Toast.LENGTH_LONG).show();
                                        }
                                        updateButtons();
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //No button clicked
                                        break;
                                }
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(MemberAdministrationAddDiscussionActivity.this);
                        if (finalSize > 1) {
                            builder.setMessage(getString(R.string.remove_question_members)).setPositiveButton("Ja", dialogClickListener)
                                    .setNegativeButton("Nein", dialogClickListener).show();
                        } else {
                            builder.setMessage(getString(R.string.remove_question_member)).setPositiveButton("Ja", dialogClickListener)
                                    .setNegativeButton("Nein", dialogClickListener).show();
                        }
                    }
                }
            }
        });

        setPlacesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (dataViewModel.getSelectedMembersForDiscussionList().getValue() != null && dataViewModel.getSelectedMembersForDiscussionList().getValue().size() > 0) {
                    startActivityForResult(new Intent(getBaseContext(), PlaceAssignmentAddDiscussionActivity.class), RequestCode.PLACE_ASSIGNMENT_DISCUSSION_CODE);
                }
            }
        });

        dataViewModel.getSelectedMembersForDiscussionList().observe(this, new Observer<ArrayList<Member>>() {

            @Override
            public void onChanged(@Nullable ArrayList<Member> members) {
                ((BaseAdapter) selectedMemberListView.getAdapter()).notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null && data.getExtras() != null && data.getExtras().getString("type") != null) {
            String resultType = data.getExtras().getString("type");
            if (resultType != null && requestCode == RequestCode.ADD_NEW_MEMBER_DISCUSSION_CODE) {
                if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(MemberAdministrationAddDiscussionActivity.this, getString(R.string.canceled_add_new_member_toast_discussion), Toast.LENGTH_LONG).show();
                } else if (resultCode == Activity.RESULT_OK) {
                    selectedMemberListAdapter.handleAddMember();
                }
            } else if (resultType != null && requestCode == RequestCode.ADD_EXISTING_MEMBER_DISCUSSION_CODE) {
                if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(MemberAdministrationAddDiscussionActivity.this, getString(R.string.canceled_add_existing_member_toast_discussion), Toast.LENGTH_LONG).show();
                } else if (resultCode == Activity.RESULT_OK) {
                    if (data.getExtras().getInt("number") != 0) {
                        for (int i = 0; i < data.getExtras().getInt("number"); ++i) {
                            selectedMemberListAdapter.handleAddMember();
                        }
                    }
                }
            } else if (resultType != null && requestCode == RequestCode.PLACE_ASSIGNMENT_DISCUSSION_CODE) {
                if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(MemberAdministrationAddDiscussionActivity.this, getString(R.string.canceled_place_assignement_toast_discussion), Toast.LENGTH_LONG).show();
                } else if (resultCode == Activity.RESULT_OK) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("type", IntentType.ADD_RESULT_TYPE);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            }
            if (dataViewModel.getSelectedMembersForDiscussionList().getValue() != null && dataViewModel.getSelectedMembersForDiscussionList().getValue().size() > 0) {
                setPlacesButton.setEnabled(true);
            } else {
                setPlacesButton.setEnabled(false);
            }
        }
    }

    private void updateButtons() {
        switch (selectedMemberListAdapter.getNumberOfSelectedItems()) {
            case 0:
                isAddNewActive = true;
                isAddExistingActive = true;
                isRemoveActive = false;
                break;

            default:
                isAddNewActive = true;
                isAddExistingActive = true;
                isRemoveActive = true;
                break;
        }
        addNewButton = findViewById(R.id.fab_action_add_new_member);
        addNewButton.setEnabled(isAddNewActive);
        addExistingButton = findViewById(R.id.fab_action_add_existing_member);
        addExistingButton.setEnabled(isAddExistingActive);
        removeButton = findViewById(R.id.fab_action_remove_member);
        removeButton.setEnabled(isRemoveActive);
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("type", IntentType.ADD_RESULT_TYPE);
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
}
