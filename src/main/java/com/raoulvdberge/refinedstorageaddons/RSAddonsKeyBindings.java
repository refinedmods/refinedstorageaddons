package com.raoulvdberge.refinedstorageaddons;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public final class RSAddonsKeyBindings {
    public static final KeyBinding OPEN_WIRELESS_CRAFTING_GRID = new KeyBinding("key.refinedstorageaddons.openWirelessCraftingGrid", KeyConflictContext.IN_GAME, KeyModifier.CONTROL, Keyboard.KEY_G, "Refined Storage Addons");

    public static void init() {
        ClientRegistry.registerKeyBinding(OPEN_WIRELESS_CRAFTING_GRID);
    }
}