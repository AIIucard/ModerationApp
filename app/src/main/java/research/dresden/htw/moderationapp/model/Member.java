package research.dresden.htw.moderationapp.model;

public class Member {
    private Integer id;
    private String title;
    private String name;
    private String organisation;
    private String description;

    public Member(Integer id, String title, String name, String organisation, String description){
        this.title = title;
        this.name = name;
        this.organisation = organisation;
        this.description = description;
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

    public String toJsonString() {
        return "{ \"country\": \"ES\",\n" +
                "    \"name\": \"Valencia\",\n" +
                "    \"latitude\": 39.466667,\n" +
                "    \"longitude\": -.366667}";
    }
}
