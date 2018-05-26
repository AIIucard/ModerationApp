package research.dresden.htw.moderationapp.model;

public class Member {
    private String title;
    private String name;
    private String organisation;
    private String description;

    public Member(String title, String name, String organisation, String description){
        this.title = title;
        this.name = name;
        this.organisation = organisation;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
