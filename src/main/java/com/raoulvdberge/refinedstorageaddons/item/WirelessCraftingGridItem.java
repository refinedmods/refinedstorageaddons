package com.raoulvdberge.refinedstorageaddons.item;

import com.raoulvdberge.refinedstorage.api.network.item.INetworkItem;
import com.raoulvdberge.refinedstorage.api.network.item.INetworkItemManager;
import com.raoulvdberge.refinedstorage.item.NetworkItem;
import com.raoulvdberge.refinedstorageaddons.RSAddons;
import com.raoulvdberge.refinedstorageaddons.apiimpl.network.item.WirelessCraftingGridNetworkItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class WirelessCraftingGridItem extends NetworkItem {
    public enum Type {
        NORMAL,
        CREATIVE
    }

    private final Type type;

    public WirelessCraftingGridItem(Type type) {
        super(new Item.Properties().group(RSAddons.MAIN_GROUP).maxStackSize(1), type == Type.CREATIVE, () -> RSAddons.SERVER_CONFIG.getWirelessCraftingGrid().getCapacity());

        this.type = type;

        this.setRegistryName(RSAddons.ID, (type == Type.CREATIVE ? "creative_" : "") + "wireless_crafting_grid");
    }

    public Type getType() {
        return type;
    }

    @Override
    @Nonnull
    public INetworkItem provide(INetworkItemManager handler, PlayerEntity player, ItemStack stack) {
        return new WirelessCraftingGridNetworkItem(handler, player, stack);
    }
}
