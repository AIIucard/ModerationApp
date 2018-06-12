package research.dresden.htw.moderationapp.activities.members;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import research.dresden.htw.moderationapp.R;
import research.dresden.htw.moderationapp.manager.MemberManager;
import research.dresden.htw.moderationapp.model.AppDataViewModel;
import research.dresden.htw.moderationapp.model.IntentType;
import research.dresden.htw.moderationapp.model.Member;
import research.dresden.htw.moderationapp.utils.AppUtils;

public class AddMemberActivity extends AppCompatActivity {

    private Boolean isTitleSet = false;
    private Boolean isNameSet = false;
    private Boolean isOrganisationSet = false;
    private Boolean isRoleSet = false;

    private Spinner titleSpinner;
    private EditText nameEditText;
    private EditText organisationEditText;
    private EditText roleEditText;

    private Button addMemberButton;

    private AppDataViewModel dataViewModel;
    private ArrayList<Member> memberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        dataViewModel = AppDataViewModel.getInstance();

        final MutableLiveData<ArrayList<Member>> memberListData = dataViewModel.getMemberList();
        memberList = memberListData.getValue();

        titleSpinner = findViewById(R.id.title_spinner);
        nameEditText = findViewById(R.id.name_edit_text_view);
        organisationEditText = findViewById(R.id.organisation_edit_text_view);
        roleEditText = findViewById(R.id.role_edit_text_view);

        addMemberButton = findViewById(R.id.button_add_member);
        addMemberButton.setEnabled(false);

        initSpinner();

        titleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int myPosition, long myID) {
                isTitleSet = !parentView.getSelectedItem().toString().equals("--");
                updateAddButton();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                isTitleSet = false;
                updateAddButton();
            }
        });

        nameEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                isNameSet = s.toString().trim().length() != 0;
                updateAddButton();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not used
            }
        });

        organisationEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                isOrganisationSet = s.toString().trim().length() != 0;
                updateAddButton();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not used
            }
        });

        roleEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                isRoleSet = s.toString().trim().length() != 0;
                updateAddButton();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not used
            }
        });

        addMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(memberList != null) {
                    titleSpinner = findViewById(R.id.title_spinner);
                    nameEditText = findViewById(R.id.name_edit_text_view);
                    organisationEditText = findViewById(R.id.organisation_edit_text_view);
                    roleEditText = findViewById(R.id.role_edit_text_view);

                    // TODO: Check if member exists already
                    Member newMember = new Member(AppUtils.getNextMemberId(memberList), titleSpinner.getSelectedItem().toString(), nameEditText.getText().toString(), organisationEditText.getText().toString(), roleEditText.getText().toString());
                    memberList.add(newMember);
                    dataViewModel.setMemberList(memberList);
                    MemberManager memberManager = MemberManager.getInstance();
                    memberManager.writeToJSONFile(getApplicationContext(), dataViewModel.getMemberList().getValue());
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("type", IntentType.ADD_RESULT_TYPE);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                    Toast.makeText(AddMemberActivity.this, getString(R.string.add_toast_member, newMember.getName(), newMember.getId()), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("type", IntentType.ADD_RESULT_TYPE);
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    private void updateAddButton() {
        addMemberButton = findViewById(R.id.button_add_member);
        if(isTitleSet && isNameSet && isOrganisationSet && isRoleSet) {
            addMemberButton.setEnabled(true);
        } else {
            addMemberButton.setEnabled(false);
        }
    }

    private void initSpinner() {
        ArrayList<String> titlesAsList = AppUtils.getTitlesAsList();
        ArrayAdapter<String> titleSpinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, titlesAsList);
        titleSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(titleSpinner != null){
            titleSpinner.setAdapter(titleSpinnerAdapter);
        }
    }
}
