/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.Vec3I
 *  com.cosmeticsmod.morecosmetics.utils.MathUtils
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built;

import com.cosmeticsmod.morecosmetics.utils.MathUtils;

public class Vec3I
implements Comparable<Vec3I> {
    public static final Vec3I NULL_VECTOR = new Vec3I(0, 0, 0);
    private final int x;
    private final int y;
    private final int z;

    public Vec3I(int xIn, int yIn, int zIn) {
        this.x = xIn;
        this.y = yIn;
        this.z = zIn;
    }

    public Vec3I(double xIn, double yIn, double zIn) {
        this(MathUtils.floorDouble((double)xIn), MathUtils.floorDouble((double)yIn), MathUtils.floorDouble((double)zIn));
    }

    public boolean equals(Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (!(p_equals_1_ instanceof Vec3I)) {
            return false;
        }
        Vec3I vec3i = (Vec3I)p_equals_1_;
        return this.getX() != vec3i.getX() ? false : (this.getY() != vec3i.getY() ? false : this.getZ() == vec3i.getZ());
    }

    public int hashCode() {
        return (this.getY() + this.getZ() * 31) * 31 + this.getX();
    }

    @Override
    public int compareTo(Vec3I p_compareTo_1_) {
        return this.getY() == p_compareTo_1_.getY() ? (this.getZ() == p_compareTo_1_.getZ() ? this.getX() - p_compareTo_1_.getX() : this.getZ() - p_compareTo_1_.getZ()) : this.getY() - p_compareTo_1_.getY();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public Vec3I crossProduct(Vec3I vec) {
        return new Vec3I(this.getY() * vec.getZ() - this.getZ() * vec.getY(), this.getZ() * vec.getX() - this.getX() * vec.getZ(), this.getX() * vec.getY() - this.getY() * vec.getX());
    }

    public double distanceSq(double toX, double toY, double toZ) {
        double d0 = (double)this.getX() - toX;
        double d1 = (double)this.getY() - toY;
        double d2 = (double)this.getZ() - toZ;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public double distanceSqToCenter(double xIn, double yIn, double zIn) {
        double d0 = (double)this.getX() + 0.5 - xIn;
        double d1 = (double)this.getY() + 0.5 - yIn;
        double d2 = (double)this.getZ() + 0.5 - zIn;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public double distanceSq(Vec3I to) {
        return this.distanceSq((double)to.getX(), (double)to.getY(), (double)to.getZ());
    }

    public String toString() {
        return this.getX() + "," + this.getY() + "," + this.getZ();
    }
}

