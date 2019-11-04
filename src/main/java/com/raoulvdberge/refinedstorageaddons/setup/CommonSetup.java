package com.raoulvdberge.refinedstorageaddons.setup;

import com.raoulvdberge.refinedstorageaddons.RSAddons;
import com.raoulvdberge.refinedstorageaddons.apiimpl.network.grid.WirelessCraftingGridGridFactory;
import com.raoulvdberge.refinedstorageaddons.item.WirelessCraftingGridItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class CommonSetup {
    @SubscribeEvent
    public void onCommonSetup(FMLCommonSetupEvent e) {
        RSAddons.RSAPI.getGridManager().add(WirelessCraftingGridGridFactory.ID, new WirelessCraftingGridGridFactory());
    }

    @SubscribeEvent
    public void onRegisterItems(RegistryEvent.Register<Item> e) {
        e.getRegistry().register(new WirelessCraftingGridItem(WirelessCraftingGridItem.Type.NORMAL));
        e.getRegistry().register(new WirelessCraftingGridItem(WirelessCraftingGridItem.Type.CREATIVE));
    }
}
