package com.raoulvdberge.refinedstorageaddons.proxy;

import com.raoulvdberge.refinedstorage.RS;
import com.raoulvdberge.refinedstorage.network.MessageNetworkItemOpen;
import com.raoulvdberge.refinedstorageaddons.RSAddons;
import com.raoulvdberge.refinedstorageaddons.RSAddonsItems;
import com.raoulvdberge.refinedstorageaddons.RSAddonsKeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class ProxyClient extends ProxyCommon {
    @SubscribeEvent
    public void registerModels(ModelBakeEvent e) {
        ModelLoader.setCustomModelResourceLocation(RSAddonsItems.WIRELESS_CRAFTING_GRID, 0, new ModelResourceLocation(RSAddons.ID + ":wireless_crafting_grid", "inventory"));
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);

        RSAddonsKeyBindings.init();
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent e) {
        InventoryPlayer inv = Minecraft.getMinecraft().player.inventory;

        if (RSAddonsKeyBindings.OPEN_WIRELESS_CRAFTING_GRID.isKeyDown()) {
            findAndOpen(inv, RSAddonsItems.WIRELESS_CRAFTING_GRID);
        }
    }

    private void findAndOpen(IInventory inv, Item search) {
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack slot = inv.getStackInSlot(i);

            if (slot.getItem() == search) {
                RS.INSTANCE.network.sendToServer(new MessageNetworkItemOpen(i));

                return;
            }
        }
    }
}
