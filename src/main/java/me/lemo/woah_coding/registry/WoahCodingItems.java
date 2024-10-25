package me.lemo.woah_coding.registry;

import me.lemo.woah_coding.WoahCoding;
import me.lemo.woah_coding.item.FadingCloudItem;
import me.lemo.woah_coding.item.OutputItem;
import me.lemo.woah_coding.item.WoahOrbItem;
import net.minecraft.item.Item;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

@SuppressWarnings("unused")
public class WoahCodingItems {
    public static final Item WOAH_ORB_ITEM = itemRegister(
            new WoahOrbItem(new Item.Settings()), "woah_orb"
    );

    public static final Item OUTPUT_ITEM = itemRegister(
            new OutputItem(new Item.Settings()), "output_item"
    );

    public static final Item FADING_CLOUD_ITEM = itemRegister(
            new FadingCloudItem(new Item.Settings()), "fading_cloud"
    );

    public static final Item BLUE_CLOUD_BOTTLE_ITEM = itemRegister(
            new Item(new Item.Settings()), "blue_cloud_bottle"
    );

    public static final Item LAVENDER_CLOUD_BOTTLE_ITEM = itemRegister(
            new Item(new Item.Settings()), "lavender_cloud_bottle"
    );

    public static final Item LILAC_CLOUD_BOTTLE_ITEM = itemRegister(
            new Item(new Item.Settings()), "lilac_cloud_bottle"
    );

    public static final Item PINK_CLOUD_BOTTLE_ITEM = itemRegister(
            new Item(new Item.Settings()), "pink_cloud_bottle"
    );

    public static final Item PURPLE_CLOUD_BOTTLE_ITEM = itemRegister(
            new Item(new Item.Settings()), "purple_cloud_bottle"
    );

    public static final Item CLOUD_WALKER_ARMOR_TRIM_SMITHING_TEMPLATE = smithingTemplateRegister(
            "cloud_walker_armor_trim_smithing_template"
    );

    public static final Item BRONZE_INGOT = itemRegister(
            new Item(new Item.Settings()), "bronze_ingot"
    );

    public static final Item TIN_INGOT = itemRegister(
            new Item(new Item.Settings()), "tin_ingot"
    );

    public static final Item ALUMINUM_INGOT = itemRegister(
            new Item(new Item.Settings()), "aluminum_ingot"
    );

    public static final Item ANCIENT_INSTRUCTIONS = itemRegister(
            new Item(new Item.Settings()), "ancient_instructions"
    );






    public static void register(){

    }

    public static Item itemRegister(Item item, String id){
        return Registry.register(Registries.ITEM, WoahCoding.id(id), item);


    }

    public static Item smithingTemplateRegister(String id){
        return Registry.register(Registries.ITEM, WoahCoding.id(id), SmithingTemplateItem.of(WoahCoding.id(id)));
    }
}
