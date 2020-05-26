package com.refinedmods.refinedstorageaddons;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

public final class RSAddonsKeyBindings {
    public static final KeyBinding OPEN_WIRELESS_CRAFTING_GRID = new KeyBinding(
        "key.refinedstorageaddons.openWirelessCraftingGrid",
        KeyConflictContext.IN_GAME,
        KeyModifier.CONTROL,
        InputMappings.Type.KEYSYM,
        GLFW.GLFW_KEY_G,
        "Refined Storage Addons"
    );
}