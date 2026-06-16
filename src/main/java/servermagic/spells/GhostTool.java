package servermagic.spells;

import java.util.Optional;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;
import servermagic.db.tables.PlayerSpellConfig;
import servermagic.spells.utils.BoundItems;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class GhostTool extends BaseSpell {

    public GhostTool(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        Optional<PlayerSpellConfig> cfg = PlayerSpellConfig.GetConfigForPlayer(
                db, player.getPlainTextName(), SetGhostTool.SPELL_CONFIG_KEY);
        if (cfg.isEmpty()) {
            player.sendSystemMessage(Component.literal(
                    "No ghost tool set. Cast 'Set Ghost Tool' first."));
            return;
        }

        HolderLookup.Provider registries = player.level().registryAccess();
        ItemStack storedItem;
        try {
            JsonElement jsonElement = SetGhostTool.GSON.fromJson(cfg.get().config, JsonElement.class);
            storedItem = ItemStack.CODEC
                    .decode(registries.createSerializationContext(JsonOps.INSTANCE), jsonElement)
                    .getOrThrow()
                    .getFirst();
        } catch (Exception e) {
            player.sendSystemMessage(Component.literal("Ghost tool data is corrupted."));
            return;
        }

        ItemStack spellbook = player.getItemInHand(hand);
        BoundItems.BuildBoundItem(player, spellbook, storedItem);
        player.setItemInHand(InteractionHand.MAIN_HAND, storedItem);

        Vec3 pos = player.position();
        world.sendParticles(ParticleTypes.PORTAL, pos.x, pos.y + 1.0, pos.z, 35, 0.5, 0.5, 0.5, 0.2);
        world.sendParticles(ParticleTypes.END_ROD, pos.x, pos.y + 1.0, pos.z, 12, 0.3, 0.4, 0.3, 0.06);
        world.playSound(null, pos.x, pos.y, pos.z,
                SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 0.7F, 0.7F);
        world.playSound(null, pos.x, pos.y, pos.z,
                SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 0.5F, 1.4F);
    }

    @Override
    public int getFlatXpCost() { return 1; }

    @Override
    public String displayName() { return "Ghost Tool"; }

    @Override
    public String description() {
        return "Summon your stored ghost tool to your hand";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.GHOST_TOOL);
    }
}
