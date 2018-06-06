package research.dresden.htw.moderationapp.activities.members;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import research.dresden.htw.moderationapp.R;
import research.dresden.htw.moderationapp.loader.MemberListViewAdapter;
import research.dresden.htw.moderationapp.manager.MemberManager;
import research.dresden.htw.moderationapp.model.AppDataViewModel;
import research.dresden.htw.moderationapp.model.IntentType;
import research.dresden.htw.moderationapp.model.Member;

public class MemberAdministrationActivity extends AppCompatActivity {

    private Boolean isAddActive = true;
    private Boolean isEditActive = false;
    private Boolean isDeleteActive = false;

    private FloatingActionButton addButton;
    private FloatingActionButton editButton;
    private FloatingActionButton deleteButton;

    private ListView member_list_view;
    private ListAdapter memberListAdapter;

    private AppDataViewModel dataViewModel;
    private List<Integer> selectedItemPositionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_administration);

        final MutableLiveData<ArrayList<Member>> memberListData = AppDataViewModel.getInstance().getMemberList();
        ArrayList<Member> memberList = memberListData.getValue();
        memberListAdapter = new MemberListViewAdapter(this, memberList);
        member_list_view = findViewById(R.id.member_list_view);
        member_list_view.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        member_list_view.setAdapter(memberListAdapter);
        addButton = findViewById(R.id.fab_action_add);
        addButton.setEnabled(isAddActive);
        editButton = findViewById(R.id.fab_action_edit);
        editButton.setEnabled(isEditActive);
        deleteButton = findViewById(R.id.fab_action_delete);
        deleteButton.setEnabled(isDeleteActive);
        dataViewModel = AppDataViewModel.getInstance();

        member_list_view.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        adjustRowItemRowColor(parent, view, position);
                        if(selectedItemPositionList.size() == 1){
                            Member selectedMember = (Member) memberListAdapter.getItem(position);
                            dataViewModel.setLastSelectedMember(selectedMember);
                        }
                        updateButtons();
                    }
                }
        );

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAddActive){
                    startActivityForResult(new Intent(getBaseContext(), AddMemberActivity.class), 1);
                }
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEditActive){
                    if(selectedItemPositionList.size() == 1){
                        startActivityForResult(new Intent(getBaseContext(), EditMemberActivity.class), 1);
                    }
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = selectedItemPositionList.size();
                if(isDeleteActive){
                    if(!selectedItemPositionList.isEmpty()) {
                        final int finalSize = size;
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        Member selectedMember = null;
                                        for ( Integer selectedItemPosition : selectedItemPositionList) {
                                            selectedMember = (Member) memberListAdapter.getItem(selectedItemPosition);
                                            ArrayList<Member> memberList = dataViewModel.getMemberList().getValue();
                                            if(memberList != null) {
                                                for (int i = 0; i < memberList.size(); i++) {
                                                    if (memberList.get(i).getId() == selectedMember.getId()) {
                                                        memberList.remove(i);
                                                    }
                                                }
                                                dataViewModel.setMemberList(memberList);
                                                MemberManager memberManager = MemberManager.getInstance();
                                                memberManager.writeToJSONFile(getApplicationContext(), memberList);
                                            }
                                        }
                                        if(finalSize > 1)
                                            Toast.makeText(MemberAdministrationActivity.this, getString(R.string.delete_toast_members), Toast.LENGTH_LONG).show();
                                        else
                                            if(selectedMember != null && selectedMember.getName() != null) {
                                                Toast.makeText(MemberAdministrationActivity.this, getString(R.string.delete_toast_member, selectedMember.getName()), Toast.LENGTH_LONG).show();
                                            }
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
                ((BaseAdapter) member_list_view.getAdapter()).notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(data != null && data.getExtras() != null && data.getExtras().getString("type") != null){
            String resultType = data.getExtras().getString("type");
            if (resultType != null && requestCode == 1) {
                if (resultCode == Activity.RESULT_CANCELED) {
                    if (resultType.equals(IntentType.ADD_RESULT_TYPE)) {
                        Toast.makeText(MemberAdministrationActivity.this, getString(R.string.canceled_add_toast_member), Toast.LENGTH_LONG).show();
                    } else if (resultType.equals(IntentType.EDIT_RESULT_TYPE)) {
                        Toast.makeText(MemberAdministrationActivity.this, getString(R.string.canceled_edit_toast_member), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    private void updateButtons() {
        switch(selectedItemPositionList.size()) {
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
        addButton = findViewById(R.id.fab_action_add);
        addButton.setEnabled(isAddActive);
        editButton = findViewById(R.id.fab_action_edit);
        editButton.setEnabled(isEditActive);
        deleteButton = findViewById(R.id.fab_action_delete);
        deleteButton.setEnabled(isDeleteActive);
    }

    private void adjustRowItemRowColor(AdapterView<?> parent, View view, int position) {
        TextView idTextView = view.findViewById(R.id.id_text_view_member);
        TextView titleTextView = view.findViewById(R.id.title_text_view_member);
        TextView nameTextView = view.findViewById(R.id.name_text_view_member);
        TextView organisationTextView = view.findViewById(R.id.organisation_text_view_member);
        TextView roleTextView = view.findViewById(R.id.role_text_view_member);
        if (!selectedItemPositionList.contains(position)) {
            selectedItemPositionList.add(position);
            parent.getChildAt(position).setBackgroundColor(Color.parseColor("#4285f4"));
            idTextView.setTextColor(Color.parseColor("#FFFFFF"));
            titleTextView.setTextColor(Color.parseColor("#FFFFFF"));
            nameTextView.setTextColor(Color.parseColor("#FFFFFF"));
            organisationTextView.setTextColor(Color.parseColor("#FFFFFF"));
            roleTextView.setTextColor(Color.parseColor("#FFFFFF"));
        } else{
            selectedItemPositionList.remove(Integer.valueOf(position));
            parent.getChildAt(position).setBackgroundColor(Color.parseColor("#FFFFFF"));
            idTextView.setTextColor(Color.parseColor("#ff000000"));
            titleTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.darker_gray));
            nameTextView.setTextColor(Color.parseColor("#ff000000"));
            organisationTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.darker_gray));
            roleTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.darker_gray));
        }
    }
}
