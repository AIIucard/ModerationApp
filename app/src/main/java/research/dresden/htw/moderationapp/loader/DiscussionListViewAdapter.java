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
import research.dresden.htw.moderationapp.model.Member;

public class DiscussionListViewAdapter extends BaseExpandableListAdapter {

    private final Context context;
    private final ArrayList<Discussion> discussionList;

    public DiscussionListViewAdapter(@NonNull Context context, ArrayList<Discussion> discussionList) {
        this.context = context;
        this.discussionList = discussionList;
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
        return memberView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        ArrayList<Member> memberList = discussionList.get(groupPosition).getMemberList();
        return memberList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (discussionList.size() > 0 && discussionList.get(groupPosition) != null) {
            return discussionList.get(groupPosition);
        } else {
            return null;
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (discussionList.size() > 0 && discussionList.get(groupPosition) != null && discussionList.get(groupPosition).getMemberList() != null) {
            ArrayList<Member> memberList = discussionList.get(groupPosition).getMemberList();
            return memberList.get(childPosition);
        } else {
            return null;
        }
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
    public View getGroupView(int groupPosition, boolean isExpanded, View discussionView,
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
            TextView timeTextView = discussionView.findViewById(R.id.time_text_view_discussion);
            if(discussionItem != null){
                idTextView.setText(String.valueOf(discussionItem.getId()));
                imageView.setImageResource(R.drawable.discussion);
                titleTextView.setText("Thema: " + String.valueOf(discussionItem.getTitle()));
                timeTextView.setText("Dauer: " + String.valueOf(discussionItem.getTime()) + " min");
            }
            if (isExpanded) {
                discussionView.setBackgroundColor(Color.parseColor("#4285f4"));
                idTextView.setTextColor(Color.parseColor("#FFFFFF"));
                titleTextView.setTextColor(Color.parseColor("#FFFFFF"));
                timeTextView.setTextColor(Color.parseColor("#FFFFFF"));
            } else {
                discussionView.setBackgroundColor(Color.parseColor("#d9d7d8"));
                idTextView.setTextColor(Color.parseColor("#ff000000"));
                titleTextView.setTextColor(Color.parseColor("#ff000000"));
                timeTextView.setTextColor(Color.parseColor("#a9a9a9"));
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
}
