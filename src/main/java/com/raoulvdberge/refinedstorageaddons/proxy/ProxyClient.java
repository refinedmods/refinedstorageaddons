package com.raoulvdberge.refinedstorageaddons.proxy;

import com.raoulvdberge.refinedstorageaddons.RSAddonsItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ProxyClient extends ProxyCommon {
    @SubscribeEvent
    public void registerModels(ModelBakeEvent e) {
        ModelLoader.setCustomModelResourceLocation(RSAddonsItems.WIRELESS_CRAFTING_GRID, 0, new ModelResourceLocation("refinedstorageaddons:wireless_crafting_grid", "inventory"));
    }
}
