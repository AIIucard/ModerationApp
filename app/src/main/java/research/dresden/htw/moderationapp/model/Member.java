package research.dresden.htw.moderationapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "id",
        "title",
        "name",
        "organisation",
        "role",
        "placeNumber"
})
public class Member {
    @JsonProperty("id")
    private int id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("name")
    private String name;

    @JsonProperty("organisation")
    private String organisation;

    @JsonProperty("role")
    private String role;

    @JsonProperty("placeNumber")
    // For Discussion only
    private int placeNumber;

    public Member() {
        // Dummy constructor or json parser
    }

    public Member(int id, String title, String name, String organisation, String role){
        this.id = id;
        this.title = title;
        this.name = name;
        this.organisation = organisation;
        this.role = role;
        placeNumber = -1;
    }

    // For Discussion only
    public Member(int id, String title, String name, String organisation, String role, int placeNumber){
        this.id = id;
        this.title = title;
        this.name = name;
        this.organisation = organisation;
        this.role = role;
        this.placeNumber = placeNumber;
    }

    /**
     *
     * @return
     * The ID
     */
    @JsonProperty("id")
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     * The ID
     */
    @JsonProperty("id")
    public void setId(int id) {
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
     * The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The organisation
     */
    @JsonProperty("organisation")
    public String getOrganisation() {
        return organisation;
    }

    /**
     *
     * @param organisation
     * The ID
     */
    @JsonProperty("organisation")
    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    /**
     *
     * @return
     * The role
     */
    @JsonProperty("role")
    public String getRole() {
        return role;
    }

    /**
     *
     * @param role
     * The role
     */
    @JsonProperty("role")
    public void setRole(String role) {
        this.role = role;
    }

    /**
     *
     * @return
     * The place number
     */
    @JsonProperty("placeNumber")
    public int getPlaceNumber() {
        return placeNumber;
    }

    /**
     *
     * @param placeNumber
     * The place number
     */
    @JsonProperty("placeNumber")
    public void setPlaceNumber(int placeNumber) {
        this.placeNumber = placeNumber;
    }

    @Override
    public boolean equals(Object object) {
        boolean isSame = false;

        if (object != null && object instanceof Member) {
            Member member = (Member) object;
            isSame = ((this.id == member.getId()) && (this.title.equals(member.getTitle())) && (this.name.equals(member.getName())) && (this.organisation.equals(member.getOrganisation())) && (this.role.equals(member.getRole())));
        }

        return isSame;
    }

    @Override
    public String toString() {
        String s = "";
        s += "[" + getId() + "] ";
        s += getTitle() + "";
        s += getName() + "; ";
        s += getOrganisation();
        return s;
    }
}
