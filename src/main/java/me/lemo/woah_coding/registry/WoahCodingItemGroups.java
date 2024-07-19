package me.lemo.woah_coding.registry;

import me.lemo.woah_coding.WoahCoding;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class WoahCodingItemGroups {
    public static final RegistryKey<ItemGroup> WOAH_ITEMS_GROUP_KEY = RegistryKey.of(
            RegistryKeys.ITEM_GROUP,
            WoahCoding.id("woah_items_group_key")
    );

    public static final ItemGroup WOAH_ITEMS_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(WoahCodingItems.WOAH_ORB))
            .displayName(Text.translatable("itemGroup." + WoahCoding.MOD_ID))
            .build();

    public static void register(){
        Registry.register(Registries.ITEM_GROUP, WOAH_ITEMS_GROUP_KEY, WOAH_ITEMS_GROUP);
    }
}
