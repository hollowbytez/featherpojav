/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.pachtes.CustomClassLoader
 *  com.cosmeticsmod.morecosmetics.pachtes.MoreCosmeticsPatch
 *  com.cosmeticsmod.morecosmetics.pachtes.PatchInfo
 *  com.cosmeticsmod.morecosmetics.pachtes.PatchLoader
 *  com.cosmeticsmod.morecosmetics.pachtes.ReflectionClassLoader
 *  com.cosmeticsmod.morecosmetics.utils.CompatibilityManager
 *  com.cosmeticsmod.morecosmetics.utils.ITickListener
 *  com.cosmeticsmod.morecosmetics.utils.Utils
 *  com.google.gson.JsonElement
 *  org.apache.commons.io.IOUtils
 */
package com.cosmeticsmod.morecosmetics.pachtes;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.pachtes.CustomClassLoader;
import com.cosmeticsmod.morecosmetics.pachtes.MoreCosmeticsPatch;
import com.cosmeticsmod.morecosmetics.pachtes.PatchInfo;
import com.cosmeticsmod.morecosmetics.pachtes.ReflectionClassLoader;
import com.cosmeticsmod.morecosmetics.utils.CompatibilityManager;
import com.cosmeticsmod.morecosmetics.utils.ITickListener;
import com.cosmeticsmod.morecosmetics.utils.Utils;
import com.google.gson.JsonElement;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.jar.JarFile;
import org.apache.commons.io.IOUtils;

public class PatchLoader
implements ITickListener {
    public static final String URL = "https://dl.cosmeticsmod.com/morecosmetics/patches.json";
    public static final String URL_PATCH_FILE = "https://dl.cosmeticsmod.com/morecosmetics/patches/";
    private static CustomClassLoader classLoader;
    private final Deque<PatchInfo> patchQueue = new LinkedList();
    private final Set<MoreCosmeticsPatch> patches = new HashSet();

    public PatchLoader() {
        if (classLoader == null) {
            classLoader = new ReflectionClassLoader();
        }
    }

    public void load() {
        try {
            File patchesDir = new File(MoreCosmetics.ROOT_DIR, "patches");
            patchesDir.mkdirs();
            JsonElement element = Utils.readJsonFromUrl((String)URL, (boolean)false, (Object[])new Object[0]);
            if (element != null && element.getAsJsonArray().size() > 0) {
                for (JsonElement obj : element.getAsJsonArray()) {
                    String name;
                    File patchFile;
                    PatchInfo patchInfo = (PatchInfo)MoreCosmetics.GSON.fromJson(obj, PatchInfo.class);
                    if (!this.matchRequirements(patchInfo) || (!(patchFile = new File(patchesDir, name = patchInfo.uuid + ".jar")).exists() || patchInfo.length > 0 && (long)patchInfo.length != patchFile.length()) && !Utils.downloadFile((String)(URL_PATCH_FILE + name), (File)patchFile)) continue;
                    if (patchInfo.length > 0 && (long)patchInfo.length != patchFile.length()) {
                        MoreCosmetics.debug((String)("Patch file length not matching: " + patchFile.getName()));
                        continue;
                    }
                    patchInfo.file = patchFile;
                    this.patchQueue.add(patchInfo);
                }
            } else {
                for (File file : patchesDir.listFiles()) {
                    if (!file.getName().toLowerCase().endsWith(".jar")) continue;
                    this.loadJarFile(file);
                }
            }
        }
        catch (Exception e) {
            MoreCosmetics.log((String)"Failed to load patches!");
            MoreCosmetics.catchThrowable((Throwable)e);
        }
    }

    public boolean matchRequirements(PatchInfo info) {
        if (info.uuid == null || this.isPatchLoaded(info.uuid)) {
            return false;
        }
        if (info.reqVersion != null && !info.reqVersion.equals(CompatibilityManager.VERSION)) {
            return false;
        }
        if (info.reqPlatform != null && info.reqPlatform.equals(CompatibilityManager.PLATFORM)) {
            return false;
        }
        if (info.reqInstall != null && info.reqInstall.equals(CompatibilityManager.INSTALLATION)) {
            return false;
        }
        return info.reqClass == null || Utils.isClassPresent((String)info.reqClass);
    }

    public void loadJarFile(File file) {
        MoreCosmetics.debug((String)("Loading local patch file: " + file.getName()));
        try (JarFile jarFile = new JarFile(file);){
            if (jarFile.getJarEntry("patch.json") == null) {
                MoreCosmetics.debug((String)("Missing patch.json in " + file.getName()));
                return;
            }
            String json = IOUtils.toString((InputStream)jarFile.getInputStream(jarFile.getJarEntry("patch.json")), (Charset)StandardCharsets.UTF_8);
            PatchInfo patchInfo = (PatchInfo)MoreCosmetics.GSON.fromJson(json, PatchInfo.class);
            if (this.matchRequirements(patchInfo)) {
                patchInfo.file = file;
                this.patchQueue.add(patchInfo);
            }
        }
        catch (Exception e) {
            MoreCosmetics.debug((String)("Failed to load local patch file: " + file.getName()));
        }
    }

    private void loadPatch(PatchInfo info) {
        if (info.file == null || !info.file.exists()) {
            return;
        }
        try {
            classLoader.addJar(info.file);
            if (info.mainClass == null) {
                MoreCosmetics.debug((String)("Loaded patch " + info.uuid + " without mainClass!"));
                return;
            }
            Class<?> cl = Class.forName(info.mainClass);
            if (MoreCosmeticsPatch.class.isAssignableFrom(cl)) {
                MoreCosmeticsPatch addon = (MoreCosmeticsPatch)cl.newInstance();
                this.patches.add(addon);
                addon.patchInfo = info;
                addon.onInit();
                MoreCosmetics.debug((String)("Loaded patch " + info.uuid + " successfully!"));
            } else {
                MoreCosmetics.debug((String)("Wrong mainClass in " + info.file.getName()));
            }
        }
        catch (Throwable e) {
            MoreCosmetics.log((String)("Patch " + info.uuid + " throwed " + e));
            MoreCosmetics.debugThrowable((Throwable)e);
        }
    }

    public boolean isPatchLoaded(String uuid) {
        for (MoreCosmeticsPatch patch : this.patches) {
            if (!uuid.equals(patch.patchInfo.uuid)) continue;
            return true;
        }
        return false;
    }

    public void updateTick(int tick) {
        while (!this.patchQueue.isEmpty()) {
            this.loadPatch((PatchInfo)this.patchQueue.pop());
        }
    }

    public static void setClassLoader(CustomClassLoader classLoader) {
        PatchLoader.classLoader = classLoader;
    }

    public static CustomClassLoader getClassLoader() {
        return classLoader;
    }

    public Set<MoreCosmeticsPatch> getPatches() {
        return this.patches;
    }
}

