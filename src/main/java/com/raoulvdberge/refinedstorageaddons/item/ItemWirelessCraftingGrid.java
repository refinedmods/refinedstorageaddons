package com.raoulvdberge.refinedstorageaddons.item;

import com.raoulvdberge.refinedstorage.api.network.item.INetworkItem;
import com.raoulvdberge.refinedstorage.api.network.item.INetworkItemHandler;
import com.raoulvdberge.refinedstorage.item.ItemWirelessGrid;
import com.raoulvdberge.refinedstorage.item.info.ItemInfo;
import com.raoulvdberge.refinedstorageaddons.RSAddons;
import com.raoulvdberge.refinedstorageaddons.apiimpl.network.item.NetworkItemWirelessCraftingGrid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ItemWirelessCraftingGrid extends ItemWirelessGrid {
    public ItemWirelessCraftingGrid() {
        super(new ItemInfo(RSAddons.ID, "wireless_crafting_grid"));

        setCreativeTab(RSAddons.INSTANCE.tab);
    }

    @Override
    @Nonnull
    public INetworkItem provide(INetworkItemHandler handler, EntityPlayer player, ItemStack stack) {
        return new NetworkItemWirelessCraftingGrid(handler, player, stack);
    }
}
