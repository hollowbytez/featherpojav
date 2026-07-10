/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.UvFaces
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.UvUnion
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo;

import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.UvFaces;

public class UvUnion {
    public double[] boxUVCoords;
    public UvFaces faceUV;
    public boolean isBoxUV;

    public static class Deserializer implements com.google.gson.JsonDeserializer<UvUnion> {
        @Override
        public UvUnion deserialize(com.google.gson.JsonElement json, java.lang.reflect.Type typeOfT, com.google.gson.JsonDeserializationContext context) throws com.google.gson.JsonParseException {
            UvUnion union = new UvUnion();
            if (json.isJsonArray()) {
                union.isBoxUV = true;
                union.boxUVCoords = context.deserialize(json, double[].class);
            } else if (json.isJsonObject()) {
                union.isBoxUV = false;
                union.faceUV = context.deserialize(json, UvFaces.class);
            }
            return union;
        }
    }

}

