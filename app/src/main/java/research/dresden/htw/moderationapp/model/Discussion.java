package research.dresden.htw.moderationapp.model;

import java.util.ArrayList;

public class Discussion {
    private Integer id;
    private String title;
    private Integer time;
    private ArrayList<Member> memberList;

    public Discussion(Integer id, String title, Integer time, ArrayList<Member> memberList){
        this.title = title;
        this.time = time;
        this.memberList = memberList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public ArrayList<Member> getMemberList() {
        return memberList;
    }

    public void setMemberList(ArrayList<Member> memberList) {
        this.memberList = memberList;
    }
}
