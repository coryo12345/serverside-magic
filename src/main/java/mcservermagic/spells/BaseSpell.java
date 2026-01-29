package mcservermagic.spells;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;

public abstract class BaseSpell {
    protected ServerLevel world;
    protected ServerPlayer player;

    public BaseSpell(ServerLevel world, ServerPlayer player) {
        this.world = world;
        this.player = player;
    }

    protected abstract void spellImplementation();

    public boolean cast() {
        try {
            this.spellImplementation();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public InteractionResult castAsInteraction() {
        boolean success = this.cast();
        if (success) {
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
}
