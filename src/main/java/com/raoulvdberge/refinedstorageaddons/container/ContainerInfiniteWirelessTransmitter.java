package com.raoulvdberge.refinedstorageaddons.container;

import com.raoulvdberge.refinedstorage.container.ContainerBase;
import com.raoulvdberge.refinedstorageaddons.tile.TileInfiniteWirelessTransmitter;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerInfiniteWirelessTransmitter extends ContainerBase {
    public ContainerInfiniteWirelessTransmitter(TileInfiniteWirelessTransmitter infiniteWirelessTransmitter, EntityPlayer player) {
        super(infiniteWirelessTransmitter, player);

        addPlayerInventory(8, 50);
    }
}
