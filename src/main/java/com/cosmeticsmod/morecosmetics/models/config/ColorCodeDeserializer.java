/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.models.config.ColorCodeDeserializer
 *  com.cosmeticsmod.morecosmetics.utils.Utils
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonParseException
 */
package com.cosmeticsmod.morecosmetics.models.config;

import com.cosmeticsmod.morecosmetics.utils.Utils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

public class ColorCodeDeserializer
implements JsonDeserializer<String> {
    public String deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        return Utils.replaceColorCodes((String)json.getAsString());
    }
}

