package com.mystic.packet.fabric;

import com.mystic.packet.Packet;
import net.fabricmc.api.ModInitializer;

public class PacketFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Packet.init();
    }
}
