/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketSplitter
 *  io.netty.buffer.ByteBuf
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.handler.codec.MessageToByteEncoder
 */
package com.cosmeticsmod.morecosmetics.networking.handler;

import com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketSplitter
extends MessageToByteEncoder<ByteBuf> {
    protected void encode(ChannelHandlerContext ctx, ByteBuf buf, ByteBuf out) throws Exception {
        int writeSize = buf.readableBytes();
        int byteWriteSize = PacketBuf.getVarIntSize((int)writeSize);
        PacketBuf packetBuf = new PacketBuf(out);
        packetBuf.ensureWritable(writeSize + byteWriteSize);
        packetBuf.writeVarInt(writeSize);
        packetBuf.writeBytes(buf);
    }
}

