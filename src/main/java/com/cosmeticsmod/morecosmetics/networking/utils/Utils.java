/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.networking.utils.Utils
 */
package com.cosmeticsmod.morecosmetics.networking.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Utils {
    public static byte[] compress(byte[] input) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);
            GZIPOutputStream gzip = new GZIPOutputStream(bos);
            gzip.write(input);
            gzip.close();
            bos.close();
            return bos.toByteArray();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] decompress(byte[] input) {
        try {
            int len;
            byte[] buffer = new byte[1024];
            ByteArrayInputStream bis = new ByteArrayInputStream(input);
            GZIPInputStream gis = new GZIPInputStream(bis);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            while ((len = gis.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            gis.close();
            out.close();
            return out.toByteArray();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

