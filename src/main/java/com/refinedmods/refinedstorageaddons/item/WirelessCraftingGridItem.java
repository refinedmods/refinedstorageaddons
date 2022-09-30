package com.refinedmods.refinedstorageaddons.item;

import com.refinedmods.refinedstorage.api.network.item.INetworkItem;
import com.refinedmods.refinedstorage.api.network.item.INetworkItemManager;
import com.refinedmods.refinedstorage.inventory.player.PlayerSlot;
import com.refinedmods.refinedstorage.item.NetworkItem;
import com.refinedmods.refinedstorageaddons.RSAddons;
import com.refinedmods.refinedstorageaddons.apiimpl.network.item.WirelessCraftingGridNetworkItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class WirelessCraftingGridItem extends NetworkItem {
    public enum Type {
        NORMAL,
        CREATIVE
    }

    private final Type type;

    public WirelessCraftingGridItem(Type type) {
        super(
            new Item.Properties().tab(RSAddons.CREATIVE_MODE_TAB).stacksTo(1),
            type == Type.CREATIVE,
            () -> RSAddons.SERVER_CONFIG.getWirelessCraftingGrid().getCapacity()
        );

        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    @Nonnull
    public INetworkItem provide(INetworkItemManager handler, Player player, ItemStack stack, PlayerSlot slot) {
        return new WirelessCraftingGridNetworkItem(handler, player, stack, slot);
    }
}
