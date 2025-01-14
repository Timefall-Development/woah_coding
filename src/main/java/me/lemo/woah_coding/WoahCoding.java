package me.lemo.woah_coding;

import me.lemo.woah_coding.entity.type.WoahCodingEntityTypes;
import me.lemo.woah_coding.loot.WoahCodingLootTables;
import me.lemo.woah_coding.registry.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class WoahCoding implements ModInitializer {
    public static final String MOD_ID = "woah_coding";
    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
    @Override
    public void onInitialize() {
        WoahCodingEntityTypes.registerAttributes();
        WoahCodingEvents.addListeners();
        WoahCodingItems.register();
        WoahCodingBlocks.register();
        WoahCodingBlockEntities.register();
        WoahCodingItemGroups.register();
        WoahCodingEffects.register();
        WoahCodingPotions.register();
        WoahCodingLootTables.registerLootTables();
        WoahCodingMemoryModuleTypes.registerMemoryModuleTypes();
        WoahCodingSensorTypes.registerSensorTypes();
    }
}