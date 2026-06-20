package servermagic.entities;

import de.tomalbrc.bil.api.AnimatedEntity;
import de.tomalbrc.bil.api.AnimatedEntityHolder;
import de.tomalbrc.bil.core.holder.entity.living.LivingEntityHolder;
import de.tomalbrc.bil.core.model.Model;
import de.tomalbrc.bil.file.loader.BbModelLoader;
import eu.pb4.polymer.virtualentity.api.attachment.EntityAttachment;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class TestBatEntity extends PathfinderMob implements AnimatedEntity {

    private static final Model MODEL = BbModelLoader.load(
            Identifier.parse("servermagic:test_bat"));

    private final LivingEntityHolder<TestBatEntity> holder;

    public TestBatEntity(EntityType<? extends TestBatEntity> type, Level level) {
        super(type, level);
        this.holder = new LivingEntityHolder<>(this, MODEL);
        EntityAttachment.ofTicking(this.holder, this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1.0)
                .add(Attributes.MOVEMENT_SPEED, 0.0);
    }

    @Override
    public AnimatedEntityHolder getHolder() {
        return holder;
    }

    @Override
    protected void registerGoals() {
        // stationary — no AI
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        this.discard();
        return true;
    }
}
