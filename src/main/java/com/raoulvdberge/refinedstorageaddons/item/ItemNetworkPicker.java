package com.raoulvdberge.refinedstorageaddons.item;

import com.raoulvdberge.refinedstorage.api.network.INetwork;
import com.raoulvdberge.refinedstorage.api.network.item.INetworkItem;
import com.raoulvdberge.refinedstorage.api.network.item.INetworkItemHandler;
import com.raoulvdberge.refinedstorage.api.network.security.Permission;
import com.raoulvdberge.refinedstorage.item.ItemNetworkItem;
import com.raoulvdberge.refinedstorage.util.RenderUtils;
import com.raoulvdberge.refinedstorage.util.WorldUtils;
import com.raoulvdberge.refinedstorageaddons.RSAddons;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;

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
        return null;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
    }

    public void doPick(INetwork network, EntityPlayerMP player, ItemStack stack) {
        if (RSAddons.INSTANCE.config.networkPickerUsesEnergy && stack.getItemDamage() != ItemNetworkPicker.TYPE_CREATIVE && stack.getCapability(CapabilityEnergy.ENERGY, null).getEnergyStored() <= RSAddons.INSTANCE.config.networkPickerUsage) {
            return;
        }

        if (!network.getSecurityManager().hasPermission(Permission.MODIFY, player)) {
            WorldUtils.sendNoPermissionMessage(player);

            return;
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
    }
}
