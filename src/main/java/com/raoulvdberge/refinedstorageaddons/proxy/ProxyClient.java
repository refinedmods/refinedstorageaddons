package com.raoulvdberge.refinedstorageaddons.proxy;

import com.raoulvdberge.refinedstorage.render.StateMapperCTM;
import com.raoulvdberge.refinedstorageaddons.RSAddons;
import com.raoulvdberge.refinedstorageaddons.RSAddonsBlocks;
import com.raoulvdberge.refinedstorageaddons.RSAddonsItems;
import com.raoulvdberge.refinedstorageaddons.network.MessagePickBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class ProxyClient extends ProxyCommon {
    private static final int PICK_BLOCK_DELAY = 300;

    @SubscribeEvent
    public void registerModels(ModelBakeEvent e) {
        ModelLoader.setCustomModelResourceLocation(RSAddonsItems.WIRELESS_CRAFTING_GRID, 0, new ModelResourceLocation("refinedstorageaddons:wireless_crafting_grid", "inventory"));

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RSAddonsBlocks.INFINITE_WIRELESS_TRANSMITTER), 0, new ModelResourceLocation("refinedstorageaddons:infinite_wireless_transmitter", "inventory"));
        ModelLoader.setCustomStateMapper(RSAddonsBlocks.INFINITE_WIRELESS_TRANSMITTER, new StateMapperCTM("refinedstorageaddons:infinite_wireless_transmitter"));

        ModelLoader.setCustomModelResourceLocation(RSAddonsItems.NETWORK_PICKER, 0, new ModelResourceLocation("refinedstorageaddons:network_picker", "inventory"));
    }

    private long lastPickBlock;

    @SubscribeEvent
    public void onMouseInputEvent(InputEvent e) {
        if (Minecraft.getMinecraft().gameSettings.keyBindPickBlock.isKeyDown() && Minecraft.getSystemTime() - lastPickBlock > PICK_BLOCK_DELAY) {
            lastPickBlock = Minecraft.getSystemTime();

            RSAddons.INSTANCE.network.sendToServer(new MessagePickBlock());
        }
    }
}
