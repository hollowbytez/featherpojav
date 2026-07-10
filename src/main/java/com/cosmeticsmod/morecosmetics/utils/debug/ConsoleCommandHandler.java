/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.gui.ModelGui
 *  com.cosmeticsmod.morecosmetics.networking.NettyClient
 *  com.cosmeticsmod.morecosmetics.utils.ModConfig
 *  com.cosmeticsmod.morecosmetics.utils.debug.ConsoleCommandHandler
 *  com.cosmeticsmod.morecosmetics.utils.debug.DebugConsole
 *  com.cosmeticsmod.morecosmetics.utils.debug.EnumDebugState
 */
package com.cosmeticsmod.morecosmetics.utils.debug;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.gui.ModelGui;
import com.cosmeticsmod.morecosmetics.networking.NettyClient;
import com.cosmeticsmod.morecosmetics.utils.ModConfig;
import com.cosmeticsmod.morecosmetics.utils.debug.DebugConsole;
import com.cosmeticsmod.morecosmetics.utils.debug.EnumDebugState;
import java.lang.reflect.Field;

public class ConsoleCommandHandler {
    public void handle(String s) {
        String[] args = s.split(" ");
        if (s.toLowerCase().startsWith("help")) {
            this.sendHelp();
        } else if (s.toLowerCase().startsWith("setting") && args.length >= 3) {
            String varName = args[1];
            String value = args[2];
            try {
                Field setting = ModConfig.class.getDeclaredField(varName);
                System.out.println(setting.getType().getSimpleName());
                if (setting.getType().getSimpleName().equals("boolean")) {
                    setting.set(ModConfig.getConfig(), Boolean.parseBoolean(value));
                    this.resp("boolean " + varName + " set to: " + value);
                } else if (setting.getType().getSimpleName().equals("int")) {
                    setting.set(ModConfig.getConfig(), Integer.parseInt(value));
                    this.resp("integer " + varName + " set to: " + value);
                } else {
                    setting.set(ModConfig.getConfig(), value);
                    this.resp(setting.getType() + " " + varName + " set to string: " + value);
                }
                ModConfig.saveConfig();
            }
            catch (Exception e) {
                this.resp("failed to set " + varName + ": " + e.toString());
            }
        } else if (s.toLowerCase().startsWith("disconnect")) {
            if (MoreCosmetics.getInstance().getConnection().isConnected()) {
                this.resp("disconnecting...");
                MoreCosmetics.getInstance().getConnection().disconnect();
            } else {
                this.resp("client isn't connected to backend");
            }
        } else if (s.toLowerCase().startsWith("connect")) {
            NettyClient con = MoreCosmetics.getInstance().getConnection();
            if (args.length == 2) {
                try {
                    int port = Integer.parseInt(args[1]);
                    Field portF = NettyClient.class.getDeclaredField("port");
                    portF.setAccessible(true);
                    portF.set(con, port);
                }
                catch (Exception e) {
                    this.resp("failed to set port: " + e.toString());
                }
            }
            if (con.isConnected()) {
                con.disconnect();
            }
            con.reconnectNewAccount(con.getPlayerName(), con.getPlayerUUID());
        } else if (s.toLowerCase().startsWith("set") && args.length >= 2 && args[1].equalsIgnoreCase("tries")) {
            MoreCosmetics.getInstance().getConnection().setConnectionTries(Integer.parseInt(args[2]));
            this.resp("set connection tries to " + args[2]);
        }
        ModelGui.refreshGui();
    }

    private void resp(String s) {
        DebugConsole.print((String)s, (EnumDebugState)EnumDebugState.RESPONSE);
    }

    private void sendHelp() {
        StringBuilder builder = new StringBuilder();
        builder.append("Commands: \n");
        builder.append("help: list all available comments \n");
        builder.append("setting <varName> <value>: set setting of modconfig manually \n");
        builder.append("disconnect: disconnects from backend server\n");
        builder.append("connect [port]: force connects to port\n");
        this.resp(builder.toString());
    }
}

