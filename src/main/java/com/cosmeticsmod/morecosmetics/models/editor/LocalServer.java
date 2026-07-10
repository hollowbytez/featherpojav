/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.models.editor.EditRequest
 *  com.cosmeticsmod.morecosmetics.models.editor.LocalServer
 */
package com.cosmeticsmod.morecosmetics.models.editor;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.models.editor.EditRequest;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class LocalServer
extends Thread {
    public static final int SERVER_PORT = 26735;
    private static boolean running;
    private static LocalServer instance;
    private static boolean enabled;

    private LocalServer() {
        running = true;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(26735, 10, InetAddress.getByName("127.0.0.1"));){
            MoreCosmetics.log((String)"[EDITOR] Local Server started on port 26735");
            while (running) {
                Socket socket = serverSocket.accept();
                if (!enabled) {
                    socket.close();
                    continue;
                }
                MoreCosmetics.debug((String)("[EDITOR] Incoming connection from " + socket.getInetAddress().getHostAddress()));
                if (socket.getInetAddress().getHostAddress().equals("127.0.0.1")) {
                    MoreCosmetics.EXECUTOR.execute((Runnable)new EditRequest(socket));
                    continue;
                }
                MoreCosmetics.debug((String)"[EDITOR] Rejected request from unauthorized connection!");
            }
        }
        catch (Exception e) {
            MoreCosmetics.catchThrowable((Throwable)e);
            running = false;
        }
    }

    public static void toggle(boolean enabled) {
        LocalServer.enabled = enabled;
        if (enabled && !running) {
            if (instance == null) {
                instance = new LocalServer();
            }
            instance.start();
        }
    }
}

