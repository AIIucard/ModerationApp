package research.dresden.htw.moderationapp.loader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import research.dresden.htw.moderationapp.R;
import research.dresden.htw.moderationapp.model.Discussion;
import research.dresden.htw.moderationapp.model.ItemPosition;
import research.dresden.htw.moderationapp.model.Member;
import research.dresden.htw.moderationapp.model.SubItemPosition;

public class DiscussionListViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<Discussion> discussionList;
    private ArrayList<SubItemPosition> coloredSubItemPositionList = new ArrayList<>();

    public DiscussionListViewAdapter(@NonNull Context context, ArrayList<Discussion> discussionList) {
        this.context = context;
        this.discussionList = discussionList;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<Member> memberList = discussionList.get(groupPosition).getMemberList();
        return memberList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View memberView, ViewGroup parent) {

        Member memberItem = (Member) getChild(groupPosition, childPosition);
        if (memberView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (infalInflater != null) {
                memberView = infalInflater.inflate(R.layout.member_discussion_row, null);
            }
        }

        if (memberView != null) {
            TextView placeNumberTextView = memberView.findViewById(R.id.place_number_text_view_member_discussion);
            TextView idTextView = memberView.findViewById(R.id.id_text_view_member_discussion);
            ImageView imageView = memberView.findViewById(R.id.image_view_member_discussion);
            TextView titleTextView = memberView.findViewById(R.id.title_text_view_member_discussion);
            TextView nameTextView = memberView.findViewById(R.id.name_text_view_member_discussion);
            TextView organisationTextView = memberView.findViewById(R.id.organisation_text_view_member_discussion);
            TextView roleTextView = memberView.findViewById(R.id.role_text_view_member_discussion);

            if (memberItem != null) {
                placeNumberTextView.setText("Platz: " + String.valueOf(memberItem.getPlaceNumber()));
                idTextView.setText("ID: " + String.valueOf(memberItem.getId()));
                imageView.setImageResource(R.drawable.member);
                titleTextView.setText(String.valueOf(memberItem.getTitle()));
                nameTextView.setText(String.valueOf(memberItem.getName()));
                organisationTextView.setText(String.valueOf(memberItem.getOrganisation()));
                roleTextView.setText(String.valueOf(memberItem.getRole()));
            }
        }
        SubItemPosition currentSubItemPosition = null;

        for ( SubItemPosition subItemPosition : coloredSubItemPositionList) {
            if(subItemPosition.equals(new SubItemPosition(new ItemPosition(groupPosition), childPosition))){
                currentSubItemPosition = subItemPosition;
            }
        }
        if(currentSubItemPosition != null && currentSubItemPosition.isColored()){
            memberView.setBackgroundColor(Color.parseColor("#4285f4"));
            notifyDataSetChanged();
        } else {
            memberView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            notifyDataSetChanged();
        }

        return memberView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        ArrayList<Member> memberList = discussionList.get(groupPosition).getMemberList();
        return memberList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return discussionList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return discussionList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View discussionView,
                             ViewGroup parent) {

        Discussion discussionItem = (Discussion) getGroup(groupPosition);
        if (discussionView == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inf != null) {
                discussionView = inf.inflate(R.layout.discussion_row, null);
            }
        }

        if(discussionView != null) {
            TextView idTextView = discussionView.findViewById(R.id.id_text_view_discussion);
            ImageView imageView = discussionView.findViewById(R.id.image_view_discussion);
            TextView titleTextView = discussionView.findViewById(R.id.title_text_view_discussion);
            TextView timeView = discussionView.findViewById(R.id.time_text_view_discussion);
            if(discussionItem != null){
                idTextView.setText(String.valueOf(discussionItem.getId()));
                imageView.setImageResource(R.drawable.discussion);
                titleTextView.setText("Thema: " + String.valueOf(discussionItem.getTitle()));
                timeView.setText("Dauer: " + String.valueOf(discussionItem.getTime()) + " min");
            }
        }


        return discussionView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public ArrayList<SubItemPosition> getColoredSubItemPositionList() {
        return coloredSubItemPositionList;
    }

    public void setColoredSubItemPositionList(ArrayList<SubItemPosition> coloredSubItemPositionList) {
        this.coloredSubItemPositionList = coloredSubItemPositionList;
    }
}
