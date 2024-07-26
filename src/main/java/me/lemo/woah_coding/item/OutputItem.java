package me.lemo.woah_coding.item;

import me.lemo.woah_coding.registry.WoahCodingItemGroups;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class OutputItem extends Item {


    //sum of 7.4 and 18
    float sumFloat1 = 7.4F;
    byte sumByte1 = 18;

    float sum = sumFloat1 + sumByte1;


    //difference of -846 and 143
    short differenceShort1 = -846;
    short differenceShort2 = 143;

    int difference = differenceShort1 - differenceShort2;


    //product of 13 and 379
    short productShort1 = 13;
    short productShort2 = 379;

    int product = productShort1 * productShort2;


    //quotient of 5 and 2
    float quotientFloat1 = 5.0F;
    float quotientFloat2 = 2.0F;

    float quotient = quotientFloat1 / quotientFloat2;
    int remainder = (int) (quotientFloat1 % quotientFloat2);

    public OutputItem(Settings settings) {
        super(settings);
        ItemGroupEvents.modifyEntriesEvent(WoahCodingItemGroups.WOAH_ITEMS_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(this.getDefaultStack());
        });
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        user.sendMessage(
                Text.literal(
                        "Addition is quite simple. I can add " + sumFloat1 + " and " + sumByte1 + " and get " + sum + ".\n"
                                + "Subtraction is also quite simple. I can subtract " + differenceShort1 + " from " + differenceShort2 + " and get " + difference + ".\n"
                                + "Multiplication is equally simple. I can multiply " + productShort1 + " by " + productShort2 + " and get " + product + ".\n"
                                + "Division is as simple as Multiplication. I can divide " + (int) quotientFloat1 + " by " + (int) quotientFloat2 + " and get " + quotient + ".\n"
                                + "Finding the remainder is also simple enough. If I divide " + (int) quotientFloat1 + " by " + (int) quotientFloat2 + ", the remainder is " + remainder + "."
                ),
                false
        );


        System.out.println(
                "Addition is quite simple. I can add " + sumFloat1 + " and " + sumByte1 + " and get " + sum + ".\n"
                        + "Subtraction is also quite simple. I can subtract " + differenceShort1 + " from " + differenceShort2 + " and get " + difference + ".\n"
                        + "Multiplication is equally simple. I can multiply " + productShort1 + " by " + productShort2 + " and get " + product + ".\n"
                        + "Division is as simple as Multiplication. I can divide " + (int) quotientFloat1 + " by " + (int) quotientFloat2 + " and get " + quotient + ".\n"
                        + "Finding the remainder is also simple enough. If I divide " + (int) quotientFloat1 + " by " + (int) quotientFloat2 + ", the remainder is " + remainder + "."
        );
        return new TypedActionResult<>(ActionResult.PASS, user.getStackInHand(hand));

    }
}
