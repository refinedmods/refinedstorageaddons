package com.refinedmods.refinedstorageaddons.setup;

import com.refinedmods.refinedstorage.item.property.NetworkItemPropertyGetter;
import com.refinedmods.refinedstorage.screen.KeyInputListener;
import com.refinedmods.refinedstorageaddons.RSAddonsItems;
import com.refinedmods.refinedstorageaddons.RSAddonsKeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public final class ClientSetup {
    private ClientSetup() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent e) {
        // ItemProperties isn't thread safe
        e.enqueueWork(() -> {
            ItemProperties.register(RSAddonsItems.WIRELESS_CRAFTING_GRID.get(), new ResourceLocation("connected"), new NetworkItemPropertyGetter());
            ItemProperties.register(RSAddonsItems.CREATIVE_WIRELESS_CRAFTING_GRID.get(), new ResourceLocation("connected"), new NetworkItemPropertyGetter());
        });
    }

    @SubscribeEvent
    public static void onRegisterKeymappings(RegisterKeyMappingsEvent e) {
        e.register(RSAddonsKeyBindings.OPEN_WIRELESS_CRAFTING_GRID);
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key e) {
        if (Minecraft.getInstance().player != null) {
            if (RSAddonsKeyBindings.OPEN_WIRELESS_CRAFTING_GRID.isDown()) {
                KeyInputListener.findAndOpen(RSAddonsItems.WIRELESS_CRAFTING_GRID.get(), RSAddonsItems.CREATIVE_WIRELESS_CRAFTING_GRID.get());
            }
        }
    }
}
