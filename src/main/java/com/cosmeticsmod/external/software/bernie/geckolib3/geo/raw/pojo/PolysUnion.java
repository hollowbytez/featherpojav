/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.PolysEnum
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.PolysUnion
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo;

import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.PolysEnum;

public class PolysUnion {
    public double[][][] doubleArrayArrayArrayValue;
    public PolysEnum enumValue;

    public static class Deserializer implements com.google.gson.JsonDeserializer<PolysUnion> {
        @Override
        public PolysUnion deserialize(com.google.gson.JsonElement json, java.lang.reflect.Type typeOfT, com.google.gson.JsonDeserializationContext context) throws com.google.gson.JsonParseException {
            PolysUnion val = new PolysUnion();
            if (json.isJsonArray()) {
                val.doubleArrayArrayArrayValue = context.deserialize(json, double[][][].class);
            } else if (json.isJsonPrimitive()) {
                val.enumValue = context.deserialize(json, PolysEnum.class);
            }
            return val;
        }
    }

}

