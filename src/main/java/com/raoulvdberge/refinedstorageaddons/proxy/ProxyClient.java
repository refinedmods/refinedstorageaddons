package com.raoulvdberge.refinedstorageaddons.proxy;

import com.raoulvdberge.refinedstorage.render.model.baked.BakedModelFullbright;
import com.raoulvdberge.refinedstorageaddons.RSAddons;
import com.raoulvdberge.refinedstorageaddons.RSAddonsBlocks;
import com.raoulvdberge.refinedstorageaddons.RSAddonsItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ProxyClient extends ProxyCommon {
    @SubscribeEvent
    public void registerModels(ModelBakeEvent e) {
        ModelLoader.setCustomModelResourceLocation(RSAddonsItems.WIRELESS_CRAFTING_GRID, 0, new ModelResourceLocation(RSAddons.ID + ":wireless_crafting_grid", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RSAddonsBlocks.INFINITE_WIRELESS_TRANSMITTER), 0, new ModelResourceLocation(RSAddons.ID + ":infinite_wireless_transmitter", "inventory"));
    }

    @SubscribeEvent
    public void onModelBake(ModelBakeEvent e) {
        for (ModelResourceLocation resource : e.getModelRegistry().getKeys()) {
            if (resource.getNamespace().equals(RSAddons.ID) && resource.getPath().equals("infinite_wireless_transmitter")) {
                e.getModelRegistry().putObject(resource, new BakedModelFullbright(e.getModelRegistry().getObject(resource), RSAddons.ID + ":blocks/infinite_wireless_transmitter_connected"));
            }
        }
    }
}
