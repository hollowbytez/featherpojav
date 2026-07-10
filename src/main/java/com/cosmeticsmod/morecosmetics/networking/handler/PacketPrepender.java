/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketPrepender
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.handler.codec.ByteToMessageDecoder
 */
package com.cosmeticsmod.morecosmetics.networking.handler;

import com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

public class PacketPrepender
extends ByteToMessageDecoder {
    private boolean reading;
    private int leftBytes;
    private byte[] byteCache;

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        PacketBuf buf = new PacketBuf(in);
        if (!this.reading && in.readableBytes() > 0) {
            this.initiateReading(buf);
        }
        while (buf.readableBytes() != 0) {
            if (this.leftBytes > 0) {
                this.byteCache[this.byteCache.length - this.leftBytes] = buf.readByte();
                --this.leftBytes;
            }
            if (this.leftBytes != 0) continue;
            out.add(Unpooled.wrappedBuffer((byte[])this.byteCache));
            this.reading = buf.readableBytes() != 0;
            if (!this.reading) continue;
            this.initiateReading(buf);
        }
    }

    private void initiateReading(PacketBuf buf) {
        this.leftBytes = buf.readVarInt();
        this.byteCache = new byte[this.leftBytes];
        this.reading = true;
    }
}

