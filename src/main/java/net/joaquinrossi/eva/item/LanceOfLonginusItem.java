package net.joaquinrossi.eva.item;

import net.joaquinrossi.eva.entity.LanceOfLonginusEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class LanceOfLonginusItem extends Item {
    public LanceOfLonginusItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getY(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5f, 1f);

        if (!world.isClient) {
            LanceOfLonginusEntity lanceEntity = new LanceOfLonginusEntity(world, user);
            lanceEntity.setItem(itemStack);
            lanceEntity.setProperties(user, user.getPitch(), user.getYaw(), 0.0f, 1.5f, 0f);
            world.spawnEntity(lanceEntity);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}