package research.dresden.htw.moderationapp.activities.members;

import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import research.dresden.htw.moderationapp.R;
import research.dresden.htw.moderationapp.activities.discussion.AddMemberAdministrationActivity;
import research.dresden.htw.moderationapp.loader.MemberListViewAdapter;
import research.dresden.htw.moderationapp.model.AppDataViewModel;
import research.dresden.htw.moderationapp.model.Member;

public class MemberAdministrationActivity extends AppCompatActivity {

    Boolean isAddActive = true;
    Boolean isEditActive = false;
    Boolean isDeleteActive = false;

    FloatingActionButton addButton;
    FloatingActionButton editButton;
    FloatingActionButton deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_administration);

        MutableLiveData<ArrayList<Member>> memberListData = AppDataViewModel.getInstance().getMemberList();
        ArrayList<Member> memberList = memberListData.getValue();
        ListAdapter memberListAdapter = new MemberListViewAdapter(this, memberList);
        ListView member_list_view = findViewById(R.id.member_list_view);
        member_list_view.setAdapter(memberListAdapter);
        addButton = (FloatingActionButton)findViewById(R.id.fab_action_add);
        addButton.setEnabled(isAddActive);
        editButton = (FloatingActionButton)findViewById(R.id.fab_action_edit);
        editButton.setEnabled(isEditActive);
        deleteButton = (FloatingActionButton)findViewById(R.id.fab_action_delete);
        deleteButton.setEnabled(isDeleteActive);

        member_list_view.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Member selectedMember = (Member) parent.getItemAtPosition(position);
                        isEditActive = true;
                        isDeleteActive = true;
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
                if(isDeleteActive){

                }
            }
        });
    }
}
