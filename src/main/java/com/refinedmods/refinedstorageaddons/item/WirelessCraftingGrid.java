package com.refinedmods.refinedstorageaddons.item;

import com.refinedmods.refinedstorage.api.network.INetwork;
import com.refinedmods.refinedstorage.api.network.grid.GridType;
import com.refinedmods.refinedstorage.api.network.grid.ICraftingGridListener;
import com.refinedmods.refinedstorage.api.network.security.Permission;
import com.refinedmods.refinedstorage.api.util.Action;
import com.refinedmods.refinedstorage.api.util.IStackList;
import com.refinedmods.refinedstorage.tile.grid.WirelessGrid;
import com.refinedmods.refinedstorage.util.StackUtils;
import com.refinedmods.refinedstorageaddons.RSAddons;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class WirelessCraftingGrid extends WirelessGrid {
    @Nullable
    private final MinecraftServer server;
    private final World world;
    private Set<ICraftingGridListener> listeners = new HashSet<>();

    private Container craftingContainer = new Container(null, 0) {
        @Override
        public boolean canInteractWith(PlayerEntity player) {
            return false;
        }

        @Override
        public void onCraftMatrixChanged(IInventory inventory) {
            if (server != null) {
                onCraftingMatrixChanged();
            }
        }
    };
    private ICraftingRecipe currentRecipe;
    private CraftingInventory matrix = new CraftingInventory(craftingContainer, 3, 3);
    private CraftResultInventory result = new CraftResultInventory();

    public WirelessCraftingGrid(ItemStack stack, World world, @Nullable MinecraftServer server, int slotId) {
        super(stack, server, slotId);

        this.server = server;
        this.world = world;

        if (stack.hasTag()) {
            StackUtils.readItems(matrix, 1, stack.getTag());
        }
    }

    @Override
    public ITextComponent getTitle() {
        return new TranslationTextComponent("gui.refinedstorage.crafting_grid");
    }

    @Override
    public GridType getGridType() {
        return GridType.CRAFTING;
    }

    @Override
    public CraftingInventory getCraftingMatrix() {
        return matrix;
    }

    @Override
    public CraftResultInventory getCraftingResult() {
        return result;
    }

    @Override
    public void onCraftingMatrixChanged() {
        if (currentRecipe == null || !currentRecipe.matches(matrix, world)) {
            currentRecipe = world.getRecipeManager().getRecipe(IRecipeType.CRAFTING, matrix, world).orElse(null);
        }

        if (currentRecipe == null) {
            result.setInventorySlotContents(0, ItemStack.EMPTY);
        } else {
            result.setInventorySlotContents(0, currentRecipe.getCraftingResult(matrix));
        }

        listeners.forEach(ICraftingGridListener::onCraftingMatrixChanged);

        if (!getStack().hasTag()) {
            getStack().setTag(new CompoundNBT());
        }

        StackUtils.writeItems(matrix, 1, getStack().getTag());
    }

    @Override
    public void onCrafted(PlayerEntity player, @Nullable IStackList<ItemStack> availableItems, @Nullable IStackList<ItemStack> usedItems) {
        RSAddons.RSAPI.getCraftingGridBehavior().onCrafted(this, currentRecipe, player, availableItems, usedItems);

        INetwork network = getNetwork();

        if (network != null) {
            network.getNetworkItemManager().drainEnergy(player, RSAddons.SERVER_CONFIG.getWirelessCraftingGrid().getCraftUsage());
        }
    }

    @Override
    public void onClear(PlayerEntity player) {
        INetwork network = getNetwork();

        if (network != null && network.getSecurityManager().hasPermission(Permission.INSERT, player)) {
            for (int i = 0; i < matrix.getSizeInventory(); ++i) {
                ItemStack slot = matrix.getStackInSlot(i);

                if (!slot.isEmpty()) {
                    matrix.setInventorySlotContents(i, network.insertItem(slot, slot.getCount(), Action.PERFORM));

                    network.getItemStorageTracker().changed(player, slot.copy());
                }
            }

            network.getNetworkItemManager().drainEnergy(player, RSAddons.SERVER_CONFIG.getWirelessCraftingGrid().getClearUsage());
        }
    }

    @Override
    public void onCraftedShift(PlayerEntity player) {
        RSAddons.RSAPI.getCraftingGridBehavior().onCraftedShift(this, player);
    }

    @Override
    public void onRecipeTransfer(PlayerEntity player, ItemStack[][] recipe) {
        RSAddons.RSAPI.getCraftingGridBehavior().onRecipeTransfer(this, player, recipe);
    }

    @Override
    public void addCraftingListener(ICraftingGridListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeCraftingListener(ICraftingGridListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void onClosed(PlayerEntity player) {
        if (!getStack().hasTag()) {
            getStack().setTag(new CompoundNBT());
        }

        StackUtils.writeItems(matrix, 1, getStack().getTag());
        super.onClosed(player);
    }
}
