package me.lemo.woah_coding.registry.tag;

import me.lemo.woah_coding.WoahCoding;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class WoahCodingTags {
    public static class Items{
        public static final TagKey<Item> CLOUD_BOTTLES = createItemTagKey("cloud_bottles");
        public static final TagKey<Item> VANILLA_BOOTS = createItemTagKey("vanilla_boots");
    }
    private static TagKey<Item> createItemTagKey(String id){
        return TagKey.of(RegistryKeys.ITEM, WoahCoding.id(id));
    }
}
