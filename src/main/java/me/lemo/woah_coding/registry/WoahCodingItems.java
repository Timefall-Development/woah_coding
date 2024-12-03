package me.lemo.woah_coding.registry;

import me.lemo.woah_coding.WoahCoding;
import me.lemo.woah_coding.item.FadingCloudItem;
import me.lemo.woah_coding.item.OutputItem;
import me.lemo.woah_coding.item.InterdimensionalOrbItem;
import net.minecraft.item.Item;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

@SuppressWarnings("unused")
public class WoahCodingItems {
    public static final Item INTERDIMENSIONAL_ORB_ITEM = itemRegister(
            new InterdimensionalOrbItem(new Item.Settings().maxDamage(256).registryKey(getItemRegistryKey("interdimensional_orb"))), "interdimensional_orb"
    );

    public static final Item OUTPUT_ITEM = itemRegister(
            new OutputItem(new Item.Settings().registryKey(getItemRegistryKey("output_item"))), "output_item"
    );

    public static final Item FADING_CLOUD_ITEM = itemRegister(
            new FadingCloudItem(new Item.Settings().registryKey(getItemRegistryKey("fading_cloud"))), "fading_cloud"
    );

    public static final Item BLUE_CLOUD_BOTTLE_ITEM = itemRegister(
            new Item(new Item.Settings().registryKey(getItemRegistryKey("blue_cloud_bottle"))), "blue_cloud_bottle"
    );

    public static final Item LAVENDER_CLOUD_BOTTLE_ITEM = itemRegister(
            new Item(new Item.Settings().registryKey(getItemRegistryKey("lavender_cloud_bottle"))), "lavender_cloud_bottle"
    );

    public static final Item LILAC_CLOUD_BOTTLE_ITEM = itemRegister(
            new Item(new Item.Settings().registryKey(getItemRegistryKey("lilac_cloud_bottle"))), "lilac_cloud_bottle"
    );

    public static final Item PINK_CLOUD_BOTTLE_ITEM = itemRegister(
            new Item(new Item.Settings().registryKey(getItemRegistryKey("pink_cloud_bottle"))), "pink_cloud_bottle"
    );

    public static final Item PURPLE_CLOUD_BOTTLE_ITEM = itemRegister(
            new Item(new Item.Settings().registryKey(getItemRegistryKey("purple_cloud_bottle"))), "purple_cloud_bottle"
    );

    public static final Item CLOUD_WALKER_ARMOR_TRIM_SMITHING_TEMPLATE = smithingTemplateRegister(
            "cloud_walker_armor_trim_smithing_template", new Item.Settings().registryKey(getItemRegistryKey("cloud_walker_armor_trim_smithing_template"))
    );

    public static final Item BRONZE_INGOT = itemRegister(
           new Item(new Item.Settings().registryKey(getItemRegistryKey("bronze_ingot"))), "bronze_ingot"
    );

    public static final Item TIN_INGOT = itemRegister(
            new Item(new Item.Settings().registryKey(getItemRegistryKey("tin_ingot"))), "tin_ingot"
    );

    public static final Item ALUMINUM_INGOT = itemRegister(
            new Item(new Item.Settings().registryKey(getItemRegistryKey("aluminum_ingot"))), "aluminum_ingot"
    );

    public static final Item ANCIENT_INSTRUCTIONS = itemRegister(
            new Item(new Item.Settings().registryKey(getItemRegistryKey("ancient_instructions"))), "ancient_instructions"
    );

    //public static final Item CREEPER_IN_A_BOX_BLOCK_ITEM = itemRegister(
    //        new Item(new Item.Settings().useBlockPrefixedTranslationKey().registryKey(WoahCodingItems.getItemRegistryKey("creeper_in_a_box"))), "creeper_in_a_box"
    //);

    //public static final Item BEE_IN_A_BOX_BLOCK_ITEM = itemRegister(
    //        new Item(new Item.Settings().useBlockPrefixedTranslationKey().registryKey(WoahCodingItems.getItemRegistryKey("bee_in_a_box"))), "bee_in_a_box"
    //);




    public static void register(){

    }

    public static Item itemRegister(Item item, String id){
        return Registry.register(Registries.ITEM, WoahCoding.id(id), item);


    }

    public static Item smithingTemplateRegister(String id, Item.Settings settings){
        return Registry.register(Registries.ITEM, WoahCoding.id(id), SmithingTemplateItem.of(settings));
    }

    public static RegistryKey<Item> getItemRegistryKey(String id){
        return RegistryKey.of(RegistryKeys.ITEM, WoahCoding.id(id));
    }
}
