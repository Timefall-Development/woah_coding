package me.lemo.woah_coding.entity.type;

import me.lemo.woah_coding.WoahCoding;
import me.lemo.woah_coding.entity.GroblinEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class WoahCodingEntityTypes {
    public static final EntityType<GroblinEntity> GROBLIN_ENTITY_ENTITY_TYPE = registerEntityType("groblin", EntityType.Builder.create(GroblinEntity::new, SpawnGroup.MISC).dimensions(0.6f, 0.6f));

    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(GROBLIN_ENTITY_ENTITY_TYPE, GroblinEntity.createAnimalAttributes());
    }

    public static <T extends Entity> EntityType<T> registerEntityType(String path, EntityType.Builder<T> entityTypeBuilder) {
        RegistryKey<EntityType<?>> key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, WoahCoding.id(path));

        return Registry.register(Registries.ENTITY_TYPE, key, entityTypeBuilder.build(key));
    }



}
