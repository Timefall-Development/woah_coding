package me.lemo.woah_coding.item;

import me.lemo.woah_coding.registry.WoahCodingItemGroups;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class WoahOrbItem extends Item {
    public WoahOrbItem(Settings settings) {
        super(settings);
        ItemGroupEvents.modifyEntriesEvent(WoahCodingItemGroups.WOAH_ITEMS_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(this.getDefaultStack());
        });
    }
}
