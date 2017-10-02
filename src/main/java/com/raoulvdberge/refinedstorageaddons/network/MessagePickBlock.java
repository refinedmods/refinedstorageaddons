package com.raoulvdberge.refinedstorageaddons.network;

import com.raoulvdberge.refinedstorage.network.MessageHandlerPlayerToServer;
import com.raoulvdberge.refinedstorageaddons.RSAddonsItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessagePickBlock extends MessageHandlerPlayerToServer<MessagePickBlock> implements IMessage {
    @Override
    protected void handle(MessagePickBlock message, EntityPlayerMP player) {
        if (!player.capabilities.isCreativeMode && player.getHeldItemOffhand().getItem() == RSAddonsItems.NETWORK_PICKER) {
            player.getHeldItemOffhand().getItem().onItemRightClick(player.world, player, EnumHand.OFF_HAND);
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }
}
