package servermagic.spells;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;
import servermagic.db.tables.PlayerSpellConfig;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class SetBifrostPoint extends BaseSpell {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public SetBifrostPoint(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        Vec3 pos = player.position();
        String dimensionId = world.dimension().identifier().toString();

        String json;
        try {
            json = MAPPER.writeValueAsString(new BifrostData(
                    pos.x, pos.y, pos.z,
                    player.getYRot(), player.getXRot(),
                    dimensionId));
        } catch (Exception e) {
            return;
        }

        PlayerSpellConfig.UpsertConfigForPlayer(db, player.getPlainTextName(), "SetBifrostPoint", json);

        Vec3 eyePos = player.getEyePosition();
        world.sendParticles(ParticleTypes.END_ROD,
                eyePos.x, eyePos.y, eyePos.z, 20, 0.4, 0.4, 0.4, 0.08);
        world.sendParticles(ParticleTypes.CLOUD,
                pos.x, pos.y + 0.5, pos.z, 8, 0.3, 0.2, 0.3, 0.03);
        world.playSound(null, pos.x, pos.y, pos.z,
                SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 1.0F, 1.4F);
        world.playSound(null, pos.x, pos.y, pos.z,
                SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 0.4F, 1.8F);

        player.sendSystemMessage(Component.literal("Bifrost point set."));
    }

    @Override
    public int getFlatXpCost() {
        return 5;
    }

    @Override
    public String displayName() {
        return "Set Bifrost Point";
    }

    @Override
    public String description() {
        return "Anchor yourself to this location — the Bifrost will carry you back here from anywhere";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.BIFROST);
    }

    // Shared data structure used by both SetBifrostPoint (write) and SummonBifrost (read)
    public static class BifrostData {
        public double x, y, z;
        public float yaw, pitch;
        public String dimension;

        public BifrostData() {}

        public BifrostData(double x, double y, double z, float yaw, float pitch, String dimension) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.yaw = yaw;
            this.pitch = pitch;
            this.dimension = dimension;
        }
    }
}
