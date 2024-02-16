package com.refinedmods.refinedstorageaddons.setup;

import com.refinedmods.refinedstorageaddons.RSAddons;
import com.refinedmods.refinedstorageaddons.RSAddonsItems;
import com.refinedmods.refinedstorageaddons.apiimpl.network.grid.WirelessCraftingGridGridFactory;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

public final class CommonSetup {
    private CommonSetup() {
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent e) {
        RSAddons.RSAPI.getGridManager().add(WirelessCraftingGridGridFactory.ID, new WirelessCraftingGridGridFactory());
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent e) {
        e.registerItem(
            Capabilities.EnergyStorage.ITEM,
            (stack, ctx) -> RSAddonsItems.WIRELESS_CRAFTING_GRID.get().createEnergyStorage(stack),
            RSAddonsItems.WIRELESS_CRAFTING_GRID.get()
        );
    }

    @SubscribeEvent
    public static void onRegister(RegisterEvent e) {
        e.register(Registries.CREATIVE_MODE_TAB, helper -> {
            helper.register(new ResourceLocation(RSAddons.ID, "general"), CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.refinedstorageaddons"))
                .icon(() -> new ItemStack(RSAddonsItems.CREATIVE_WIRELESS_CRAFTING_GRID.get()))
                .displayItems((params, output) -> {
                    output.accept(RSAddonsItems.WIRELESS_CRAFTING_GRID.get());
                    output.accept(RSAddonsItems.CREATIVE_WIRELESS_CRAFTING_GRID.get());
                })
                .build());
        });
    }
}
