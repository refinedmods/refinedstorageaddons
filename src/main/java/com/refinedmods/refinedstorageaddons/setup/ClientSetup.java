package com.refinedmods.refinedstorageaddons.setup;

import com.refinedmods.refinedstorage.item.property.NetworkItemPropertyGetter;
import com.refinedmods.refinedstorage.screen.KeyInputListener;
import com.refinedmods.refinedstorageaddons.RSAddonsItems;
import com.refinedmods.refinedstorageaddons.RSAddonsKeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public final class ClientSetup {
    private ClientSetup() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent e) {
        ClientRegistry.registerKeyBinding(RSAddonsKeyBindings.OPEN_WIRELESS_CRAFTING_GRID);

        ItemProperties.register(RSAddonsItems.WIRELESS_CRAFTING_GRID, new ResourceLocation("connected"), new NetworkItemPropertyGetter());
        ItemProperties.register(RSAddonsItems.CREATIVE_WIRELESS_CRAFTING_GRID, new ResourceLocation("connected"), new NetworkItemPropertyGetter());
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent e) {
        if (e.getAction() != 1 || Minecraft.getInstance().player == null) return;
        if (e.getKey() == RSAddonsKeyBindings.OPEN_WIRELESS_CRAFTING_GRID.getKey()) {
            KeyInputListener.findAndOpen(RSAddonsItems.WIRELESS_CRAFTING_GRID, RSAddonsItems.CREATIVE_WIRELESS_CRAFTING_GRID);
        }
    }
}
