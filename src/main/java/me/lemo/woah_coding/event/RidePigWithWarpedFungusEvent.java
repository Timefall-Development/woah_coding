package me.lemo.woah_coding.event;

import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;

public class RidePigWithWarpedFungusEvent {
    public static void registerEvent() {

        UseItemCallback.EVENT.register((player, world, hand) -> {
            if (!world.isClient() && world instanceof ServerWorld) {
                ItemStack heldItem = player.getStackInHand(hand);
                Item pigUpgradeItem = Items.WARPED_FUNGUS_ON_A_STICK;

                if (heldItem.isOf(pigUpgradeItem)) {
                    Entity entity = player.getRootVehicle();
                    if (entity instanceof PigEntity pigEntity) {
                        pigEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 100, 5, false, false));
                        if (!user.isCreative())
                            user.getStackInHand(hand).damage(1, user, EquipmentSlot.MAINHAND);
                        if (!user.isCreative() && user.getStackInHand(hand).getDamage() <= 0)
                            player.swingHand();
                            return ActionResult.SUCCESS;
                    }
                }
            }
            return ActionResult.PASS;
        });
    }
}

// tried to add hand waving and durability thingy but it wasnt working :/
