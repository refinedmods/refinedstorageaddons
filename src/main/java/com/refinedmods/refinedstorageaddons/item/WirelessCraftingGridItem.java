package com.refinedmods.refinedstorageaddons.item;

import com.refinedmods.refinedstorage.api.network.item.INetworkItem;
import com.refinedmods.refinedstorage.api.network.item.INetworkItemManager;
import com.refinedmods.refinedstorage.inventory.player.PlayerSlot;
import com.refinedmods.refinedstorage.item.NetworkItem;
import com.refinedmods.refinedstorageaddons.RSAddons;
import com.refinedmods.refinedstorageaddons.apiimpl.network.item.WirelessCraftingGridNetworkItem;
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
        super(
            new Item.Properties().group(RSAddons.MAIN_GROUP).maxStackSize(1),
            type == Type.CREATIVE,
            () -> RSAddons.SERVER_CONFIG.getWirelessCraftingGrid().getCapacity()
        );

        this.type = type;

        this.setRegistryName(RSAddons.ID, (type == Type.CREATIVE ? "creative_" : "") + "wireless_crafting_grid");
    }

    public Type getType() {
        return type;
    }

    @Override
    @Nonnull
    public INetworkItem provide(INetworkItemManager handler, PlayerEntity player, ItemStack stack, PlayerSlot slot) {
        return new WirelessCraftingGridNetworkItem(handler, player, stack, slot);
    }
}
