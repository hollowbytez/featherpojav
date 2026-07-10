/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.LocatorClass
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.LocatorValue
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo;

import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.LocatorClass;

public class LocatorValue {
    public LocatorClass locatorClassValue;
    public double[] doubleArrayValue;

    public static class Deserializer implements com.google.gson.JsonDeserializer<LocatorValue> {
        @Override
        public LocatorValue deserialize(com.google.gson.JsonElement json, java.lang.reflect.Type typeOfT, com.google.gson.JsonDeserializationContext context) throws com.google.gson.JsonParseException {
            LocatorValue val = new LocatorValue();
            if (json.isJsonArray()) {
                val.doubleArrayValue = context.deserialize(json, double[].class);
            } else if (json.isJsonObject()) {
                val.locatorClassValue = context.deserialize(json, LocatorClass.class);
            }
            return val;
        }
    }

}

