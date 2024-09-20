package me.lemo.woah_coding.item;

import me.lemo.woah_coding.registry.WoahCodingBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class FadingCloudItem extends Item {
    public FadingCloudItem(Settings settings) {
        super(settings.maxDamage(256));
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand){
        if (user.getWorld() instanceof ServerWorld serverWorld
                && (user.getWorld().getBlockState(user.getBlockPos().down()).isReplaceable()
                && !user.getWorld().getBlockState(user.getBlockPos().down()).isOf(Blocks.WATER)
                && !user.getWorld().getBlockState(user.getBlockPos().down()).isOf(Blocks.LAVA))){
        serverWorld.setBlockState(user.getBlockPos().down(), WoahCodingBlocks.FADING_CLOUD.getDefaultState());
            if (!user.isCreative())
                user.getStackInHand(hand).damage(1, user, EquipmentSlot.MAINHAND);
            return new TypedActionResult<>(ActionResult.success(world.isClient), user.getStackInHand(hand));
        }
        return new TypedActionResult<>(ActionResult.PASS, user.getStackInHand(hand));
    }

}

/*TODO
   ADD feature that lets you use a bottle in the sky above the build limit that gives you the cloud
   ADD cloud bottles ✓
   ADD feature to not take fall damage when falling on the block ✓
   CHANGE item break sound
   FIGURE OUT why i jump too high ✓
 */

