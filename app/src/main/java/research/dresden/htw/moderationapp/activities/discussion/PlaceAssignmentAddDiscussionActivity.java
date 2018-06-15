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

import research.dresden.htw.moderationapp.R;
import research.dresden.htw.moderationapp.model.AppDataViewModel;
import research.dresden.htw.moderationapp.model.IntentType;
import research.dresden.htw.moderationapp.model.Member;
import research.dresden.htw.moderationapp.model.SpinnerMemberItem;

public class PlaceAssignmentAddDiscussionActivity extends AppCompatActivity {

    private final String CLOSED = "Geschlossen";
    private final String SPINNER_PLACE_1 = "SpinnerPlace1";
    private final String SPINNER_PLACE_2 = "SpinnerPlace2";
    private final String SPINNER_PLACE_3 = "SpinnerPlace3";
    private final String SPINNER_PLACE_4 = "SpinnerPlace4";
    private final String SPINNER_PLACE_5 = "SpinnerPlace5";
    boolean allMembersPlaced = false;

    boolean isReSelectedSpinnerPlace1 = false;
    boolean isReSelectedSpinnerPlace2 = false;
    boolean isReSelectedSpinnerPlace3 = false;
    boolean isReSelectedSpinnerPlace4 = false;
    boolean isReSelectedSpinnerPlace5 = false;

    private Spinner spinnerPlace1;
    private Spinner spinnerPlace2;
    private Spinner spinnerPlace3;
    private Spinner spinnerPlace4;
    private Spinner spinnerPlace5;
    private ArrayAdapter<SpinnerMemberItem> spinnerAdapterPlace1;
    private ArrayAdapter<SpinnerMemberItem> spinnerAdapterPlace2;
    private ArrayAdapter<SpinnerMemberItem> spinnerAdapterPlace3;
    private ArrayAdapter<SpinnerMemberItem> spinnerAdapterPlace4;
    private ArrayAdapter<SpinnerMemberItem> spinnerAdapterPlace5;
    private SpinnerMemberItem previousSpinnerValuePlace1;
    private SpinnerMemberItem previousSpinnerValuePlace2;
    private SpinnerMemberItem previousSpinnerValuePlace3;
    private SpinnerMemberItem previousSpinnerValuePlace4;
    private SpinnerMemberItem previousSpinnerValuePlace5;
    private ArrayList<SpinnerMemberItem> availableMembersSpinnerPlace1 = new ArrayList<>();
    private ArrayList<SpinnerMemberItem> availableMembersSpinnerPlace2 = new ArrayList<>();
    private ArrayList<SpinnerMemberItem> availableMembersSpinnerPlace3 = new ArrayList<>();
    private ArrayList<SpinnerMemberItem> availableMembersSpinnerPlace4 = new ArrayList<>();
    private ArrayList<SpinnerMemberItem> availableMembersSpinnerPlace5 = new ArrayList<>();

    private ArrayList<Member> selectedMembersForDiscussionList;
    private ArrayList<Member> totalAvailableMembers = new ArrayList<>();
    private ArrayList<Member> totalSelectedMembers = new ArrayList<>();

    Button completeButton;

    private AppDataViewModel dataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_assignment_add_discussion);

        initActivityData();
        initSpinnerListener();

        completeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                handleCompleteButtonPressed();
            }
        });
    }

    private void initSpinnerListener() {
        spinnerPlace1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int myPosition, long myID) {
                if (!isReSelectedSpinnerPlace1) {
                    handleRemoveFromTotalSelectedMembers(previousSpinnerValuePlace1);
                    handleSpinnerSelection(parentView, SPINNER_PLACE_1, checkIfAddToTotalAvailableMembersAllowed(previousSpinnerValuePlace1));
                    previousSpinnerValuePlace1 = (SpinnerMemberItem) parentView.getSelectedItem();

                } else {
                    isReSelectedSpinnerPlace1 = false;
                }
                updateButton();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Not used
            }
        });

        spinnerPlace2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int myPosition, long myID) {
                if (!isReSelectedSpinnerPlace2) {
                    handleRemoveFromTotalSelectedMembers(previousSpinnerValuePlace2);
                    handleSpinnerSelection(parentView, SPINNER_PLACE_2, checkIfAddToTotalAvailableMembersAllowed(previousSpinnerValuePlace2));
                    previousSpinnerValuePlace2 = (SpinnerMemberItem) parentView.getSelectedItem();
                } else {
                    isReSelectedSpinnerPlace2 = false;
                }
                updateButton();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Not used
            }
        });

        spinnerPlace3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int myPosition, long myID) {
                if (!isReSelectedSpinnerPlace3) {
                    handleRemoveFromTotalSelectedMembers(previousSpinnerValuePlace3);
                    handleSpinnerSelection(parentView, SPINNER_PLACE_3, checkIfAddToTotalAvailableMembersAllowed(previousSpinnerValuePlace3));
                    previousSpinnerValuePlace3 = (SpinnerMemberItem) parentView.getSelectedItem();
                } else {
                    isReSelectedSpinnerPlace3 = false;
                }
                updateButton();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Not used
            }
        });

        spinnerPlace4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int myPosition, long myID) {
                if (!isReSelectedSpinnerPlace4) {
                    handleRemoveFromTotalSelectedMembers(previousSpinnerValuePlace4);
                    handleSpinnerSelection(parentView, SPINNER_PLACE_4, checkIfAddToTotalAvailableMembersAllowed(previousSpinnerValuePlace4));
                    previousSpinnerValuePlace4 = (SpinnerMemberItem) parentView.getSelectedItem();
                } else {
                    isReSelectedSpinnerPlace4 = false;
                }
                updateButton();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Not used
            }
        });

        spinnerPlace5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int myPosition, long myID) {
                if (!isReSelectedSpinnerPlace5) {
                    handleRemoveFromTotalSelectedMembers(previousSpinnerValuePlace5);
                    handleSpinnerSelection(parentView, SPINNER_PLACE_5, checkIfAddToTotalAvailableMembersAllowed(previousSpinnerValuePlace5));
                    previousSpinnerValuePlace5 = (SpinnerMemberItem) parentView.getSelectedItem();
                } else {
                    isReSelectedSpinnerPlace5 = false;
                }
                updateButton();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Not used
            }
        });
    }

    private void handleRemoveFromTotalSelectedMembers(SpinnerMemberItem oldSelection) {
        if (!oldSelection.getDescription().equals(CLOSED)) {
            totalSelectedMembers.remove(oldSelection.getMember());
        }
    }

    private boolean checkIfAddToTotalAvailableMembersAllowed(SpinnerMemberItem oldValue) {
        return !oldValue.getDescription().equals(CLOSED);
    }

    private void handleSpinnerSelection(AdapterView<?> parentView, String currentSpinner, boolean isAddToTotalAvailableMembersAllowed) {
        Member memberToRemove = null;
        Member memberToAdd = null;

        if (!((SpinnerMemberItem) parentView.getSelectedItem()).getDescription().equals(CLOSED)) {
            memberToRemove = ((SpinnerMemberItem) parentView.getSelectedItem()).getMember();

            // Remove Member from total available Spinner Data
            totalAvailableMembers.remove(memberToRemove);

            // Add Member to selected Members
            totalSelectedMembers.add(memberToRemove);
        }

        // Get the missing Member from the old selection and at to available Members
        if (isAddToTotalAvailableMembersAllowed) {
            memberToAdd = getMissingMember();
            if (memberToAdd != null) {
                totalAvailableMembers.add(memberToAdd);
            }
        }
        updateSpinnerData(currentSpinner, memberToRemove, memberToAdd, isAddToTotalAvailableMembersAllowed);
    }

    private Member getMissingMember() {
        Collection<Member> remainingMemberCollection = new ArrayList<>(selectedMembersForDiscussionList);
        Collection<Member> oldAvailableMemberList = new ArrayList<>(totalAvailableMembers);
        Collection<Member> currentSelectedMemberList = new ArrayList<>(totalSelectedMembers);
        remainingMemberCollection.removeAll(oldAvailableMemberList);
        remainingMemberCollection.removeAll(currentSelectedMemberList);
        ArrayList<Member> remainingMemberList = new ArrayList<>(remainingMemberCollection);
        return remainingMemberList.get(0);
    }

    private void updateSpinnerData(String currentSpinner, Member memberToRemove, Member memberToAdd, boolean isAddAllowed) {
        if (memberToRemove != null) {
            if (!currentSpinner.equals(SPINNER_PLACE_1)) {
                previousSpinnerValuePlace1 = (SpinnerMemberItem) spinnerPlace1.getSelectedItem();
                int oldItemSelectionPosition = spinnerAdapterPlace1.getPosition(previousSpinnerValuePlace1);
                availableMembersSpinnerPlace1.remove(new SpinnerMemberItem(memberToRemove.toString(), memberToRemove));
                if (oldItemSelectionPosition != spinnerAdapterPlace1.getPosition(previousSpinnerValuePlace1)) {
                    isReSelectedSpinnerPlace1 = true;
                    spinnerPlace1.setSelection(spinnerAdapterPlace1.getPosition(previousSpinnerValuePlace1));
                }
            }
            if (!currentSpinner.equals(SPINNER_PLACE_2)) {
                previousSpinnerValuePlace2 = (SpinnerMemberItem) spinnerPlace2.getSelectedItem();
                int oldItemSelectionPosition = spinnerAdapterPlace2.getPosition(previousSpinnerValuePlace2);
                availableMembersSpinnerPlace2.remove(new SpinnerMemberItem(memberToRemove.toString(), memberToRemove));
                if (oldItemSelectionPosition != spinnerAdapterPlace2.getPosition(previousSpinnerValuePlace2)) {
                    isReSelectedSpinnerPlace2 = true;
                    spinnerPlace2.setSelection(spinnerAdapterPlace2.getPosition(previousSpinnerValuePlace2));
                }
            }
            if (!currentSpinner.equals(SPINNER_PLACE_3)) {
                previousSpinnerValuePlace3 = (SpinnerMemberItem) spinnerPlace3.getSelectedItem();
                int oldItemSelectionPosition = spinnerAdapterPlace3.getPosition(previousSpinnerValuePlace3);
                availableMembersSpinnerPlace3.remove(new SpinnerMemberItem(memberToRemove.toString(), memberToRemove));
                if (oldItemSelectionPosition != spinnerAdapterPlace3.getPosition(previousSpinnerValuePlace3)) {
                    isReSelectedSpinnerPlace3 = true;
                    spinnerPlace3.setSelection(spinnerAdapterPlace3.getPosition(previousSpinnerValuePlace3));
                }
            }
            if (!currentSpinner.equals(SPINNER_PLACE_4)) {
                previousSpinnerValuePlace4 = (SpinnerMemberItem) spinnerPlace4.getSelectedItem();
                int oldItemSelectionPosition = spinnerAdapterPlace4.getPosition(previousSpinnerValuePlace4);
                availableMembersSpinnerPlace4.remove(new SpinnerMemberItem(memberToRemove.toString(), memberToRemove));
                if (oldItemSelectionPosition != spinnerAdapterPlace4.getPosition(previousSpinnerValuePlace4)) {
                    isReSelectedSpinnerPlace4 = true;
                    spinnerPlace4.setSelection(spinnerAdapterPlace4.getPosition(previousSpinnerValuePlace4));
                }
            }
            if (!currentSpinner.equals(SPINNER_PLACE_5)) {
                previousSpinnerValuePlace5 = (SpinnerMemberItem) spinnerPlace5.getSelectedItem();
                int oldItemSelectionPosition = spinnerAdapterPlace5.getPosition(previousSpinnerValuePlace5);
                availableMembersSpinnerPlace5.remove(new SpinnerMemberItem(memberToRemove.toString(), memberToRemove));
                if (oldItemSelectionPosition != spinnerAdapterPlace5.getPosition(previousSpinnerValuePlace5)) {
                    isReSelectedSpinnerPlace5 = true;
                    spinnerPlace5.setSelection(spinnerAdapterPlace5.getPosition(previousSpinnerValuePlace5));
                }
            }
        }
        if (memberToAdd != null && isAddAllowed && !totalSelectedMembers.contains(memberToAdd)) {
            if (!currentSpinner.equals(SPINNER_PLACE_1)) {
                previousSpinnerValuePlace1 = (SpinnerMemberItem) spinnerPlace1.getSelectedItem();
                int oldItemSelectionPosition = spinnerAdapterPlace1.getPosition(previousSpinnerValuePlace1);
                availableMembersSpinnerPlace1.add(new SpinnerMemberItem(memberToAdd.toString(), memberToAdd));
                if (oldItemSelectionPosition != spinnerAdapterPlace1.getPosition(previousSpinnerValuePlace1)) {
                    isReSelectedSpinnerPlace1 = true;
                    spinnerPlace1.setSelection(spinnerAdapterPlace1.getPosition(previousSpinnerValuePlace1));
                }
            }
            if (!currentSpinner.equals(SPINNER_PLACE_2)) {
                previousSpinnerValuePlace2 = (SpinnerMemberItem) spinnerPlace2.getSelectedItem();
                int oldItemSelectionPosition = spinnerAdapterPlace2.getPosition(previousSpinnerValuePlace2);
                availableMembersSpinnerPlace2.add(new SpinnerMemberItem(memberToAdd.toString(), memberToAdd));
                if (oldItemSelectionPosition != spinnerAdapterPlace2.getPosition(previousSpinnerValuePlace2)) {
                    isReSelectedSpinnerPlace2 = true;
                    spinnerPlace2.setSelection(spinnerAdapterPlace2.getPosition(previousSpinnerValuePlace2));
                }
            }
            if (!currentSpinner.equals(SPINNER_PLACE_3)) {
                previousSpinnerValuePlace3 = (SpinnerMemberItem) spinnerPlace3.getSelectedItem();
                int oldItemSelectionPosition = spinnerAdapterPlace3.getPosition(previousSpinnerValuePlace3);
                availableMembersSpinnerPlace3.add(new SpinnerMemberItem(memberToAdd.toString(), memberToAdd));
                if (oldItemSelectionPosition != spinnerAdapterPlace3.getPosition(previousSpinnerValuePlace3)) {
                    isReSelectedSpinnerPlace3 = true;
                    spinnerPlace3.setSelection(spinnerAdapterPlace3.getPosition(previousSpinnerValuePlace3));
                }
            }
            if (!currentSpinner.equals(SPINNER_PLACE_4)) {
                previousSpinnerValuePlace4 = (SpinnerMemberItem) spinnerPlace4.getSelectedItem();
                int oldItemSelectionPosition = spinnerAdapterPlace4.getPosition(previousSpinnerValuePlace4);
                availableMembersSpinnerPlace4.add(new SpinnerMemberItem(memberToAdd.toString(), memberToAdd));
                if (oldItemSelectionPosition != spinnerAdapterPlace4.getPosition(previousSpinnerValuePlace4)) {
                    isReSelectedSpinnerPlace4 = true;
                    spinnerPlace4.setSelection(spinnerAdapterPlace4.getPosition(previousSpinnerValuePlace4));
                }
            }
            if (!currentSpinner.equals(SPINNER_PLACE_5)) {
                previousSpinnerValuePlace5 = (SpinnerMemberItem) spinnerPlace5.getSelectedItem();
                int oldItemSelectionPosition = spinnerAdapterPlace5.getPosition(previousSpinnerValuePlace5);
                availableMembersSpinnerPlace5.add(new SpinnerMemberItem(memberToAdd.toString(), memberToAdd));
                if (oldItemSelectionPosition != spinnerAdapterPlace5.getPosition(previousSpinnerValuePlace5)) {
                    isReSelectedSpinnerPlace5 = true;
                    spinnerPlace5.setSelection(spinnerAdapterPlace5.getPosition(previousSpinnerValuePlace5));
                }
            }
        }
        spinnerAdapterPlace1.notifyDataSetChanged();
        spinnerAdapterPlace2.notifyDataSetChanged();
        spinnerAdapterPlace3.notifyDataSetChanged();
        spinnerAdapterPlace4.notifyDataSetChanged();
        spinnerAdapterPlace5.notifyDataSetChanged();
    }

    private void initActivityData() {

        dataViewModel = AppDataViewModel.getInstance();

        MutableLiveData<ArrayList<Member>> selectedMembersForDiscussionListData = dataViewModel.getSelectedMembersForDiscussionList();
        selectedMembersForDiscussionList = selectedMembersForDiscussionListData.getValue();
        if (selectedMembersForDiscussionList != null) {
            totalAvailableMembers = new ArrayList<>(selectedMembersForDiscussionList);
        }

        spinnerPlace1 = findViewById(R.id.place_1_spinner);
        spinnerPlace2 = findViewById(R.id.place_2_spinner);
        spinnerPlace3 = findViewById(R.id.place_3_spinner);
        spinnerPlace4 = findViewById(R.id.place_4_spinner);
        spinnerPlace5 = findViewById(R.id.place_5_spinner);

        completeButton = findViewById(R.id.complete_place_assignment_discussion);

        for (Member availableMember : totalAvailableMembers) {
            availableMembersSpinnerPlace1.add(new SpinnerMemberItem(availableMember.toString(), availableMember));
            availableMembersSpinnerPlace2.add(new SpinnerMemberItem(availableMember.toString(), availableMember));
            availableMembersSpinnerPlace3.add(new SpinnerMemberItem(availableMember.toString(), availableMember));
            availableMembersSpinnerPlace4.add(new SpinnerMemberItem(availableMember.toString(), availableMember));
            availableMembersSpinnerPlace5.add(new SpinnerMemberItem(availableMember.toString(), availableMember));
        }
        availableMembersSpinnerPlace1.add(new SpinnerMemberItem(CLOSED, null));
        availableMembersSpinnerPlace2.add(new SpinnerMemberItem(CLOSED, null));
        availableMembersSpinnerPlace3.add(new SpinnerMemberItem(CLOSED, null));
        availableMembersSpinnerPlace4.add(new SpinnerMemberItem(CLOSED, null));
        availableMembersSpinnerPlace5.add(new SpinnerMemberItem(CLOSED, null));

        spinnerAdapterPlace1 = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, availableMembersSpinnerPlace1);
        spinnerAdapterPlace1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlace1.setAdapter(spinnerAdapterPlace1);

        spinnerAdapterPlace2 = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, availableMembersSpinnerPlace2);
        spinnerAdapterPlace2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlace2.setAdapter(spinnerAdapterPlace2);

        spinnerAdapterPlace3 = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, availableMembersSpinnerPlace3);
        spinnerAdapterPlace3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlace3.setAdapter(spinnerAdapterPlace3);

        spinnerAdapterPlace4 = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, availableMembersSpinnerPlace4);
        spinnerAdapterPlace4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlace4.setAdapter(spinnerAdapterPlace4);

        spinnerAdapterPlace5 = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, availableMembersSpinnerPlace5);
        spinnerAdapterPlace5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlace5.setAdapter(spinnerAdapterPlace5);

        spinnerPlace1.setSelection(spinnerAdapterPlace1.getPosition(new SpinnerMemberItem(CLOSED, null)));
        previousSpinnerValuePlace1 = (SpinnerMemberItem) spinnerPlace1.getSelectedItem();
        isReSelectedSpinnerPlace1 = true;
        spinnerPlace2.setSelection(spinnerAdapterPlace2.getPosition(new SpinnerMemberItem(CLOSED, null)));
        previousSpinnerValuePlace2 = (SpinnerMemberItem) spinnerPlace2.getSelectedItem();
        isReSelectedSpinnerPlace2 = true;
        spinnerPlace3.setSelection(spinnerAdapterPlace3.getPosition(new SpinnerMemberItem(CLOSED, null)));
        previousSpinnerValuePlace3 = (SpinnerMemberItem) spinnerPlace3.getSelectedItem();
        isReSelectedSpinnerPlace3 = true;
        spinnerPlace4.setSelection(spinnerAdapterPlace4.getPosition(new SpinnerMemberItem(CLOSED, null)));
        previousSpinnerValuePlace4 = (SpinnerMemberItem) spinnerPlace4.getSelectedItem();
        isReSelectedSpinnerPlace4 = true;
        spinnerPlace5.setSelection(spinnerAdapterPlace5.getPosition(new SpinnerMemberItem(CLOSED, null)));
        previousSpinnerValuePlace5 = (SpinnerMemberItem) spinnerPlace5.getSelectedItem();
        isReSelectedSpinnerPlace5 = true;
    }

    private void updateButton() {
        completeButton = findViewById(R.id.complete_place_assignment_discussion);
        allMembersPlaced = totalAvailableMembers.size() == 0;
        completeButton.setEnabled(allMembersPlaced);
    }

    private void handleCompleteButtonPressed() {
        if (totalSelectedMembers != null) {

            spinnerPlace1 = findViewById(R.id.place_1_spinner);
            spinnerPlace2 = findViewById(R.id.place_2_spinner);
            spinnerPlace3 = findViewById(R.id.place_3_spinner);
            spinnerPlace4 = findViewById(R.id.place_4_spinner);
            spinnerPlace5 = findViewById(R.id.place_5_spinner);

            if (!((SpinnerMemberItem) spinnerPlace1.getSelectedItem()).getDescription().equals(CLOSED)) {
                for (Member selectedMember : selectedMembersForDiscussionList) {
                    if (selectedMember.getId() == ((SpinnerMemberItem) spinnerPlace1.getSelectedItem()).getMember().getId()) {
                        selectedMember.setPlaceNumber(1);
                    }
                }
            }

            if (!((SpinnerMemberItem) spinnerPlace2.getSelectedItem()).getDescription().equals(CLOSED)) {
                for (Member selectedMember : selectedMembersForDiscussionList) {
                    if (selectedMember.getId() == ((SpinnerMemberItem) spinnerPlace2.getSelectedItem()).getMember().getId()) {
                        selectedMember.setPlaceNumber(2);
                    }
                }
            }

            if (!((SpinnerMemberItem) spinnerPlace3.getSelectedItem()).getDescription().equals(CLOSED)) {
                for (Member selectedMember : selectedMembersForDiscussionList) {
                    if (selectedMember.getId() == ((SpinnerMemberItem) spinnerPlace3.getSelectedItem()).getMember().getId()) {
                        selectedMember.setPlaceNumber(3);
                    }
                }
            }

            if (!((SpinnerMemberItem) spinnerPlace4.getSelectedItem()).getDescription().equals(CLOSED)) {
                for (Member selectedMember : selectedMembersForDiscussionList) {
                    if (selectedMember.getId() == ((SpinnerMemberItem) spinnerPlace4.getSelectedItem()).getMember().getId()) {
                        selectedMember.setPlaceNumber(4);
                    }
                }
            }

            if (!((SpinnerMemberItem) spinnerPlace5.getSelectedItem()).getDescription().equals(CLOSED)) {
                for (Member selectedMember : selectedMembersForDiscussionList) {
                    if (selectedMember.getId() == ((SpinnerMemberItem) spinnerPlace5.getSelectedItem()).getMember().getId()) {
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

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("type", IntentType.ADD_RESULT_TYPE);
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
}