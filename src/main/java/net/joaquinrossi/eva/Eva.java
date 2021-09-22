package net.joaquinrossi.eva;

import net.joaquinrossi.eva.item.LCLFluid;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.effect.StatusEffectInstance;

public class Eva implements ModInitializer {
    public static final String MOD_ID = "eva";

    public static Block LCL;
    public static FlowableFluid LCL_STILL;
    public static FlowableFluid LCL_FLOWING;
    public static Item LCL_BUCKET;

    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(
        new Identifier(MOD_ID, MOD_ID),
        () -> new ItemStack(LCL_BUCKET));

    @Override
    public void onInitialize() {
        LCL_STILL   = Registry.register(Registry.FLUID, new Identifier(MOD_ID, "lcl"),         new LCLFluid.Still());
        LCL_FLOWING = Registry.register(Registry.FLUID, new Identifier(MOD_ID, "lcl_flowing"), new LCLFluid.Flowing());
        LCL_BUCKET  = Registry.register(Registry.ITEM,  new Identifier(MOD_ID, "lcl_bucket"),
            new BucketItem(LCL_STILL, new Item.Settings().group(ITEM_GROUP).recipeRemainder(Items.BUCKET).maxCount(1)));

        LCL = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "lcl"), new FluidBlock(LCL_STILL, FabricBlockSettings.copy(Blocks.WATER)){
            @Override
            public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
                super.onEntityCollision(state, world, pos, entity);

                if (entity instanceof LivingEntity livingEntity) {
                    livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.REGENERATION, 10, 0)));
                    livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.WATER_BREATHING, 10, 0)));
                }
            }
        });
    }
}