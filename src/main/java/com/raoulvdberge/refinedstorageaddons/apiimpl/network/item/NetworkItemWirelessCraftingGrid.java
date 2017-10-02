package com.raoulvdberge.refinedstorageaddons.apiimpl.network.item;

import com.raoulvdberge.refinedstorage.api.network.INetwork;
import com.raoulvdberge.refinedstorage.api.network.item.INetworkItemHandler;
import com.raoulvdberge.refinedstorage.api.network.item.NetworkItemAction;
import com.raoulvdberge.refinedstorage.api.network.security.Permission;
import com.raoulvdberge.refinedstorage.apiimpl.network.item.NetworkItemWirelessGrid;
import com.raoulvdberge.refinedstorage.util.WorldUtils;
import com.raoulvdberge.refinedstorageaddons.RSAddons;
import com.raoulvdberge.refinedstorageaddons.item.ItemWirelessCraftingGrid;
import com.raoulvdberge.refinedstorageaddons.item.WirelessCraftingGrid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class NetworkItemWirelessCraftingGrid extends NetworkItemWirelessGrid {
    private INetworkItemHandler handler;
    private ItemStack stack;

    public NetworkItemWirelessCraftingGrid(INetworkItemHandler handler, EntityPlayer player, ItemStack stack) {
        super(handler, player, stack);

        this.handler = handler;
        this.stack = stack;
    }

    @Override
    public boolean onOpen(INetwork network, EntityPlayer player, EnumHand hand) {
        if (RSAddons.INSTANCE.config.wirelessCraftingGridUsesEnergy && stack.getItemDamage() != ItemWirelessCraftingGrid.TYPE_CREATIVE && stack.getCapability(CapabilityEnergy.ENERGY, null).getEnergyStored() <= RSAddons.INSTANCE.config.wirelessCraftingGridOpenUsage) {
            return false;
        }

        if (!network.getSecurityManager().hasPermission(Permission.MODIFY, player)) {
            WorldUtils.sendNoPermissionMessage(player);

            return false;
        }

        RSAddons.RSAPI.openWirelessGrid(player, hand, network.world().provider.getDimension(), WirelessCraftingGrid.ID);

        network.sendItemStorageToClient((EntityPlayerMP) player);

        drainEnergy(RSAddons.INSTANCE.config.wirelessCraftingGridOpenUsage);

        return true;
    }

    @Override
    public void onAction(NetworkItemAction action) {
        switch (action) {
            case ITEM_INSERTED:
                drainEnergy(RSAddons.INSTANCE.config.wirelessCraftingGridInsertUsage);
                break;
            case ITEM_EXTRACTED:
                drainEnergy(RSAddons.INSTANCE.config.wirelessCraftingGridExtractUsage);
                break;
            case ITEM_CRAFTED:
                drainEnergy(RSAddons.INSTANCE.config.wirelessCraftingGridCraftUsage);
                break;
        }
    }

    private void drainEnergy(int energy) {
        if (RSAddons.INSTANCE.config.wirelessCraftingGridUsesEnergy && stack.getItemDamage() != ItemWirelessCraftingGrid.TYPE_CREATIVE) {
            IEnergyStorage energyStorage = stack.getCapability(CapabilityEnergy.ENERGY, null);

            energyStorage.extractEnergy(energy, false);

            if (energyStorage.getEnergyStored() <= 0) {
                handler.onClose(getPlayer());

                getPlayer().closeScreen();
            }
        }
    }
}
