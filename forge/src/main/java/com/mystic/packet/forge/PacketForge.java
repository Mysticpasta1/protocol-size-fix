package com.mystic.packet.forge;

import dev.architectury.platform.forge.EventBuses;
import com.mystic.packet.Packet;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Packet.MOD_ID)
public class PacketForge {
    public PacketForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(Packet.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        Packet.init();
    }
}
