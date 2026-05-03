package servermagic.spells;

import java.util.Optional;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import servermagic.cosmetics.CosmeticItemHelper;
import servermagic.db.Database;
import servermagic.spells.utils.SummonedArmor;
import servermagic.spells.utils.SummonedArmor.ArmorDescriptor;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class AngelWings extends BaseSpell {

    public AngelWings(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        ItemStack equipped = player.getItemBySlot(EquipmentSlot.CHEST);
        if (equipped == null) {
            return;
        }

        ArmorDescriptor ad = SummonedArmor.GetInfo(equipped);
        boolean shouldSummon = equipped.isEmpty() || !"angelwings".equals(ad.armorType());

        if (shouldSummon) {
            ItemStack tempChestplate = SummonedArmor.BuildSummonedItem(player, equipped,
                    new ItemStack(Items.ELYTRA), "angelwings");
            if (tempChestplate == null) {
                return;
            }
            tempChestplate.setDamageValue(tempChestplate.getMaxDamage() - 100);
            CosmeticItemHelper.setEquippableAssetId(tempChestplate, "servermagic:angelwings");
            player.setItemSlot(EquipmentSlot.CHEST, tempChestplate);
            double px = player.getX(), py = player.getY() + 1.0, pz = player.getZ();
            world.sendParticles(ParticleTypes.WAX_ON, px, py, pz, 20, 0.6, 0.8, 0.6, 0.2);
            world.sendParticles(ParticleTypes.CLOUD, px, py, pz, 15, 0.5, 0.7, 0.5, 0.03);
            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.PHANTOM_FLAP, SoundSource.PLAYERS, 1.0F, 1.3F);
        } else {
            ItemStack original = SummonedArmor.DecodeOriginalFromSummonedItem(player, equipped);
            if (original == null) {
                player.setItemSlot(EquipmentSlot.CHEST, ItemStack.EMPTY);
            } else {
                player.setItemSlot(EquipmentSlot.CHEST, original);
            }
            double px = player.getX(), py = player.getY() + 1.0, pz = player.getZ();
            world.sendParticles(ParticleTypes.CLOUD, px, py, pz, 20, 0.5, 0.7, 0.5, 0.04);
            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.PHANTOM_FLAP, SoundSource.PLAYERS, 0.8F, 0.8F);
        }
    }

    @Override

    public int getFlatXpCost() {
        return 8;
    }

    public double getLevelPercentCost() {
        return 0.2;
    }

    public String displayName() {
        return "Conjure: Angel Wings";
    }

    @Override
    public String description() {
        return "Replace your current armor with angel wings. 'He can fly!'";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.ANGEL_WINGS);
    }
}
