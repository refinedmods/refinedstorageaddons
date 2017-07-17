package com.raoulvdberge.refinedstorageaddons.item;

import com.raoulvdberge.refinedstorage.api.network.INetwork;
import com.raoulvdberge.refinedstorage.api.network.item.INetworkItemHandler;
import com.raoulvdberge.refinedstorage.api.network.security.Permission;
import com.raoulvdberge.refinedstorage.apiimpl.network.item.NetworkItemWirelessGrid;
import com.raoulvdberge.refinedstorage.util.WorldUtils;
import com.raoulvdberge.refinedstorageaddons.RSAddons;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class NetworkItemWirelessCraftingGrid extends NetworkItemWirelessGrid {
    public NetworkItemWirelessCraftingGrid(INetworkItemHandler handler, EntityPlayer player, ItemStack stack) {
        super(handler, player, stack);
    }

    @Override
    public boolean onOpen(INetwork network, EntityPlayer player, World controllerWorld, EnumHand hand) {
        if (RSAddons.INSTANCE.config.wirelessCraftingGridUsesEnergy && stack.getItemDamage() != ItemWirelessCraftingGrid.TYPE_CREATIVE && stack.getCapability(CapabilityEnergy.ENERGY, null).getEnergyStored() <= RSAddons.INSTANCE.config.wirelessCraftingGridOpenUsage) {
            return false;
        }

        if (!network.getSecurityManager().hasPermission(Permission.MODIFY, player)) {
            WorldUtils.sendNoPermissionMessage(player);

            return false;
        }

        RSAddons.RSAPI.openWirelessGrid(player, hand, controllerWorld.provider.getDimension(), WirelessCraftingGrid.ID);

        network.sendItemStorageToClient((EntityPlayerMP) player);

        drainEnergy(RSAddons.INSTANCE.config.wirelessCraftingGridOpenUsage);

        return true;
    }

    public void drainEnergy(int energy) {
        if (RSAddons.INSTANCE.config.wirelessCraftingGridUsesEnergy && stack.getItemDamage() != ItemWirelessCraftingGrid.TYPE_CREATIVE) {
            IEnergyStorage energyStorage = stack.getCapability(CapabilityEnergy.ENERGY, null);

            energyStorage.extractEnergy(energy, false);

            if (energyStorage.getEnergyStored() <= 0) {
                handler.onClose(getPlayer());

                getPlayer().closeScreen();
            }
        }
    }

    @Override
    public int getInsertUsage() {
        return RSAddons.INSTANCE.config.wirelessCraftingGridInsertUsage;
    }

    @Override
    public int getExtractUsage() {
        return RSAddons.INSTANCE.config.wirelessCraftingGridExtractUsage;
    }
}
