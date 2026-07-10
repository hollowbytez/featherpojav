/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.utils.debug.ConsoleCommandHandler
 *  com.cosmeticsmod.morecosmetics.utils.debug.DebugConsole
 *  com.cosmeticsmod.morecosmetics.utils.debug.EnumDebugState
 */
package com.cosmeticsmod.morecosmetics.utils.debug;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.utils.debug.ConsoleCommandHandler;
import com.cosmeticsmod.morecosmetics.utils.debug.EnumDebugState;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/*
 * Exception performing whole class analysis ignored.
 */
public class DebugConsole
extends JFrame {
    private static DebugConsole instance = null;
    private static boolean headless;
    private ConsoleCommandHandler handler = new ConsoleCommandHandler();
    private JPanel contentPane;
    private JTextPane outputArea;
    private JTextField commandLine;

    private DebugConsole() {
    }

    public static void open() {
        if (headless) {
            MoreCosmetics.log((String)"Failed to open console! Running headless.");
            return;
        }
        if (instance != null) {
            if (!instance.isVisible()) {
                EventQueue.invokeLater(() -> {
                    instance.setVisible(true);
                    instance.toFront();
                });
            }
            return;
        }
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                instance = new DebugConsole();
                instance.init();
                instance.setTitle("Debug Console");
                URL logo = Thread.currentThread().getContextClassLoader().getResource("assets/minecraft/morecosmetics/gui/logo/icon.png");
                instance.setIconImage(new ImageIcon(logo).getImage());
                instance.setResizable(false);
                instance.setVisible(true);
                instance.toFront();
            }
            catch (HeadlessException e) {
                headless = true;
                MoreCosmetics.log((String)"Failed to open console! Running headless.");
            }
            catch (Exception e) {
                MoreCosmetics.catchThrowable((Throwable)e);
            }
        });
    }

    private void init() {
        this.setBounds(100, 100, 1100, 600);
        this.contentPane = new JPanel();
        this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.contentPane.setLayout(null);
        this.contentPane.setBackground(new Color(25, 26, 31));
        this.outputArea = new JTextPane();
        JScrollPane outputScrollPane = new JScrollPane(this.outputArea);
        outputScrollPane.setBounds(10, 10, 1064, 509);
        outputScrollPane.setBackground(new Color(19, 18, 23));
        outputScrollPane.setBorder(null);
        this.outputArea.setBorder(null);
        this.outputArea.setEditable(false);
        this.outputArea.setBackground(new Color(19, 18, 23));
        this.outputArea.setForeground(Color.WHITE);
        this.contentPane.add(outputScrollPane);
        this.commandLine = new JTextField();
        this.commandLine.setBorder(null);
        this.commandLine.setForeground(Color.WHITE);
        this.commandLine.setBackground(new Color(19, 18, 23));
        this.commandLine.setBounds(10, 530, 1064, 20);
        this.contentPane.add(this.commandLine);
        this.commandLine.addActionListener(e -> {
            if (e.getID() == 1001) {
                DebugConsole.print((String)e.getActionCommand(), (EnumDebugState)EnumDebugState.COMMAND);
                this.handler.handle(e.getActionCommand());
                this.commandLine.setText("");
            }
        });
        this.setContentPane((Container)this.contentPane);
    }

    public static void print(String text, EnumDebugState state) {
        if (headless && state == EnumDebugState.DEBUG) {
            MoreCosmetics.log((String)(state.getPrefix() + " " + text));
            return;
        }
        if (instance == null) {
            return;
        }
        SwingUtilities.invokeLater(() -> {
            StyleContext sc = StyleContext.getDefaultStyleContext();
            AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, state.getTextColor());
            aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Consolas");
            aset = sc.addAttribute(aset, StyleConstants.Alignment, 3);
            int len = DebugConsole.instance.outputArea.getDocument().getLength();
            DebugConsole.instance.outputArea.setEditable(true);
            DebugConsole.instance.outputArea.setCaretPosition(len);
            DebugConsole.instance.outputArea.setCharacterAttributes(aset, false);
            DebugConsole.instance.outputArea.replaceSelection(state.getPrefix() + " " + text + "\n");
            DebugConsole.instance.outputArea.setEditable(false);
        });
    }
}

