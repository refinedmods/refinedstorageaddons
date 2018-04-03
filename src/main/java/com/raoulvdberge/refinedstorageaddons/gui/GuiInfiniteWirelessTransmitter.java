package com.raoulvdberge.refinedstorageaddons.gui;

import com.raoulvdberge.refinedstorage.gui.GuiBase;
import com.raoulvdberge.refinedstorage.gui.control.SideButtonRedstoneMode;
import com.raoulvdberge.refinedstorageaddons.RSAddons;
import com.raoulvdberge.refinedstorageaddons.container.ContainerInfiniteWirelessTransmitter;
import com.raoulvdberge.refinedstorageaddons.tile.TileInfiniteWirelessTransmitter;

public class GuiInfiniteWirelessTransmitter extends GuiBase {
    public GuiInfiniteWirelessTransmitter(ContainerInfiniteWirelessTransmitter container) {
        super(container, 176, 131);
    }

    @Override
    public void init(int x, int y) {
        addSideButton(new SideButtonRedstoneMode(this, TileInfiniteWirelessTransmitter.REDSTONE_MODE));
    }

    @Override
    public void update(int x, int y) {
    }

    @Override
    public void drawBackground(int x, int y, int mouseX, int mouseY) {
        bindTexture(RSAddons.ID, "gui/infinite_wireless_transmitter.png");

        drawTexture(x, y, 0, 0, screenWidth, screenHeight);
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {
        drawString(7, 7, t("gui.refinedstorageaddons:infinite_wireless_transmitter"));
        drawString(7, 39, t("container.inventory"));
    }
}
