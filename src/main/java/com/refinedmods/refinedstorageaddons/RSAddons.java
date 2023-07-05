package com.refinedmods.refinedstorageaddons;

import com.refinedmods.refinedstorage.api.IRSAPI;
import com.refinedmods.refinedstorage.api.RSAPIInject;
import com.refinedmods.refinedstorageaddons.config.ServerConfig;
import com.refinedmods.refinedstorageaddons.setup.ClientSetup;
import com.refinedmods.refinedstorageaddons.setup.CommonSetup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(RSAddons.ID)
public final class RSAddons {
    @RSAPIInject
    public static IRSAPI RSAPI;

    public static final String ID = "refinedstorageaddons";
    public static final ServerConfig SERVER_CONFIG = new ServerConfig();

    public RSAddons() {
        //new ItemStack(RSAddonsItems.CREATIVE_WIRELESS_CRAFTING_GRID.get())
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::onClientSetup);
            MinecraftForge.EVENT_BUS.addListener(ClientSetup::onKeyInput);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::onRegisterKeymappings);
        });

        RSAddonsItems.register();

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_CONFIG.getSpec());

        FMLJavaModLoadingContext.get().getModEventBus().addListener(CommonSetup::onCommonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(CommonSetup::onRegister);
    }
}
