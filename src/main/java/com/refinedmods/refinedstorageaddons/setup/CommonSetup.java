package com.refinedmods.refinedstorageaddons.setup;

import com.refinedmods.refinedstorageaddons.RSAddons;
import com.refinedmods.refinedstorageaddons.apiimpl.network.grid.WirelessCraftingGridGridFactory;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public final class CommonSetup {
    private CommonSetup() {
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent e) {
        RSAddons.RSAPI.getGridManager().add(WirelessCraftingGridGridFactory.ID, new WirelessCraftingGridGridFactory());
    }
}
