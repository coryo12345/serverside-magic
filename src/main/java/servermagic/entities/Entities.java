package servermagic.entities;

import eu.pb4.polymer.core.api.entity.PolymerEntityUtils;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class Entities {

    public static EntityType<TestBatEntity> TEST_BAT;

    public static void register() {
        ResourceKey<EntityType<?>> key = ResourceKey.create(
                Registries.ENTITY_TYPE,
                Identifier.parse("servermagic:test_bat"));
        TEST_BAT = EntityType.Builder.<TestBatEntity>of(TestBatEntity::new, MobCategory.MISC)
                .sized(1.0f, 0.75f)
                .build(key);
        Registry.register(BuiltInRegistries.ENTITY_TYPE, Identifier.parse("servermagic:test_bat"), TEST_BAT);
        FabricDefaultAttributeRegistry.register(TEST_BAT, TestBatEntity.createAttributes());
        PolymerEntityUtils.registerType(TEST_BAT);
    }
}
