/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.Main
 */
package com.cosmeticsmod.morecosmetics;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/*
 * Exception performing whole class analysis ignored.
 */
public class Main {
    private static HashMap<String, String> lang = new HashMap();

    public static void main(String[] args) {
        block8: {
            Main.initLookAndFeel();
            Main.initLanguage();
            try {
                File dir = new File(Main.initDirectory());
                if (!dir.exists()) {
                    throw new IOException("No .minecraft/mods directory found!");
                }
                if (!Main.showConfirmDialog((String)String.format((String)lang.get("installation"), "1.2"))) break block8;
                File run = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                if (run.exists() && run.isFile()) {
                    File mods18 = new File(dir, "1.8.9");
                    if (mods18.exists()) {
                        Main.removeOldInstallations((File)mods18);
                    }
                    Main.removeOldInstallations((File)dir);
                    String name = run.getName().toLowerCase().contains("morecosmetics") ? run.getName() : "MoreCosmetics.jar";
                    File mod = new File(dir, name);
                    Files.copy(run.toPath(), mod.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    Main.showMessageDialog((String)((String)lang.get("success")), (int)1);
                    break block8;
                }
                throw new IOException("Invalid path: " + run.getAbsolutePath());
            }
            catch (FileSystemException e) {
                e.printStackTrace();
                if (e.getReason() != null && !e.getReason().isEmpty()) {
                    Main.showMessageDialog((String)((String)lang.get("closed") + "\n" + e.getReason()), (int)0);
                } else {
                    Main.showMessageDialog((String)((String)lang.get("error")), (int)0);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                Main.showMessageDialog((String)((String)lang.get("error")), (int)0);
            }
        }
    }

    private static void removeOldInstallations(File folder) {
        for (File mod : folder.listFiles()) {
            if (!mod.getName().toLowerCase().contains("morecosmetics")) continue;
            mod.delete();
        }
    }

    private static void initLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String initDirectory() {
        String dir = System.getenv("APPDATA") + "/.minecraft/mods/";
        if (!new File(dir).exists()) {
            dir = System.getProperty("user.home") + "/Library/Application Support/minecraft/mods";
        }
        if (!new File(dir).exists()) {
            dir = System.getProperty("user.home") + "/.minecraft/mods/";
        }
        return dir;
    }

    private static void initLanguage() {
        if (Locale.getDefault().toString().toLowerCase().contains("de")) {
            lang.put("installation", "MoreCosmetics v%s kann nun installiert werden!");
            lang.put("success", "MoreCosmetics Installation abgeschlossen!");
            lang.put("closed", "Ist Minecraft geschlossen?");
            lang.put("error", "Installation fehlgeschlagen!\nKopiere die Mod in das Verzeichnis .minecraft/mods und starte Minecraft!");
        } else if (Locale.getDefault().toString().toLowerCase().contains("es")) {
            lang.put("installation", "\u00a1Se puede instalar el MoreCosmetics v%s ahora!");
            lang.put("success", "La instalaci\u00f3n del MoreCosmetics a terminado");
            lang.put("closed", "\u00bfMinecraft est\u00e1 cerrado?");
            lang.put("error", "\u00a1La instalaci\u00f3n fall\u00f3!\n\u00a1Copi\u00e1 la Mod en la carpeta .minecraft/mods y empez\u00e1 a jugar!");
        } else if (Locale.getDefault().toString().toLowerCase().contains("pt")) {
            lang.put("installation", "O MoreCosmetics v%s est\u00e1 pronto para instala\u00e7\u00e3o!\nFeche o Minecraft antes de continuar!");
            lang.put("success", "Instala\u00e7\u00e3o do MoreCosmetics conclu\u00edda!");
            lang.put("closed", "O Minecraft est\u00e1 fechado?");
            lang.put("error", "Instala\u00e7\u00e3o falhou!\nCopie os arquivos para a pasta .minecraft/mods e abra o Minecraft!");
        } else {
            lang.put("installation", "MoreCosmetics v%s is now ready for installation!");
            lang.put("success", "MoreCosmetics installation finished!");
            lang.put("closed", "Minecraft closed?");
            lang.put("error", "Installation failed!\nCopy the file into .minecraft/mods and start Minecraft!");
        }
    }

    private static boolean showConfirmDialog(String msg) {
        return JOptionPane.showConfirmDialog(null, msg, "MoreCosmetics powered by CosmeticsMod", 2, 1, null) == 0;
    }

    private static void showMessageDialog(String msg, int mode) {
        JOptionPane.showMessageDialog(null, msg, "MoreCosmetics powered by CosmeticsMod", mode);
    }
}

