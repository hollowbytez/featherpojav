/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.networking.NettyClient
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketEncoder
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketInitialisation
 *  com.cosmeticsmod.morecosmetics.networking.packets.Packet
 *  io.netty.buffer.ByteBuf
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.handler.codec.MessageToByteEncoder
 */
package com.cosmeticsmod.morecosmetics.networking.handler;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.networking.NettyClient;
import com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf;
import com.cosmeticsmod.morecosmetics.networking.handler.PacketInitialisation;
import com.cosmeticsmod.morecosmetics.networking.packets.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import javax.crypto.Cipher;

public class PacketEncoder
extends MessageToByteEncoder<Packet> {
    private NettyClient client;
    private Cipher encryptionCipher;

    public PacketEncoder(NettyClient c) {
        this.client = c;
    }

    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) throws Exception {
        PacketBuf writeOut = new PacketBuf(out);
        int packet_id = PacketInitialisation.getInstance().getPacketId(packet);
        if (this.encryptionCipher == null) {
            writeOut.writeVarInt(packet_id);
            packet.write(new PacketBuf(writeOut.buf));
        } else {
            PacketBuf decrypted = new PacketBuf();
            decrypted.writeVarInt(packet_id);
            packet.write(new PacketBuf(decrypted.buf));
            byte[] decryptedByts = new byte[decrypted.buf.readableBytes()];
            decrypted.readBytes(decryptedByts);
            writeOut.writeBytes(this.encryptionCipher.doFinal(decryptedByts));
        }
    }

    public void activateEncryption(Cipher encryptionCipher) {
        this.encryptionCipher = encryptionCipher;
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        NettyClient.log((String)"Failed to encode packet properly");
        MoreCosmetics.catchThrowable((Throwable)cause);
        this.client.disconnect();
    }
}

