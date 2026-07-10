/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.gui.core.notification.AnimationState
 *  com.cosmeticsmod.morecosmetics.gui.core.notification.Notification
 */
package com.cosmeticsmod.morecosmetics.gui.core.notification;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.gui.core.notification.AnimationState;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

public class Notification {
    private String logoLocation;
    private String title;
    private String[] lines;
    private AnimationState animationState = AnimationState.NONE;
    private int progressColor;
    private int remainingColor;
    private double animationIndex;
    private long initTime;
    private long animTime;
    private int ticks;

    public Notification(String title, String logoLocation, int progressColor, String message) {
        this.title = title;
        this.logoLocation = logoLocation;
        this.progressColor = progressColor;
        this.remainingColor = new Color(progressColor).darker().darker().getRGB();
        this.splitLines(message);
    }

    private void splitLines(String message) {
        String[] msgs = message.split(" ");
        ArrayList<String> lineCache = new ArrayList<String>();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < msgs.length; ++i) {
            String msg = msgs[i];
            String current = builder.toString();
            String next = builder.append(msg).toString();
            if (MoreCosmetics.getInstance().getNotificationHandler().getStringWidth(next) > 150) {
                lineCache.add(current);
                builder = new StringBuilder(msg);
            }
            if (i >= msgs.length - 1) continue;
            builder.append(" ");
        }
        lineCache.add(builder.toString());
        this.lines = lineCache.toArray(new String[lineCache.size()]);
    }

    public String getLogo() {
        return this.logoLocation;
    }

    public String[] getLines() {
        return this.lines;
    }

    public String getTitle() {
        return this.title;
    }

    public long getInitTime() {
        return this.initTime;
    }

    public void setInitTime() {
        this.initTime = System.currentTimeMillis();
    }

    public int getProgressColor() {
        return this.progressColor;
    }

    public int getRemainingColor() {
        return this.remainingColor;
    }

    public double getAnimationIndex() {
        return this.animationIndex;
    }

    public void addAnimationsIndex() {
        if (this.animTime == 0L) {
            this.animTime = System.currentTimeMillis();
        }
        this.animationIndex = (double)(System.currentTimeMillis() - this.animTime) / 300.0;
    }

    public AnimationState getAnimationState() {
        return this.animationState;
    }

    public void setAnimationState(AnimationState animationState) {
        this.animationState = animationState;
        if (animationState == AnimationState.OUT) {
            this.animTime = System.currentTimeMillis() - 600L;
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Notification)) {
            return false;
        }
        Notification notification = (Notification)obj;
        return notification.title.equals(this.title) && Arrays.equals(notification.lines, this.lines);
    }
}

