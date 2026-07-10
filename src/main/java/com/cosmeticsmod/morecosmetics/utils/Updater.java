/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.utils.Updater
 *  com.cosmeticsmod.morecosmetics.utils.Utils
 *  org.apache.commons.io.FileUtils
 */
package com.cosmeticsmod.morecosmetics.utils;

import com.cosmeticsmod.morecosmetics.utils.Utils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import org.apache.commons.io.FileUtils;

public class Updater
extends Thread {
    private File jar;
    private String downloadUrl;

    public Updater(String downloadUrl) {
        this.downloadUrl = downloadUrl;
        try {
            String file = Updater.class.getProtectionDomain().getCodeSource().getLocation().getFile();
            if (file.contains("!")) {
                file = file.substring(6, file.indexOf("!"));
            }
            this.jar = new File(URLDecoder.decode(file, "UTF-8"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            if (this.jar != null && this.jar.exists() && this.downloadUrl != null) {
                FileUtils.copyInputStreamToFile((InputStream)Utils.getInputStream((String)this.downloadUrl, (int)5000, (Object[])new Object[0]), (File)this.jar);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

