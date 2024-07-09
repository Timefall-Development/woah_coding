package me.lemo.woah_coding.registry;

import me.lemo.woah_coding.WoahCoding;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class WoahCodingItems {
    public static final Item WOAH_ORB = itemRegister(
            new Item(new Item.Settings()), "woah_orb"
    );

    public static void register(){

    }

    public static Item itemRegister(Item item, String id){
        return Registry.register(Registries.ITEM, WoahCoding.id(id), item);
    }
}
