package com.refinedmods.refinedstorageaddons.item.group;

import com.refinedmods.refinedstorageaddons.RSAddons;
import com.refinedmods.refinedstorageaddons.RSAddonsItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class MainCreativeModeTab extends CreativeModeTab {
    public MainCreativeModeTab() {
        super(RSAddons.ID);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(RSAddonsItems.CREATIVE_WIRELESS_CRAFTING_GRID);
    }
}
