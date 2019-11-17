package com.raoulvdberge.refinedstorageaddons.apiimpl.network.grid;

import com.raoulvdberge.refinedstorage.api.network.grid.GridFactoryType;
import com.raoulvdberge.refinedstorage.api.network.grid.IGrid;
import com.raoulvdberge.refinedstorage.api.network.grid.IGridFactory;
import com.raoulvdberge.refinedstorageaddons.RSAddons;
import com.raoulvdberge.refinedstorageaddons.item.WirelessCraftingGrid;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class WirelessCraftingGridGridFactory implements IGridFactory {
    public static final ResourceLocation ID = new ResourceLocation(RSAddons.ID, "wireless_crafting_grid");

    @Nullable
    @Override
    public IGrid createFromStack(PlayerEntity player, ItemStack stack, int slotId) {
        return new WirelessCraftingGrid(stack, player.world, player.getServer(), slotId);
    }

    @Nullable
    @Override
    public IGrid createFromBlock(PlayerEntity player, BlockPos pos) {
        return null;
    }

    @Nullable
    @Override
    public TileEntity getRelevantTile(World world, BlockPos pos) {
        return null;
    }

    @Override
    public GridFactoryType getType() {
        return GridFactoryType.STACK;
    }
}
