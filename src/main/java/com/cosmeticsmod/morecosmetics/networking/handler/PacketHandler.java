/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.gui.ModelGui
 *  com.cosmeticsmod.morecosmetics.gui.core.notification.NotificationHandler
 *  com.cosmeticsmod.morecosmetics.networking.NettyClient
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketDecoder
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketEncoder
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketHandler
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketInitialisation
 *  com.cosmeticsmod.morecosmetics.networking.packets.Packet
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketAESKey
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketComplete
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketInfo
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketKick
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketServerHash
 *  com.cosmeticsmod.morecosmetics.networking.utils.CryptionUtils
 *  com.cosmeticsmod.morecosmetics.networking.utils.EnumDisconnection
 *  com.cosmeticsmod.morecosmetics.networking.utils.EnumInfo
 *  com.cosmeticsmod.morecosmetics.utils.LanguageHandler
 *  com.cosmeticsmod.morecosmetics.utils.SharedVars
 *  com.cosmeticsmod.morecosmetics.utils.VersionAdapter
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.channel.SimpleChannelInboundHandler
 */
package com.cosmeticsmod.morecosmetics.networking.handler;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.gui.ModelGui;
import com.cosmeticsmod.morecosmetics.gui.core.notification.NotificationHandler;
import com.cosmeticsmod.morecosmetics.networking.NettyClient;
import com.cosmeticsmod.morecosmetics.networking.handler.PacketDecoder;
import com.cosmeticsmod.morecosmetics.networking.handler.PacketEncoder;
import com.cosmeticsmod.morecosmetics.networking.handler.PacketInitialisation;
import com.cosmeticsmod.morecosmetics.networking.packets.Packet;
import com.cosmeticsmod.morecosmetics.networking.packets.PacketAESKey;
import com.cosmeticsmod.morecosmetics.networking.packets.PacketComplete;
import com.cosmeticsmod.morecosmetics.networking.packets.PacketInfo;
import com.cosmeticsmod.morecosmetics.networking.packets.PacketKick;
import com.cosmeticsmod.morecosmetics.networking.packets.PacketServerHash;
import com.cosmeticsmod.morecosmetics.networking.utils.CryptionUtils;
import com.cosmeticsmod.morecosmetics.networking.utils.EnumDisconnection;
import com.cosmeticsmod.morecosmetics.networking.utils.EnumInfo;
import com.cosmeticsmod.morecosmetics.utils.LanguageHandler;
import com.cosmeticsmod.morecosmetics.utils.SharedVars;
import com.cosmeticsmod.morecosmetics.utils.VersionAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.security.KeyPair;
import java.security.PrivateKey;
import javax.crypto.Cipher;

public class PacketHandler
extends SimpleChannelInboundHandler<Packet> {
    private NettyClient client;
    private KeyPair keyPair;
    private String serverHash;

    public PacketHandler(NettyClient client) {
        this.client = client;
    }

    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        MoreCosmetics.debug((String)("[CONNECTION] IN: " + packet.getClass().getSimpleName() + (packet.getContent() != null ? " | " + packet.getContent() : "")));
        if (PacketInitialisation.getInstance().doesNeedVerify(packet) && this.client.isVerified()) {
            packet.handle(this.client);
        } else if (!PacketInitialisation.getInstance().doesNeedVerify(packet)) {
            int packet_id = PacketInitialisation.getInstance().getPacketId(packet);
            if (packet_id == 2) {
                PacketAESKey packetAES = (PacketAESKey)packet;
                byte[] key = CryptionUtils.decryptRSA((byte[])packetAES.getKey(), (PrivateKey)this.keyPair.getPrivate());
                byte[] iv = CryptionUtils.decryptRSA((byte[])packetAES.getIv(), (PrivateKey)this.keyPair.getPrivate());
                if (iv.length != 12 || key.length != 16) {
                    NettyClient.log((String)"Received invalid aes key");
                    this.client.disconnect();
                }
                Cipher encryptCipher = CryptionUtils.generateCipher((byte[])key, (int)1, (byte[])iv);
                Cipher decryptCipher = CryptionUtils.generateCipher((byte[])key, (int)2, (byte[])iv);
                this.client.send((Packet)new PacketInfo(EnumInfo.ACCEPTED_AESKEY));
                PacketDecoder decoder = (PacketDecoder)ctx.pipeline().get(PacketDecoder.class);
                PacketEncoder encoder = (PacketEncoder)ctx.pipeline().get(PacketEncoder.class);
                decoder.activateDecryption(decryptCipher);
                encoder.activateEncryption(encryptCipher);
            } else if (packet_id == 3) {
                PacketInfo packetInfo = (PacketInfo)packet;
                this.client.getPacketHandler().handle(packetInfo);
            } else if (packet_id == 4) {
                PacketServerHash packetHash = (PacketServerHash)packet;
                this.serverHash = packetHash.getServerHash();
                VersionAdapter va = MoreCosmetics.getInstance().getVersionAdapter();
                if (va.authenticate(this.serverHash)) {
                    this.client.send((Packet)new PacketInfo(EnumInfo.VERIFY_HASHJOIN_REQUEST));
                } else {
                    if (va.isInGame()) {
                        NotificationHandler.sendError((String)LanguageHandler.get((String)"noconnection"));
                    }
                    MoreCosmetics.log((String)"Playing in offline mode!");
                    SharedVars.OFFLINE_MODE = true;
                    this.client.setKicked();
                }
            } else if (packet_id == 5) {
                PacketKick packetKick = (PacketKick)packet;
                if (packetKick.getDisconnect() != EnumDisconnection.SHUTDOWN) {
                    this.client.setKicked();
                }
                this.client.disconnect();
                NettyClient.log((String)("Client kicked for: " + packetKick.getDisconnect().getMessage()));
                ModelGui.refreshGui();
            } else if (packet_id == 6) {
                PacketComplete packetComplete = (PacketComplete)packet;
                NettyClient.log((String)"Login complete!");
                this.client.completedLogin();
                MoreCosmetics.getInstance().getUserHandler().setLoginData(packetComplete);
                ModelGui.refreshGui();
            }
        } else {
            NettyClient.log((String)"Server tried to send non verified packets");
            this.client.disconnect();
        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        MoreCosmetics.debugThrowable((Throwable)cause);
        this.client.disconnect();
    }

    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }
}

