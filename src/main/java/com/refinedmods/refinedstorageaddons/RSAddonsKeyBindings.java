package com.refinedmods.refinedstorageaddons;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

public final class RSAddonsKeyBindings {
    public static final KeyMapping OPEN_WIRELESS_CRAFTING_GRID = new KeyMapping(
        "key.refinedstorageaddons.openWirelessCraftingGrid",
        KeyConflictContext.IN_GAME,
        KeyModifier.CONTROL,
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_G,
        "Refined Storage Addons"
    );
}