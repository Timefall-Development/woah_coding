package me.lemo.woah_coding.registry;

import me.lemo.woah_coding.WoahCoding;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;

public class WoahCodingItemGroups {
    public static final RegistryKey<ItemGroup> WOAH_ITEMS_GROUP_KEY = RegistryKey.of(
            RegistryKeys.ITEM_GROUP,
            WoahCoding.id("woah_items_group_key")
    );

    public static final ItemGroup WOAH_ITEMS_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(WoahCodingItems.WOAH_ORB_ITEM))
            .displayName(Text.translatable("itemGroup." + WoahCoding.MOD_ID))
            .entries(((displayContext, entries) -> {
                entries.add(WoahCodingItems.WOAH_ORB_ITEM);
                entries.add(WoahCodingItems.OUTPUT_ITEM);
                entries.add(WoahCodingItems.FADING_CLOUD_ITEM);
                entries.add(WoahCodingItems.PINK_CLOUD_BOTTLE_ITEM);
                entries.add(WoahCodingItems.PURPLE_CLOUD_BOTTLE_ITEM);
                entries.add(WoahCodingItems.LAVENDER_CLOUD_BOTTLE_ITEM);
                entries.add(WoahCodingItems.LILAC_CLOUD_BOTTLE_ITEM);
                entries.add(WoahCodingItems.BLUE_CLOUD_BOTTLE_ITEM);
                entries.add(WoahCodingItems.CLOUD_WALKER_ARMOR_TRIM_SMITHING_TEMPLATE);
                entries.add(WoahCodingBlocks.CREEPER_IN_A_BOX_BLOCK.asItem());
                entries.add(WoahCodingBlocks.BEE_IN_A_BOX_BLOCK.asItem());
            }))
            .build();

    public static void register(){
        Registry.register(Registries.ITEM_GROUP, WOAH_ITEMS_GROUP_KEY, WOAH_ITEMS_GROUP);
    }
}
