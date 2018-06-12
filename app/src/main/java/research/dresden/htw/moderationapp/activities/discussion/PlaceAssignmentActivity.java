package research.dresden.htw.moderationapp.activities.discussion;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import research.dresden.htw.moderationapp.R;
import research.dresden.htw.moderationapp.model.AppDataViewModel;
import research.dresden.htw.moderationapp.model.IntentType;
import research.dresden.htw.moderationapp.model.Member;

public class PlaceAssignmentActivity extends AppCompatActivity {

    private final ArrayList<Member> selectedMembers = new ArrayList<>();
    private final String closed = "Geschlossen";
    private Spinner place1Spinner;
    private Spinner place2Spinner;
    private Spinner place3Spinner;
    private Spinner place4Spinner;
    private Spinner place5Spinner;
    private String oldPlace1SpinnerValue;
    private String oldPlace2SpinnerValue;
    private String oldPlace3SpinnerValue;
    private String oldPlace4SpinnerValue;
    private String oldPlace5SpinnerValue;
    private ArrayAdapter<String> place1SpinnerAdapter;
    private ArrayAdapter<String> place2SpinnerAdapter;
    private ArrayAdapter<String> place3SpinnerAdapter;
    private ArrayAdapter<String> place4SpinnerAdapter;
    private ArrayAdapter<String> place5SpinnerAdapter;
    private AppDataViewModel dataViewModel;
    private ArrayList<Member> selectedMembersForDiscussionList;
    private ArrayList<Member> availableMembers = new ArrayList<>();
    private ArrayList<String> availableMembersAsStringList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Fix this class
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_assignment);

        dataViewModel = AppDataViewModel.getInstance();

        MutableLiveData<ArrayList<Member>> selectedMembersForDiscussionListData = dataViewModel.getSelectedMembersForDiscussionList();
        selectedMembersForDiscussionList = selectedMembersForDiscussionListData.getValue();
        if (selectedMembersForDiscussionList != null) {
            availableMembers = new ArrayList<>(selectedMembersForDiscussionList);
        }

        place1Spinner = findViewById(R.id.place_1_spinner);
        place2Spinner = findViewById(R.id.place_2_spinner);
        place3Spinner = findViewById(R.id.place_3_spinner);
        place4Spinner = findViewById(R.id.place_4_spinner);
        place5Spinner = findViewById(R.id.place_5_spinner);

        Button completeButton = findViewById(R.id.complete_place_assignment_discussion);

        initSpinner();

        updateSpinnerData();

        place1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int myPosition, long myID) {
                handleRemoveFromSelection(oldPlace1SpinnerValue);
                handleSpinnerSelection(parentView, checkIfAddAllowed(oldPlace1SpinnerValue));
                oldPlace1SpinnerValue = parentView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Not used
            }
        });

        place2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int myPosition, long myID) {
                handleRemoveFromSelection(oldPlace2SpinnerValue);
                handleSpinnerSelection(parentView, checkIfAddAllowed(oldPlace2SpinnerValue));
                oldPlace2SpinnerValue = parentView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Not used
            }
        });

        place3Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int myPosition, long myID) {
                handleRemoveFromSelection(oldPlace3SpinnerValue);
                handleSpinnerSelection(parentView, checkIfAddAllowed(oldPlace3SpinnerValue));
                oldPlace3SpinnerValue = parentView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Not used
            }
        });

        place4Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int myPosition, long myID) {
                handleRemoveFromSelection(oldPlace4SpinnerValue);
                handleSpinnerSelection(parentView, checkIfAddAllowed(oldPlace4SpinnerValue));
                oldPlace4SpinnerValue = parentView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Not used
            }
        });

        place5Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int myPosition, long myID) {
                handleRemoveFromSelection(oldPlace5SpinnerValue);
                handleSpinnerSelection(parentView, checkIfAddAllowed(oldPlace5SpinnerValue));
                oldPlace5SpinnerValue = parentView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Not used
            }
        });

        completeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (selectedMembers != null) {

                    place1Spinner = findViewById(R.id.place_1_spinner);
                    place2Spinner = findViewById(R.id.place_2_spinner);
                    place3Spinner = findViewById(R.id.place_3_spinner);
                    place4Spinner = findViewById(R.id.place_4_spinner);
                    place5Spinner = findViewById(R.id.place_5_spinner);

                    if (!place1Spinner.getSelectedItem().equals(closed)) {
                        for (Member selectedMember : selectedMembersForDiscussionList) {
                            if (selectedMember.getId() != Integer.parseInt(getIDFromString(place1Spinner.getSelectedItem().toString()))) {
                                selectedMember.setPlaceNumber(1);
                            }
                        }
                    }

                    if (!place2Spinner.getSelectedItem().equals(closed)) {
                        for (Member selectedMember : selectedMembersForDiscussionList) {
                            if (selectedMember.getId() != Integer.parseInt(getIDFromString(place2Spinner.getSelectedItem().toString()))) {
                                selectedMember.setPlaceNumber(2);
                            }
                        }
                    }

                    if (!place3Spinner.getSelectedItem().equals(closed)) {
                        for (Member selectedMember : selectedMembersForDiscussionList) {
                            if (selectedMember.getId() != Integer.parseInt(getIDFromString(place3Spinner.getSelectedItem().toString()))) {
                                selectedMember.setPlaceNumber(3);
                            }
                        }
                    }

                    if (!place4Spinner.getSelectedItem().equals(closed)) {
                        for (Member selectedMember : selectedMembersForDiscussionList) {
                            if (selectedMember.getId() != Integer.parseInt(getIDFromString(place4Spinner.getSelectedItem().toString()))) {
                                selectedMember.setPlaceNumber(4);
                            }
                        }
                    }

                    if (!place5Spinner.getSelectedItem().equals(closed)) {
                        for (Member selectedMember : selectedMembersForDiscussionList) {
                            if (selectedMember.getId() != Integer.parseInt(getIDFromString(place5Spinner.getSelectedItem().toString()))) {
                                selectedMember.setPlaceNumber(5);
                            }
                        }
                    }
                    dataViewModel.setSelectedMembersForDiscussionList(selectedMembersForDiscussionList);
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("type", IntentType.ADD_RESULT_TYPE);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            }
        });
    }

    private boolean checkIfAddAllowed(String oldValue) {
        return !oldValue.equals(closed);
    }

    private void handleRemoveFromSelection(String oldValueWithID) {
        if (!oldValueWithID.equals(closed)) {
            String idString = getIDFromString(oldValueWithID);
            List<Member> toRemoveList = new ArrayList<>();
            for (Member currentMember : selectedMembers) {
                if (currentMember.getId() == Integer.parseInt(idString)) {
                    toRemoveList.add(currentMember);
                }
            }
            selectedMembers.removeAll(toRemoveList);
        }
    }

    private void handleSpinnerSelection(AdapterView<?> parentView, boolean isAddAllowed) {
        if (!parentView.getSelectedItem().toString().equals(closed)) {
            String selectedItemString = parentView.getSelectedItem().toString();
            String idString = getIDFromString(selectedItemString);

            // Remove Member from available Spinner Data
            List<Member> toRemoveList = new ArrayList<>();
            for (Member currentMember : availableMembers) {
                if (currentMember.getId() == Integer.parseInt(idString)) {
                    // Add Member to selected Members
                    selectedMembers.add(currentMember);
                    toRemoveList.add(currentMember);
                }
            }
            availableMembers.removeAll(toRemoveList);

            // Get the missing Member from the old selection and at to available Members
            if (isAddAllowed) {
                Collection<Member> availableMemberList = new ArrayList<>(selectedMembersForDiscussionList);
                Collection<Member> oldAvailableMemberList = new ArrayList<>(availableMembers);
                Collection<Member> selectedMemberList = new ArrayList<>(selectedMembers);
                availableMemberList.removeAll(oldAvailableMemberList);
                availableMemberList.removeAll(selectedMemberList);
                availableMembers.addAll(availableMemberList);
            }
            updateSpinnerData();
        }
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("type", IntentType.ADD_RESULT_TYPE);
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    private void updateSpinnerData() {
        availableMembersAsStringList.clear();
        for (Member availableMember : availableMembers) {
            if (availableMember != null) {
                String s = "";
                s += "[" + availableMember.getId() + "] ";
                s += availableMember.getTitle() + "";
                s += availableMember.getName() + "; ";
                s += availableMember.getOrganisation();
                availableMembersAsStringList.add(s);
            }
        }
        availableMembersAsStringList.add(closed);
        place1SpinnerAdapter.notifyDataSetChanged();
        place2SpinnerAdapter.notifyDataSetChanged();
        place3SpinnerAdapter.notifyDataSetChanged();
        place4SpinnerAdapter.notifyDataSetChanged();
        place5SpinnerAdapter.notifyDataSetChanged();
    }

    private void initSpinner() {
        availableMembersAsStringList = new ArrayList<>();
        for (Member availableMember : availableMembers) {
            if (availableMember != null) {
                String s = "";
                s += "[" + availableMember.getId() + "] ";
                s += availableMember.getTitle() + "";
                s += availableMember.getName() + "; ";
                s += availableMember.getOrganisation();
                availableMembersAsStringList.add(s);
            }
        }
        availableMembersAsStringList.add(closed);
        place1SpinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, availableMembersAsStringList);
        place1SpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        place1Spinner.setAdapter(place1SpinnerAdapter);

        place2SpinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, availableMembersAsStringList);
        place2SpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        place2Spinner.setAdapter(place2SpinnerAdapter);

        place3SpinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, availableMembersAsStringList);
        place3SpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        place3Spinner.setAdapter(place3SpinnerAdapter);

        place4SpinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, availableMembersAsStringList);
        place4SpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        place4Spinner.setAdapter(place4SpinnerAdapter);

        place5SpinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, availableMembersAsStringList);
        place5SpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        place5Spinner.setAdapter(place5SpinnerAdapter);

        place1Spinner.setSelection(availableMembersAsStringList.size() - 1);
        place2Spinner.setSelection(availableMembersAsStringList.size() - 1);
        place3Spinner.setSelection(availableMembersAsStringList.size() - 1);
        place4Spinner.setSelection(availableMembersAsStringList.size() - 1);
        place5Spinner.setSelection(availableMembersAsStringList.size() - 1);

        oldPlace1SpinnerValue = closed;
        oldPlace2SpinnerValue = closed;
        oldPlace3SpinnerValue = closed;
        oldPlace4SpinnerValue = closed;
        oldPlace5SpinnerValue = closed;
    }

    private String getIDFromString(String s) {
        return s.substring(s.indexOf("[") + 1, s.indexOf("]"));
    }
}