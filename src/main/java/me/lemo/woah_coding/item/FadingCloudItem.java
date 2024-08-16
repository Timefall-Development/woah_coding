package me.lemo.woah_coding.item;

import me.lemo.woah_coding.registry.WoahCodingBlocks;
import me.lemo.woah_coding.registry.WoahCodingItemGroups;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Blocks;
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
        super(settings);
        ItemGroupEvents.modifyEntriesEvent(WoahCodingItemGroups.WOAH_ITEMS_GROUP_KEY).register(
                itemGroup -> itemGroup.add(this.getDefaultStack())
        );
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.getWorld() instanceof ServerWorld serverWorld
                && user.getWorld().getBlockState(user.getBlockPos()).isOf(Blocks.AIR)) {
            serverWorld.setBlockState(user.getBlockPos().down(), WoahCodingBlocks.FADING_CLOUD.getDefaultState());
            return new TypedActionResult<>(ActionResult.CONSUME, user.getStackInHand(hand));
        }

        return new TypedActionResult<>(ActionResult.SUCCESS, user.getStackInHand(hand));
    }
}
