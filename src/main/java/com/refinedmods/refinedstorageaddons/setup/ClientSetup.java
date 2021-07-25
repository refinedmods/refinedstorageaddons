package com.refinedmods.refinedstorageaddons.setup;

import com.refinedmods.refinedstorage.item.property.NetworkItemPropertyGetter;
import com.refinedmods.refinedstorage.screen.KeyInputListener;
import com.refinedmods.refinedstorageaddons.RSAddonsItems;
import com.refinedmods.refinedstorageaddons.RSAddonsKeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
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

        ItemModelsProperties.func_239418_a_(RSAddonsItems.WIRELESS_CRAFTING_GRID, new ResourceLocation("connected"), new NetworkItemPropertyGetter());
        ItemModelsProperties.func_239418_a_(RSAddonsItems.CREATIVE_WIRELESS_CRAFTING_GRID, new ResourceLocation("connected"), new NetworkItemPropertyGetter());
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent e) {
        if (Minecraft.getInstance().player != null) {
            if (RSAddonsKeyBindings.OPEN_WIRELESS_CRAFTING_GRID.isKeyDown()) {
                KeyInputListener.findAndOpen(RSAddonsItems.WIRELESS_CRAFTING_GRID, RSAddonsItems.CREATIVE_WIRELESS_CRAFTING_GRID);
            }
        }
    }
}
