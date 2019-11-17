package com.raoulvdberge.refinedstorageaddons.apiimpl.network.item;

import com.raoulvdberge.refinedstorage.api.network.INetwork;
import com.raoulvdberge.refinedstorage.api.network.item.INetworkItem;
import com.raoulvdberge.refinedstorage.api.network.item.INetworkItemManager;
import com.raoulvdberge.refinedstorage.api.network.security.Permission;
import com.raoulvdberge.refinedstorage.apiimpl.API;
import com.raoulvdberge.refinedstorage.util.WorldUtils;
import com.raoulvdberge.refinedstorageaddons.RSAddons;
import com.raoulvdberge.refinedstorageaddons.apiimpl.network.grid.WirelessCraftingGridGridFactory;
import com.raoulvdberge.refinedstorageaddons.item.WirelessCraftingGridItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class WirelessCraftingGridNetworkItem implements INetworkItem {
    private INetworkItemManager handler;
    private PlayerEntity player;
    private ItemStack stack;
    private int slotId;

    public WirelessCraftingGridNetworkItem(INetworkItemManager handler, PlayerEntity player, ItemStack stack, int slotId) {
        this.handler = handler;
        this.player = player;
        this.stack = stack;
        this.slotId = slotId;
    }

    @Override
    public PlayerEntity getPlayer() {
        return player;
    }

    @Override
    public boolean onOpen(INetwork network) {
        IEnergyStorage energy = stack.getCapability(CapabilityEnergy.ENERGY, null).orElse(null);

        if (RSAddons.SERVER_CONFIG.getWirelessCraftingGrid().getUseEnergy() &&
            ((WirelessCraftingGridItem) stack.getItem()).getType() != WirelessCraftingGridItem.Type.CREATIVE &&
            energy != null &&
            energy.getEnergyStored() <= RSAddons.SERVER_CONFIG.getWirelessCraftingGrid().getOpenUsage()) {
            sendOutOfEnergyMessage();

            return false;
        }

        if (!network.getSecurityManager().hasPermission(Permission.MODIFY, player)) {
            WorldUtils.sendNoPermissionMessage(player);

            return false;
        }

        API.instance().getGridManager().openGrid(WirelessCraftingGridGridFactory.ID, (ServerPlayerEntity) player, stack, slotId);

        drainEnergy(RSAddons.SERVER_CONFIG.getWirelessCraftingGrid().getOpenUsage());

        return true;
    }

    @Override
    public void drainEnergy(int energy) {
        if (RSAddons.SERVER_CONFIG.getWirelessCraftingGrid().getUseEnergy() && ((WirelessCraftingGridItem) stack.getItem()).getType() != WirelessCraftingGridItem.Type.CREATIVE) {
            stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(energyStorage -> {
                energyStorage.extractEnergy(energy, false);

                if (energyStorage.getEnergyStored() <= 0) {
                    handler.close(player);

                    player.closeScreen();

                    sendOutOfEnergyMessage();
                }
            });
        }
    }

    private void sendOutOfEnergyMessage() {
        player.sendMessage(new TranslationTextComponent("misc.refinedstorage.network_item.out_of_energy", new TranslationTextComponent(stack.getItem().getTranslationKey())));
    }
}
