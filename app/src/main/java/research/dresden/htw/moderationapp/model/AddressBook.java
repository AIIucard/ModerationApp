package research.dresden.htw.moderationapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "members"
})
public class AddressBook {

    @JsonProperty("members")
    private ArrayList<Member> memberList;

    public AddressBook() {
        // Dummy constructor or json parser
    }

    public AddressBook(ArrayList<Member> memberList){
        this.memberList = memberList;
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
    public void setMemberList(ArrayList<Member> memberList) {
        this.memberList = memberList;
    }
}
