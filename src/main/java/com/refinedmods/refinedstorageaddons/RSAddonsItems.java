package com.refinedmods.refinedstorageaddons;

import com.refinedmods.refinedstorageaddons.item.WirelessCraftingGridItem;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class RSAddonsItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, RSAddons.ID);

    static {
        WIRELESS_CRAFTING_GRID = ITEMS.register("wireless_crafting_grid", () -> new WirelessCraftingGridItem(WirelessCraftingGridItem.Type.NORMAL));
        CREATIVE_WIRELESS_CRAFTING_GRID = ITEMS.register("creative_wireless_crafting_grid", () -> new WirelessCraftingGridItem(WirelessCraftingGridItem.Type.CREATIVE));
    }

    public static final DeferredHolder<Item, WirelessCraftingGridItem> WIRELESS_CRAFTING_GRID;
    public static final DeferredHolder<Item, WirelessCraftingGridItem> CREATIVE_WIRELESS_CRAFTING_GRID;

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
