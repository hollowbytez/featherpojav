/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.utils.FileChooser
 */
package com.cosmeticsmod.morecosmetics.utils;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Window;
import java.io.File;
import java.util.function.Consumer;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/*
 * Exception performing whole class analysis ignored.
 */
public class FileChooser {
    private static boolean init;
    private static boolean godmode;
    private static Window window;

    public static boolean isOpened() {
        if (window != null) {
            window.toFront();
            window.requestFocus();
            return true;
        }
        return false;
    }

    public static void openFileDialog(String title, File path, Consumer<File> callback, String filterDescription, String ... extensions) {
        if (!FileChooser.isOpened()) {
            MoreCosmetics.EXECUTOR.execute(() -> {
                File file;
                FileChooser.checkLookAndFeel();
                File file2 = file = godmode ? FileChooser.openAWTFileDialog((String)title, (File)path) : FileChooser.openSwingFileDialog((String)title, (File)path, (String)filterDescription, (String[])extensions);
                if (file != null && file.exists()) {
                    callback.accept(file);
                }
            });
        }
    }

    private static File openSwingFileDialog(String title, File path, String filterDesc, String ... extensions) {
        window = new JFrame(title);
        window.setAlwaysOnTop(true);
        window.toFront();
        window.requestFocus();
        JFileChooser chooser = new JFileChooser(path);
        chooser.setFileFilter(new FileNameExtensionFilter(filterDesc, extensions));
        int callback = chooser.showOpenDialog(window);
        window = null;
        return callback == 0 ? chooser.getSelectedFile() : null;
    }

    private static File openAWTFileDialog(String title, File path) {
        FileDialog chooser = new FileDialog((Frame)null, title);
        window = chooser;
        chooser.setMode(0);
        chooser.setDirectory(path.getAbsolutePath());
        chooser.setAlwaysOnTop(true);
        chooser.toFront();
        chooser.requestFocus();
        chooser.setVisible(true);
        String filename = chooser.getFile();
        String dirname = chooser.getDirectory();
        chooser.dispose();
        window = null;
        return dirname != null && filename != null ? new File(dirname, filename) : null;
    }

    private static void checkLookAndFeel() {
        if (!init) {
            init = true;
            try {
                godmode = FileChooser.checkGodmodeFolder();
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            catch (Exception e) {
                MoreCosmetics.catchThrowable((Throwable)e);
            }
        }
    }

    private static boolean checkGodmodeFolder() {
        File home = FileSystemView.getFileSystemView().getHomeDirectory();
        for (String file : home.list()) {
            if (!file.contains("{ED7BA470-8E54-465E-825C-99712043E01C}")) continue;
            return true;
        }
        return false;
    }
}

