package servermagic.web.skill;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import servermagic.ServerMagic;

public class SkillTree {
    public Skill skill;
    public boolean unlocked;
    public List<SkillTree> branches;

    public SkillTree(Skill skill) {
        this.skill = skill;
        this.branches = new ArrayList<>();
        this.unlocked = false;
    }

    public static List<SkillTree> GetTrees() {
        // Step 1: get all the available skills
        Field[] declaredFields = Skills.class.getDeclaredFields();
        List<Skill> allSkills = new ArrayList<>();
        for (Field field : declaredFields) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                try {
                    Object o = field.get(null);
                    if (o instanceof Skill s) {
                        allSkills.add(s);
                    }
                } catch (NullPointerException | IllegalAccessException | IllegalArgumentException e) {
                    // shouldn't happen since we're using static fields
                    ServerMagic.LOGGER.error("Failed to access skill using reflection: " + e.getStackTrace());
                }
            }
        }

        // Step 2: build a tree out of the skills
        Map<Skill, SkillTree> baseNodes = new HashMap<>();
        Map<Skill, SkillTree> allNodes = new HashMap<>();

        // Find base nodes (skills with no parent)
        List<Skill> remainingSkills = new ArrayList<>(allSkills);
        List<Skill> toRemove = new ArrayList<>();

        for (Skill s : remainingSkills) {
            if (s.parent() == null) {
                SkillTree node = new SkillTree(s);
                baseNodes.put(s, node);
                allNodes.put(s, node);
                toRemove.add(s);
            }
        }
        remainingSkills.removeAll(toRemove);

        // Iteratively add children to their parents
        boolean changed = true;
        while (changed && !remainingSkills.isEmpty()) {
            changed = false;
            toRemove.clear();
            for (Skill s : remainingSkills) {
                if (allNodes.containsKey(s.parent())) {
                    SkillTree node = new SkillTree(s);
                    allNodes.get(s.parent()).branches.add(node);
                    allNodes.put(s, node);
                    toRemove.add(s);
                    changed = true;
                }
            }
            remainingSkills.removeAll(toRemove);
        }

        if (!remainingSkills.isEmpty()) {
            ServerMagic.LOGGER.warn("Some skills could not be placed in a tree (orphaned or circular dependencies): " + remainingSkills);
        }

        return new ArrayList<SkillTree>(baseNodes.values());
    }
}
