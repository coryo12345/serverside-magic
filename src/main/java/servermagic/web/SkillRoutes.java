package servermagic.web;

import java.util.List;
import java.util.Optional;

import io.javalin.Javalin;
import net.minecraft.server.MinecraftServer;
import servermagic.db.Database;
import servermagic.db.tables.SkillUnlocks;
import servermagic.web.skill.SkillTree;

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
            Optional<List<SkillUnlocks>> unlocks = SkillUnlocks.GetAllPlayerUnlockedSkills(db, username, false);
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
