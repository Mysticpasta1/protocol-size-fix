package com.mystic.packet.fabric;

import net.examplemod.ExampleMod;
import net.fabricmc.api.ModInitializer;

public class PacketFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Packet.init();
    }
}
