package me.lemo.woah_coding.entity.type;

import me.lemo.woah_coding.WoahCoding;
import me.lemo.woah_coding.entity.BrainyGroblinEntity;
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

    //private static final RegistryKey<EntityType<?>> GROBLIN_KEY = RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(WoahCoding.MOD_ID, "groblin"));

    //public static final EntityType<GroblinEntity> GROBLIN_ENTITY = Registry.register(
    //        Registries.ENTITY_TYPE,
    //        Identifier.of(WoahCoding.MOD_ID, "groblin"),
    //        EntityType.Builder.create(GroblinEntity::new, SpawnGroup.MISC)
    //                .dimensions(0.3f, 0.6f).build(GROBLIN_KEY));

    public static final EntityType<GroblinEntity> GROBLIN_ENTITY_ENTITY_TYPE = registerEntityType(
            "groblin",
            EntityType.Builder.create(
                    GroblinEntity::new,
                    SpawnGroup.MISC
            ).dimensions(0.3f, 0.6f));
    public static final EntityType<BrainyGroblinEntity> BRAINY_GROBLIN_ENTITY_ENTITY_TYPE = registerEntityType(
            "brainy_groblin",
            EntityType.Builder.create(
                    BrainyGroblinEntity::new,
                    SpawnGroup.MISC
            ).dimensions(0.3f, 0.6f));

    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(GROBLIN_ENTITY_ENTITY_TYPE, GroblinEntity.createGroblinAttributes());
        FabricDefaultAttributeRegistry.register(BRAINY_GROBLIN_ENTITY_ENTITY_TYPE, BrainyGroblinEntity.createBrainyGroblinAttributes());
    }

    public static <T extends Entity> EntityType<T> registerEntityType(String path, EntityType.Builder<T> entityTypeBuilder) {
        RegistryKey<EntityType<?>> key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, WoahCoding.id(path));

        return Registry.register(Registries.ENTITY_TYPE, key, entityTypeBuilder.build(key));
    }



}
