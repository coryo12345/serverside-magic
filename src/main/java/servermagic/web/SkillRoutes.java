package servermagic.web;

import java.util.List;
import java.util.Optional;

import io.javalin.Javalin;
import net.minecraft.server.MinecraftServer;
import servermagic.db.Database;
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
        this.requireAuthForGroup("/api/spells/");

        app.get("/api/skills/tree", ctx -> {
            String username = this.getAuthSubject(ctx);

            // first we get the skill trees setup
            List<SkillTree> trees = SkillTree.GetTrees();

            // then we need to determine which skills the player has and mark accordingly
            Optional<List<SkillUnlocks>> unlocks = SkillUnlocks.GetAllPlayerUnlockedSkills(db, username);
            if (unlocks.isEmpty()) {
                ctx.status(500).result("Unable to determine unlocked skills for player");
                return;
            }

            List<String> unlockedSkillIds = unlocks.get().stream().map(u -> u.skill).toList();
            for (SkillTree tree : trees) {
                this.markUnlocked(tree, unlockedSkillIds);
            }

            ctx.status(200).json(trees);
        });

        app.post("/api/skills/unlock", ctx -> {
            String username = this.getAuthSubject(ctx);
            String skillId = ctx.formParam("skillId");
            if (skillId == null || skillId.trim().length() == 0) {
                ctx.status(404).result("Skill not found");
                return;
            }

            List<Skill> skills = Skills.GetAllSkills();
            Optional<Skill> requestedSkill = skills.stream().filter(s -> s.id().equals(skillId)).findFirst();
            if (requestedSkill.isEmpty()) {
                ctx.status(404).result("Skill not found");
                return;
            }

            Optional<SkillUnlocks> unlock = SkillUnlocks.UnlockSkillForPlayerIfAble(db, username, requestedSkill.get());
            if (unlock.isEmpty()) {
                ctx.status(400).result("Not able to unlock skill");
                return;
            }
            ctx.status(200).json(unlock);
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
