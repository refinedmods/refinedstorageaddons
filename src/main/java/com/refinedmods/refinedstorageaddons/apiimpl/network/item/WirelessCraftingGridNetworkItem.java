package com.refinedmods.refinedstorageaddons.apiimpl.network.item;

import com.refinedmods.refinedstorage.api.network.INetwork;
import com.refinedmods.refinedstorage.api.network.item.INetworkItem;
import com.refinedmods.refinedstorage.api.network.item.INetworkItemManager;
import com.refinedmods.refinedstorage.api.network.security.Permission;
import com.refinedmods.refinedstorage.apiimpl.API;
import com.refinedmods.refinedstorage.inventory.player.PlayerSlot;
import com.refinedmods.refinedstorage.util.LevelUtils;
import com.refinedmods.refinedstorageaddons.RSAddons;
import com.refinedmods.refinedstorageaddons.apiimpl.network.grid.WirelessCraftingGridGridFactory;
import com.refinedmods.refinedstorageaddons.item.WirelessCraftingGridItem;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;

public class WirelessCraftingGridNetworkItem implements INetworkItem {
    private final INetworkItemManager handler;
    private final Player player;
    private final ItemStack stack;
    private final PlayerSlot slot;

    public WirelessCraftingGridNetworkItem(INetworkItemManager handler, Player player, ItemStack stack, PlayerSlot slot) {
        this.handler = handler;
        this.player = player;
        this.stack = stack;
        this.slot = slot;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean onOpen(INetwork network) {
        IEnergyStorage energy = stack.getCapability(ForgeCapabilities.ENERGY, null).orElse(null);

        if (RSAddons.SERVER_CONFIG.getWirelessCraftingGrid().getUseEnergy() &&
            ((WirelessCraftingGridItem) stack.getItem()).getType() != WirelessCraftingGridItem.Type.CREATIVE &&
            energy != null &&
            energy.getEnergyStored() <= RSAddons.SERVER_CONFIG.getWirelessCraftingGrid().getOpenUsage()) {
            sendOutOfEnergyMessage();

            return false;
        }

        if (!network.getSecurityManager().hasPermission(Permission.MODIFY, player)) {
            LevelUtils.sendNoPermissionMessage(player);

            return false;
        }

        API.instance().getGridManager().openGrid(WirelessCraftingGridGridFactory.ID, (ServerPlayer) player, stack, slot);

        drainEnergy(RSAddons.SERVER_CONFIG.getWirelessCraftingGrid().getOpenUsage());

        return true;
    }

    @Override
    public void drainEnergy(int energy) {
        if (RSAddons.SERVER_CONFIG.getWirelessCraftingGrid().getUseEnergy() && ((WirelessCraftingGridItem) stack.getItem()).getType() != WirelessCraftingGridItem.Type.CREATIVE) {
            stack.getCapability(ForgeCapabilities.ENERGY).ifPresent(energyStorage -> {
                energyStorage.extractEnergy(energy, false);

                if (energyStorage.getEnergyStored() <= 0) {
                    handler.close(player);

                    player.closeContainer();

                    sendOutOfEnergyMessage();
                }
            });
        }
    }

    private void sendOutOfEnergyMessage() {
        player.sendSystemMessage(Component.translatable("misc.refinedstorage.network_item.out_of_energy", stack.getItem().getDescription()));
    }
}
