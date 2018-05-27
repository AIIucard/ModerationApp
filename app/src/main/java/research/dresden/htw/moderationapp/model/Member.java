package research.dresden.htw.moderationapp.model;

public class Member {
    private int id;
    private String title;
    private String name;
    private String organisation;
    private String role;

    // For Discussion only
    private int placeNumber;

    public Member(){

    }

    public Member(int id, String title, String name, String organisation, String role){
        this.id = id;
        this.title = title;
        this.name = name;
        this.organisation = organisation;
        this.role = role;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(int placeNumber) {
        this.placeNumber = placeNumber;
    }

    public String toJsonString() {
        return "{ \"country\": \"ES\",\n" +
                "    \"name\": \"Valencia\",\n" +
                "    \"latitude\": 39.466667,\n" +
                "    \"longitude\": -.366667}";
    }
}
