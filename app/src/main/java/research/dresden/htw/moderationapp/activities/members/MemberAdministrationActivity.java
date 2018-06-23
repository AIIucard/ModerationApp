package research.dresden.htw.moderationapp.activities.members;

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
import android.widget.ListView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import research.dresden.htw.moderationapp.R;
import research.dresden.htw.moderationapp.loader.MemberListViewAdapter;
import research.dresden.htw.moderationapp.manager.MemberManager;
import research.dresden.htw.moderationapp.model.AppDataViewModel;
import research.dresden.htw.moderationapp.model.IntentType;
import research.dresden.htw.moderationapp.model.Member;
import research.dresden.htw.moderationapp.model.RequestCode;

public class MemberAdministrationActivity extends AppCompatActivity {

    private Boolean isAddActive = true;
    private Boolean isEditActive = false;
    private Boolean isDeleteActive = false;

    private FloatingActionButton addButton;
    private FloatingActionButton editButton;
    private FloatingActionButton deleteButton;

    private MemberListViewAdapter memberListAdapter;

    private AppDataViewModel dataViewModel;
    private ListView memberListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_administration);

        dataViewModel = AppDataViewModel.getInstance();

        final MutableLiveData<ArrayList<Member>> memberListData = dataViewModel.getMemberList();
        final ArrayList<Member> memberList = memberListData.getValue();
        memberListAdapter = new MemberListViewAdapter(this, memberList);
        memberListView = findViewById(R.id.member_list_view);
        memberListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        memberListView.setAdapter(memberListAdapter);

        addButton = findViewById(R.id.fab_action_add_member);
        addButton.setEnabled(isAddActive);
        editButton = findViewById(R.id.fab_action_edit_member);
        editButton.setEnabled(isEditActive);
        deleteButton = findViewById(R.id.fab_action_delete_member);
        deleteButton.setEnabled(isDeleteActive);

        memberListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        memberListAdapter.handleSelectionMember(position);
                        if (memberListAdapter.getNumberOfSelectedItems() == 1) {
                            Member selectedMember = memberListAdapter.getItem(position);
                            dataViewModel.setLastSelectedMember(selectedMember);
                        }
                        memberListAdapter.notifyDataSetChanged();
                        updateButtons();
                    }
                }
        );

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAddActive){
                    startActivityForResult(new Intent(getBaseContext(), AddMemberActivity.class), RequestCode.MEMBER_ADMINISTRATION_CODE);
                }
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEditActive){
                    if (memberListAdapter.getNumberOfSelectedItems() == 1) {
                        startActivityForResult(new Intent(getBaseContext(), EditMemberActivity.class), RequestCode.MEMBER_ADMINISTRATION_CODE);
                    }
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = memberListAdapter.getNumberOfSelectedItems();
                if(isDeleteActive){
                    if (size != 0) {
                        final int finalSize = size;
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        Member selectedMember = null;
                                        for (int itemPosition = 0; itemPosition < memberListAdapter.getSelectedItemsList().size(); ++itemPosition) {
                                            if (memberListAdapter.getSelectedItemsList().get(itemPosition)) {
                                                boolean isDeleted = false;
                                                selectedMember = memberListAdapter.getItem(itemPosition);
                                                ArrayList<Member> memberList = dataViewModel.getMemberList().getValue();
                                                if (memberList != null) {
                                                    for (int j = 0; j < memberList.size(); j++) {
                                                        if (selectedMember != null && memberList.get(j).equals(selectedMember)) {
                                                            memberList.remove(j);
                                                            isDeleted = true;
                                                        }
                                                    }
                                                    dataViewModel.setMemberList(memberList);
                                                    MemberManager memberManager = MemberManager.getInstance();
                                                    memberManager.writeToJSONFile(getApplicationContext(), memberList);
                                                }
                                                if (isDeleted) {
                                                    memberListAdapter.handleRemoveMember(itemPosition);
                                                    memberListAdapter.notifyDataSetChanged();
                                                }
                                            }
                                        }
                                        if(finalSize > 1)
                                            Toast.makeText(MemberAdministrationActivity.this, getString(R.string.delete_toast_members), Toast.LENGTH_LONG).show();
                                        else if (selectedMember != null && selectedMember.getName() != null) {
                                                Toast.makeText(MemberAdministrationActivity.this, getString(R.string.delete_toast_member, selectedMember.getName()), Toast.LENGTH_LONG).show();
                                            }
                                        updateButtons();
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //No button clicked
                                        break;
                                }
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(MemberAdministrationActivity.this);
                        if(finalSize > 1) {
                            builder.setMessage(getString(R.string.delete_question_members)).setPositiveButton("Ja", dialogClickListener)
                                    .setNegativeButton("Nein", dialogClickListener).show();
                        } else {
                            builder.setMessage(getString(R.string.delete_question_member)).setPositiveButton("Ja", dialogClickListener)
                                    .setNegativeButton("Nein", dialogClickListener).show();
                        }
                    }
                }
            }
        });

        dataViewModel.getMemberList().observe(this, new Observer<ArrayList<Member>>() {

            @Override
            public void onChanged(@Nullable ArrayList<Member> members) {
                ((BaseAdapter) memberListView.getAdapter()).notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(data != null && data.getExtras() != null && data.getExtras().getString("type") != null){
            String resultType = data.getExtras().getString("type");
            if (resultType != null && requestCode == RequestCode.MEMBER_ADMINISTRATION_CODE) {
                if (resultCode == Activity.RESULT_CANCELED) {
                    if (resultType.equals(IntentType.ADD_RESULT_TYPE)) {
                        Toast.makeText(MemberAdministrationActivity.this, getString(R.string.canceled_add_toast_member), Toast.LENGTH_LONG).show();
                    } else if (resultType.equals(IntentType.EDIT_RESULT_TYPE)) {
                        Toast.makeText(MemberAdministrationActivity.this, getString(R.string.canceled_edit_toast_member), Toast.LENGTH_LONG).show();
                    }
                } else if (resultCode == Activity.RESULT_OK) {
                    if (resultType.equals(IntentType.ADD_RESULT_TYPE)) {
                        memberListAdapter.handleAddMember();
                    }
                }
            }
        }
    }

    private void updateButtons() {
        switch (memberListAdapter.getNumberOfSelectedItems()) {
            case 0:
                isAddActive = true;
                isEditActive = false;
                isDeleteActive = false;
                break;

            case 1:
                isEditActive = true;
                isDeleteActive = true;
                break;

            default:
                isEditActive = false;
                isDeleteActive = true;
                break;
        }
        addButton = findViewById(R.id.fab_action_add_member);
        addButton.setEnabled(isAddActive);
        editButton = findViewById(R.id.fab_action_edit_member);
        editButton.setEnabled(isEditActive);
        deleteButton = findViewById(R.id.fab_action_delete_member);
        deleteButton.setEnabled(isDeleteActive);
    }
}
