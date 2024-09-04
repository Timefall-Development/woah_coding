package me.lemo.woah_coding.registry;

import me.lemo.woah_coding.WoahCoding;
import me.lemo.woah_coding.item.FadingCloudItem;
import me.lemo.woah_coding.item.OutputItem;
import me.lemo.woah_coding.item.WoahOrbItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

@SuppressWarnings("unused")
public class WoahCodingItems {
    public static final Item WOAH_ORB = itemRegister(
            new WoahOrbItem(new Item.Settings()), "woah_orb"
    );

    public static final Item OUTPUT_ITEM = itemRegister(
            new OutputItem(new Item.Settings()), "output_item"
    );

    public static final Item FADING_CLOUD_ITEM = itemRegister(
            new FadingCloudItem(new Item.Settings()), "fading_cloud_item"
    );

    public static void register(){

    }

    public static Item itemRegister(Item item, String id){
        return Registry.register(Registries.ITEM, WoahCoding.id(id), item);
    }
}
