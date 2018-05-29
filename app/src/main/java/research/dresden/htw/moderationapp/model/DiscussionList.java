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
public class DiscussionList {

    @JsonProperty("discussions")
    private ArrayList<Discussion> discussionList;

    public DiscussionList() {
        // Dummy constructor or json parser
    }

    public DiscussionList(ArrayList<Discussion> discussionList){
        this.discussionList = discussionList;
    }

    /**
     *
     * @return
     * The discussions
     */
    @JsonProperty("discussions")
    public ArrayList<Discussion> getDiscussionList() {
        return discussionList;
    }

    /**
     *
     * @param discussions
     * The discussions
     */
    public void setDiscussionList(ArrayList<Discussion> discussionList) {
        this.discussionList = discussionList;
    }
}
