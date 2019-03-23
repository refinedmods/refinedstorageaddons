package com.raoulvdberge.refinedstorageaddons.item;

import com.raoulvdberge.refinedstorage.api.network.INetwork;
import com.raoulvdberge.refinedstorage.api.network.grid.GridType;
import com.raoulvdberge.refinedstorage.api.network.grid.IGridCraftingListener;
import com.raoulvdberge.refinedstorage.apiimpl.network.node.NetworkNodeGrid;
import com.raoulvdberge.refinedstorage.tile.grid.WirelessGrid;
import com.raoulvdberge.refinedstorage.util.StackUtils;
import com.raoulvdberge.refinedstorageaddons.RSAddons;
import com.raoulvdberge.refinedstorageaddons.RSAddonsConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.DimensionManager;

import java.util.HashSet;
import java.util.Set;

public class WirelessCraftingGrid extends WirelessGrid {
    public static int ID;

    private int controllerDimension;
    private Container craftingContainer = new Container() {
        @Override
        public boolean canInteractWith(EntityPlayer player) {
            return false;
        }

        @Override
        public void onCraftMatrixChanged(IInventory inventory) {
            if (server) {
                onCraftingMatrixChanged();
            }
        }
    };
    private IRecipe currentRecipe;
    private InventoryCrafting matrix = new InventoryCrafting(craftingContainer, 3, 3);
    private InventoryCraftResult result = new InventoryCraftResult();
    private boolean server;
    private Set<IGridCraftingListener> craftingListeners = new HashSet<>();

    public WirelessCraftingGrid(ItemStack stack, boolean server) {
        super(stack);

        this.controllerDimension = ItemWirelessCraftingGrid.getDimensionId(stack);
        this.server = server;

        if (stack.hasTagCompound()) {
            StackUtils.readItems(matrix, 1, stack.getTagCompound());
        }
    }

    @Override
    public String getGuiTitle() {
        return "gui.refinedstorage:crafting_grid";
    }

    @Override
    public GridType getGridType() {
        return GridType.CRAFTING;
    }

    @Override
    public InventoryCrafting getCraftingMatrix() {
        return matrix;
    }

    @Override
    public InventoryCraftResult getCraftingResult() {
        return result;
    }

    @Override
    public void onCraftingMatrixChanged() {
        if (currentRecipe == null || !currentRecipe.matches(matrix, DimensionManager.getWorld(controllerDimension))) {
            currentRecipe = CraftingManager.findMatchingRecipe(matrix, DimensionManager.getWorld(controllerDimension));
        }

        if (currentRecipe == null) {
            result.setInventorySlotContents(0, ItemStack.EMPTY);
        } else {
            result.setInventorySlotContents(0, currentRecipe.getCraftingResult(matrix));
        }

        craftingListeners.forEach(IGridCraftingListener::onCraftingMatrixChanged);

        if (!getStack().hasTagCompound()) {
            getStack().setTagCompound(new NBTTagCompound());
        }

        StackUtils.writeItems(matrix, 1, getStack().getTagCompound());
    }

    @Override
    public void onCrafted(EntityPlayer player) {
        INetwork network = getNetwork();

        if (network != null) {
            network.getNetworkItemHandler().drainEnergy(player, RSAddons.INSTANCE.config.wirelessCraftingGridCraftUsage);
        }

        NetworkNodeGrid.onCrafted(this, DimensionManager.getWorld(controllerDimension), player);
    }

    @Override
    public void onCraftedShift(EntityPlayer player) {
        NetworkNodeGrid.onCraftedShift(this, player);
    }

    @Override
    public void onRecipeTransfer(EntityPlayer player, ItemStack[][] recipe) {
        NetworkNodeGrid.onRecipeTransfer(this, player, recipe);
    }

    @Override
    public void addCraftingListener(IGridCraftingListener listener) {
        craftingListeners.add(listener);
    }

    @Override
    public void removeCraftingListener(IGridCraftingListener listener) {
        craftingListeners.remove(listener);
    }
}
