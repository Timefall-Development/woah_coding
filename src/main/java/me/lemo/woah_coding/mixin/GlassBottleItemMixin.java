package me.lemo.woah_coding.mixin;

import me.lemo.woah_coding.registry.WoahCodingItems;
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
import org .spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GlassBottleItem.class)
public class GlassBottleItemMixin {

    @Unique
    float ultraColdMin = -2.0f;
    @Unique
    float ultraColdMax = 0.19f;

    @Unique
    float coldMin = 0.2f;
    @Unique
    float coldMax = 0.59f;

    @Unique
    float warmMin = 0.6f;
    @Unique
    float warmMax = 0.79f;

    @Unique
    float hotMin = 0.8f;
    @Unique
    float hotMax = 1.99f;

    @Unique
    float ultraHot = 2.0f;

    @Inject(method = "use", at = @At(value = "HEAD"), cancellable = true)
    public void woah_coding$collectCloudsOnUse(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if (world.isClient())
            return;

        GlassBottleItem glassBottleItem = (GlassBottleItem) (Object) this;
        BlockHitResult blockHitResult = ItemInvoker.callRaycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        ItemStack heldStack = user.getStackInHand(hand);


        float temperature = user.getWorld().getBiome(user.getBlockPos()).value().getTemperature();

        ItemStack cloudBottleItemStack = getCloudBottleColorFromTemperature(temperature);

        if (user.getY() >= 330 && isInOverworld(user)){
            cir.setReturnValue(TypedActionResult.success(this.woah_coding$fill(heldStack, user, cloudBottleItemStack)));
        } else {
            cir.setReturnValue(TypedActionResult.consume(heldStack));
        }
    }


    @Unique
    private ItemStack getCloudBottleColorFromTemperature(float temperature){
        if (temperature >= ultraColdMin && temperature <= ultraColdMax){
            return new ItemStack(WoahCodingItems.BLUE_CLOUD_BOTTLE_ITEM);

        } else if (temperature >= coldMin && temperature <= coldMax){
            return new ItemStack(WoahCodingItems.LAVENDER_CLOUD_BOTTLE_ITEM);

        } else if (temperature >= warmMin && temperature <= warmMax){
            return new ItemStack(WoahCodingItems.LILAC_CLOUD_BOTTLE_ITEM);

        } else if (temperature >= hotMin && temperature <= hotMax){
            return new ItemStack(WoahCodingItems.PINK_CLOUD_BOTTLE_ITEM);

        }else if (temperature >= ultraHot) {
            return new ItemStack(WoahCodingItems.PURPLE_CLOUD_BOTTLE_ITEM);
        }
        return ItemStack.EMPTY;
    }

    @Unique
    protected ItemStack woah_coding$fill(ItemStack stack, PlayerEntity player, ItemStack outputStack) {
        GlassBottleItem glassBottleItem = (GlassBottleItem) (Object) this;

        player.incrementStat(Stats.USED.getOrCreateStat(glassBottleItem));
        return ItemUsage.exchangeStack(stack, player, outputStack);

    }

    @Unique
    private static boolean isInOverworld(PlayerEntity playerEntity){
        return playerEntity.getWorld().getDimension().bedWorks();
    }
}

/* TODO:
    Check if above y330 ✓
    Check if your in the overworld ✓
    Check if on a server ✓
    Use bottle fill sound
    Fill empty bottle with correct cloud bottle
    5 Items for cloud bottles ✓
    Recipe for fading cloud ✓
    5 biome groups ✓
    to get biome temp the player is in: playerEntity.getWorld().getBiome(playerEntity.getBlockPos()).value().getTemperature()

*/