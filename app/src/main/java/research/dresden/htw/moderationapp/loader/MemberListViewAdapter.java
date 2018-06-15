package research.dresden.htw.moderationapp.loader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import research.dresden.htw.moderationapp.R;
import research.dresden.htw.moderationapp.model.Member;

public class MemberListViewAdapter extends ArrayAdapter<Member>{

    int numberOfSelectedItems = 0;
    private ArrayList<Boolean> isPositionSelectedList;
    private Context context;

    public MemberListViewAdapter(@NonNull Context context, ArrayList<Member> members) {
        super(context, R.layout.member_row, members);
        this.context = context;
        isPositionSelectedList = new ArrayList<>();
        for (int i = 0; i < members.size(); ++i) {
            isPositionSelectedList.add(false);
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater memberInflater = LayoutInflater.from(getContext());
        @SuppressLint("ViewHolder") View memberView = memberInflater.inflate(R.layout.member_row, parent, false);
        Member memberItem = getItem(position);

        TextView idTextView = memberView.findViewById(R.id.id_text_view_member);
        ImageView imageView = memberView.findViewById(R.id.image_view_member);
        TextView titleTextView = memberView.findViewById(R.id.title_text_view_member);
        TextView nameTextView = memberView.findViewById(R.id.name_text_view_member);
        TextView organisationTextView = memberView.findViewById(R.id.organisation_text_view_member);
        TextView roleTextView = memberView.findViewById(R.id.role_text_view_member);

        idTextView.setText("ID: " + String.valueOf(memberItem.getId()));
        imageView.setImageResource(R.drawable.member);
        titleTextView.setText(String.valueOf(memberItem.getTitle()));
        nameTextView.setText(String.valueOf(memberItem.getName()));
        organisationTextView.setText(String.valueOf(memberItem.getOrganisation()));
        roleTextView.setText(String.valueOf(memberItem.getRole()));

        if (isPositionSelectedList.get(position)) {
            memberView.setBackgroundColor(Color.parseColor("#4285f4"));
            idTextView.setTextColor(Color.parseColor("#FFFFFF"));
            titleTextView.setTextColor(Color.parseColor("#FFFFFF"));
            nameTextView.setTextColor(Color.parseColor("#FFFFFF"));
            organisationTextView.setTextColor(Color.parseColor("#FFFFFF"));
            roleTextView.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            memberView.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_list_view_row));
            idTextView.setTextColor(ContextCompat.getColor(context, R.color.list_view_row_id_color));
            titleTextView.setTextColor(ContextCompat.getColor(context, R.color.member_row_title_color));
            nameTextView.setTextColor(ContextCompat.getColor(context, R.color.member_row_name_color));
            organisationTextView.setTextColor(ContextCompat.getColor(context, R.color.member_row_organisation_color));
            roleTextView.setTextColor(ContextCompat.getColor(context, R.color.member_row_role_color));
        }
        return memberView;
    }

    public void handleAddMember() {
        isPositionSelectedList.add(false);
    }

    public void handleRemoveMember(int position) {
        if (position < isPositionSelectedList.size()) {
            isPositionSelectedList.remove(position);
        }
    }

    public void handleSelectionMember(int position) {
        if (position < isPositionSelectedList.size()) {
            if (!isPositionSelectedList.get(position)) {
                ++numberOfSelectedItems;
            } else {
                --numberOfSelectedItems;
            }
            isPositionSelectedList.set(position, !isPositionSelectedList.get(position));
        }
    }

    public int getNumberOfSelectedItems() {
        return numberOfSelectedItems;
    }

    public ArrayList<Boolean> getSelectedItemsList() {
        return isPositionSelectedList;
    }
}
