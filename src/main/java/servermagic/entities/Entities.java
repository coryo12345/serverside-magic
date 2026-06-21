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
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

public class Entities {

    public static EntityType<TestBatEntity> TEST_BAT;

    public static void register() {
        TEST_BAT = registerEntity("test_bat", TestBatEntity::new, TestBatEntity.createAttributes(), 1.0f, 0.75f);
    }

    private static <T extends PathfinderMob> EntityType<T> registerEntity(
            String id,
            EntityType.EntityFactory<T> factory,
            AttributeSupplier.Builder attributes,
            float width, float height) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(
                Registries.ENTITY_TYPE, Identifier.parse("servermagic:" + id));
        EntityType<T> type = EntityType.Builder.<T>of(factory, MobCategory.MISC)
                .sized(width, height)
                .build(key);
        Registry.register(BuiltInRegistries.ENTITY_TYPE, Identifier.parse("servermagic:" + id), type);
        FabricDefaultAttributeRegistry.register(type, attributes);
        PolymerEntityUtils.registerType(type);
        return type;
    }
}
