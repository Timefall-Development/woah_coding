package me.lemo.woah_coding;

import me.lemo.woah_coding.registry.WoahCodingItemGroups;
import me.lemo.woah_coding.registry.WoahCodingItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class WoahCoding implements ModInitializer {
    public static final String MOD_ID = "woah_coding";
    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
    @Override
    public void onInitialize() {
        WoahCodingItems.register();
        WoahCodingItemGroups.register();
    }
}
