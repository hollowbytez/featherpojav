/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.com.eliotlash.mclib.utils.JsonUtils
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.stream.JsonWriter
 */
package com.cosmeticsmod.external.com.eliotlash.mclib.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;
import java.io.StringWriter;
import java.io.Writer;

public class JsonUtils {
    public static String jsonToPretty(JsonElement element) {
        StringWriter writer = new StringWriter();
        JsonWriter jsonWriter = new JsonWriter((Writer)writer);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        jsonWriter.setIndent("    ");
        gson.toJson(element, jsonWriter);
        return writer.toString();
    }
}

