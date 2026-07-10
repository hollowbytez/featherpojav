/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.gui.core.notification.NotificationHandler
 *  com.cosmeticsmod.morecosmetics.utils.Authenticator
 *  com.cosmeticsmod.morecosmetics.utils.LanguageHandler
 *  org.apache.commons.io.IOUtils
 */
package com.cosmeticsmod.morecosmetics.utils;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.gui.core.notification.NotificationHandler;
import com.cosmeticsmod.morecosmetics.utils.LanguageHandler;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.io.IOUtils;

public class Authenticator {
    public static final String SERVER_HASH = "ec45123a6e015638b15d2f574e4b6c1bd3f65642";
    private static Authenticator instance;
    private long lastSuccessful;
    private String token;
    private MoreCosmetics mod = MoreCosmetics.getInstance();

    private Authenticator() {
    }

    public void openPanel() {
        String name = this.mod.getVersionAdapter().getPlayerName();
        String uuid = this.mod.getVersionAdapter().getUuid(false).toString();
        if (this.token == null || System.currentTimeMillis() - this.lastSuccessful >= 300000L) {
            if (!MoreCosmetics.getInstance().getVersionAdapter().authenticate(SERVER_HASH)) {
                NotificationHandler.sendError((String)LanguageHandler.get((String)"verifyerror"));
                return;
            }
            try {
                HttpURLConnection con = (HttpURLConnection)new URL("https://cosmeticsmod.com/user/auth.php?name=" + name + "&uuid=" + uuid).openConnection();
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                con.setConnectTimeout(3000);
                con.setReadTimeout(3000);
                con.connect();
                int code = con.getResponseCode();
                if (code != 200) {
                    NotificationHandler.sendError((String)LanguageHandler.get((String)(code == 403 ? "verifyblocked" : "verifyerror")));
                    return;
                }
                this.token = IOUtils.toString((InputStream)con.getInputStream());
            }
            catch (IOException e) {
                MoreCosmetics.catchThrowable((Throwable)e);
                NotificationHandler.sendError((String)LanguageHandler.get((String)"verifyerror"));
                return;
            }
            this.lastSuccessful = System.currentTimeMillis();
        }
        this.showWebLogin("https://cosmeticsmod.com/user/login.php?name=" + name + "&token=" + this.token);
    }

    private void showWebLogin(String url) {
        this.mod.getVersionAdapter().showConfirmDialog("Nametag Panel", LanguageHandler.get((String)"verifypanel"), () -> this.mod.getVersionAdapter().openBrowser(url));
    }

    public static Authenticator getAuthenticator() {
        return instance == null ? (instance = new Authenticator()) : instance;
    }
}

