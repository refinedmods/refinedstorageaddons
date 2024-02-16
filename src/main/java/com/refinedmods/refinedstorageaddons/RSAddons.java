package com.refinedmods.refinedstorageaddons;

import com.refinedmods.refinedstorage.api.IRSAPI;
import com.refinedmods.refinedstorage.api.RSAPIInject;
import com.refinedmods.refinedstorageaddons.config.ServerConfig;
import com.refinedmods.refinedstorageaddons.setup.ClientSetup;
import com.refinedmods.refinedstorageaddons.setup.CommonSetup;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;

@Mod(RSAddons.ID)
public final class RSAddons {
    @RSAPIInject
    public static IRSAPI RSAPI;

    public static final String ID = "refinedstorageaddons";
    public static final ServerConfig SERVER_CONFIG = new ServerConfig();

    public RSAddons(IEventBus eventBus) {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            eventBus.addListener(ClientSetup::onClientSetup);
            eventBus.addListener(ClientSetup::onRegisterKeymappings);
            NeoForge.EVENT_BUS.addListener(ClientSetup::onKeyInput);
        }

        RSAddonsItems.register(eventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_CONFIG.getSpec());

        eventBus.addListener(CommonSetup::onCommonSetup);
        eventBus.addListener(CommonSetup::onRegisterCapabilities);
        eventBus.addListener(CommonSetup::onRegister);
    }
}
