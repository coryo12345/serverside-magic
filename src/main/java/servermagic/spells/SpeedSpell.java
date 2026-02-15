package servermagic.spells;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import servermagic.db.Database;

public class SpeedSpell extends BaseSpell {

    public SpeedSpell(ServerLevel world, ServerPlayer player, Database db) {
        super(world, player, db);
    }

    @Override
    protected void spellImplementation() {
        // 30 seconds = 30 * 20 ticks = 600 ticks
        // Amplifier 0 is Speed I, Amplifier 1 is Speed II, etc.
        player.addEffect(new MobEffectInstance(MobEffects.SPEED, 600, 0));
    }

    @Override
    public String displayName() {
        return "Speed";
    }

    @Override
    public String description() {
        return "Gives you speed for 30 seconds";
    }
}
