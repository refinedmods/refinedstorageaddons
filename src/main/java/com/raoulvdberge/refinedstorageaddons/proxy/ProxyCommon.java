package com.raoulvdberge.refinedstorageaddons.proxy;

import com.raoulvdberge.refinedstorage.apiimpl.network.node.NetworkNode;
import com.raoulvdberge.refinedstorageaddons.RSAddons;
import com.raoulvdberge.refinedstorageaddons.RSAddonsBlocks;
import com.raoulvdberge.refinedstorageaddons.RSAddonsItems;
import com.raoulvdberge.refinedstorageaddons.apiimpl.network.grid.wireless.WirelessGridFactoryWirelessCraftingGrid;
import com.raoulvdberge.refinedstorageaddons.apiimpl.network.node.NetworkNodeInfiniteWirelessTransmitter;
import com.raoulvdberge.refinedstorageaddons.gui.GuiHandler;
import com.raoulvdberge.refinedstorageaddons.item.WirelessCraftingGrid;
import com.raoulvdberge.refinedstorageaddons.network.MessagePickBlock;
import com.raoulvdberge.refinedstorageaddons.tile.TileInfiniteWirelessTransmitter;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class ProxyCommon {
    public void preInit(FMLPreInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void init(FMLInitializationEvent e) {
        WirelessCraftingGrid.ID = RSAddons.RSAPI.getWirelessGridRegistry().add(new WirelessGridFactoryWirelessCraftingGrid());

        RSAddons.RSAPI.getNetworkNodeRegistry().add(NetworkNodeInfiniteWirelessTransmitter.ID, (tag, world, pos) -> {
            NetworkNode node = new NetworkNodeInfiniteWirelessTransmitter(world, pos);

            node.read(tag);

            return node;
        });

        GameRegistry.registerTileEntity(TileInfiniteWirelessTransmitter.class, RSAddons.ID + ":infinite_wireless_transmitter");

        RSAddons.INSTANCE.network.registerMessage(MessagePickBlock.class, MessagePickBlock.class, 0, Side.SERVER);

        NetworkRegistry.INSTANCE.registerGuiHandler(RSAddons.INSTANCE, new GuiHandler());
    }

    public void postInit(FMLPostInitializationEvent e) {
        // NO OP
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> e) {
        e.getRegistry().register(RSAddonsBlocks.INFINITE_WIRELESS_TRANSMITTER);
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> e) {
        e.getRegistry().register(RSAddonsItems.WIRELESS_CRAFTING_GRID);
        e.getRegistry().register(RSAddonsItems.NETWORK_PICKER);
        e.getRegistry().register(RSAddonsBlocks.INFINITE_WIRELESS_TRANSMITTER.createItem());
    }
}
