package com.refinedmods.refinedstorageaddons.setup;

import com.refinedmods.refinedstorageaddons.RSAddons;
import com.refinedmods.refinedstorageaddons.apiimpl.network.grid.WirelessCraftingGridGridFactory;
import com.refinedmods.refinedstorageaddons.item.WirelessCraftingGridItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public final class CommonSetup {
    private CommonSetup() {
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent e) {
        RSAddons.RSAPI.getGridManager().add(WirelessCraftingGridGridFactory.ID, new WirelessCraftingGridGridFactory());
    }

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> e) {
        e.getRegistry().register(new WirelessCraftingGridItem(WirelessCraftingGridItem.Type.NORMAL));
        e.getRegistry().register(new WirelessCraftingGridItem(WirelessCraftingGridItem.Type.CREATIVE));
    }
}
