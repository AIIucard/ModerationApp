package research.dresden.htw.moderationapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "id",
        "title",
        "time",
        "members"
})
public class Discussion {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("time")
    private Integer time;

    @JsonProperty("members")
    private ArrayList<Member> memberList;

    public Discussion() {
        // Dummy constructor or json parser
    }

    public Discussion(Integer id, String title, Integer time, ArrayList<Member> memberList){
        this.title = title;
        this.time = time;
        this.memberList = memberList;
    }

    /**
     *
     * @return
     * The ID
     */
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The title
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The time
     */
    @JsonProperty("time")
    public Integer getTime() {
        return time;
    }

    /**
     *
     * @param time
     * The time
     */
    @JsonProperty("time")
    public void setTime(Integer time) {
        this.time = time;
    }

    /**
     *
     * @return
     * The members
     */
    @JsonProperty("members")
    public ArrayList<Member> getMemberList() {
        return memberList;
    }

    /**
     *
     * @param memberList
     * The members
     */
    @JsonProperty("members")
    public void setMemberList(ArrayList<Member> memberList) {
        this.memberList = memberList;
    }
}
