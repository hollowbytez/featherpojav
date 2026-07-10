/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.utils.Utils
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.stream.JsonReader
 *  org.apache.commons.io.FileUtils
 *  org.apache.commons.io.IOUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.core.LoggerContext
 *  org.apache.logging.log4j.core.lookup.Interpolator
 */
package com.cosmeticsmod.morecosmetics.utils;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.lookup.Interpolator;

/*
 * Exception performing whole class analysis ignored.
 */
public class Utils {
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36";
    public static final Gson PRETTY_GSON = new GsonBuilder().setPrettyPrinting().create();
    private static SSLSocketFactory sslSocketFactory;

    public static String readURL(String url, String backupUrl) {
        if (url == null) {
            return null;
        }
        try {
            return IOUtils.toString((InputStream)Utils.getInputStream((String)url, (int)5000, (Object[])new Object[0]), (String)"UTF-8");
        }
        catch (Exception e) {
            MoreCosmetics.log((String)("Exception while reading url: " + e.toString()));
            return Utils.readURL((String)backupUrl, null);
        }
    }

    public static String throwableToString(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    public static void removeLookups() {
        // Log4j lookups reflection removed to prevent antivirus flags and modern JVM warnings
    }

    public static BufferedImage getScaledImage(BufferedImage image, int width, int height) {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        double scaleX = (double)width / (double)imageWidth;
        double scaleY = (double)height / (double)imageHeight;
        AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
        AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, 2);
        return bilinearScaleOp.filter(image, new BufferedImage(width, height, image.getType()));
    }

    public static boolean isUrlString(String str) {
        String u = str.toLowerCase();
        return u.startsWith("http://") || u.startsWith("https://");
    }

    public static boolean endsWith(String str, String ... endings) {
        String u = str.toLowerCase();
        for (String en : endings) {
            if (!u.endsWith(en)) continue;
            return true;
        }
        return false;
    }

    public static boolean isOfflinePlayer() {
        MoreCosmetics mod = MoreCosmetics.getInstance();
        String url = "https://api.mojang.com/users/profiles/minecraft/" + mod.getVersionAdapter().getPlayerName();
        JsonElement e = Utils.readJsonFromUrl((String)url, (boolean)false, (Object[])new Object[0]);
        if (e == null) {
            return true;
        }
        String stripUuid = mod.getVersionAdapter().getUuid(false).toString().replace("-", "");
        return !e.getAsJsonObject().get("id").getAsString().equals(stripUuid);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static JsonElement readJsonFromUrl(String url, boolean gzip, Object ... obj) {
        try {
            InputStream stream = Utils.getInputStream((String)url, (int)5000, (Object[])obj);
            if (gzip) {
                stream = new GZIPInputStream(stream);
            }
            try (JsonReader reader = new JsonReader((Reader)new InputStreamReader(stream, "UTF-8"));){
                JsonElement jsonElement = MoreCosmetics.PARSER.parse(reader);
                return jsonElement;
            }
        }
        catch (IOException e) {
            MoreCosmetics.log((String)e.toString());
            return null;
        }
    }

    public static InputStream getInputStream(String url, int timeout, Object ... obj) throws IOException {
        return Utils.getInputStream((String)url, (int)timeout, (String[])null, (Object[])obj);
    }

    public static InputStream getInputStream(String url, int timeout, String[] data, Object ... obj) throws IOException {
        url = obj == null ? url : String.format(url, obj);
        HttpURLConnection con = (HttpURLConnection)new URL(url).openConnection();
        if (url.toLowerCase().startsWith("https") && con instanceof HttpsURLConnection) {
            ((HttpsURLConnection)con).setSSLSocketFactory(Utils.getSSLSocketFactory());
        }
        con.setRequestProperty("User-Agent", "MoreCosmetics-1.2");
        con.setConnectTimeout(timeout);
        con.setReadTimeout(timeout);
        con.connect();
        int code = con.getResponseCode();
        if (code / 100 == 2) {
            if (data != null) {
                data[0] = con.getContentType();
            }
            return con.getInputStream();
        }
        InputStream errorStream = con.getErrorStream();
        String error = errorStream != null ? " " + IOUtils.toString((InputStream)errorStream) : "";
        throw new IOException("Response code: " + code + error);
    }

    public static void gzipToFile(String url, File file, int timeout) throws IOException {
        FileUtils.copyInputStreamToFile((InputStream)new GZIPInputStream(Utils.getInputStream((String)url, (int)timeout, (Object[])new Object[0])), (File)file);
    }

    public static boolean downloadFile(String url, File file) {
        try {
            FileUtils.copyInputStreamToFile((InputStream)Utils.getInputStream((String)url, (int)5000, (Object[])new Object[0]), (File)file);
            return true;
        }
        catch (IOException e) {
            MoreCosmetics.catchThrowable((Throwable)e);
            return false;
        }
    }

    public static void downloadFile(String url, File file, int timeout) throws IOException {
        FileUtils.copyInputStreamToFile((InputStream)Utils.getInputStream((String)url, (int)timeout, (Object[])new Object[0]), (File)file);
    }

    public static String trimUrl(String url) {
        return url.contains("?") ? url.substring(0, url.indexOf("?")) : url;
    }

    public static String replaceColorCodes(String msg) {
        if (msg == null || !msg.contains("&")) {
            return msg;
        }
        char[] c = msg.toCharArray();
        for (int i = 0; i < c.length - 1; ++i) {
            if (c[i] != '&' || "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(c[i + 1]) == -1) continue;
            c[i] = 167;
        }
        return new String(c);
    }

    public static String replaceColorCodesReverse(String msg) {
        if (msg == null || !msg.contains("\u00a7")) {
            return msg;
        }
        char[] c = msg.toCharArray();
        for (int i = 0; i < c.length - 1; ++i) {
            if (c[i] != '\u00a7' || "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(c[i + 1]) == -1) continue;
            c[i] = 38;
        }
        return new String(c);
    }

    public static int toRGB(int r, int g, int b, int a) {
        return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF) << 0;
    }

    public static int toRGB(float r, float g, float b, float a) {
        return ((int)((double)(a * 255.0f) + 0.5) & 0xFF) << 24 | ((int)((double)(r * 255.0f) + 0.5) & 0xFF) << 16 | ((int)((double)(g * 255.0f) + 0.5) & 0xFF) << 8 | ((int)((double)(b * 255.0f) + 0.5) & 0xFF) << 0;
    }

    public static SSLSocketFactory getSSLSocketFactory() {
        if (sslSocketFactory != null) {
            return sslSocketFactory;
        }
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() { return null; }
                public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                public void checkServerTrusted(X509Certificate[] certs, String authType) { }
            }}, new SecureRandom());
            sslSocketFactory = sc.getSocketFactory();
            return sslSocketFactory;
        }
        catch (KeyManagementException | NoSuchAlgorithmException e) {
            MoreCosmetics.catchThrowable((Throwable)e);
            return null;
        }
    }

    public static boolean isClassPresent(String name) {
        try {
            Class.forName(name);
            return true;
        }
        catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static Field findField(Class<?> clazz, String ... fieldNames) throws NoSuchFieldException {
        Throwable failed = null;
        for (String fieldName : fieldNames) {
            try {
                Field f = clazz.getDeclaredField(fieldName);
                f.setAccessible(true);
                return f;
            }
            catch (Exception e) {
                failed = e;
            }
        }
        throw new NoSuchFieldException(failed.getMessage());
    }

    public static Method findMethod(Class<?> clazz, String ... methodNames) throws NoSuchFieldException {
        Throwable failed = null;
        for (String methodName : methodNames) {
            try {
                for (Method m : clazz.getDeclaredMethods()) {
                    if (!m.getName().equals(methodName)) continue;
                    m.setAccessible(true);
                    return m;
                }
            }
            catch (Exception e) {
                failed = e;
            }
        }
        throw new NoSuchFieldException(failed.getMessage());
    }
}

