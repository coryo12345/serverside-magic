package servermagic.web.skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import servermagic.ServerMagic;
import servermagic.db.Database;
import servermagic.db.tables.SkillUnlocks;

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
        List<Skill> allSkills = Skills.GetAllSkills();

        // Step 2: build a tree out of the skills
        Map<String, SkillTree> baseNodes = new HashMap<>();
        Map<String, SkillTree> allNodes = new HashMap<>();

        // Find base nodes (skills with no parent)
        List<Skill> remainingSkills = new ArrayList<>(allSkills);
        List<Skill> toRemove = new ArrayList<>();

        for (Skill s : remainingSkills) {
            if (s.parentId() == null) {
                SkillTree node = new SkillTree(s);
                baseNodes.put(s.id(), node);
                allNodes.put(s.id(), node);
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
                if (allNodes.containsKey(s.parentId())) {
                    SkillTree node = new SkillTree(s);
                    allNodes.get(s.parentId()).branches.add(node);
                    allNodes.put(s.id(), node);
                    toRemove.add(s);
                    changed = true;
                }
            }
            remainingSkills.removeAll(toRemove);
        }

        if (!remainingSkills.isEmpty()) {
            ServerMagic.LOGGER.warn("Some skills could not be placed in a tree (orphaned or circular dependencies): "
                    + remainingSkills);
        }

        return new ArrayList<SkillTree>(baseNodes.values());
    }

    public static void UpdateSpellAvailabiltyForPlayer(Database db, String player) {
        Optional<List<SkillUnlocks>> su = SkillUnlocks.GetAllPlayerUnlockedSkills(db, player, true);
        if (su.isEmpty()) {
            return;
        }
        
        List<SkillTree> trees = GetTrees();

        // we need to recurse over the nodes in each tree. 
        // For skill tree node, check if the player has the skillunlock for it.
        // If the player does not have the skillunlock, do not look at any of this nodes children.
        // If the player has the unlock, and it is marked as available, continue on with each of the children of this node.
        // If the player has the unlock, but it is not marked as available, save this skill to a list, we need to mark it as available. Continue on with all children of this node
    }
}
