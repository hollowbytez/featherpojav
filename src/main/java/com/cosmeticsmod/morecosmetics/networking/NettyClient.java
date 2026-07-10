/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.networking.ClientPacketHandler
 *  com.cosmeticsmod.morecosmetics.networking.NettyClient
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketDecoder
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketEncoder
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketHandler
 *  com.cosmeticsmod.morecosmetics.networking.packets.Packet
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketClientInfo
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketHello
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketIndicator
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketInfo
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketRSAKey
 *  com.cosmeticsmod.morecosmetics.networking.utils.CryptionUtils
 *  com.cosmeticsmod.morecosmetics.networking.utils.EnumInfo
 *  com.cosmeticsmod.morecosmetics.utils.CompatibilityManager
 *  com.cosmeticsmod.morecosmetics.utils.ITickListener
 *  io.netty.bootstrap.Bootstrap
 *  io.netty.channel.Channel
 *  io.netty.channel.ChannelFuture
 *  io.netty.channel.ChannelHandler
 *  io.netty.channel.EventLoopGroup
 *  io.netty.channel.epoll.Epoll
 *  io.netty.channel.epoll.EpollEventLoopGroup
 *  io.netty.channel.epoll.EpollSocketChannel
 *  io.netty.channel.nio.NioEventLoopGroup
 *  io.netty.channel.socket.nio.NioSocketChannel
 */
package com.cosmeticsmod.morecosmetics.networking;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.networking.ClientPacketHandler;
import com.cosmeticsmod.morecosmetics.networking.handler.PacketDecoder;
import com.cosmeticsmod.morecosmetics.networking.handler.PacketEncoder;
import com.cosmeticsmod.morecosmetics.networking.handler.PacketHandler;
import com.cosmeticsmod.morecosmetics.networking.packets.Packet;
import com.cosmeticsmod.morecosmetics.networking.packets.PacketClientInfo;
import com.cosmeticsmod.morecosmetics.networking.packets.PacketHello;
import com.cosmeticsmod.morecosmetics.networking.packets.PacketIndicator;
import com.cosmeticsmod.morecosmetics.networking.packets.PacketInfo;
import com.cosmeticsmod.morecosmetics.networking.packets.PacketRSAKey;
import com.cosmeticsmod.morecosmetics.networking.utils.CryptionUtils;
import com.cosmeticsmod.morecosmetics.networking.utils.EnumInfo;
import com.cosmeticsmod.morecosmetics.utils.CompatibilityManager;
import com.cosmeticsmod.morecosmetics.utils.ITickListener;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/*
 * Exception performing whole class analysis ignored.
 */
public class NettyClient
implements ITickListener {
    public static final int KEEP_ALIVE_DURATION = 4;
    public static final int NETTY_CLIENT_VERSION = 3;
    public static final int MAX_TRIES = 5;
    private String serverHost;
    private ClientPacketHandler packetHandler;
    private ExecutorService service;
    private Channel channel;
    private int port;
    private HashMap<Long, Integer> indicated = new HashMap();
    private HashSet<Integer> clientIds = new HashSet();
    private int currentOnlinePlayers;
    private boolean indicatorEnabled;
    private String playerName;
    private UUID playerUUID;
    private int version;
    private int clientID;
    private int connectionTries;
    private int ticksComplete;
    private long lastKeepAlive;
    private long reconnectDuration;
    private boolean connected;
    private boolean kicked;
    private boolean connecting;
    private Consumer<Boolean> callback;
    private boolean verified;

    public NettyClient(String serverHost, int port, String pName, UUID pUUID, int version, int clientID, boolean indicatorEnabled) {
        this.serverHost = serverHost;
        this.port = port;
        this.playerName = pName;
        this.playerUUID = pUUID;
        this.version = version;
        this.clientID = clientID;
        this.indicatorEnabled = indicatorEnabled;
        this.service = Executors.newSingleThreadExecutor();
        this.clientIds.add(clientID);
        this.connect(null);
    }

    public void reconnectNewAccount(String pName, UUID pUUID) {
        if (!this.connected) {
            return;
        }
        this.disconnect();
        this.playerName = pName;
        this.playerUUID = pUUID;
        this.connectionTries = 5;
        this.service.execute(() -> {
            try {
                Thread.sleep(5000L);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.connectionTries = 0;
            this.connect(null);
        });
    }

    public void reconnectAttempt(Consumer<Boolean> callback) {
        if (this.playerName == null || this.playerUUID == null) {
            callback.accept(false);
            return;
        }
        this.connectionTries = 4;
        this.connect(callback);
    }

    public NettyClient() {
        this.connectionTries = 5;
    }

    private void connect(Consumer<Boolean> callback) {
        this.callback = callback;
        this.connecting = true;
        ++this.connectionTries;
        this.lastKeepAlive = System.currentTimeMillis();
        boolean epoll = Epoll.isAvailable();
        io.netty.channel.EventLoopGroup eventGroup = epoll ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        PacketHandler handler = new PacketHandler(this);
        PacketEncoder encoder = new PacketEncoder(this);
        PacketDecoder decoder = new PacketDecoder(this);
        this.service.execute(() -> this.synth_connect_1((EventLoopGroup)eventGroup, epoll, encoder, decoder, handler));
    }

    public void updateTick(int ticks) {
        if (this.connected && TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - this.lastKeepAlive) >= 4L) {
            this.lastKeepAlive = System.currentTimeMillis();
            this.sendAsync((Packet)new PacketInfo(EnumInfo.KEEP_ALIVE));
        }
        if (this.canConnect()) {
            this.connect(null);
        }
        if (this.connected && this.verified && ++this.ticksComplete == 100) {
            this.sendAsync((Packet)new PacketClientInfo(this.clientIds, CompatibilityManager.VERSION, CompatibilityManager.INSTALLATION, CompatibilityManager.PLATFORM));
        }
    }

    public void sendIndication(Long[] transmit) {
        if (this.indicatorEnabled && this.verified) {
            this.sendAsync((Packet)new PacketIndicator(transmit));
        }
    }

    public void sendAsync(Packet packet) {
        if (this.connected) {
            MoreCosmetics.debug((String)("[CONNECTION] OUT: " + packet.getClass().getSimpleName() + (packet.getContent() != null ? " | " + packet.getContent() : "")));
            this.service.execute(() -> this.channel.writeAndFlush((Object)packet, this.channel.voidPromise()));
        }
    }

    public void send(Packet packet) {
        if (this.connected) {
            MoreCosmetics.debug((String)("[CONNECTION] OUT: " + packet.getClass().getSimpleName() + (packet.getContent() != null ? " | " + packet.getContent() : "")));
            this.channel.writeAndFlush((Object)packet, this.channel.voidPromise());
        }
    }

    public void completedLogin() {
        this.verified = true;
        this.ticksComplete = 0;
        this.runCallback(true);
    }

    public void disconnect() {
        if (this.channel != null && this.channel.isActive()) {
            this.channel.disconnect();
            this.channel.close();
            NettyClient.log((String)"Disconnected!");
        }
        this.runCallback(false);
        this.connected = false;
        this.connecting = false;
        this.verified = false;
        this.lastKeepAlive = System.currentTimeMillis();
        this.reconnectDuration = 60 + MoreCosmetics.RANDOM.nextInt(60);
        MoreCosmetics.debug((String)"Disconnected from backend!");
        MoreCosmetics.debug((String)("Reconnect attempt in " + this.reconnectDuration + " seconds."));
    }

    private void runCallback(boolean value) {
        if (this.callback != null) {
            this.callback.accept(value);
            this.callback = null;
        }
    }

    public void updateIndicator(HashMap<Long, Integer> indicated, int currentOnlinePlayers) {
        if (this.indicatorEnabled) {
            this.indicated.putAll(indicated);
            this.currentOnlinePlayers = currentOnlinePlayers;
        }
    }

    public int getCurrentOnlinePlayers() {
        return this.currentOnlinePlayers;
    }

    public HashMap<Long, Integer> getIndicated() {
        return this.indicated;
    }

    public void setKicked() {
        this.kicked = true;
    }

    public int getClientId(long bits) {
        return this.indicated.getOrDefault(bits, -1);
    }

    public boolean isUsing(long bits) {
        return this.indicated.containsKey(bits);
    }

    public boolean isConnected() {
        return this.connected;
    }

    public boolean canConnect() {
        return this.connectionTries < 5 && !this.connected && !this.kicked && !this.connecting && TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - this.lastKeepAlive) >= this.reconnectDuration;
    }

    public boolean isVerified() {
        return this.verified;
    }

    public void setPacketHandler(ClientPacketHandler packetHandler) {
        this.packetHandler = packetHandler;
    }

    public ClientPacketHandler getPacketHandler() {
        return this.packetHandler;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public UUID getPlayerUUID() {
        return this.playerUUID;
    }

    public static void log(String msg) {
        MoreCosmetics.log((String)("[Connection] " + msg));
    }

    public void setConnectionTries(int connectionTries) {
        this.connectionTries = connectionTries;
    }

    public void registerClientId(int id) {
        this.clientIds.add(id);
    }

    private /* synthetic */ void synth_connect_1(EventLoopGroup eventGroup, boolean epoll, PacketEncoder encoder, PacketDecoder decoder, PacketHandler handler) {
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventGroup);
            bootstrap.channel(epoll ? EpollSocketChannel.class : NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast("decoder", decoder);
                    ch.pipeline().addLast("encoder", encoder);
                    ch.pipeline().addLast("handler", handler);
                }
            });
            ChannelFuture future = bootstrap.connect(this.serverHost, this.port).sync();
            if (future.isSuccess()) {
                this.channel = future.channel();
                NettyClient.log((String)"Connected to CosmeticsMod services!");
                this.connectionTries = 0;
                this.connected = true;
                this.send((Packet)new PacketHello(this.playerName, this.playerUUID, this.version, this.clientID, 3));
                KeyPair pair = CryptionUtils.generateRSAKeyPair();
                handler.setKeyPair(pair);
                this.send((Packet)new PacketRSAKey(CryptionUtils.encodePublicKey((PublicKey)pair.getPublic())));
            }
        }
        catch (Exception e) {
            NettyClient.log((String)("Connection to CosmeticsMod services failed: " + e));
            MoreCosmetics.debugThrowable((Throwable)e);
            this.disconnect();
        }
        this.connecting = false;
    }
}

