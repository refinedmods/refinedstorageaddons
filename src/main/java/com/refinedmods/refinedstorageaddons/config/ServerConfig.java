package com.refinedmods.refinedstorageaddons.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig {
    private final ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
    private final ModConfigSpec spec;
    private final WirelessCraftingGrid wirelessCraftingGrid;

    public ServerConfig() {
        wirelessCraftingGrid = new WirelessCraftingGrid();

        spec = builder.build();
    }

    public WirelessCraftingGrid getWirelessCraftingGrid() {
        return wirelessCraftingGrid;
    }

    public class WirelessCraftingGrid {
        private final ModConfigSpec.BooleanValue useEnergy;
        private final ModConfigSpec.IntValue capacity;
        private final ModConfigSpec.IntValue openUsage;
        private final ModConfigSpec.IntValue craftUsage;
        private final ModConfigSpec.IntValue clearUsage;

        public WirelessCraftingGrid() {
            builder.push("wirelessCraftingGrid");

            useEnergy = builder.comment("Whether the Wireless Crafting Grid uses energy").define("useEnergy", true);
            capacity = builder.comment("The energy capacity of the Wireless Crafting Grid").defineInRange("capacity", 3200, 0, Integer.MAX_VALUE);
            openUsage = builder.comment("The energy used by the Wireless Crafting Grid to open").defineInRange("openUsage", 30, 0, Integer.MAX_VALUE);
            craftUsage = builder.comment("The energy used by the Wireless Crafting Grid to craft an item").defineInRange("craftUsage", 1, 0, Integer.MAX_VALUE);
            clearUsage = builder.comment("The energy used by the Wireless Crafting Grid to clear the crafting matrix").defineInRange("clearUsage", 10, 0, Integer.MAX_VALUE);

            builder.pop();
        }

        public boolean getUseEnergy() {
            return useEnergy.get();
        }

        public int getCapacity() {
            return capacity.get();
        }

        public int getOpenUsage() {
            return openUsage.get();
        }

        public int getCraftUsage() {
            return craftUsage.get();
        }

        public int getClearUsage() {
            return clearUsage.get();
        }
    }

    public ModConfigSpec getSpec() {
        return spec;
    }
}
