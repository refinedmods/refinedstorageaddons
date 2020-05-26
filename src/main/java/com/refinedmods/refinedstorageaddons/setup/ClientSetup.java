package com.refinedmods.refinedstorageaddons.setup;

import com.refinedmods.refinedstorage.screen.KeyInputListener;
import com.refinedmods.refinedstorageaddons.RSAddonsItems;
import com.refinedmods.refinedstorageaddons.RSAddonsKeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientSetup {
    public ClientSetup() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
    }

    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent e) {
        MinecraftForge.EVENT_BUS.register(this);

        ClientRegistry.registerKeyBinding(RSAddonsKeyBindings.OPEN_WIRELESS_CRAFTING_GRID);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent e) {
        if (Minecraft.getInstance().player != null) {
            PlayerInventory inv = Minecraft.getInstance().player.inventory;

            if (RSAddonsKeyBindings.OPEN_WIRELESS_CRAFTING_GRID.isKeyDown()) {
                KeyInputListener.findAndOpen(inv, (error) -> Minecraft.getInstance().player.sendMessage(error), RSAddonsItems.WIRELESS_CRAFTING_GRID, RSAddonsItems.CREATIVE_WIRELESS_CRAFTING_GRID);
            }
        }
    }
}
