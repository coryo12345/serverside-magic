package servermagic.web.skill;

public class Skill {
    public String id;
    public String name;
    public String description;
    public String parentId;
    public String advancementResourceLocation;
    public String advancementName;
    public String unlockDescription;
    public String tabTitle;

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

    public String advancementName() {
        return this.advancementName;
    }

    public Skill setAdvancement(String resourceLocation, String name) {
        this.advancementResourceLocation = resourceLocation;
        this.advancementName = name;
        return this;
    }

    public Skill setUnlockDescription(String unlockDescription) {
        this.unlockDescription = unlockDescription;
        return this;
    }

    public Skill setTabTitle(String title) {
        this.tabTitle = title;
        return this;
    }
}
