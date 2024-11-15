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
import net.minecraft.util.ActionResult;

public class RidePigWithWarpedFungusEvent {
    public static void registerEvent() {

        UseItemCallback.EVENT.register((player, world, hand) -> {
            if (!world.isClient()) {
                ItemStack heldItem = player.getStackInHand(hand);
                Item pigUpgradeItem = Items.WARPED_FUNGUS_ON_A_STICK;

                if (heldItem.isOf(pigUpgradeItem)) {
                    Entity entity = player.getRootVehicle();
                    if (entity instanceof PigEntity pigEntity) {
                        pigEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 100, 5, false, false));
                        if (!player.isCreative()) {
                            player.getStackInHand(hand).damage(1, player, EquipmentSlot.MAINHAND);
                            return new ActionResult.Success(ActionResult.SwingSource.SERVER, new ActionResult.ItemContext(true, null));
                        }
                    }
                }
            }
            return ActionResult.PASS;
        });
    }
}