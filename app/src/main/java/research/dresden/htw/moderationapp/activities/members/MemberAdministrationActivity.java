package research.dresden.htw.moderationapp.activities.members;

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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
import research.dresden.htw.moderationapp.model.Member;

public class MemberAdministrationActivity extends AppCompatActivity {

    Boolean isAddActive = true;
    Boolean isEditActive = false;
    Boolean isDeleteActive = false;

    FloatingActionButton addButton;
    FloatingActionButton editButton;
    FloatingActionButton deleteButton;

    ListView member_list_view;
    ListAdapter memberListAdapter;

    AppDataViewModel dataViewModel;
    List<Integer> selectedItemPositionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_administration);

        MutableLiveData<ArrayList<Member>> memberListData = AppDataViewModel.getInstance().getMemberList();
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
                        updateButtons();
                    }
                }
        );

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAddActive){
                    startActivity(new Intent(getBaseContext(), AddMemberActivity.class));
                }
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEditActive){
                    startActivity(new Intent(getBaseContext(), EditMemberActivity.class));
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = -1;
                size = selectedItemPositionList.size();
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
                                            selectedMember = (Member) memberListAdapter.getItem(selectedItemPosition.intValue());
                                            ArrayList<Member> memberList = dataViewModel.getMemberList().getValue();
                                            for (int i = 0; i < memberList.size(); i++) {
                                                if(memberList.get(i).getId() == selectedMember.getId()){
                                                    memberList.remove(i);
                                                }
                                            }
                                            dataViewModel.setMemberList(memberList);
                                            MemberManager memberManager = MemberManager.getInstance();
                                            memberManager.writeToJSONFile(getApplicationContext(), memberList);
                                        }
                                        if(finalSize > 1) {
                                            Toast.makeText(MemberAdministrationActivity.this, getString(R.string.delete_toast_members), Toast.LENGTH_LONG);
                                        } else {
                                            Toast.makeText(MemberAdministrationActivity.this, getString(R.string.delete_toast_member, selectedMember.getName()) , Toast.LENGTH_LONG);
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

    private void updateButtons() {
        if(selectedItemPositionList.size() == 0) {
            isAddActive = true;
            isEditActive = false;
            isDeleteActive = false;
        } else if(selectedItemPositionList.size() == 1){
            isEditActive = true;
            isDeleteActive = true;
        } else {
            isEditActive = false;
            isDeleteActive = true;
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
