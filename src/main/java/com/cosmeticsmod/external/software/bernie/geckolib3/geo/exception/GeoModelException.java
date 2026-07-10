/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.exception.GeoModelException
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.geo.exception;

public class GeoModelException
extends RuntimeException {
    public GeoModelException(String fileLocation, String message) {
        super(fileLocation + ": " + message);
    }

    public GeoModelException(String fileLocation, String message, Throwable cause) {
        super(fileLocation + ": " + message, cause);
    }
}

