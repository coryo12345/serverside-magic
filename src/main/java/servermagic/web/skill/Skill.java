package servermagic.web.skill;

public class Skill {
    public String id;
    public String name;
    public String description;
    public String parentId;
    public String advancementResourceLocation;

    public Skill(String id, String name, String description, String parentId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.parentId = parentId;
    }

    public String id() {
        return this.id;
    }

    public String name() {
        return this.name;
    }

    public String description() {
        return this.description;
    }

    public String parentId() {
        return this.parentId;
    }

    public String advancementResourceLocation() {
        return this.advancementResourceLocation;
    }

    public Skill setAdvancement(String resourceLocation) {
        this.advancementResourceLocation = resourceLocation;
        return this;
    }
}
