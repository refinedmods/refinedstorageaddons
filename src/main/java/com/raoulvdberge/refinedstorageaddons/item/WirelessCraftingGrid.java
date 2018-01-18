package com.raoulvdberge.refinedstorageaddons.item;

import com.raoulvdberge.refinedstorage.api.network.grid.GridType;
import com.raoulvdberge.refinedstorage.apiimpl.network.node.NetworkNodeGrid;
import com.raoulvdberge.refinedstorage.tile.grid.WirelessGrid;
import com.raoulvdberge.refinedstorage.util.StackUtils;
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
            onCraftingMatrixChanged();
        }
    };
    private IRecipe currentRecipe;
    private InventoryCrafting matrix = new InventoryCrafting(craftingContainer, 3, 3);
    private InventoryCraftResult result = new InventoryCraftResult();

    public WirelessCraftingGrid(int controllerDimension, ItemStack stack) {
        super(controllerDimension, stack);

        this.controllerDimension = controllerDimension;

        if (stack.hasTagCompound()) {
            StackUtils.readItems(matrix, 1, stack.getTagCompound());
        }
    }

    @Override
    public String getGuiTitle() {
        return "gui.refinedstorage:crafting_grid";
    }

    @Override
    public GridType getType() {
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

        if (!getStack().hasTagCompound()) {
            getStack().setTagCompound(new NBTTagCompound());
        }

        StackUtils.writeItems(matrix, 1, getStack().getTagCompound());
    }

    @Override
    public void onCrafted(EntityPlayer player) {
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
}
