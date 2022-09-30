package com.refinedmods.refinedstorageaddons;

import com.refinedmods.refinedstorageaddons.item.WirelessCraftingGridItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class RSAddonsItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RSAddons.ID);

    static {
        WIRELESS_CRAFTING_GRID = ITEMS.register("wireless_crafting_grid", () -> new WirelessCraftingGridItem(WirelessCraftingGridItem.Type.NORMAL));
        CREATIVE_WIRELESS_CRAFTING_GRID = ITEMS.register("creative_wireless_crafting_grid", () -> new WirelessCraftingGridItem(WirelessCraftingGridItem.Type.CREATIVE));
    }

    public static final RegistryObject<WirelessCraftingGridItem> WIRELESS_CRAFTING_GRID;
    public static final RegistryObject<WirelessCraftingGridItem> CREATIVE_WIRELESS_CRAFTING_GRID;

    public static void register() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
