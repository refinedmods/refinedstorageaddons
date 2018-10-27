package com.raoulvdberge.refinedstorageaddons.apiimpl.network.grid.wireless;

import com.raoulvdberge.refinedstorage.api.network.grid.IGrid;
import com.raoulvdberge.refinedstorage.api.network.grid.wireless.IWirelessGridFactory;
import com.raoulvdberge.refinedstorageaddons.item.WirelessCraftingGrid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;

import javax.annotation.Nonnull;

public class WirelessGridFactoryWirelessCraftingGrid implements IWirelessGridFactory {
    @Nonnull
    @Override
    public IGrid create(EntityPlayer player, EnumHand hand, int controllerDimension) {
        return new WirelessCraftingGrid(controllerDimension, player.getHeldItem(hand), !player.getEntityWorld().isRemote);
    }
}
