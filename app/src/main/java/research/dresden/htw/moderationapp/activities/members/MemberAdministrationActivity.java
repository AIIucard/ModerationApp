package research.dresden.htw.moderationapp.activities.members;

import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

    List<Integer> selectedItemPositionList = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_administration);

        MutableLiveData<ArrayList<Member>> memberListData = AppDataViewModel.getInstance().getMemberList();
        ArrayList<Member> memberList = memberListData.getValue();
        ListAdapter memberListAdapter = new MemberListViewAdapter(this, memberList);
        ListView member_list_view = findViewById(R.id.member_list_view);
        member_list_view.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
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
                        adjustRowItemRowColor(parent, view, position);
                        updateButtons();
                    }
                }
        );

        member_list_view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    // TODO: Geht noch nicht
                    ListView memberListView = (ListView) view.findViewById(R.id.member_list_view);
                    for(int a = 0; a < memberListView.getChildCount(); a++)
                    {
                        memberListView.getChildAt(a).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    }

                    // Set Text Color to white
                    TextView idTextView = (TextView) view.findViewById(R.id.id_text_view_member);
                    TextView titleTextView = (TextView) view.findViewById(R.id.title_text_view_member);
                    TextView nameTextView = (TextView) view.findViewById(R.id.name_text_view_member);
                    TextView organisationTextView = (TextView) view.findViewById(R.id.organisation_text_view_member);
                    TextView roleTextView = (TextView) view.findViewById(R.id.role_text_view_member);

                    idTextView.setTextColor(Color.parseColor("#FFFFFF"));
                    titleTextView.setTextColor(Color.parseColor("#FFFFFF"));
                    nameTextView.setTextColor(Color.parseColor("#FFFFFF"));
                    organisationTextView.setTextColor(Color.parseColor("#FFFFFF"));
                    roleTextView.setTextColor(Color.parseColor("#FFFFFF"));
                }
            }
        });

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

    private void updateButtons() {
        if(selectedItemPositionList.size() == 0) {
            isAddActive = true;
            isEditActive = false;
            isDeleteActive = false;
        } else if(selectedItemPositionList.size() == 1){
            isEditActive = true;
            isDeleteActive = true;
        } else if(selectedItemPositionList.size() > 1) {
            isEditActive = false;
            isDeleteActive = true;
        }
        addButton = (FloatingActionButton)findViewById(R.id.fab_action_add);
        addButton.setEnabled(isAddActive);
        editButton = (FloatingActionButton)findViewById(R.id.fab_action_edit);
        editButton.setEnabled(isEditActive);
        deleteButton = (FloatingActionButton)findViewById(R.id.fab_action_delete);
        deleteButton.setEnabled(isDeleteActive);
    }

    private void adjustRowItemRowColor(AdapterView<?> parent, View view, int position) {
        TextView idTextView = view.findViewById(R.id.id_text_view_member);
        TextView titleTextView = view.findViewById(R.id.title_text_view_member);
        TextView nameTextView = view.findViewById(R.id.name_text_view_member);
        TextView organisationTextView = view.findViewById(R.id.organisation_text_view_member);
        TextView roleTextView = view.findViewById(R.id.role_text_view_member);
        if (!selectedItemPositionList.contains(position)) {
            selectedItemPositionList.add(new Integer(position));
            parent.getChildAt(position).setBackgroundColor(Color.parseColor("#4285f4"));
            idTextView.setTextColor(Color.parseColor("#FFFFFF"));
            titleTextView.setTextColor(Color.parseColor("#FFFFFF"));
            nameTextView.setTextColor(Color.parseColor("#FFFFFF"));
            organisationTextView.setTextColor(Color.parseColor("#FFFFFF"));
            roleTextView.setTextColor(Color.parseColor("#FFFFFF"));
        } else{
            selectedItemPositionList.remove(new Integer(position));
            parent.getChildAt(position).setBackgroundColor(Color.parseColor("#FFFFFF"));
            idTextView.setTextColor(Color.parseColor("#ff000000"));
            titleTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.darker_gray));
            nameTextView.setTextColor(Color.parseColor("#ff000000"));
            organisationTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.darker_gray));
            roleTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.darker_gray));
        }
    }
}
