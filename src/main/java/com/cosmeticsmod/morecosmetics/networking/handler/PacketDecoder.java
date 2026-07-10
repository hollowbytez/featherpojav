/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.networking.NettyClient
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketDecoder
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketInitialisation
 *  com.cosmeticsmod.morecosmetics.networking.packets.Packet
 *  io.netty.buffer.ByteBuf
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.handler.codec.ByteToMessageDecoder
 */
package com.cosmeticsmod.morecosmetics.networking.handler;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.networking.NettyClient;
import com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf;
import com.cosmeticsmod.morecosmetics.networking.handler.PacketInitialisation;
import com.cosmeticsmod.morecosmetics.networking.packets.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.io.IOException;
import java.util.List;
import javax.crypto.Cipher;

public class PacketDecoder
extends ByteToMessageDecoder {
    private NettyClient client;
    private Cipher decryptionCipher;

    public PacketDecoder(NettyClient c) {
        this.client = c;
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        PacketBuf fetched = null;
        if (this.decryptionCipher != null) {
            byte[] encrypted = new byte[in.readableBytes()];
            in.readBytes(encrypted);
            byte[] decrypted = this.decryptionCipher.doFinal(encrypted);
            fetched = new PacketBuf(decrypted);
        } else {
            fetched = new PacketBuf(in);
        }
        if (fetched.readableBytes() == 0) {
            NettyClient.log((String)"Can't decode empty packet");
            return;
        }
        int packet_id = fetched.readVarInt();
        try {
            Packet packet = PacketInitialisation.getInstance().getPacket(packet_id);
            packet.read(fetched);
            out.add(packet);
        }
        catch (IOException e) {
            NettyClient.log((String)(packet_id + " has no default construtor"));
        }
    }

    public void activateDecryption(Cipher decryptionCipher) {
        this.decryptionCipher = decryptionCipher;
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        NettyClient.log((String)"Failed to decode packet properly");
        MoreCosmetics.catchThrowable((Throwable)cause);
        this.client.disconnect();
    }
}

