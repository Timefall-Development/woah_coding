package me.lemo.woah_coding.datagen.providers;

import me.lemo.woah_coding.WoahCoding;
import me.lemo.woah_coding.registry.WoahCodingPotions;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.function.SetPotionLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class WoahCodingBarterLootTableProvider extends SimpleFabricLootTableProvider {
    public WoahCodingBarterLootTableProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup, LootContextTypes.BARTER);
    }

    private static final RegistryKey<LootTable> GROBLIN_BARTERING_GAMEPLAY = RegistryKey.of(RegistryKeys.LOOT_TABLE, WoahCoding.id("gameplay/groblin_bartering"));

    @Override
    public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
        lootTableBiConsumer.accept(
                GROBLIN_BARTERING_GAMEPLAY,
                LootTable.builder()
                        .pool(LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(ItemEntry.builder(Items.AMETHYST_SHARD)
                                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 2.0F)))
                                        .weight(3))
                                .with(ItemEntry.builder(Items.DIAMOND).weight(1))
                                .with(ItemEntry.builder(Items.PURPLE_BUNDLE).weight(1))
                                .with(ItemEntry.builder(Items.RAW_IRON)
                                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 2.0F)))
                                        .weight(3))
                                .with(ItemEntry.builder(Items.RAW_GOLD)
                                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 2.0F)))
                                        .weight(3))
                                .with(ItemEntry.builder(Items.SPIDER_EYE).weight(10))
                                .with(ItemEntry.builder(Items.GUNPOWDER).weight(8))
                                .with(ItemEntry.builder(Items.POTATO)
                                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 3.0F)))
                                        .weight(20))
                                .with(ItemEntry.builder(Items.POTION)
                                        .apply(SetPotionLootFunction.builder(WoahCodingPotions.HIGH_GRAVITY_POTION))
                                        .weight(2))
                                .build()));
    }
}
