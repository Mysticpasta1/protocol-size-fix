package com.mystic.packet.forge.mixin;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.CompressionEncoder;
import net.minecraft.network.FriendlyByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.zip.Deflater;

@Mixin(CompressionEncoder.class)
public class PacketFixEncoder {

    @Shadow private int threshold;
    @Shadow private Deflater deflater;
    @Shadow private byte[] encodeBuf;

    @Inject(method = "encode", at = @At("HEAD"), cancellable = true)
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2, CallbackInfoReturnable<ByteBuf> cir) {
        cir.cancel();
        int i = byteBuf.readableBytes();
        FriendlyByteBuf friendlyByteBuf = new FriendlyByteBuf(byteBuf2);
        if (i < this.threshold) {
            friendlyByteBuf.writeVarInt(0);
            friendlyByteBuf.writeBytes(byteBuf);
        } else {
            byte[] bs = new byte[i];
            byteBuf.readBytes(bs);
            friendlyByteBuf.writeVarInt(bs.length);
            this.deflater.setInput(bs, 0, i);
            this.deflater.finish();

            while(!this.deflater.finished()) {
                int j = this.deflater.deflate(this.encodeBuf);
                friendlyByteBuf.writeBytes(this.encodeBuf, 0, j);
            }
            this.deflater.reset();
        }
    }
}
