package com.raoulvdberge.refinedstorageaddons.network;

import com.raoulvdberge.refinedstorage.network.MessageHandlerPlayerToServer;
import com.raoulvdberge.refinedstorageaddons.RSAddonsItems;
import com.raoulvdberge.refinedstorageaddons.item.ItemNetworkPicker;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessagePickBlock extends MessageHandlerPlayerToServer<MessagePickBlock> implements IMessage {
    @Override
    protected void handle(MessagePickBlock message, EntityPlayerMP player) {
        ItemStack held = player.getHeldItemOffhand();
        if (!player.capabilities.isCreativeMode && held.getItem() == RSAddonsItems.NETWORK_PICKER) {
            ItemNetworkPicker picker = (ItemNetworkPicker) held.getItem();

            picker.applyNetwork(held, n -> picker.doPick(n, player, held), e -> {
                // NO OP
            });
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }
}
