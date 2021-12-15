package com.refinedmods.refinedstorageaddons;

import com.refinedmods.refinedstorage.api.IRSAPI;
import com.refinedmods.refinedstorage.api.RSAPIInject;
import com.refinedmods.refinedstorageaddons.config.ServerConfig;
import com.refinedmods.refinedstorageaddons.item.group.MainItemGroup;
import com.refinedmods.refinedstorageaddons.setup.ClientSetup;
import com.refinedmods.refinedstorageaddons.setup.CommonSetup;
import net.minecraft.world.item.Item;
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
    public static final MainItemGroup MAIN_GROUP = new MainItemGroup();

    public RSAddons() {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::onClientSetup);
            MinecraftForge.EVENT_BUS.addListener(ClientSetup::onKeyInput);
        });

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_CONFIG.getSpec());

        FMLJavaModLoadingContext.get().getModEventBus().addListener(CommonSetup::onCommonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, CommonSetup::onRegisterItems);
    }
}
