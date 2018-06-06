package research.dresden.htw.moderationapp.utils;

import java.util.ArrayList;

import research.dresden.htw.moderationapp.model.Member;
import research.dresden.htw.moderationapp.model.Title;

public class AppUtils {

    public static int getNextId(ArrayList<Member> memberList){
        int highestID = 1;
        for (Member currentMember : memberList) {
            if(currentMember.getId() > highestID){
                highestID = currentMember.getId();
            }
        }
        return highestID + 1;
    }

    public static ArrayList<String> getTitlesAsList(){
        ArrayList<String> titleList = new ArrayList<>();
        titleList.add("--");
        titleList.add(Title.DIPLOMA_OF_ARTS);
        titleList.add(Title.DIPLOMA_OF_LANGUAGE_STUDIES);
        titleList.add(Title.DIPLOMA_OF_LANGUAGES);
        titleList.add(Title.DIPLOMA_OF_SOCIAL_SCIENCES);
        titleList.add(Title.DIPLOMA_OF_EDUCATION);
        titleList.add(Title.DIPLOMA_OF_MUSIC);
        return titleList;
    }
}
