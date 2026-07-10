/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.models.config.DataAdapter
 *  com.cosmeticsmod.morecosmetics.models.config.DefaultValue
 *  com.cosmeticsmod.morecosmetics.models.config.ModelData
 *  com.cosmeticsmod.morecosmetics.models.config.NoSerialization
 *  com.cosmeticsmod.morecosmetics.models.config.SettingOverlay
 *  com.google.gson.TypeAdapter
 *  com.google.gson.stream.JsonReader
 *  com.google.gson.stream.JsonWriter
 */
package com.cosmeticsmod.morecosmetics.models.config;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.models.config.DefaultValue;
import com.cosmeticsmod.morecosmetics.models.config.ModelData;
import com.cosmeticsmod.morecosmetics.models.config.NoSerialization;
import com.cosmeticsmod.morecosmetics.models.config.SettingOverlay;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Field;

public class DataAdapter
extends TypeAdapter<ModelData> {
    public void write(JsonWriter out, ModelData value) throws IOException {
        try {
            out.beginObject();
            out.name("v");
            out.value((long)value.version);
            for (Field f : ModelData.class.getDeclaredFields()) {
                if (f.isAnnotationPresent(NoSerialization.class) || f.get(value) == null) continue;
                out.name(f.getName());
                out.beginArray();
                try {
                    if (f.get(value) instanceof SettingOverlay[]) {
                        for (SettingOverlay ov : (SettingOverlay[])f.get(value)) {
                            out.beginObject();
                            this.filterSettings(out, ov);
                            out.endObject();
                        }
                    }
                }
                catch (Exception e) {
                    MoreCosmetics.catchThrowable((Throwable)e);
                }
                out.endArray();
            }
            this.filterSettings(out, (SettingOverlay)value);
            out.endObject();
        }
        catch (IllegalAccessException | IllegalArgumentException | IllegalStateException e) {
            MoreCosmetics.catchThrowable((Throwable)e);
        }
    }

    private void filterSettings(JsonWriter out, SettingOverlay value) throws IOException {
        for (Field f : SettingOverlay.class.getFields()) {
            try {
                Object o = f.get(value);
                if (o == null) continue;
                if (o instanceof Number) {
                    float n = ((Number)o).floatValue();
                    if (f.isAnnotationPresent(DefaultValue.class) && f.getAnnotation(DefaultValue.class).value() == n) continue;
                    out.name(f.getName());
                    out.value((Number)o);
                    continue;
                }
                if (o instanceof Boolean) {
                    boolean bool = (Boolean)o;
                    if (f.isAnnotationPresent(DefaultValue.class) && f.getAnnotation(DefaultValue.class).value() == (float)(bool ? 1 : 0)) continue;
                    out.name(f.getName());
                    out.value(bool);
                    continue;
                }
                if (!(o instanceof String) || ((String)o).isEmpty()) continue;
                out.name(f.getName());
                out.value((String)o);
            }
            catch (IllegalAccessException e) {
                MoreCosmetics.catchThrowable((Throwable)e);
            }
        }
    }

    public ModelData read(JsonReader in) throws IOException {
        return null;
    }
}

