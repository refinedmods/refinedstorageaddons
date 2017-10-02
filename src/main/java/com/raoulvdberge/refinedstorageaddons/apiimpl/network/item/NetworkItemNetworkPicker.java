package com.raoulvdberge.refinedstorageaddons.apiimpl.network.item;

import com.raoulvdberge.refinedstorage.api.network.INetwork;
import com.raoulvdberge.refinedstorage.api.network.item.INetworkItem;
import com.raoulvdberge.refinedstorage.api.network.item.NetworkItemAction;
import com.raoulvdberge.refinedstorage.api.network.security.Permission;
import com.raoulvdberge.refinedstorage.util.RenderUtils;
import com.raoulvdberge.refinedstorage.util.WorldUtils;
import com.raoulvdberge.refinedstorageaddons.RSAddons;
import com.raoulvdberge.refinedstorageaddons.item.ItemNetworkPicker;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.energy.CapabilityEnergy;

public class NetworkItemNetworkPicker implements INetworkItem {
    private EntityPlayer player;
    private ItemStack stack;

    public NetworkItemNetworkPicker(EntityPlayer player, ItemStack stack) {
        this.player = player;
        this.stack = stack;
    }

    @Override
    public EntityPlayer getPlayer() {
        return player;
    }

    @Override
    public boolean onOpen(INetwork network, EntityPlayer player, EnumHand hand) {
        if (RSAddons.INSTANCE.config.networkPickerUsesEnergy && stack.getItemDamage() != ItemNetworkPicker.TYPE_CREATIVE && stack.getCapability(CapabilityEnergy.ENERGY, null).getEnergyStored() <= RSAddons.INSTANCE.config.networkPickerUsage) {
            return false;
        }

        if (!network.getSecurityManager().hasPermission(Permission.MODIFY, player)) {
            WorldUtils.sendNoPermissionMessage(player);

            return false;
        }

        RayTraceResult target = player.world.rayTraceBlocks(RenderUtils.getStart(player), RenderUtils.getEnd(player));

        if (target != null) {
            IBlockState state = player.world.getBlockState(target.getBlockPos());

            ItemStack toExtract = state.getBlock().getPickBlock(state, target, player.world, target.getBlockPos(), player);

            if (!toExtract.isEmpty()) {
                ItemStack result = network.extractItem(toExtract, toExtract.getMaxStackSize(), false);

                if (result != null) {
                    if (!player.inventory.addItemStackToInventory(result)) {
                        network.insertItem(result, result.getCount(), false);
                    }
                }
            }
        }

        if (RSAddons.INSTANCE.config.networkPickerUsesEnergy && stack.getItemDamage() != ItemNetworkPicker.TYPE_CREATIVE) {
            stack.getCapability(CapabilityEnergy.ENERGY, null).extractEnergy(RSAddons.INSTANCE.config.networkPickerUsage, false);
        }

        return false;
    }

    @Override
    public void onAction(NetworkItemAction action) {
    }
}
