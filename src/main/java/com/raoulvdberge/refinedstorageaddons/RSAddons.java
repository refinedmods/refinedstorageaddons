package com.raoulvdberge.refinedstorageaddons;

import com.raoulvdberge.refinedstorage.api.IRSAPI;
import com.raoulvdberge.refinedstorage.api.RSAPIInject;
import com.raoulvdberge.refinedstorageaddons.proxy.ProxyCommon;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = RSAddons.ID, version = RSAddons.VERSION, dependencies = RSAddons.DEPENDENCIES, acceptedMinecraftVersions = "[1.12,1.13)", guiFactory = RSAddons.GUI_FACTORY)
public final class RSAddons {
    @RSAPIInject
    public static IRSAPI RSAPI;

    public static final String ID = "refinedstorageaddons";
    public static final String VERSION = "@version@";
    public static final String DEPENDENCIES = "required-after:refinedstorage@[1.6.9,);";
    public static final String GUI_FACTORY = "com.raoulvdberge.refinedstorageaddons.gui.config.ModGuiFactory";

    @Mod.Instance
    public static RSAddons INSTANCE;

    public RSAddonsConfig config;
    public final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(ID);
    public final CreativeTabs tab = new CreativeTabs(RSAddons.ID) {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(RSAddonsItems.WIRELESS_CRAFTING_GRID);
        }
    };

    @SidedProxy(clientSide = "com.raoulvdberge.refinedstorageaddons.proxy.ProxyClient", serverSide = "com.raoulvdberge.refinedstorageaddons.proxy.ProxyCommon")
    public static ProxyCommon PROXY;

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        config = new RSAddonsConfig(e.getSuggestedConfigurationFile());

        PROXY.preInit(e);
    }

    @EventHandler
    public void init(FMLInitializationEvent e) {
        PROXY.init(e);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        PROXY.postInit(e);
    }
}
