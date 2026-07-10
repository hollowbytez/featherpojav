/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.PolysEnum
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo;

import java.io.IOException;

public enum PolysEnum {
    QUAD_LIST,
    TRI_LIST;


    public static PolysEnum forValue(String value) throws IOException {
        if (value.equals("quad_list")) {
            return QUAD_LIST;
        }
        if (value.equals("tri_list")) {
            return TRI_LIST;
        }
        throw new IOException("Cannot deserialize PolysEnum");
    }
}

