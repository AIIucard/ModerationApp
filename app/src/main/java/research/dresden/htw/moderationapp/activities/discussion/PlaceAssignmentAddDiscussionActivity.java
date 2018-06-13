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
import research.dresden.htw.moderationapp.model.SpinnerMemberItem;

public class PlaceAssignmentAddDiscussionActivity extends AppCompatActivity {

    boolean allMembersPlaced = false;

    private final String CLOSED = "Geschlossen";
    private final String SPINNER_NUMBER_1 = "SpinnerNumber1";
    private final String SPINNER_NUMBER_2 = "SpinnerNumber2";
    private final String SPINNER_NUMBER_3 = "SpinnerNumber3";
    private final String SPINNER_NUMBER_4 = "SpinnerNumber4";
    private final String SPINNER_NUMBER_5 = "SpinnerNumber5";
    private Spinner place1Spinner;
    private Spinner place2Spinner;
    private Spinner place3Spinner;
    private Spinner place4Spinner;
    private Spinner place5Spinner;
    boolean ignoreSelection = false;
    private ArrayAdapter<SpinnerMemberItem> place1SpinnerAdapter;
    private ArrayAdapter<SpinnerMemberItem> place2SpinnerAdapter;
    private ArrayAdapter<SpinnerMemberItem> place3SpinnerAdapter;
    private ArrayAdapter<SpinnerMemberItem> place4SpinnerAdapter;
    private ArrayAdapter<SpinnerMemberItem> place5SpinnerAdapter;
    private SpinnerMemberItem oldPlace1SpinnerValue;
    private SpinnerMemberItem oldPlace2SpinnerValue;
    private SpinnerMemberItem oldPlace3SpinnerValue;
    private SpinnerMemberItem oldPlace4SpinnerValue;
    private SpinnerMemberItem oldPlace5SpinnerValue;
    private ArrayList<SpinnerMemberItem> availableMembersSpinner1 = new ArrayList<>();
    private ArrayList<SpinnerMemberItem> availableMembersSpinner2 = new ArrayList<>();
    private ArrayList<SpinnerMemberItem> availableMembersSpinner3 = new ArrayList<>();
    private ArrayList<SpinnerMemberItem> availableMembersSpinner4 = new ArrayList<>();
    private ArrayList<SpinnerMemberItem> availableMembersSpinner5 = new ArrayList<>();

    private ArrayList<Member> selectedMembersForDiscussionList;
    private ArrayList<Member> availableTotalMembers = new ArrayList<>();
    private ArrayList<Member> selectedTotalMembers = new ArrayList<>();

    Button completeButton;

    private AppDataViewModel dataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_assignment_add_discussion);

        dataViewModel = AppDataViewModel.getInstance();

        MutableLiveData<ArrayList<Member>> selectedMembersForDiscussionListData = dataViewModel.getSelectedMembersForDiscussionList();
        selectedMembersForDiscussionList = selectedMembersForDiscussionListData.getValue();
        if (selectedMembersForDiscussionList != null) {
            availableTotalMembers = new ArrayList<>(selectedMembersForDiscussionList);
        }

        place1Spinner = findViewById(R.id.place_1_spinner);
        place2Spinner = findViewById(R.id.place_2_spinner);
        place3Spinner = findViewById(R.id.place_3_spinner);
        place4Spinner = findViewById(R.id.place_4_spinner);
        place5Spinner = findViewById(R.id.place_5_spinner);

        completeButton = findViewById(R.id.complete_place_assignment_discussion);

        initSpinner();

        place1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int myPosition, long myID) {
                if (!ignoreSelection) {
                    handleRemoveFromSelection(oldPlace1SpinnerValue);
                    handleSpinnerSelection(parentView, SPINNER_NUMBER_1, checkIfAddAllowed(oldPlace1SpinnerValue));
                    oldPlace1SpinnerValue = (SpinnerMemberItem) parentView.getSelectedItem();

                } else {
                    ignoreSelection = false;
                }
                updateButton();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Not used
            }
        });

        place2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int myPosition, long myID) {
                if (!ignoreSelection) {
                    handleRemoveFromSelection(oldPlace2SpinnerValue);
                    handleSpinnerSelection(parentView, SPINNER_NUMBER_2, checkIfAddAllowed(oldPlace2SpinnerValue));
                    oldPlace2SpinnerValue = (SpinnerMemberItem) parentView.getSelectedItem();
                } else {
                    ignoreSelection = false;
                }
                updateButton();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Not used
            }
        });

        place3Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int myPosition, long myID) {
                if (!ignoreSelection) {
                    handleRemoveFromSelection(oldPlace3SpinnerValue);
                    handleSpinnerSelection(parentView, SPINNER_NUMBER_3, checkIfAddAllowed(oldPlace3SpinnerValue));
                    oldPlace3SpinnerValue = (SpinnerMemberItem) parentView.getSelectedItem();
                } else {
                    ignoreSelection = false;
                }
                updateButton();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Not used
            }
        });

        place4Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int myPosition, long myID) {
                if (!ignoreSelection) {
                    handleRemoveFromSelection(oldPlace4SpinnerValue);
                    handleSpinnerSelection(parentView, SPINNER_NUMBER_4, checkIfAddAllowed(oldPlace4SpinnerValue));
                    oldPlace4SpinnerValue = (SpinnerMemberItem) parentView.getSelectedItem();
                } else {
                    ignoreSelection = false;
                }
                updateButton();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Not used
            }
        });

        place5Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int myPosition, long myID) {
                if (!ignoreSelection) {
                    handleRemoveFromSelection(oldPlace5SpinnerValue);
                    handleSpinnerSelection(parentView, SPINNER_NUMBER_5, checkIfAddAllowed(oldPlace5SpinnerValue));
                    oldPlace5SpinnerValue = (SpinnerMemberItem) parentView.getSelectedItem();
                } else {
                    ignoreSelection = false;
                }
                updateButton();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Not used
            }
        });

        completeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (selectedTotalMembers != null) {

                    place1Spinner = findViewById(R.id.place_1_spinner);
                    place2Spinner = findViewById(R.id.place_2_spinner);
                    place3Spinner = findViewById(R.id.place_3_spinner);
                    place4Spinner = findViewById(R.id.place_4_spinner);
                    place5Spinner = findViewById(R.id.place_5_spinner);

                    if (!((SpinnerMemberItem) place1Spinner.getSelectedItem()).getDescription().equals(CLOSED)) {
                        for (Member selectedMember : selectedMembersForDiscussionList) {
                            if (selectedMember.getId() == ((SpinnerMemberItem) place1Spinner.getSelectedItem()).getMember().getId()) {
                                selectedMember.setPlaceNumber(1);
                            }
                        }
                    }

                    if (!((SpinnerMemberItem) place2Spinner.getSelectedItem()).getDescription().equals(CLOSED)) {
                        for (Member selectedMember : selectedMembersForDiscussionList) {
                            if (selectedMember.getId() == ((SpinnerMemberItem) place2Spinner.getSelectedItem()).getMember().getId()) {
                                selectedMember.setPlaceNumber(2);
                            }
                        }
                    }

                    if (!((SpinnerMemberItem) place3Spinner.getSelectedItem()).getDescription().equals(CLOSED)) {
                        for (Member selectedMember : selectedMembersForDiscussionList) {
                            if (selectedMember.getId() == ((SpinnerMemberItem) place3Spinner.getSelectedItem()).getMember().getId()) {
                                selectedMember.setPlaceNumber(3);
                            }
                        }
                    }

                    if (!((SpinnerMemberItem) place4Spinner.getSelectedItem()).getDescription().equals(CLOSED)) {
                        for (Member selectedMember : selectedMembersForDiscussionList) {
                            if (selectedMember.getId() == ((SpinnerMemberItem) place4Spinner.getSelectedItem()).getMember().getId()) {
                                selectedMember.setPlaceNumber(4);
                            }
                        }
                    }

                    if (!((SpinnerMemberItem) place5Spinner.getSelectedItem()).getDescription().equals(CLOSED)) {
                        for (Member selectedMember : selectedMembersForDiscussionList) {
                            if (selectedMember.getId() == ((SpinnerMemberItem) place5Spinner.getSelectedItem()).getMember().getId()) {
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

    private boolean checkIfAddAllowed(SpinnerMemberItem oldValue) {
        return !oldValue.getDescription().equals(CLOSED);
    }

    private void handleRemoveFromSelection(SpinnerMemberItem oldSelection) {
        if (!oldSelection.getDescription().equals(CLOSED)) {
            int id = oldSelection.getMember().getId();
            List<Member> toRemoveList = new ArrayList<>();
            for (Member currentMember : selectedTotalMembers) {
                if (currentMember.getId() == id) {
                    toRemoveList.add(currentMember);
                }
            }
            selectedTotalMembers.removeAll(toRemoveList);
        }
    }

    private void handleSpinnerSelection(AdapterView<?> parentView, String currentSpinner, boolean isAddAllowed) {
        Member memberToRemove = null;
        Member memberToAdd = null;

        if (!((SpinnerMemberItem) parentView.getSelectedItem()).getDescription().equals(CLOSED)) {
            SpinnerMemberItem selectedItem = (SpinnerMemberItem) parentView.getSelectedItem();
            memberToRemove = selectedItem.getMember();

            // Remove Member from total available Spinner Data
            availableTotalMembers.remove(memberToRemove);

            // Add Member to selected Members
            selectedTotalMembers.add(memberToRemove);
        }
        // Get the missing Member from the old selection and at to available Members

        if (isAddAllowed) {
            memberToAdd = getMissingMember();
            if (memberToAdd != null) {
                availableTotalMembers.add(memberToAdd);
            }
        }
        updateSpinnerData(currentSpinner, memberToRemove, memberToAdd, isAddAllowed);
    }

    private Member getMissingMember() {
        Collection<Member> remainingMemberCollection = new ArrayList<>(selectedMembersForDiscussionList);
        Collection<Member> oldAvailableMemberList = new ArrayList<>(availableTotalMembers);
        Collection<Member> currentSelectedMemberList = new ArrayList<>(selectedTotalMembers);
        remainingMemberCollection.removeAll(oldAvailableMemberList);
        remainingMemberCollection.removeAll(currentSelectedMemberList);
        ArrayList<Member> remainingMemberList = new ArrayList<>(remainingMemberCollection);
        return remainingMemberList.get(0);
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("type", IntentType.ADD_RESULT_TYPE);
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    private void updateSpinnerData(String currentSpinner, Member memberToRemove, Member memberToAdd, boolean isAddAllowed) {
        if (memberToRemove != null) {
            if (!currentSpinner.equals(SPINNER_NUMBER_1)) {
                availableMembersSpinner1.remove(new SpinnerMemberItem(memberToRemove.toString(), memberToRemove));
            }
            if (!currentSpinner.equals(SPINNER_NUMBER_2)) {
                availableMembersSpinner2.remove(new SpinnerMemberItem(memberToRemove.toString(), memberToRemove));
            }
            if (!currentSpinner.equals(SPINNER_NUMBER_3)) {
                availableMembersSpinner3.remove(new SpinnerMemberItem(memberToRemove.toString(), memberToRemove));
            }
            if (!currentSpinner.equals(SPINNER_NUMBER_4)) {
                availableMembersSpinner4.remove(new SpinnerMemberItem(memberToRemove.toString(), memberToRemove));
            }
            if (!currentSpinner.equals(SPINNER_NUMBER_5)) {
                availableMembersSpinner5.remove(new SpinnerMemberItem(memberToRemove.toString(), memberToRemove));
            }
        }
        if (memberToAdd != null && isAddAllowed) {
            if (!currentSpinner.equals(SPINNER_NUMBER_1)) {
                if (availableMembersSpinner1.size() != 1) {
                    availableMembersSpinner1.add(new SpinnerMemberItem(memberToAdd.toString(), memberToAdd));
                } else {
                    ignoreSelection = true;
                    availableMembersSpinner1.add(new SpinnerMemberItem(memberToAdd.toString(), memberToAdd));
                    place1Spinner.setSelection(place1SpinnerAdapter.getPosition(oldPlace1SpinnerValue));
                }
            }
            if (!currentSpinner.equals(SPINNER_NUMBER_2)) {
                if (availableMembersSpinner2.size() != 1) {
                    availableMembersSpinner2.add(new SpinnerMemberItem(memberToAdd.toString(), memberToAdd));
                } else {
                    ignoreSelection = true;
                    availableMembersSpinner2.add(new SpinnerMemberItem(memberToAdd.toString(), memberToAdd));
                    place2Spinner.setSelection(place2SpinnerAdapter.getPosition(oldPlace2SpinnerValue));
                }
            }
            if (!currentSpinner.equals(SPINNER_NUMBER_3)) {
                if (availableMembersSpinner3.size() != 1) {
                    availableMembersSpinner3.add(new SpinnerMemberItem(memberToAdd.toString(), memberToAdd));
                } else {
                    ignoreSelection = true;
                    availableMembersSpinner3.add(new SpinnerMemberItem(memberToAdd.toString(), memberToAdd));
                    place3Spinner.setSelection(place3SpinnerAdapter.getPosition(oldPlace3SpinnerValue));
                }
            }
            if (!currentSpinner.equals(SPINNER_NUMBER_4)) {
                if (availableMembersSpinner4.size() != 1) {
                    availableMembersSpinner4.add(new SpinnerMemberItem(memberToAdd.toString(), memberToAdd));
                } else {
                    ignoreSelection = true;
                    availableMembersSpinner4.add(new SpinnerMemberItem(memberToAdd.toString(), memberToAdd));
                    place4Spinner.setSelection(place4SpinnerAdapter.getPosition(oldPlace4SpinnerValue));
                }
            }
            if (!currentSpinner.equals(SPINNER_NUMBER_5)) {
                if (availableMembersSpinner5.size() != 1) {
                    availableMembersSpinner5.add(new SpinnerMemberItem(memberToAdd.toString(), memberToAdd));
                } else {
                    ignoreSelection = true;
                    availableMembersSpinner5.add(new SpinnerMemberItem(memberToAdd.toString(), memberToAdd));
                    place5Spinner.setSelection(place5SpinnerAdapter.getPosition(oldPlace5SpinnerValue));
                }
            }
        }
        place1SpinnerAdapter.notifyDataSetChanged();
        place2SpinnerAdapter.notifyDataSetChanged();
        place3SpinnerAdapter.notifyDataSetChanged();
        place4SpinnerAdapter.notifyDataSetChanged();
        place5SpinnerAdapter.notifyDataSetChanged();
    }

    private void initSpinner() {

        for (Member availableMember : availableTotalMembers) {
            availableMembersSpinner1.add(new SpinnerMemberItem(availableMember.toString(), availableMember));
            availableMembersSpinner2.add(new SpinnerMemberItem(availableMember.toString(), availableMember));
            availableMembersSpinner3.add(new SpinnerMemberItem(availableMember.toString(), availableMember));
            availableMembersSpinner4.add(new SpinnerMemberItem(availableMember.toString(), availableMember));
            availableMembersSpinner5.add(new SpinnerMemberItem(availableMember.toString(), availableMember));
        }
        availableMembersSpinner1.add(new SpinnerMemberItem(CLOSED, null));
        availableMembersSpinner2.add(new SpinnerMemberItem(CLOSED, null));
        availableMembersSpinner3.add(new SpinnerMemberItem(CLOSED, null));
        availableMembersSpinner4.add(new SpinnerMemberItem(CLOSED, null));
        availableMembersSpinner5.add(new SpinnerMemberItem(CLOSED, null));

        place1SpinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, availableMembersSpinner1);
        place1SpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        place1Spinner.setAdapter(place1SpinnerAdapter);

        place2SpinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, availableMembersSpinner2);
        place2SpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        place2Spinner.setAdapter(place2SpinnerAdapter);

        place3SpinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, availableMembersSpinner3);
        place3SpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        place3Spinner.setAdapter(place3SpinnerAdapter);

        place4SpinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, availableMembersSpinner4);
        place4SpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        place4Spinner.setAdapter(place4SpinnerAdapter);

        place5SpinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, availableMembersSpinner5);
        place5SpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        place5Spinner.setAdapter(place5SpinnerAdapter);

        place1Spinner.setSelection(place1SpinnerAdapter.getPosition(new SpinnerMemberItem(CLOSED, null)));
        oldPlace1SpinnerValue = (SpinnerMemberItem) place1Spinner.getSelectedItem();
        place2Spinner.setSelection(place2SpinnerAdapter.getPosition(new SpinnerMemberItem(CLOSED, null)));
        oldPlace2SpinnerValue = (SpinnerMemberItem) place2Spinner.getSelectedItem();
        place3Spinner.setSelection(place3SpinnerAdapter.getPosition(new SpinnerMemberItem(CLOSED, null)));
        oldPlace3SpinnerValue = (SpinnerMemberItem) place3Spinner.getSelectedItem();
        place4Spinner.setSelection(place4SpinnerAdapter.getPosition(new SpinnerMemberItem(CLOSED, null)));
        oldPlace4SpinnerValue = (SpinnerMemberItem) place4Spinner.getSelectedItem();
        place5Spinner.setSelection(place5SpinnerAdapter.getPosition(new SpinnerMemberItem(CLOSED, null)));
        oldPlace5SpinnerValue = (SpinnerMemberItem) place5Spinner.getSelectedItem();
    }

    private void updateButton() {
        completeButton = findViewById(R.id.complete_place_assignment_discussion);
        allMembersPlaced = availableTotalMembers.size() == 0;
        completeButton.setEnabled(allMembersPlaced);
    }
}