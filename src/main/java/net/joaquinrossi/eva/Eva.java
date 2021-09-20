package net.joaquinrossi.eva;

import net.joaquinrossi.eva.entity.LanceOfLonginusEntity;
import net.joaquinrossi.eva.item.LanceOfLonginusItem;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Eva implements ModInitializer {
    public static final String MOD_ID = "eva";

    public static final EntityType<LanceOfLonginusEntity> LanceOfLonginusEntityType = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(MOD_ID, "lance_of_longinus"),
            FabricEntityTypeBuilder.<LanceOfLonginusEntity>create(SpawnGroup.MISC, LanceOfLonginusEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F)) // dimensions in Minecraft units of the projectile
                    .trackRangeBlocks(4).trackedUpdateRate(10) // necessary for all thrown projectiles
                    .build());

    public static final Item LANCE_OF_LONGINUS = new LanceOfLonginusItem(
            new Item.Settings()
                .maxCount(1)
                .maxDamage(500)
                .group(ItemGroup.COMBAT));

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "lance_of_longinus"), LANCE_OF_LONGINUS);
    }
}