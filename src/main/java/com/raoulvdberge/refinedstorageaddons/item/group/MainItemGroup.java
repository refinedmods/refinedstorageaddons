package com.raoulvdberge.refinedstorageaddons.item.group;

import com.raoulvdberge.refinedstorageaddons.RSAddons;
import com.raoulvdberge.refinedstorageaddons.RSAddonsItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class MainItemGroup extends ItemGroup {
    public MainItemGroup() {
        super(RSAddons.ID);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(RSAddonsItems.CREATIVE_WIRELESS_CRAFTING_GRID);
    }
}
