package research.dresden.htw.moderationapp.loader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

    public MemberListViewAdapter(@NonNull Context context, ArrayList<Member> members) {
        super(context, R.layout.member_row, members);
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

        if (memberItem != null) {
            idTextView.setText(String.valueOf(memberItem.getId()));
            imageView.setImageResource(R.drawable.member);
            titleTextView.setText(String.valueOf(memberItem.getTitle()));
            nameTextView.setText(String.valueOf(memberItem.getName()));
            organisationTextView.setText(String.valueOf(memberItem.getOrganisation()));
            roleTextView.setText(String.valueOf(memberItem.getRole()));
        }

        return memberView;
    }
}
