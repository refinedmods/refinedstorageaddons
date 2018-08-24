package com.raoulvdberge.refinedstorageaddons.proxy;

import com.raoulvdberge.refinedstorage.apiimpl.util.OneSixMigrationHelper;
import com.raoulvdberge.refinedstorageaddons.RSAddons;
import com.raoulvdberge.refinedstorageaddons.RSAddonsItems;
import com.raoulvdberge.refinedstorageaddons.apiimpl.network.grid.wireless.WirelessGridFactoryWirelessCraftingGrid;
import com.raoulvdberge.refinedstorageaddons.item.WirelessCraftingGrid;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ProxyCommon {
    public void preInit(FMLPreInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void init(FMLInitializationEvent e) {
        WirelessCraftingGrid.ID = RSAddons.RSAPI.getWirelessGridRegistry().add(new WirelessGridFactoryWirelessCraftingGrid());
    }

    public void postInit(FMLPostInitializationEvent e) {
        // NO OP
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> e) {
        e.getRegistry().register(RSAddonsItems.WIRELESS_CRAFTING_GRID);
    }

    @SubscribeEvent
    public void fixBlockMappings(RegistryEvent.MissingMappings<Block> e) {
        OneSixMigrationHelper.removalHook();

        for (RegistryEvent.MissingMappings.Mapping<Block> missing : e.getMappings()) {
            if (missing.key.getNamespace().equals(RSAddons.ID) && missing.key.getPath().equals("infinite_wireless_transmitter")) {
                missing.ignore();
            }
        }
    }

    @SubscribeEvent
    public void fixItemMappings(RegistryEvent.MissingMappings<Item> e) {
        OneSixMigrationHelper.removalHook();

        for (RegistryEvent.MissingMappings.Mapping<Item> missing : e.getMappings()) {
            if (missing.key.getNamespace().equals(RSAddons.ID) && (missing.key.getPath().equals("infinite_wireless_transmitter") || missing.key.getPath().equals("network_bag") || missing.key.getPath().equals("network_picker"))) {
                missing.ignore();
            }
        }
    }
}
