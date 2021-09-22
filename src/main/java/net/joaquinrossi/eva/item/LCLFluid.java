package net.joaquinrossi.eva.item;

import net.joaquinrossi.eva.Eva;
import net.joaquinrossi.eva.item.AFluid;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;

public abstract class LCLFluid extends AFluid {
    @Override
    public Fluid getStill() {
        return Eva.LCL_STILL;
    }

    @Override
    public Fluid getFlowing() {
        return Eva.LCL_FLOWING;
    }

    @Override
    public Item getBucketItem() {
        return Eva.LCL_BUCKET;
    }

    @Override
    protected BlockState toBlockState(FluidState fluidState) {
        return Eva.LCL.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(fluidState));
    }

    public static class Flowing extends LCLFluid {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getLevel(FluidState fluidState) {
            return fluidState.get(LEVEL);
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return false;
        }
    }

    public static class Still extends LCLFluid {
        @Override
        public int getLevel(FluidState fluidState) {
            return 8;
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return true;
        }
    }
}