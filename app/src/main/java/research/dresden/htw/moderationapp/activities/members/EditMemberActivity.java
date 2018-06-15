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

public class EditMemberActivity  extends AppCompatActivity {

    private Boolean isTitleChanged = false;
    private Boolean isNameChanged = false;
    private Boolean isOrganisationChanged = false;
    private Boolean isRoleChanged = false;

    private Spinner titleSpinner;
    private String oldTitle = "";
    private EditText nameEditText;
    private String oldName = "";
    private EditText organisationEditText;
    private String oldOrganisation = "";
    private EditText roleEditText;
    private String oldRole = "";

    private Button editMemberButton;

    private AppDataViewModel dataViewModel;
    private ArrayList<Member> memberList;
    private Member selectedMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_member);

        final MutableLiveData<ArrayList<Member>> memberListData = AppDataViewModel.getInstance().getMemberList();
        memberList = memberListData.getValue();

        titleSpinner = findViewById(R.id.title_spinner);
        nameEditText = findViewById(R.id.name_edit_text_view);
        organisationEditText = findViewById(R.id.organisation_edit_text_view);
        roleEditText = findViewById(R.id.role_edit_text_view);

        editMemberButton = findViewById(R.id.button_edit_member);
        editMemberButton.setEnabled(false);

        dataViewModel = AppDataViewModel.getInstance();
        selectedMember = dataViewModel.getLastSelectedMember().getValue();

        initValues();

        titleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int myPosition, long myID) {
                isTitleChanged = !parentView.getSelectedItem().toString().equals(oldTitle);
                updateEditButton();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                isTitleChanged = false;
                updateEditButton();
            }
        });

        nameEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                isNameChanged = s.toString().trim().length() != 0 && !s.toString().trim().equals(oldName);
                updateEditButton();
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

                isOrganisationChanged = s.toString().trim().length() != 0&& !s.toString().trim().equals(oldOrganisation);
                updateEditButton();
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

                isRoleChanged = s.toString().trim().length() != 0&& !s.toString().trim().equals(oldRole);
                updateEditButton();
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

        editMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(memberList != null && selectedMember != null) {
                    titleSpinner = findViewById(R.id.title_spinner);
                    nameEditText = findViewById(R.id.name_edit_text_view);
                    organisationEditText = findViewById(R.id.organisation_edit_text_view);
                    roleEditText = findViewById(R.id.role_edit_text_view);
                    Member newMember = null;
                    for (Member currentMember : memberList) {
                        if(currentMember.getId() == selectedMember.getId()){
                            currentMember.setTitle(titleSpinner.getSelectedItem().toString());
                            currentMember.setName(nameEditText.getText().toString());
                            currentMember.setOrganisation(organisationEditText.getText().toString());
                            currentMember.setRole(roleEditText.getText().toString());
                            newMember = currentMember;
                        }
                    }
                    dataViewModel.setMemberList(memberList);
                    if (newMember != null) {
                        dataViewModel.setLastSelectedMember(newMember);
                    }
                    MemberManager memberManager = MemberManager.getInstance();
                    memberManager.writeToJSONFile(getApplicationContext(), dataViewModel.getMemberList().getValue());
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("type", IntentType.EDIT_RESULT_TYPE);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                    Toast.makeText(EditMemberActivity.this, getString(R.string.edit_toast_member, selectedMember.getId()), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updateEditButton() {
        editMemberButton = findViewById(R.id.button_edit_member);
        if(isTitleChanged || isNameChanged || isOrganisationChanged || isRoleChanged) {
            editMemberButton.setEnabled(true);
        } else {
            editMemberButton.setEnabled(false);
        }
    }
    private void initValues() {
        if(selectedMember != null) {
            oldTitle = selectedMember.getTitle();
            oldName = selectedMember.getName();
            oldOrganisation = selectedMember.getOrganisation();
            oldRole = selectedMember.getRole();

            ArrayList<String> titlesAsList = AppUtils.getTitlesAsList();
            ArrayAdapter<String> titleSpinnerAdapter = new ArrayAdapter<>(
                    this, R.layout.spinner_item, titlesAsList);
            titleSpinnerAdapter.setDropDownViewResource(R.layout.spinner_drop_down_item);
            if (titleSpinner != null) {
                titleSpinner.setAdapter(titleSpinnerAdapter);
                int spinnerPosition = titleSpinnerAdapter.getPosition(oldTitle);
                titleSpinner.setSelection(spinnerPosition);
            }
            if (nameEditText != null) nameEditText.setText(oldName);
            if (organisationEditText != null) organisationEditText.setText(oldOrganisation);
            if (roleEditText != null) roleEditText.setText(oldRole);
        }
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("type", IntentType.EDIT_RESULT_TYPE);
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
}
