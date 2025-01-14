package me.lemo.woah_coding.loot;

import me.lemo.woah_coding.WoahCoding;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.HashSet;
import java.util.Set;

public class WoahCodingLootTables {
    private static final Set<RegistryKey<LootTable>> LOOT_TABLES = new HashSet<>();

    public static final RegistryKey<LootTable> GROBLIN_BARTERING_GAMEPLAY = register("gameplay/groblin_bartering");

    private static RegistryKey<LootTable> register(String id) {
        return registerLootTable(RegistryKey.of(RegistryKeys.LOOT_TABLE, WoahCoding.id(id)));
    }

    private static RegistryKey<LootTable> registerLootTable(RegistryKey<LootTable> key) {
        if (LOOT_TABLES.add(key)) {
            return key;
        } else {
            throw new IllegalArgumentException(key.getValue() + " is already a registered built-in loot table");
        }
    }

    public static void registerLootTables() {
    }
}
