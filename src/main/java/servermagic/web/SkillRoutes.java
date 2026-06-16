package servermagic.web;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import io.javalin.Javalin;
import net.minecraft.server.MinecraftServer;
import servermagic.db.Database;
import servermagic.db.tables.Config;
import servermagic.db.tables.SkillUnlocks;
import servermagic.web.skill.Skill;
import servermagic.web.skill.SkillTree;
import servermagic.web.skill.Skills;

public class SkillRoutes extends RouteGroup {

    public SkillRoutes(Javalin app, Database db, MinecraftServer server) {
        super(app, db, server);
    }

    @Override
    public void registerRoutes() {
        this.requireAuthForGroup("/api/skills/");

        app.get("/api/skills/tree", ctx -> {
            String username = this.getAuthSubject(ctx);
            boolean superuserMode = "true".equals(ctx.queryParam("superuser")) && Config.IsSuperuser(db, username);

            // Ensure availability is synced
            SkillTree.UpdateSpellAvailabiltyForPlayer(db, username);

            // first we get the skill trees setup
            List<SkillTree> allTrees = SkillTree.GetTrees();

            // filter to specified base nodes (trees)
            List<SkillTree> trees = allTrees.stream()
                    .filter(tree -> List.of(Skills.UNLOCK_MAGIC_ELEMENTAL, Skills.UNLOCK_MAGIC_BUILDING,
                            Skills.UNLOCK_MAGIC_EFFECTS, Skills.UNLOCK_MAGIC_UTILITY).contains(tree.skill))
                    .toList();

            List<String> unlockedSkillIds;
            if (superuserMode) {
                unlockedSkillIds = Skills.GetAllSkills().stream().map(s -> s.id()).toList();
            } else {
                Optional<List<SkillUnlocks>> unlocks = SkillUnlocks.GetAllPlayerUnlockedSkills(db, username, false);
                if (unlocks.isEmpty()) {
                    ctx.status(500).result("Unable to determine unlocked skills for player");
                    return;
                }
                unlockedSkillIds = unlocks.get().stream().map(u -> u.skill).toList();
            }

            for (SkillTree tree : trees) {
                this.markUnlocked(tree, unlockedSkillIds);
            }

            ctx.status(200).json(trees);
        });

        app.get("/api/skills/secrets", ctx -> {
            String username = this.getAuthSubject(ctx);
            boolean superuserMode = "true".equals(ctx.queryParam("superuser")) && Config.IsSuperuser(db, username);

            SkillTree.UpdateSpellAvailabiltyForPlayer(db, username);

            List<Map<String, Object>> secrets;
            if (superuserMode) {
                secrets = Skills.GetAllSkills().stream()
                        .filter(s -> Skills.SECRETS.id().equals(s.parentId()))
                        .map(s -> Map.<String, Object>of(
                                "id", s.id,
                                "name", s.name,
                                "description", s.description))
                        .toList();
            } else {
                Optional<List<SkillUnlocks>> unlocks = SkillUnlocks.GetAllPlayerUnlockedSkills(db, username, false);
                if (unlocks.isEmpty()) {
                    ctx.status(500).result("Unable to determine unlocked skills for player");
                    return;
                }
                Set<String> unlockedIds = unlocks.get().stream()
                        .map(u -> u.skill)
                        .collect(Collectors.toSet());
                secrets = Skills.GetAllSkills().stream()
                        .filter(s -> Skills.SECRETS.id().equals(s.parentId()))
                        .filter(s -> unlockedIds.contains(s.id()))
                        .map(s -> Map.<String, Object>of(
                                "id", s.id,
                                "name", s.name,
                                "description", s.description))
                        .toList();
            }

            ctx.status(200).json(secrets);
        });
    }

    private void markUnlocked(SkillTree tree, List<String> unlockedSkillIds) {
        if (unlockedSkillIds.contains(tree.skill.id())) {
            tree.unlocked = true;
        }
        for (SkillTree branch : tree.branches) {
            this.markUnlocked(branch, unlockedSkillIds);
        }
    }
}
