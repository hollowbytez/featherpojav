/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.pachtes.CustomClassLoader
 *  com.cosmeticsmod.morecosmetics.pachtes.ReflectionClassLoader
 */
package com.cosmeticsmod.morecosmetics.pachtes;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.pachtes.CustomClassLoader;
import java.io.File;
import java.lang.reflect.Method;

public class ReflectionClassLoader
implements CustomClassLoader {
    private final ClassLoader classLoader;
    private Method addMethod;

    public ReflectionClassLoader() {
        this.classLoader = this.getClass().getClassLoader();
        this.initMethod();
    }

    public ReflectionClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
        this.initMethod();
    }

    private void initMethod() {
        try {
            for (Method method : this.classLoader.getClass().getDeclaredMethods()) {
                if (!method.getName().toLowerCase().contains("addurl") || method.getParameterCount() != 1) continue;
                this.addMethod = method;
                this.addMethod.setAccessible(true);
                break;
            }
            if (this.addMethod == null) {
                MoreCosmetics.log((String)"[Patcher] No add method found!");
            }
        }
        catch (Exception e) {
            MoreCosmetics.catchThrowable((Throwable)e);
        }
    }

    public void addJar(File file) {
        if (this.addMethod == null) {
            return;
        }
        try {
            this.addMethod.invoke((Object)this.classLoader, file.toURI().toURL());
        }
        catch (Exception e) {
            MoreCosmetics.catchThrowable((Throwable)e);
        }
    }

    public ClassLoader getClassLoader() {
        return this.classLoader;
    }
}

