/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.Vec3D
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.Vec3F
 *  com.cosmeticsmod.external.software.bernie.geckolib3.util.VectorUtils
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.Validate
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.util;

import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.Vec3D;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.Vec3F;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;

public class VectorUtils {
    public static Vec3D fromArray(double[] array) {
        Validate.validIndex((Object[])ArrayUtils.toObject((double[])array), (int)2);
        return new Vec3D(array[0], array[1], array[2]);
    }

    public static Vec3F fromArray(float[] array) {
        Validate.validIndex((Object[])ArrayUtils.toObject((float[])array), (int)2);
        return new Vec3F(array[0], array[1], array[2]);
    }

    public static Vec3F convertDoubleToFloat(Vec3D vector) {
        return new Vec3F((float)vector.x, (float)vector.y, (float)vector.z);
    }

    public static Vec3F convertFloatToDouble(Vec3F vector) {
        return new Vec3F(vector.getX(), vector.getY(), vector.getZ());
    }
}

