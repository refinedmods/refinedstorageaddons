package com.raoulvdberge.refinedstorageaddons.item;

import com.raoulvdberge.refinedstorage.api.network.item.INetworkItem;
import com.raoulvdberge.refinedstorage.api.network.item.INetworkItemHandler;
import com.raoulvdberge.refinedstorage.item.ItemNetworkItem;
import com.raoulvdberge.refinedstorageaddons.RSAddons;
import com.raoulvdberge.refinedstorageaddons.apiimpl.network.item.NetworkItemNetworkPicker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ItemNetworkPicker extends ItemNetworkItem {
    public ItemNetworkPicker() {
        super("network_picker");

        setCreativeTab(RSAddons.INSTANCE.tab);
    }

    @Override
    protected String getDomain() {
        return RSAddons.ID;
    }

    @Nonnull
    @Override
    public INetworkItem provide(INetworkItemHandler handler, EntityPlayer player, ItemStack stack) {
        return new NetworkItemNetworkPicker(player, stack);
    }
}
