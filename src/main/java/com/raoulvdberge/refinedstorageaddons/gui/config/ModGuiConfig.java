package com.raoulvdberge.refinedstorageaddons.gui.config;

import com.raoulvdberge.refinedstorageaddons.RSAddons;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ModGuiConfig extends GuiConfig {
    public ModGuiConfig(GuiScreen guiScreen) {
        super(
            guiScreen,
            RSAddons.INSTANCE.config.getConfigElements(),
            RSAddons.ID,
            false,
            false,
            GuiConfig.getAbridgedConfigPath(RSAddons.INSTANCE.config.getConfig().toString())
        );
    }
}
