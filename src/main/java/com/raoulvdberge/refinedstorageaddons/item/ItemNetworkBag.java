package com.raoulvdberge.refinedstorageaddons.item;

import com.raoulvdberge.refinedstorage.api.network.item.INetworkItem;
import com.raoulvdberge.refinedstorage.api.network.item.INetworkItemHandler;
import com.raoulvdberge.refinedstorage.api.network.security.Permission;
import com.raoulvdberge.refinedstorage.item.ItemNetworkItem;
import com.raoulvdberge.refinedstorageaddons.RSAddons;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemNetworkBag extends ItemNetworkItem {
    public ItemNetworkBag() {
        super("network_bag");

        setCreativeTab(RSAddons.INSTANCE.tab);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);

        tooltip.add(TextFormatting.YELLOW + "WIP" + TextFormatting.RESET);
        tooltip.add("Filtering options coming soon");
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

    public void onPlayerPickup(EntityItemPickupEvent e, ItemStack stack) {
        applyNetwork(stack, n -> {
            if (RSAddons.INSTANCE.config.networkBagUsesEnergy && stack.getItemDamage() != ItemNetworkBag.TYPE_CREATIVE && stack.getCapability(CapabilityEnergy.ENERGY, null).getEnergyStored() <= RSAddons.INSTANCE.config.networkBagUsage) {
                return;
            }

            if (!n.getSecurityManager().hasPermission(Permission.INSERT, e.getEntityPlayer())) {
                return;
            }

            if (n.insertItem(e.getItem().getItem(), e.getItem().getItem().getCount(), true) == null) {
                e.setResult(Event.Result.ALLOW);
                e.setCanceled(true);

                n.insertItem(e.getItem().getItem(), e.getItem().getItem().getCount(), false);

                e.getItem().setDead();

                if (RSAddons.INSTANCE.config.networkBagUsesEnergy && stack.getItemDamage() != ItemNetworkBag.TYPE_CREATIVE) {
                    stack.getCapability(CapabilityEnergy.ENERGY, null).extractEnergy(RSAddons.INSTANCE.config.networkBagUsage, false);
                }
            }
        }, error -> {
            // NO OP
        });
    }
}
