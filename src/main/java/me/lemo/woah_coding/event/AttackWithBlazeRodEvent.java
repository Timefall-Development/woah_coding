package me.lemo.woah_coding.event;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;

public class AttackWithBlazeRodEvent {
    public static void registerEvent() {


        AttackEntityCallback.EVENT.register(((player, world, hand, entity, hitResult) -> {
            if (!world.isClient() && world instanceof ServerWorld serverWorld) {
                ItemStack heldItem = player.getStackInHand(hand);
                Item ignitorItem = Items.BLAZE_ROD;

                if (heldItem.isOf(ignitorItem)){
                    if (!(entity instanceof LivingEntity))
                        return ActionResult.PASS;
                    if (!(entity instanceof CreeperEntity creeperEntity)){
                        entity.setOnFireFor(5);

                        return ActionResult.SUCCESS;
                    } else {
                        creeperEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE,140,0));
                        EntityType.LIGHTNING_BOLT.spawn(serverWorld, creeperEntity.getBlockPos(), SpawnReason.TRIGGERED);
                        return ActionResult.SUCCESS;
                    }
                }
            }

            return ActionResult.PASS;
        }));
    }
}

/*TODO
 * item that the player is holding ✓
 * what entity specifically was hit ✓
 * is the player on a server ✓
 */