package research.dresden.htw.moderationapp.activities.discussion;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import research.dresden.htw.moderationapp.R;
import research.dresden.htw.moderationapp.loader.DiscussionListViewAdapter;
import research.dresden.htw.moderationapp.loader.MemberListViewAdapter;
import research.dresden.htw.moderationapp.model.AppDataViewModel;
import research.dresden.htw.moderationapp.model.Discussion;
import research.dresden.htw.moderationapp.model.ItemPosition;
import research.dresden.htw.moderationapp.model.Member;
import research.dresden.htw.moderationapp.model.SubItemPosition;

public class DiscussionAdministrationActivity extends AppCompatActivity {

    private Boolean isAddDiscussionActive = true;
    private Boolean isAddMemberActive = true;
    private Boolean isEditDiscussionActive = false;
    private Boolean isEditMemberActive = false;
    private Boolean isDeleteDiscussionActive = false;
    private Boolean isDeleteMemberActive = false;

    private FloatingActionButton addButton;
    private FloatingActionButton editButton;
    private FloatingActionButton deleteButton;

    private ExpandableListView discussion_list_view;
    private DiscussionListViewAdapter discussionListAdapter;

    private AppDataViewModel dataViewModel;
    private List<ItemPosition> selectedItemPositionList = new ArrayList<>();
    private List<SubItemPosition> selectedSubItemPositionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_administration);

        final MutableLiveData<ArrayList<Discussion>> discussionListData = AppDataViewModel.getInstance().getDiscussionList();
        ArrayList<Discussion> discussionList = discussionListData.getValue();
        discussionListAdapter = new DiscussionListViewAdapter(this, discussionList);
        discussion_list_view = findViewById(R.id.discussion_expandable_list_view);
        discussion_list_view.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        discussion_list_view.setAdapter(discussionListAdapter);
        addButton = findViewById(R.id.fab_action_add);
        addButton.setEnabled(isAddDiscussionActive);
        editButton = findViewById(R.id.fab_action_edit);
        editButton.setEnabled(isEditDiscussionActive);
        deleteButton = findViewById(R.id.fab_action_delete);
        deleteButton.setEnabled(isDeleteDiscussionActive);
        dataViewModel = AppDataViewModel.getInstance();

        discussion_list_view.setOnGroupClickListener(
            new ExpandableListView.OnGroupClickListener() {

                @Override
                public boolean onGroupClick(ExpandableListView parent, View view, int groupPosition, long id) {
                    adjustRowGroupItemRowColor(parent, view, groupPosition);
                    if(selectedItemPositionList.size() == 1){
                        Discussion selectedDiscussion = (Discussion) discussionListAdapter.getGroup(groupPosition);
                        dataViewModel.setLastSelectedDiscussion(selectedDiscussion);
                    }
                    updateButtons();
                    return false;
                }
        });

        discussion_list_view.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

           @Override
           public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id) {
               adjustChildItemRowColor(parent, view, groupPosition, childPosition);
               if(selectedItemPositionList.size() == 1){
                   Member selectedMember = (Member) discussionListAdapter.getChild(groupPosition, childPosition);
                   dataViewModel.setLastSelectedMember(selectedMember);
               }
               updateButtons();
               return false;
           }
        });

        dataViewModel.getDiscussionList().observe(this, new Observer<ArrayList<Discussion>>() {

            @Override
            public void onChanged(@Nullable ArrayList<Discussion> discussions) {
                ((BaseAdapter) discussion_list_view.getAdapter()).notifyDataSetChanged();
            }
        });
    }

    private void updateButtons() {
        addButton = findViewById(R.id.fab_action_add);
        editButton = findViewById(R.id.fab_action_edit);
        deleteButton = findViewById(R.id.fab_action_delete);

        if(selectedItemPositionList.size() == 0 && selectedSubItemPositionList.size() == 0){
            isAddDiscussionActive = true;
            isEditDiscussionActive = false;
            isDeleteDiscussionActive = false;
            isAddMemberActive = false;
            isEditMemberActive = false;
            isDeleteMemberActive = false;
            addButton.setTitle("Neue Diskussion hinzufügen");
            editButton.setTitle("Diskussion bearbeiten");
            deleteButton.setTitle("Diskussion löschen");
        } else if(selectedItemPositionList.size() == 1 && selectedSubItemPositionList.size() == 0){
            isAddDiscussionActive = false;
            isEditDiscussionActive = true;
            isDeleteDiscussionActive = true;
            isAddMemberActive = true;
            isEditMemberActive = false;
            isDeleteMemberActive = false;
        } else if(selectedItemPositionList.size() == 0 && selectedSubItemPositionList.size() == 1){
            isAddDiscussionActive = false;
            isEditDiscussionActive = false;
            isDeleteDiscussionActive = false;
            isAddMemberActive = false;
            isEditMemberActive = true;
            isDeleteMemberActive = true;
        } else if(selectedItemPositionList.size() > 0 && selectedSubItemPositionList.size() > 0) {
            isAddDiscussionActive = false;
            isEditDiscussionActive = false;
            isDeleteDiscussionActive = false;
            isAddMemberActive = false;
            isEditMemberActive = false;
            isDeleteMemberActive = true;
        }

        if(isAddMemberActive){
            addButton.setEnabled(isAddMemberActive);
        } else {
            addButton.setEnabled(isAddDiscussionActive);
        }
        if(isEditMemberActive){
            editButton.setEnabled(isEditMemberActive);
        } else {
            editButton.setEnabled(isEditDiscussionActive);
        }
        if(isDeleteMemberActive){
            deleteButton.setEnabled(isDeleteMemberActive);
        } else {
            deleteButton.setEnabled(isDeleteDiscussionActive);
        }
    }

    private void adjustRowGroupItemRowColor(AdapterView<?> parent, View view, int position) {
        TextView idTextView = view.findViewById(R.id.id_text_view_discussion);
        TextView titleTextView = view.findViewById(R.id.title_text_view_discussion);
        TextView timeTextView = view.findViewById(R.id.time_text_view_discussion);
        if (!selectedItemPositionList.contains(new ItemPosition(position))) {
            selectedItemPositionList.add(new ItemPosition(position));
            parent.getChildAt(position).setBackgroundColor(Color.parseColor("#4285f4"));
            idTextView.setTextColor(Color.parseColor("#FFFFFF"));
            titleTextView.setTextColor(Color.parseColor("#FFFFFF"));
            timeTextView.setTextColor(Color.parseColor("#FFFFFF"));
        } else{
            selectedItemPositionList.remove(new ItemPosition(position));
            parent.getChildAt(position).setBackgroundColor(Color.parseColor("#FFFFFF"));
            idTextView.setTextColor(Color.parseColor("#ff000000"));
            titleTextView.setTextColor(Color.parseColor("#ff000000"));
            timeTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.darker_gray));
        }
    }

    private void adjustChildItemRowColor(ExpandableListView parent, View view, int groupPosition, int position) {
        TextView placeNumberTextView = view.findViewById(R.id.place_number_text_view_member_discussion);
        TextView idTextView = view.findViewById(R.id.id_text_view_member_discussion);
        TextView titleTextView = view.findViewById(R.id.title_text_view_member_discussion);
        TextView nameTextView = view.findViewById(R.id.name_text_view_member_discussion);
        TextView organisationTextView = view.findViewById(R.id.organisation_text_view_member_discussion);
        TextView roleTextView = view.findViewById(R.id.role_text_view_member_discussion);
        if (!selectedSubItemPositionList.contains(new SubItemPosition(new ItemPosition(groupPosition), position))) {
            selectedSubItemPositionList.add(new SubItemPosition(new ItemPosition(groupPosition), position));
            DiscussionListViewAdapter listViewAdapter = ((DiscussionListViewAdapter)parent.getExpandableListAdapter());
            // TODO: Debug from here
            ArrayList<SubItemPosition> coloredSubItemPositions = listViewAdapter.getColoredSubItemPositionList();
            coloredSubItemPositions.add(new SubItemPosition(new ItemPosition(groupPosition), position));
            ((DiscussionListViewAdapter)parent.getExpandableListAdapter()).setColoredSubItemPositionList(coloredSubItemPositions);
            listViewAdapter.notifyDataSetChanged();
            placeNumberTextView.setTextColor(Color.parseColor("#FFFFFF"));
            idTextView.setTextColor(Color.parseColor("#FFFFFF"));
            titleTextView.setTextColor(Color.parseColor("#FFFFFF"));
            nameTextView.setTextColor(Color.parseColor("#FFFFFF"));
            organisationTextView.setTextColor(Color.parseColor("#FFFFFF"));
            roleTextView.setTextColor(Color.parseColor("#FFFFFF"));
        } else{
            selectedSubItemPositionList.remove(new SubItemPosition(new ItemPosition(groupPosition), position));
            DiscussionListViewAdapter listViewAdapter = ((DiscussionListViewAdapter)parent.getExpandableListAdapter());
            ArrayList<SubItemPosition> coloredSubItemPositions = listViewAdapter.getColoredSubItemPositionList();
            coloredSubItemPositions.remove(new SubItemPosition(new ItemPosition(groupPosition), position));
            ((DiscussionListViewAdapter)parent.getExpandableListAdapter()).setColoredSubItemPositionList(coloredSubItemPositions);
            listViewAdapter.notifyDataSetChanged();
            placeNumberTextView.setTextColor(Color.parseColor("#ff000000"));
            idTextView.setTextColor(Color.parseColor("#ff000000"));
            titleTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.darker_gray));
            nameTextView.setTextColor(Color.parseColor("#ff000000"));
            organisationTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.darker_gray));
            roleTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.darker_gray));
        }
    }

}
