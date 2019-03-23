package com.raoulvdberge.refinedstorageaddons.apiimpl.network.item;

import com.raoulvdberge.refinedstorage.api.network.INetwork;
import com.raoulvdberge.refinedstorage.api.network.item.INetworkItemHandler;
import com.raoulvdberge.refinedstorage.api.network.security.Permission;
import com.raoulvdberge.refinedstorage.apiimpl.API;
import com.raoulvdberge.refinedstorage.apiimpl.network.item.NetworkItemWirelessGrid;
import com.raoulvdberge.refinedstorage.util.WorldUtils;
import com.raoulvdberge.refinedstorageaddons.RSAddons;
import com.raoulvdberge.refinedstorageaddons.item.ItemWirelessCraftingGrid;
import com.raoulvdberge.refinedstorageaddons.item.WirelessCraftingGrid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class NetworkItemWirelessCraftingGrid extends NetworkItemWirelessGrid {
    private INetworkItemHandler handler;
    private ItemStack stack;
    private EntityPlayer player;

    public NetworkItemWirelessCraftingGrid(INetworkItemHandler handler, EntityPlayer player, ItemStack stack) {
        super(handler, player, stack);

        this.handler = handler;
        this.player = player;
        this.stack = stack;
    }

    @Override
    public boolean onOpen(INetwork network) {
        if (RSAddons.INSTANCE.config.wirelessCraftingGridUsesEnergy && stack.getItemDamage() != ItemWirelessCraftingGrid.TYPE_CREATIVE && stack.getCapability(CapabilityEnergy.ENERGY, null).getEnergyStored() <= RSAddons.INSTANCE.config.wirelessCraftingGridOpenUsage) {
            return false;
        }

        if (!network.getSecurityManager().hasPermission(Permission.MODIFY, player)) {
            WorldUtils.sendNoPermissionMessage(player);

            return false;
        }

        API.instance().getGridManager().openGrid(WirelessCraftingGrid.ID, (EntityPlayerMP) player, stack);

        drainEnergy(RSAddons.INSTANCE.config.wirelessCraftingGridOpenUsage);

        return true;
    }

    @Override
    public void drainEnergy(int energy) {
        if (RSAddons.INSTANCE.config.wirelessCraftingGridUsesEnergy && stack.getItemDamage() != ItemWirelessCraftingGrid.TYPE_CREATIVE) {
            IEnergyStorage energyStorage = stack.getCapability(CapabilityEnergy.ENERGY, null);

            energyStorage.extractEnergy(energy, false);

            if (energyStorage.getEnergyStored() <= 0) {
                handler.close(getPlayer());

                getPlayer().closeScreen();
            }
        }
    }
}
