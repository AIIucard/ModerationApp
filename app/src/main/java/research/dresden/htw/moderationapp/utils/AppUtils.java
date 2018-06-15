package research.dresden.htw.moderationapp.utils;

import java.util.ArrayList;

import research.dresden.htw.moderationapp.model.Discussion;
import research.dresden.htw.moderationapp.model.Member;
import research.dresden.htw.moderationapp.model.Title;

public class AppUtils {

    public static int getNextMemberId(ArrayList<Member> memberList) {
        int highestID = 1;
        for (Member currentMember : memberList) {
            if(currentMember.getId() > highestID){
                highestID = currentMember.getId();
            }
        }
        return highestID + 1;
    }

    public static int getNextDiscussionId(ArrayList<Discussion> discussionList) {
        int highestID = 1;
        for (Discussion currentDiscussion : discussionList) {
            if (currentDiscussion.getId() > highestID) {
                highestID = currentDiscussion.getId();
            }
        }
        return highestID + 1;
    }

    public static ArrayList<String> getTitlesAsList(){
        ArrayList<String> titleList = new ArrayList<>();
        titleList.add("--");
        titleList.add(Title.WITHOUT);
        titleList.add(Title.BACHELOR);
        titleList.add(Title.MASTER);
        titleList.add(Title.DIPLOM);
        titleList.add(Title.DOCTOR);
        return titleList;
    }

    public static boolean isTwoMemberListsWithSameValues(ArrayList<Member> list1, ArrayList<Member> list2) {
        //null checking
        if (list1 == null && list2 == null)
            return true;
        if (list1 == null || list2 == null)
            return false;

        if (list1.size() != list2.size())
            return false;
        for (Member itemList1 : list1) {
            if (!list2.contains(itemList1))
                return false;
        }

        return true;
    }
}
