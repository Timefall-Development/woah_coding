package me.lemo.woah_coding.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GlassBottleItem.class)
public class GlassBottleItemMixin {
    @Inject(method = "use", at = @At(value = "TAIL"))
    public void woah_coding$use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        GlassBottleItem glassBottleItem = (GlassBottleItem) (Object) this;
        BlockHitResult blockHitResult = ItemInvoker.callRaycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);

        float ultraColdMin = -2.0f;
        float ultraColdMax = 0.19f;

        float coldMin = 0.2f;
        float coldMax = 0.59f;

        float warmMin = 0.6f;
        float warmMax = 0.79f;

        float hotMin = 0.8f;
        float hotMax = 1.99f;

        float ultraHot = 2.0f;


        cir.getReturnValue();
    }

    
    @Unique
    protected ItemStack woah_coding$fill(ItemStack stack, PlayerEntity player, ItemStack outputStack) {
        GlassBottleItem glassBottleItem = (GlassBottleItem) (Object) this;

        player.incrementStat(Stats.USED.getOrCreateStat(glassBottleItem));
        return ItemUsage.exchangeStack(stack, player, outputStack);
    }
}

/* TODO:
    Check if above y330
    Check if on a server
    Use bottle fill sound
    Fill empty bottle with correct cloud bottle
    5 Items for cloud bottles ✓
    Recipe for fading cloud ✓
    5 biome groups
    to get biome temp the player is in: playerEntity.getWorld().getBiome(playerEntity.getBlockPos()).value().getTemperature()

*/
/*
 Biome Temp reference

 Ultra Cold. -2.0 - 0.19
 Cold. 0.2 - 0.59
 Warm. 0.6 - 0.79
 Hot. 0.8 - 0.99
 Ultra Hot. 1.0 - 2.0

 */