package com.refinedmods.refinedstorageaddons.item.group;

import com.refinedmods.refinedstorageaddons.RSAddons;
import com.refinedmods.refinedstorageaddons.RSAddonsItems;
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
