package com.raoulvdberge.refinedstorageaddons.item;

import com.raoulvdberge.refinedstorage.api.network.item.INetworkItem;
import com.raoulvdberge.refinedstorage.api.network.item.INetworkItemHandler;
import com.raoulvdberge.refinedstorage.item.ItemWirelessGrid;
import com.raoulvdberge.refinedstorageaddons.RSAddons;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ItemWirelessCraftingGrid extends ItemWirelessGrid {
    public ItemWirelessCraftingGrid() {
        super("wireless_crafting_grid");

        setCreativeTab(RSAddons.INSTANCE.tab);
    }

    @Override
    @Nonnull
    public INetworkItem provide(INetworkItemHandler handler, EntityPlayer player, ItemStack stack) {
        return new NetworkItemWirelessCraftingGrid(handler, player, stack);
    }

    @Override
    protected String getDomain() {
        return RSAddons.ID;
    }
}
