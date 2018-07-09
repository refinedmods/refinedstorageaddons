package com.raoulvdberge.refinedstorageaddons.proxy;

import com.raoulvdberge.refinedstorage.render.statemapper.StateMapperCTM;
import com.raoulvdberge.refinedstorageaddons.RSAddonsBlocks;
import com.raoulvdberge.refinedstorageaddons.RSAddonsItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ProxyClient extends ProxyCommon {
    @SubscribeEvent
    public void registerModels(ModelBakeEvent e) {
        ModelLoader.setCustomModelResourceLocation(RSAddonsItems.WIRELESS_CRAFTING_GRID, 0, new ModelResourceLocation("refinedstorageaddons:wireless_crafting_grid", "inventory"));

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RSAddonsBlocks.INFINITE_WIRELESS_TRANSMITTER), 0, new ModelResourceLocation("refinedstorageaddons:infinite_wireless_transmitter", "inventory"));
        ModelLoader.setCustomStateMapper(RSAddonsBlocks.INFINITE_WIRELESS_TRANSMITTER, new StateMapperCTM(new ResourceLocation("refinedstorageaddons", "infinite_wireless_transmitter")));
    }
}
