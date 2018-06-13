package research.dresden.htw.moderationapp.model;

public class SpinnerMemberItem {

    String description;
    Member member;

    public SpinnerMemberItem(String description, Member member) {
        this.description = description;
        this.member = member;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @Override
    public String toString() {
        if (member != null) {
            return member.toString();
        }
        return description;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (!(other instanceof SpinnerMemberItem))
            return false;
        SpinnerMemberItem otherSpinnerItem = (SpinnerMemberItem) other;
        if (otherSpinnerItem.getDescription().equals(description))
            return true;

        return false;
    }

}
