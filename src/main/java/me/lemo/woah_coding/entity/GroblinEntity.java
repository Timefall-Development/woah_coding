
package me.lemo.woah_coding.entity;

import me.lemo.woah_coding.registry.WoahCodingItems;
import me.lemo.woah_coding.registry.WoahCodingPotions;
import net.minecraft.block.BlockState;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GroblinEntity extends AnimalEntity {

    public static final List<Item> GROBLIN_TRADE_ITEMS =
            List.of(
                    Items.AMETHYST_SHARD, Items.DIAMOND, Items.PURPLE_BUNDLE, Items.RAW_IRON, Items.RAW_GOLD, Items.SPIDER_EYE, Items.GUNPOWDER, PotionContentsComponent.createStack(Items.POTION, WoahCodingPotions.HIGH_GRAVITY_POTION).getItem(), Items.POTATO
            );

    public GroblinEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        // What should the Groblin do?
        // Maybe make brain for Groblin to be able to flee emeralds when seen
        // If a Groblin finds an emerald on the ground, it will pick it up and find a way to destroy it;
            // if it cannot find a place to destroy it, it will delete it from the world?
        this.goalSelector.add(0, new SwimGoal(this)); // No drowning
        this.goalSelector.add(1, new WanderAroundGoal(this, 0.50d));
        this.goalSelector.add(4, new TemptGoal(this, 1.0, stack -> stack.isOf(WoahCodingItems.COPPER_TOKEN_ITEM), true));
    }


    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item potentialGroblinItem = GROBLIN_TRADE_ITEMS.get(player.getRandom().nextInt(GROBLIN_TRADE_ITEMS.size()));
        if(itemStack.isOf(WoahCodingItems.COPPER_TOKEN_ITEM)){
            itemStack.decrement(1);
            ItemEntity groblinItemEntity = new ItemEntity(player.getWorld(), player.getX(), player.getY(), player.getZ(), potentialGroblinItem.getDefaultStack());
            player.getWorld().spawnEntity(groblinItemEntity);
        } else if (player.getWorld() instanceof ServerWorld serverWorld && itemStack.isOf(Items.EMERALD)) {
            player.damage(serverWorld, serverWorld.getDamageSources().mobAttackNoAggro(this), ((float) this.getAttributes().getBaseValue(EntityAttributes.ATTACK_DAMAGE)));
        }
        return super.interactMob(player, hand);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    public static DefaultAttributeContainer.Builder createGroblinAttributes() {
        // Should the Groblin have unique attributes?
        // Custom Death Message
        return AnimalEntity.createAnimalAttributes()
                .add(EntityAttributes.TEMPT_RANGE, 12)
                .add(EntityAttributes.ATTACK_DAMAGE, 3);
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_STRIDER_AMBIENT;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_STRIDER_HURT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_STRIDER_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 0.6F;
    }

    @Override
    // Should the Groblin have unique sounds?

    public float getSoundPitch() {
        return 1.1F;
    }
}

